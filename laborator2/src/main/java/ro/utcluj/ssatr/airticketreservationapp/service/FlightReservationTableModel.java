/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.service;

import java.sql.SQLException;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ro.utcluj.ssatr.airticketreservationapp.model.FlightReservation;
import ro.utcluj.ssatr.airticketreservationapp.repository.DBAccess;

/**
 *
 * @author DELL_PC1
 */
public class FlightReservationTableModel extends AbstractTableModel {
     private DBAccess connection;

    public FlightReservationTableModel(DBAccess connect) {
        this.connection = connect;
    }

    public void updateTable(){
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        try {
          //  System.out.println(connection.findAll().size());
            return connection.findAll().size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            List<FlightReservation> list = connection.findReservAll();
            FlightReservation f = list.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return f.getIdreservation();
                case 1:
                    return f.getFlightNumber();
                case 2:
                    return f.getNoOfTickets();
                default:
                    throw new IllegalArgumentException("Invalid column index");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
