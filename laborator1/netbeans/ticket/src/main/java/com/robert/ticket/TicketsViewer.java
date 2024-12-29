/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.robert.ticket;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import static com.robert.ticket.QrRead.readQR;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;



/**
 *
 * @author DELL_PC1
 */
public class TicketsViewer extends JFrame {

    private JPanel mainPanel;
    private JTextArea textArea;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private JLabel imageLabel;
    private JButton pdfDecode;
    private JButton pdfCreate;
    private JButton pdfClose;
    private String filePath;
    private  PdfCreator test;
    private String topPdfText;
    private String bottomPdfText;
    
    
    public TicketsViewer(String folderPath) {
        super("File List Image Display");
        topPdfText="Acest text apare pe antetul PDF";
        bottomPdfText="Acest text apare pe subsolul PDF";
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
      
        listModel = new DefaultListModel<String>();
        fileList = new JList<String>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        String selectedFileName = null;
                
        fileList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                   // System.out.println("SELECTED");
                    String selectedFileName = fileList.getSelectedValue();
                    if (selectedFileName != null) {
                        File selectedFile = new File(folderPath + File.separator + selectedFileName);
                        Image image = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
                        filePath = selectedFile.getAbsolutePath();
                        imageLabel.setIcon(new ImageIcon(image));
                        pdfCreate.setEnabled(false); 
                         textArea.setText(null);
                    }
                }
            }
        });
           
          
           
           
            
            
            //generate decode Button
            pdfDecode=new JButton("Decode QR");  
            pdfDecode.setBounds(450,300,150,50);  
            //pdfDecode.setSize(100,50);  
            pdfDecode.setLayout(null);  
            pdfDecode.addActionListener(new ActionListener() {    
            
                @Override
                public void actionPerformed (ActionEvent e) { 
                    // Path where the QR code is saved
                     if (filePath != null) {              
                   // String filePath = "d:\\Tickets\\qr\\ticket_ff_fff.png";
                    // Encoding charset
                   String charset = "UTF-8";
                   Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType,
                                      ErrorCorrectionLevel>();

                    hashMap.put(EncodeHintType.ERROR_CORRECTION,
                                ErrorCorrectionLevel.L);
                  
                    try {
                        // System.out.println("  ---  ");
                        textArea.setText(null);
                         String decode = QrRead.readQR(filePath, charset, hashMap);
                                  System.out.println(decode);
                          textArea.append(decode);
                           pdfCreate.setEnabled(true);     
                    } catch (IOException ex) {
                        Logger.getLogger(TicketsViewer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotFoundException ex) {
                        Logger.getLogger(TicketsViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                               System.out.println("Close window");    
                         
                   }
                     else{
                            JOptionPane.showMessageDialog(null,"No QR selected ","Info Validation",1);
                     }}
                });  
            mainPanel.add(pdfDecode);
            
            
            
            //generate PDF Button
            pdfCreate=new JButton("Create PDF");  
            pdfCreate.setBounds(280,300,150,50);  
            //pdfDecode.setSize(100,50);  
            pdfCreate.setLayout(null);  
            pdfCreate.addActionListener(new ActionListener() 
                    {    
                    @Override
                    public void actionPerformed (ActionEvent e) 
                            { 
                                 // Path where the QR code is saved
                                if (filePath != null) 
                                {              
                                     try {
                                        System.out.println("  ---  ");

                                        String myStr = textArea.getText();
                                        //System.out.println(myStr);
                                        // 12 lungimea First Name: 11 lungimea Last Name: 2 lungimea | 
                                        int step1= myStr.indexOf("First Name:")+12;
                                        int step2 = myStr.indexOf("Last Name:")-1;
                                        int step3 = myStr.indexOf("Last Name:")+11;
                                        int step4 = myStr.indexOf(" Email:")-2;
                                       // System.out.println(step1);
                                       // System.out.println(step2);
                                        String varFirstName=myStr.substring(step1, step2);
                                        String varLastName=myStr.substring(step3, step4);
                                       // System.out.println(varFirstName);
                                       // System.out.println(varLastName);
                                       // String pdfPath = "d:\\pdf_image_serban_robert.pdf";
                                       String  imagePath="d:\\Tickets\\ticket_"+varFirstName+"_"+varLastName+".png";
                                       String  pdfPath="d:\\Tickets\\pdf\\pdf_"+varFirstName+"_"+varLastName+".pdf";
                                       test =new PdfCreator();
                                       PdfCreator.DoPdfWrite(imagePath, pdfPath,topPdfText, bottomPdfText );
                                        
                                        //last
                                         JOptionPane.showMessageDialog(null,"Pdf Ok  ","Info Validation",1);
                                        dispose();
                                         //System.out.println();
                                       
                                         /*
                                         String decode = QrRead.readQR(filePath, charset, hashMap);
                                                  System.out.println(decode);
                                          textArea.append(decode);
                                          */


                                        } catch (Exception ex) {
                                                   System.out.println("Close window");    
                                        }
                                   }
                                    else {
                                        JOptionPane.showMessageDialog(null,"Show Name Ok","Info Validation",1);
                                        }
                                }
                            
                     }); 
                        
            pdfCreate.setEnabled(false);            
            mainPanel.add(pdfCreate);
            
            
             //generate PDF Close button
            pdfClose=new JButton("Close Window");  
            pdfClose.setBounds(620,300,150,50);  
            //pdfDecode.setSize(100,50);  
            pdfClose.setLayout(null);  
            pdfClose.setVisible(true);   
           
            
            
            pdfClose.addActionListener(new ActionListener() {    
                public void actionPerformed (ActionEvent e) { 
              dispose();
                   System.out.println("Close window");    
                    }    
                });  
            
               mainPanel.add(pdfClose);
            
               
               
               
        JScrollPane listScrollPane = new JScrollPane(fileList);
        mainPanel.add(listScrollPane, BorderLayout.WEST);

        
          // Define text area
            textArea = new JTextArea();
            textArea.setBounds(180,10,600,50);
            textArea.append("");
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true); 
            //textArea.setRows(20);
           // textArea.setColumns(5);
          //  textArea.setVisible(true);
            mainPanel.add(textArea);
           
            imageLabel = new JLabel();
            imageLabel.setLocation(100, 100);
           // mainPanel.add(imageLabel, BorderLayout.CENTER);
            mainPanel.add(imageLabel);
     

        
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                listModel.addElement(file.getName());
            }
        }

        getContentPane().add(mainPanel);
        setSize(900, 400);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    
    
    public static void main(String[] args)  {
          
        String folderPath = "D:\\Tickets\\";
        new TicketsViewer(folderPath);
    }
}


