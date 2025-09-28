package com.tanque_isaac.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TankRepository {

    public static void listAll() {
        String sql = "SELECT id, name, type, hp, weapon FROM tanks ORDER BY id";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("---- TANQUES CADASTRADOS ----");
            while (rs.next()) {
                System.out.printf("#%d | %s | Tipo=%s | HP=%d | Arma=%s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("hp"),
                        rs.getString("weapon"));
            }
        } catch (SQLException e) {
            System.err.println("[DB] Erro ao listar tanques: " + e.getMessage());
        }
    }

    // ===== NOVO: exporta o conteúdo da tabela tanks para um arquivo CSV =====
    public static int exportToCsv(String outputPath) {
        String sql = "SELECT id, name, type, hp, weapon FROM tanks ORDER BY id";
        int linhas = 0;

        // Garante que a pasta de destino exista (ex.: "export/tanks_export.csv")
        try {
            Path parent = Path.of(outputPath).toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            System.err.println("[CSV] Não foi possível criar pastas: " + e.getMessage());
            return 0;
        }

        try (
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, false))
        ) {
            // 1) Cabeçalho
            bw.write("id,name,type,hp,weapon");
            bw.newLine();

            // 2) Linhas de dados
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                int hp = rs.getInt("hp");
                String weapon = rs.getString("weapon");

                // Observação: aqui estamos assumindo que não há vírgulas dentro dos campos.
                // Se um dia precisar escapar vírgulas/aspas, me avisa que adapto.
                String linha = id + "," + name + "," + type + "," + hp + "," + weapon;
                bw.write(linha);
                bw.newLine();
                linhas++;
            }

            bw.flush();
            System.out.println("[CSV] Exportação concluída em: " + Path.of(outputPath).toAbsolutePath());
            System.out.println("[CSV] Linhas exportadas (sem contar cabeçalho): " + linhas);
            return linhas;

        } catch (SQLException e) {
            System.err.println("[DB] Erro ao consultar tanques: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[CSV] Erro ao escrever arquivo: " + e.getMessage());
        }
        return 0;
    }
}
