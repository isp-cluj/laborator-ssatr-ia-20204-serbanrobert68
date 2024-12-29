/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.repository;

import ro.utcluj.ssatr.airticketreservationapp.model.FlightInformation;
import ro.utcluj.ssatr.airticketreservationapp.model.FlightReservation;
import ro.utcluj.ssatr.airticketreservationapp.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mihai.hulea
 */
public class DBAccess {

    private Connection connection;

    public DBAccess() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                System.out.println("Connecting Microsoft SQL ...");
                //Connection connection = null;
                String dbURL = "jdbc:sqlserver://localhost\\DELL_ROBERT:1433;databaseName=flights;encrypt=false";
                String user = "appcontrol";
                String pass = "baiamare2024";
                connection = DriverManager.getConnection(dbURL, user, pass);
                System.out.println("Connected!");    
        /*
        Class.forName("com.mysql.cj.jdbc.Driver");
        //conectare la baza de date            
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flights", "root", "root");
        */
    }
    
    public void insertFlight(FlightInformation f) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [flights].[dbo].[flights]([FLIGHTNUMBER], [NOOFSEATS], [DEPARTUREDATE]) VALUES(?,?,?)");
            ps.setString(1, f.getFlightNumber());
            ps.setInt(2, f.getNumberOfSeats());
            ps.setString(3, f.getDepartureDate());
            ps.executeUpdate();
            //          s.executeUpdate("INSERT INTO FLIGHTS(FLIGHTNUMBER, NOOFSEATS, DEPARTUREDATE) VALUES('" + f.getFlightNumber() + "'," + f.getNumberOfSeats() + ",'" + f.getDepartureDate() + "')");
        }
    }

    private void insertReservation(FlightReservation reservation) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO  [flights].[dbo].[reservations]([FLIGHTNUMBER], [NOOFTICKETS]) VALUES('" + reservation.getFlightNumber() + "'," + reservation.getNoOfTickets() + ")");
        }

    }
    
     private void insertNewUser(User newuser) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO  [flights].[dbo].[user] ( [LOGINNAME], [FIRSTNAME], [LASTNAME] VALUES('" + newuser.getLoginId() + "'," + newuser.getFirstName() + "'," + newuser.getLastName() + ")");
        }

    }

    public FlightInformation findFlight(String flightNumber) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT [FLIGHTNUMBER], [NOOFSEATS], [DEPARTUREDATE] FROM [flights].[dbo].[flights] WHERE FLIGHTNUMBER='" + flightNumber + "'");
            if (rs.next()) {
                return new FlightInformation(rs.getString("FLIGHTNUMBER"), rs.getInt("NOOFSEATS"), rs.getString("DEPARTUREDATE"));
            } else {
                return null;
            }
        }
    }

    public void deleteFlight(String flightNumber) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("DELETE FROM [flights].[dbo].[flights]  WHERE FLIGHTNUMBER='" + flightNumber + "'");
        }
    }
    
     public boolean verifyUser(String loginname) throws SQLException {
            boolean result=false;
        try ( Statement s = connection.createStatement()) {
             ResultSet rslogin = s.executeQuery("SELECT LOGINNAME FROM [flights].[dbo].[user] WHERE LOGINNAME ='" + loginname +"'" );
             int contor=0;
             while(rslogin.next()) {
                 contor++;
                }
              System.out.println(contor);
                   if(contor>0){
                       result= true;
                   }
                 }
       
        return result;
    }
    
    
    
    
    

    public List<FlightInformation> findAll() throws SQLException {
        try ( Statement stm1 = connection.createStatement()) {
            ArrayList<FlightInformation> listInfo = new ArrayList<>();

            ResultSet rs = stm1.executeQuery("SELECT [FLIGHTNUMBER], [NOOFSEATS], [DEPARTUREDATE] FROM [flights].[dbo].[flights]");
            while (rs.next()) {
                listInfo.add(new FlightInformation(rs.getString("FLIGHTNUMBER"), rs.getInt("NOOFSEATS"), rs.getString("DEPARTUREDATE") ));
                //System.out.println(rs.getString("FLIGHTNUMBER"));
            }
            return listInfo;
        }

    }
    
     public List<FlightReservation> findReservAll() throws SQLException {
        try ( Statement stm2 = connection.createStatement()) {
            ArrayList<FlightReservation> listReserv = new ArrayList<>();
            ResultSet rsReserv = stm2.executeQuery("SELECT [IDRESERVATION],  [FLIGHTNUMBER], [NOOFTICKETS] FROM [flights].[dbo].[reservations]");
            while (rsReserv.next()) {
                listReserv.add(new FlightReservation(rsReserv.getInt("IDRESERVATION"), rsReserv.getString("FLIGHTNUMBER"), rsReserv.getInt("NOOFTICKETS") ));
                //System.out.println(rs.getString("FLIGHTNUMBER")
            }
            return listReserv;
        }

    }
          
     public List<User> findAllUsers() throws SQLException {
        try ( Statement s = connection.createStatement()) {
            ArrayList<User> userlist = new ArrayList<>();
            ResultSet rs = s.executeQuery("SELECT LOGINNAME, FIRSTNAME, LASTNAME FROM [flights].[dbo].[user]");
            while (rs.next()) {
               userlist.add(new User(rs.getString("LOGINNAME"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME")));
              //System.out.println(userlist[0][0]);
               //System.out.println(rs.getString("LOGINNAME"));
            }
            return userlist;
        }

    }

    public void makeReservation(FlightReservation reservation) {
        Statement statement = null;
        ResultSet resultSet = null;
        System.out.println("PREPARE TO MAKE RESERVATION");
        try {
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            //read about SELECT FOR UPDATE gere https://www.cockroachlabs.com/blog/select-for-update/
         //   String lockQuery = "SELECT [FLIGHTNUMBER],[NOOFTICKETS] FROM [flights].[dbo].[reservations] FOR UPDATE";
               String lockQuery = "SELECT [FLIGHTNUMBER],[NOOFTICKETS] FROM [flights].[dbo].[reservations] UPDLOCK";
         
            resultSet = statement.executeQuery(lockQuery);

            // Perform your updates on the table here
            FlightInformation f = findFlight(reservation.getFlightNumber());
            if (f.getNumberOfSeats() - reservation.getNoOfTickets() >= 0) {
                updateSeats(reservation.getFlightNumber(), f.getNumberOfSeats() - reservation.getNoOfTickets());
                insertReservation(reservation);
                System.out.println("RESERVATION COMPLETE");
                connection.commit();
            }else{
                throw new SQLException("Error reservation, no seats available.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    // Handle rollback failure
                    ex.printStackTrace();
                }
            }
            // Handle the exception
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle result set closure failure
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle statement closure failure
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);

                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle connection closure failure
                }
            }
        }

    }
    

    public void cancelReservation(int reservationId) {

    }

    void updateSeats(String flightNumber, int noOfTikets) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("UPDATE [flights].[dbo].[flights] SET NOOFSEATS=" + noOfTikets + " WHERE FLIGHTNUMBER='" + flightNumber + "'");
        }
    }
    
    public void insertUser(User u) {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO [flights].[dbo].[user] (LOGINNAME, FIRSTNAME, LASTNAME) VALUES('" + u.getLoginId() + "','" + u.getFirstName() + "','" + u.getLastName() +"')");
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBAccess db = new DBAccess();
        System.out.println("Connection ok...");
        db.insertUser(new User("PrimulUser","Prenume User", "Nume User"));
        //db.insertFlight(new FlightInformation("SM0101", 140, "19-01-2023"));
        //db.insertUser("u1", "Demo User");
        //db.updateSeats("CJB01", 199);
    }


}
