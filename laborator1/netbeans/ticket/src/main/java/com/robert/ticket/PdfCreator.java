/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.robert.ticket;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileNotFoundException;
import com.itextpdf.kernel.pdf.PdfWriter;

/**
 *
 * @author DELL_PC1
 */
public class PdfCreator {
      public static void DoPdfWrite(String imagePath, String pdfPath, String topText, String bottomText) throws Exception{
        // Define the PDF output file path
        //String pdfPath = "pdf_image_serban_robert.pdf";
        // Define the image file path
        //String imagePath = "d:\\Tickets\\ticket_Ioan_Popescu.png";  // Replace with your image path

        try {
               // Create a PdfWriter object
            PdfWriter writer = new PdfWriter(pdfPath);

            // Initialize the PDF document
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Create a document to work with layout
            Document document = new Document(pdfDoc);

            // Add a paragraph to the document
            document.add(new Paragraph(topText));
            System.out.println(imagePath);
            // Load the image
            ImageData imageData = ImageDataFactory.create(imagePath);
            Image image = new Image(imageData);

            // Optionally, scale the image to fit the page or set specific dimensions
            image.scaleToFit(200, 200);  // Example: scaling the image

            // Add the image to the document
            document.add(image);

            // Add more content (e.g., another paragraph)
            document.add(new Paragraph(bottomText));

            // Close the document
            document.close();

                    System.out.println("PDF created for event access: " + pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
