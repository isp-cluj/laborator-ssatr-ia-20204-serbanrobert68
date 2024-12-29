package ro.utcluj.ssatr.airticketreservationapp.service;

import ro.utcluj.ssatr.airticketreservationapp.model.FlightInformation;
import ro.utcluj.ssatr.airticketreservationapp.model.FlightReservation;
import ro.utcluj.ssatr.airticketreservationapp.repository.DBAccess;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ro.utcluj.ssatr.airticketreservationapp.model.User;

public class FlightReservationService {
    private DBAccess connection;
    private FlightInformationTableModel flightInformationTableModel;
    private FlightReservationTableModel flightReservationTableModel;
    private UsersTableModel userTableModel;
    private List<FlightInformation> list = new ArrayList<>();
    private List<User> userlist = new ArrayList<>();
    private boolean tryuser;
    
    
    public FlightReservationService() {
        try {
            connection = new DBAccess();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        flightInformationTableModel  = new FlightInformationTableModel(connection);
        flightReservationTableModel  = new FlightReservationTableModel(connection);
        userTableModel = new UsersTableModel(connection);
        
        
    }

    public FlightInformationTableModel getFlightInformationTableModel() {
        return flightInformationTableModel;
    }

      public FlightReservationTableModel getFlightReservationTableModel() {
        return flightReservationTableModel;
    }
    
    public UsersTableModel getUserTableModel() {
        return userTableModel;
    }
    
    public void addUser(User u){
       this.getUserTableModel().updateTable();
       connection.insertUser(u);
       
    }
    
    
    public void insertNewUser(String loginName, String firstName, String lastName){
      
            //....
            User fuser = new User(loginName,firstName,lastName);
            connection.insertUser(fuser);
            userTableModel.updateTable();
             JOptionPane.showMessageDialog(null,"New user add OK","Info Validation",1);
            /*
            try {
             } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
       
    }
    
    
    public void addNewFlight(String flightNumber, int noOfSeats, String departureDate){
        try {
            //....
            FlightInformation f = new FlightInformation(flightNumber,noOfSeats,departureDate);
            connection.insertFlight(new FlightInformation(flightNumber,noOfSeats,departureDate));
            list.add(new FlightInformation(flightNumber,noOfSeats,departureDate));
            flightInformationTableModel.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    public boolean makeReservation(String flightNumber, int noOfTikets ){
        try {

            FlightReservation reservation = new FlightReservation(0,flightNumber,noOfTikets);
            System.out.println("SEARCH FLIGHT");
            FlightInformation f = connection.findFlight(flightNumber);
            System.out.println(f);
            if(f!=null){
                if(f.getNumberOfSeats()>=noOfTikets){
                    //........UPDATE ROW IN DATABASE
                    connection.makeReservation(reservation);
                    return true;
                }else{
                    System.out.println("Nomber of seats not available");
                }
            }else{
                System.out.println("No flight number found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
    
    public void cancelReservation(){
        //TODO implement cancel reservation logic 
    }
    
    public String getAllFlights(){
      
        try {
            list = connection.findAll();
        } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
         String s = "";
        // System.out.println(list.isEmpty());
        for(  FlightInformation fi: list){
            
            
            s=s+fi.toString()+"\n";
              System.out.println("**************");
              System.out.println(s);
        }

        s+="-------------------";
        return s;
    }
    public String getAllUsers(){
      
        try {
            userlist = connection.findAllUsers();
        } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        String s = "";
        //System.out.println(userlist.get(0));
        for(  User us: userlist){
            
            
            s=s+us.toString()+"\n";
              System.out.println("**************");
              //System.out.println(s);
        }

        s+="-------------------";
        return s;
    }
    public boolean getLogin(String login){
       
        try {
           tryuser = connection.verifyUser(login);
        } catch (SQLException ex) {
            Logger.getLogger(FlightReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tryuser;
    }

}
