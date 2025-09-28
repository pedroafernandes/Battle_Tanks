package com.tanque_isaac.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CsvImporter {

    public static int importTanksFromCsv(String csvPath) {
        // comando SQL para inserir os dados
        String insertSql = "INSERT INTO tanks (name, type, hp, weapon) VALUES (?, ?, ?, ?)";
        int count = 0; // contador de quantos foram inseridos

        try (
            // abrir o CSV
            BufferedReader br = new BufferedReader(new FileReader(csvPath));
            // abrir a conexão com o banco
            Connection conn = Database.getConnection();
            // preparar o comando SQL
            PreparedStatement ps = conn.prepareStatement(insertSql)
        ) {
            conn.setAutoCommit(false); // só salva no final (mais rápido e seguro)

            String line = br.readLine(); // primeira linha (cabeçalho), descartamos
            if (line == null) {
                System.out.println("[CSV] Arquivo vazio.");
                return 0;
            }

            // ler linha por linha até o fim do arquivo
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue; // pular linhas em branco

                // separar os valores por vírgula
                String[] parts = line.split(",", -1);
                if (parts.length < 4) {
                    System.out.println("[CSV] Linha inválida: " + line);
                    continue;
                }

                // pegar cada valor
                String name = parts[0].trim();
                String type = parts[1].trim();
                String hpStr = parts[2].trim();
                String weapon = parts[3].trim();

                // transformar hp (texto) em número
                int hp;
                try {
                    hp = Integer.parseInt(hpStr);
                } catch (NumberFormatException nfe) {
                    System.out.println("[CSV] HP inválido: " + hpStr);
                    continue;
                }

                // preencher o comando SQL
                ps.setString(1, name);
                ps.setString(2, type);
                ps.setInt(3, hp);
                ps.setString(4, weapon);
                ps.addBatch(); // adiciona no "pacote"

                count++;
            }

            ps.executeBatch(); // executa todos os INSERTs juntos
            conn.commit();     // confirma a transação
            System.out.println("[CSV] Importação concluída. Registros inseridos: " + count);
            return count;

        } catch (IOException e) {
            System.err.println("[CSV] Erro de leitura: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[CSV] Erro de banco: " + e.getMessage());
        }
        return 0;
    }
}
