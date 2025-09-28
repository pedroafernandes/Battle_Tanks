package com.tanque_isaac.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:arena.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        String createTanks = """
            CREATE TABLE IF NOT EXISTS tanks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                type TEXT NOT NULL,
                hp INTEGER NOT NULL,
                weapon TEXT NOT NULL
            );
        """;
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(createTanks);
            System.out.println("[DB] Tabelas verificadas/criadas.");
        } catch (SQLException e) {
            System.err.println("[DB] Erro ao inicializar: " + e.getMessage());
        }
    }
}
