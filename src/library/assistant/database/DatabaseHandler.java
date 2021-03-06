/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author Ambro
 */
public final class DatabaseHandler {
    
    private static DatabaseHandler handler;
    
    // This line stores the database in a folder called database. 
    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection connection = null;
    private static Statement statement = null; 

    // Constructor here. First we make the connection, now we have to make
    // The table. 
    public DatabaseHandler() {
        createConnection();
        setupBookTable();
    }
        
    void createConnection () {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void setupBookTable() {
        // The table for storing this data is going to be called BOOK.
        String TABLE_NAME = "BOOK";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Good to go.");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "     id varchar(200) primary key,\n" // varchar is string data type, and takes 200 char (in this case)
                        + "     title varchar(200,\n"
                        + "     author varchar(200),\n"
                        + "     publisher varchar(100),\n"
                        + "     isAvail boolean default true"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- Please set up database.");
        } finally {
        }        
    }
    
    // Used for executing a Query statement, which always returns some kind of Data.
    // Example, if you want the data.
    public ResultSet execQuery(String query) {
       ResultSet result;
       try {
           statement = connection.createStatement();
           result = statement.executeQuery(query);
       } catch (SQLException ex) {
           System.out.println("Exception at execQuery:dataHandler"+ ex.getLocalizedMessage());
           return null;
       } finally {           
       }
       return result;
    }
   
    // Doing some action such as inserting or creating table.
    // Does not return any values
    public boolean execAction (String qu) {
       try {
           statement = connection.createStatement();
           statement.execute(qu);
           return true;
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"ErrorL" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
           System.out.println("Exception at execQuery:dataHandler"+ ex.getLocalizedMessage());
           return false;
       } finally {
       }
    }
    
}
