package org.learning.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        // Parametri di connessione
        String url = "jdbc:mysql://localhost:3306/db_nations";
        String user = "root";
        String password = "root";

        // provo ad aprire una connessione con try-with-resources

        try (Connection connection = DriverManager.getConnection(url, user,password)) {
            // dentro al try ho la connection aperta
            // provo un sout per testare la connessione
            // System.out.println(connection.getCatalog());




        }catch (SQLException e) {
            System.out.println("Unable to connect");
            e.printStackTrace();
        }

    }
}
