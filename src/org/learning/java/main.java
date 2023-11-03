package org.learning.java;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // Parametri di connessione
        String url = "jdbc:mysql://localhost:3306/db_nations";
        String user = "root";
        String password = "root";

        // formatto data se serve
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:dd");

        // creo lo scanner
        Scanner scanner = new Scanner(System.in);

        // provo ad aprire una connessione con try-with-resources
        try (Connection connection = DriverManager.getConnection(url, user,password)) {

            // dentro al try ho la connection aperta


            // provo un sout per testare la connessione (update funziona, commento tutto)
            // System.out.println(connection.getCatalog());


            // mi prendo i dati in entrata come richiede milestone 3
            System.out.println("Insert filtering word");
            String userFilter = scanner.nextLine();

            // creo la query

            String query = "SELECT * "
                    + "FROM countries c "
                    + "JOIN regions r on c.region_id = r.region_id "
                    + "JOIN continents c2 on r.continent_id = c2.continent_id "
                    + "WHERE c.name LIKE ?";
            //la connessione prepara uno statement sql
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                //facciamo il binding dei parametri
                // il primo (e unico) paramentro è una stringa
                preparedStatement.setString(1, "%" + userFilter + "%");
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    //itero sul result set
                    while(resultSet.next()) {
                        // ad ogni iterazione resultSet si sposta e punta alla riga successiva
                        String countryID = resultSet.getString("country_id");
                        String countryName = resultSet.getString("c.name");
                        String regionName = resultSet.getString("r.name");
                        String continentName = resultSet.getString("c2.name");

                        System.out.println("ID: "+countryID+ "\nCountry: "+countryName + "\nRegion: " +regionName + "\nContinent: " + continentName );
                        System.out.println("************************************************************************************");
                    }
                }catch (SQLException e) {
                    System.out.println("unable to execute query");
                    e.printStackTrace();
                }
            }catch (SQLException e) {
                System.out.println("unable to prepare statement");
                e.printStackTrace();
            }

            //chiedo dati in entrata per codice id
            System.out.println("Insert country ID");
            int userId = scanner.nextInt();
            scanner.nextLine();

            //creo la seconda query

            String query2 = "SELECT * "
                    + "FROM languages l "
                    + "JOIN country_languages cl on l.language_id = cl.language_id "
                    + "JOIN countries c on cl.country_id = c.country_id "
                    + "WHERE cl.country_id = ?";

            // La connessione prepara uno statement sql
            try(PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
                //facciamo il binding dei parametri
                // il primo (e unico) paramentro è un int
                preparedStatement.setInt(1,  userId);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    //itero sul result set
                    String nationName = "";
                    ArrayList<String> languages = new ArrayList<>();
                    while(resultSet.next()) {
                        // ad ogni iterazione resultSet si sposta e punta alla riga successiva
                        nationName = resultSet.getString("name");
                        languages.add(resultSet.getString("language"));

                    }
                    System.out.println("Country name: "+nationName + "\nLanguages: " + languages );
                }catch (SQLException e) {
                    System.out.println("unable to execute query");
                    e.printStackTrace();
                }
            }catch (SQLException e) {
                System.out.println("unable to prepare statement");
                e.printStackTrace();
            }

            //creo la terza query

            String query3 = "SELECT * "
                    + "FROM country_stats cs "
                    + "WHERE country_id = ?";

            // La connessione prepara uno statement sql
            try(PreparedStatement preparedStatement = connection.prepareStatement(query3)) {
                //facciamo il binding dei parametri
                // il primo (e unico) paramentro è un int
                preparedStatement.setInt(1,  userId);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    String year = "";
                    String population = "";
                    String gdp = "";
                    //itero sul result set
                    while(resultSet.next()) {
                        // ad ogni iterazione resultSet si sposta e punta alla riga successiva
                        year = resultSet.getString("year");
                        population = resultSet.getString("population");
                        gdp = resultSet.getString("gdp");


                    }

                    System.out.println("Most recent stats ");
                    System.out.println("Year: "+year + "\nPopulation: " + population + "\nGDP: " + gdp );
                }catch (SQLException e) {
                    System.out.println("unable to execute query");
                    e.printStackTrace();
                }
            }catch (SQLException e) {
                System.out.println("unable to prepare statement");
                e.printStackTrace();
            }











        }catch (SQLException e) {
            System.out.println("Unable to connect");
            e.printStackTrace();
        }

    }
}
