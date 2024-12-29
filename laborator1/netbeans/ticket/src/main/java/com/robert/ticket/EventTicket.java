package com.robert.ticket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 *
 * @author DELL_PC1
 */
public class EventTicket {
    private String eventName;
    private LocalDateTime eventDate;
    private String ticketType;
    private double ticketPrice;
    private String dateShow;
    private String pin;

    public EventTicket(String eventName, LocalDateTime eventDate, String ticketType, double ticketPrice) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.ticketType = ticketType;
        this.ticketPrice = ticketPrice;
        //this.pin =pin;
    }
    
    
   @Override
    public String toString() {
        return "Event: " + eventName + " | Date: " + eventDate + " | Ticket Type: " + ticketType + " | Price: $" + ticketPrice + " | date Show: " + dateShow;
    }

}
