package com.tanque_isaac;

import java.util.Scanner;

import com.tanque_isaac.io.CsvImporter;
import com.tanque_isaac.io.Database;
import com.tanque_isaac.io.TankRepository;

public class Main {
    public static void main(String[] args) {
        Database.init();
        Scanner sc = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n=== MENU ARENA ===");
            System.out.println("1. Importar tanques de CSV");
            System.out.println("2. Listar tanques do banco");
            System.out.println("3. Agendar partida [TODO]");
            System.out.println("4. Executar partida [TODO]");
            System.out.println("5. Relatórios [TODO]");
            System.out.println("6. Exportar tanques para CSV");  // <- NOVO
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            while (!sc.hasNextInt()) { sc.next(); System.out.print("Escolha (número): "); }
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    String path = "src/main/resources/tanks.csv";
                    int inseridos = CsvImporter.importTanksFromCsv(path);
                    System.out.println("Tanques importados: " + inseridos);
                }
                case 2 -> TankRepository.listAll();
                case 3 -> System.out.println("[TODO] Agendar partida");
                case 4 -> System.out.println("[TODO] Executar partida");
                case 5 -> System.out.println("[TODO] Relatórios");
                case 6 -> {
                    String out = "export/tanks_export.csv"; // você pode mudar o caminho se quiser
                    int linhas = TankRepository.exportToCsv(out);
                    System.out.println("Linhas exportadas: " + linhas);
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        sc.close();
    }
}
