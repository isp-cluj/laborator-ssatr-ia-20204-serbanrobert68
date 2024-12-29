/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.model;

/**
 *
 * @author mihai
 */
public class User {
    private String loginId;
    private String firstname;
    private String lastname;
    
    public User(String loginId, String firstname, String lastname) {
        this.loginId = loginId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getFirstName() {
        return firstname;
    }
    public String getLastName() {
        return lastname;
    }
     @Override
    public String toString() {
        return "Login Name: " + loginId + "First Name:  " + firstname + "Last Name: " + lastname;
    }
}
