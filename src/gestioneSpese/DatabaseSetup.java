package gestioneSpese;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;


public class DatabaseSetup {
    
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "gestione_spese";
    private static String user;
    private static String password;

    public static void main(String[] args) {
        user = args[0];
        password = args[1];
        createDatabase();
        createTables();
        inserisciCinqueCategorie();
    }

    private static void createDatabase() {
        String createDB = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createDB);
            System.out.println("Database Creato Con Successo!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() {
        String creaSpeseTable = "CREATE TABLE IF NOT EXISTS spese( "
                              + " id_spesa INT AUTO_INCREMENT PRIMARY KEY,"
                              + " nome VARCHAR(50) NOT NULL, "
                              + " importo DECIMAL(10, 2) NOT NULL," 
                              + " data DATE NOT NULL, "
                              + " id_categoria INT)";

        String creaCategorieTable = "CREATE TABLE IF NOT EXISTS categorie( "
                                  + " id INT AUTO_INCREMENT PRIMARY KEY,"
                                  + " nome VARCHAR(50) NOT NULL, "
                                  + " descrizione TEXT) ";

        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(creaSpeseTable);
            stmt.executeUpdate(creaCategorieTable);
            System.out.println("Tabelle Create Con Successo!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserisciCinqueCategorie() {
        List<String> categorieDaInserire = Arrays.asList(
            "Alimentari", "Trasporti", "Bollette", "Svago", "Abbigliamento"
        );

        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, user, password);
             Statement stmt = conn.createStatement()) {
            for (String categoria : categorieDaInserire) {
                // Verifica se la categoria esiste già nella tabella
                String controllaCategorie = "SELECT id FROM categorie WHERE nome = '" + categoria + "'";
                if (!stmt.executeQuery(controllaCategorie).next()) {
                    // Inserisce la categoria solo se non esiste già
                    String inserisciCategorie = "INSERT INTO categorie (nome, descrizione) "
                            + "VALUES ('" + categoria + "', 'Spese destinate a " + categoria + "')";
                    stmt.executeUpdate(inserisciCategorie);
                   // System.out.println("Categoria '" + categoria + "' inserita con successo.");
                } else {
                    //System.out.println("Categoria '" + categoria + "' già presente, saltata l'inserimento.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
