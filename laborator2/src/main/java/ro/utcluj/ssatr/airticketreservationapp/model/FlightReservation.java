/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.model;

/**
 *
 * @author mihai
 */
public class FlightReservation {
    private String flightNumber;
    private int noOfTickets;
    private int idreservation;


    public FlightReservation(int idreservation, String flightNumber, int noOfTickets) {
     
        this.flightNumber = flightNumber;
        this.noOfTickets = noOfTickets;
        this.idreservation = idreservation;
    }

    public int getIdreservation() {
        return idreservation;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }
}
