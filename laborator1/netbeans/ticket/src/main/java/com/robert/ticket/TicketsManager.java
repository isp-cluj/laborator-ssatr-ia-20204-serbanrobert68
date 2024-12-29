/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.robert.ticket;

/**
 *
 * @author DELL_PC1
 */

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class TicketsManager {
     public PurchasedTicket createTicket(String firstName, String lastName, String email, String phoneNumber, String eventName, LocalDateTime eventDate, String ticketType, double ticketPrice, String purchaseDate,  String pin) {
        EventTicket eventTicket = new EventTicket(eventName, eventDate, ticketType, ticketPrice);
        PurchasedTicket ticketOwner = new PurchasedTicket(firstName, lastName, email, phoneNumber, eventTicket, purchaseDate,  pin);
        return ticketOwner;
    }
    
    public PurchasedTicket createTicket(EventTicket ticket, String firstName, String lastName, String email, String phoneNumber, String purchaseDate, String dateShow, String pin){
        PurchasedTicket ticketOwner = new PurchasedTicket(firstName, lastName, email, phoneNumber, ticket, purchaseDate, pin);
        return ticketOwner; 
    }
    
    public void generateElectronicTicket(PurchasedTicket ticketOwner){
        QRCodeWriter barcodeWriter = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = barcodeWriter.encode(ticketOwner.toString(), BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage bi =  MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bi, "png", new File("d:\\Tickets\\ticket_"+ticketOwner.getfirstName()+"_"+ticketOwner.getlastName()+".png"));
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public boolean checkinTicket(String imagePath, String phoneNumber) throws IOException, NotFoundException {
        File file = new File(imagePath);
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), getImageData(image));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        String qrText =  result.getText();
        System.out.println("Validating QR ticket: " + qrText);
        if(qrText.contains("Phone Number: "+phoneNumber+" |")){
            System.out.println("Ticket is valid!Checkin complete.");
            return true;
        }else{
            System.out.println("Ticket is not valid!Checkin failed.");
            return false;
        }
    }

    private int[] getImageData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] data = new int[width * height];
        image.getRGB(0, 0, width, height, data, 0, width);
        return data;
    }

}
