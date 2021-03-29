/* @Author Luka Alhonen, luka.alhonen@protonmail.com
 *
 * This class contains all the client side stuff for the login and user creation pages
 *
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginPage {
    private PrintWriter writer;
    private BufferedReader reader;
    private Boolean barry = null;
    private String userCreation;

    public LoginPage(PrintWriter w, BufferedReader r){
        writer = w;
        reader = r;
        Thread t = new Thread(new threadJob());
        t.start();
    }

    public class threadJob implements Runnable{
        public void run(){
            String line;
            try{
                while((line = reader.readLine()) != null){
                    if(line.equals("0")){
                        barry = true;
                    } else if(line.equals("1")){
                        barry = false;
                    } else if(line.equals("00")){
                        userCreation = line;
                    } else if(line.equals("01")){
                        userCreation = line;
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String encrypt(String line){
        String lineHex = "";

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            byte[] hashbytes = digest.digest(line.getBytes(StandardCharsets.UTF_8));
            lineHex = bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return lineHex;
    }

    public static String bytesToHex(byte[] hash){
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void logIn() {
        JFrame frame2 = new JFrame();
        JLabel UsrName =  new JLabel("Username:");
        JLabel PassWrd = new JLabel("Password:");
        JPasswordField passInput = new JPasswordField();
        JTextField usrInput = new JTextField();
        JButton login = new JButton("login");
        JButton newUser = new JButton("Sign up");

        UsrName.setBounds(50,110,100,25);
        PassWrd.setBounds(50, 140, 100, 25);
        passInput.setBounds(155, 140, 175, 25);
        usrInput.setBounds(155, 110, 175, 25);
        login.setBounds(155, 170, 75, 25);
        login.addActionListener(actionEvent -> {
            String password = String.valueOf(passInput.getPassword());
            String username = usrInput.getText();
            String usrShaHex = encrypt(username);
            String passShaHex = encrypt(password);
            String logInDetails = usrShaHex + "\n" + passShaHex;
            writer.println(logInDetails);
            writer.flush();
            while(barry == null){
            }
            if(barry){
                frame2.dispose();
                //getIP();
            } else{
                barry = null;
            }
        });

        newUser.setBounds(240, 170, 90, 25);
        newUser.addActionListener(actionEvent -> {
            frame2.dispose();
            userCreation();
        });

        frame2.add(newUser);
        frame2.add(login);
        frame2.add(UsrName);
        frame2.add(PassWrd);
        frame2.add(passInput);
        frame2.add(usrInput);
        frame2.setLayout(null);
        frame2.setSize(400, 350);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void userCreation(){
        // Setting up the GUI for user creation
        JFrame frame3 = new JFrame();
        JLabel UsrName =  new JLabel("Username:");
        JLabel PassWrd = new JLabel("Password:");
        JPasswordField passInput = new JPasswordField();
        JTextField usrInput = new JTextField();
        JButton login = new JButton("Create user");

        UsrName.setBounds(50,110,100,25);
        PassWrd.setBounds(50, 140, 100, 25);
        passInput.setBounds(155, 140, 175, 25);
        usrInput.setBounds(155, 110, 175, 25);
        login.setBounds(155, 170, 120, 25);
        login.addActionListener(actionEvent -> {
            String password = String.valueOf(passInput.getPassword());
            String username = usrInput.getText();
            String usrShaHex = encrypt(username);
            String passShaHex = encrypt(password);
            String logInDetails = "new\n" + usrShaHex + "\n" + passShaHex;
            writer.println(logInDetails);
            writer.flush();
            while(true){
                if(userCreation == null){
                } else if(userCreation.equals("00")){
                    System.out.println("User successfully created");
                    frame3.dispose();
                    logIn();
                    break;
                } else{
                    System.out.println("User already exists");
                }
            }
        });

        /*

         */
        frame3.add(login);
        frame3.add(UsrName);
        frame3.add(PassWrd);
        frame3.add(passInput);
        frame3.add(usrInput);
        frame3.setLayout(null);
        frame3.setSize(400, 350);
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
