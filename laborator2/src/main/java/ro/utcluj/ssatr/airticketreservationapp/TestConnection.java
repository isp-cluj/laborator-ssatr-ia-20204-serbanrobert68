/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp;

import java.sql.Connection;
import java.sql.DriverManager;


public class TestConnection {
    public static void main(String[] args) {
        
        
        try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                System.out.println("Connecting Microsoft SQL ...");
                Connection conn = null;
                String dbURL = "jdbc:sqlserver://localhost\\DELL_ROBERT:1433;databaseName=flights;encrypt=false";
                String user = "appcontrol";
                String pass = "baiamare2024";
                conn = DriverManager.getConnection(dbURL, user, pass);
                System.out.println("Connected!");    
            /*
            //incarcare driver petru baza de date
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                System.out.println("Connecting...");
                //conectare la baza de date            
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_database","my_user","my_password");
                System.out.println("Connected!");    
                //inchide cnexiune la baza de date
                */
                conn.close();

                 
            
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
 
}