package com.robert.ticket;


import com.robert.ticket.EventTicket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL_PC1
 */
public class PurchasedTicket {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private EventTicket eventTicket;
    private String purchaseDate;
    private String dateShow;
    private String pin;

      public String getfirstName() {
        return firstName;
    }
    
      public String getlastName() {
        return lastName;
    }
    
       public String getemail() {
        return email;
    }
    
       
       public String getphoneNumber() {
        return phoneNumber;
    }
       
         public EventTicket geteventTicket(){
         return eventTicket;
         }
     public String getpurchaseDate() {
        return purchaseDate;
    }  
      public String getdateShow() {
        return dateShow;
    }  
     
      public String getpin() {
        return pin;
    }  
       
    public PurchasedTicket(String firstName, String lastName, String email, String phoneNumber, EventTicket eventTicket, String purchaseDate, String pin) {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.eventTicket = eventTicket;
        this.pin = pin;
    }
    @Override
    public String toString() {
        return "First Name: " + firstName + " Last Name: " + lastName + " | Email: " + email + " | Phone Number: " + phoneNumber + " | " + eventTicket + " | Purchase Date: " + dateShow + " | " + pin;
    }

}
