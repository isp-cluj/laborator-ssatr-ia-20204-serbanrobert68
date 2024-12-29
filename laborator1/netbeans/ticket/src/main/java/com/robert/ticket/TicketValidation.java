/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.robert.ticket;
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import javax.swing.JTextField;

/**
 *
 * @author DELL_PC1
 */
public class TicketValidation extends JFrame {

    private JPanel mainPanel;
    private JTextArea textArea;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private JLabel imageLabel;
    private JButton pdfDecode;
    private JButton ticketValid;
    private JButton pdfClose;
    private String filePath;
    private PdfCreator test;
    private String topPdfText;
    private String bottomPdfText;
    private String pyn;
    private String  decode;
    private JTextField textPyn;
    private JLabel pynText, statusText, dateText;
    private String pynConfirm;
    private String validstatus, datevalidstatus;
    private String textStatus,textDateStatus; 
            
    public TicketValidation(String folderPath) {
        super("File List Image Display");
        topPdfText="Acest text apare pe antetul PDF";
        bottomPdfText="Acest text apare pe subsolul PDF";
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
      
        listModel = new DefaultListModel<String>();
        fileList = new JList<String>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        String selectedFileName = null;
        
        // Label PYN
        pynText = new JLabel();
        pynText.setText("Enter pyn to confirm");
        pynText.setBounds(400,70,200, 20);
        mainPanel.add(pynText);
        
        
           // define text for verify pyn
          JTextField textPyn = new  JTextField();
          textPyn.setBounds(400,100,100,20);
          textPyn.setText(null);
          mainPanel.add(textPyn);
          
         // Label Status
        statusText = new JLabel();
        statusText.setText("Status QR confirmation");
        statusText.setBounds(400,120,200, 20);
        mainPanel.add(statusText);
        
          
           // define text for verify pyn
          JTextField textStatus = new  JTextField();
          textStatus.setBounds(400,140,200,20);
          textStatus.setText(null);
          mainPanel.add(textStatus);
          
           // Label Date
        dateText = new JLabel();
        dateText.setText("Date Status");
        dateText.setBounds(400,160,200, 20);
        mainPanel.add(dateText);
        
          // define text for verify pyn
          JTextField textDateStatus = new  JTextField();
          textDateStatus.setBounds(400,180,200,20);
          textDateStatus.setText(null);
          mainPanel.add(textDateStatus);
                
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
                        ticketValid.setEnabled(false); 
                         textArea.setText(null);
                         pynConfirm = textPyn.getText();
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
                   // Encoding charset
                   String charset = "UTF-8";
                   Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType,
                                      ErrorCorrectionLevel>();

                    hashMap.put(EncodeHintType.ERROR_CORRECTION,
                                ErrorCorrectionLevel.L);
                  
                    try {
                        // System.out.println("  ---  ");
                        textArea.setText(null);
                        decode = QrRead.readQR(filePath, charset, hashMap);
                                 // System.out.println(decode);
                          textArea.append(decode);
                          
                           
                           
                                        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                                   
                                        Connection conn = null;
                                        String dbURL = "jdbc:sqlserver://localhost\\DELL_ROBERT:1433;databaseName=ticket;encrypt=false";
                                        String user = "appcontrol";
                                        String pass = "baiamare2024";
                                        conn = DriverManager.getConnection(dbURL, user, pass);

                                        if (conn != null) {
                                      

                                         // interogare show
                                         String interogareShow = "SELECT stare, datavalid  FROM [ticket].[dbo].[client] WHERE  qr=\'" + decode+"\'";
                                         Statement stmtValid = conn.createStatement();
                                         ResultSet validShow = stmtValid.executeQuery(interogareShow);

                                            while(validShow.next()) {
                                               validstatus = validShow.getString("stare");
                                               datevalidstatus = validShow.getString("datavalid");
                                               textStatus.setText(validstatus);
                                               textDateStatus.setText(datevalidstatus);
                                               //System.out.println( validstatus);
                                               //System.out.println( datevalidstatus);
                                              }
                                            
                                            validShow.close();
                                            
                                      //System.out.println("*************");
                                     if(validstatus.equals("ACTIV")){
                                         ticketValid.setEnabled(true);     
                                     }else{
                                          JOptionPane.showMessageDialog(null,"Ticket Invalid","Info Validation",1);
                                     }
                                                                             
                                       validShow.close();
                                    }else{
                                        JOptionPane.showMessageDialog(null,"File Name Missing","Info Validation",1);
                                    }

                                                              
                    } catch (IOException ex) {
                        Logger.getLogger(TicketsViewer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotFoundException ex) {
                        Logger.getLogger(TicketsViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }    catch (SQLException ex) {
                             Logger.getLogger(TicketValidation.class.getName()).log(Level.SEVERE, null, ex);
                         }
                               System.out.println("Close window");    
                         
                   }
                     else{
                            JOptionPane.showMessageDialog(null,"No QR selected ","Info Validation",1);
                     }}
                });  
            mainPanel.add(pdfDecode);
            
            
            
            //generate PDF Button
            ticketValid=new JButton("Validate Ticket");  
            ticketValid.setBounds(280,300,150,50);  
            //pdfDecode.setSize(100,50);  
            ticketValid.setLayout(null);  
            ticketValid.addActionListener(new ActionListener() 
                    {    
                    @Override
                    public void actionPerformed (ActionEvent e) 
                      
                   { 
                     System.out.println("  ---  ");
                     System.out.println(pynConfirm);
                       if(pynConfirm!=null){
                                 // Path where the QR code is saved
                                if (filePath != null) 
                                {            
                                    
                                    
                                      
                                    try {
                                        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                                   
                                        Connection conn = null;
                                        String dbURL = "jdbc:sqlserver://localhost\\DELL_ROBERT:1433;databaseName=ticket;encrypt=false";
                                        String user = "appcontrol";
                                        String pass = "baiamare2024";
                                        conn = DriverManager.getConnection(dbURL, user, pass);

                                        if (conn != null) {
                                      

                                         // interogare show
                                         String interogareShow = "SELECT pyn  FROM [ticket].[dbo].[client] WHERE  qr=\'" + decode+"\'";
                                         Statement stmtShow = conn.createStatement();
                                         ResultSet rShow = stmtShow.executeQuery(interogareShow);

                                     while(rShow.next()) {
                                        pyn = rShow.getString("pyn");
                                        System.out.println( pyn );
                                       }
                                      stmtShow.close();
                                      //System.out.println("*************");
                                       pynConfirm = textPyn.getText();
                                       if(pynConfirm.equals(pyn)){
                                          
                                           
                                               if (conn != null) {

                                                    String insertQuery = "UPDATE [ticket].[dbo].[client] SET stare='INACTIV', datavalid='"+LocalDateTime.now().toString()+"' WHERE QR='"+decode+"'";
                                                    PreparedStatement invalidStmt = conn.prepareStatement(insertQuery);  
                                                    invalidStmt.executeUpdate(); 
                                                    System.out.println("Activate sub");
                                                    dispose();
                                               }else{
                                               JOptionPane.showMessageDialog(null,"DataBase update Fail","Info Validation",1);
                                               }
                                       }
                                       else{
                                            JOptionPane.showMessageDialog(null,"Enter proper PYN to validate","Info Validation",1);
                                             System.out.println("Activate fail");
                                       }
                                       
                                       
                                       
                                       //System.out.println(pynConfirm);
                                      
                                       rShow.close();
                                    }else{
                                        JOptionPane.showMessageDialog(null,"File Name Missing","Info Validation",1);
                                    }

                                    } catch (SQLException ex) {
                                        Logger.getLogger(TicketValidation.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                       
                                   }
                                    else {
                                        JOptionPane.showMessageDialog(null,"File Name Missing","Info Validation",1);
                                        }
                        }else{
                         JOptionPane.showMessageDialog(null,"Enter PYN to validate","Info Validation",1);
                        }            
                       }
                       
                     }); 
                        
            ticketValid.setEnabled(false);            
            mainPanel.add(ticketValid);
            
            
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
        
        // define label 
         
      
        
           
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

