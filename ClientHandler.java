/* @Author Luka Alhonen, luka.alhonen@protonmail.com
*
*  This class contains all necessary methods for dealing with clients
*  Every time a new client connects to the server an instance of this class will be created
*
*/

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket sock;
    BufferedReader reader;
    String username;
    String password;
    PrintWriter writer;
    // Modify this so it works on your system
    private static final File usrData = new File("/home/luka/IdeaProjects/LukasApp/usrData.txt");
    public ClientHandler(Socket clientSocket){
        try {
            sock = clientSocket;
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // Check if client is logging in or creating new user
                if(line.equals("new")){
                    // New user creation
                    username = reader.readLine();
                    password = reader.readLine();
                    if(addUser(username, password)){
                        // If new user was successfully created, send verification to client
                        writer.println("00");
                    } else{
                        // If new user couldn't be created, notify client
                        writer.println("01");
                    }
                    writer.flush();
                } else{
                    // Login
                    username = line;
                    password = reader.readLine();
                    if(verifyUser(username, password)){
                        // If correct login info
                        writer.println("0");
                        writer.flush();
                    } else{
                        // If incorrect login info
                        writer.println("1");
                        writer.flush();
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method for adding new unique user
    public static boolean addUser(String username, String password){
        try {
            usrData.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(usrData, true));
            // Check if user already exists
            if(verifyUser(username,password)){
                return false;
            }
            // Write new user login details to file
            writer.append(username + ":" + password+"\n");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    // Method for checking if user has been written to usrData.txt
    public static boolean verifyUser(String username, String password){
        try {
            usrData.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(usrData));
            String line = null;
            while((line = reader.readLine()) != null){
                String[] data = line.split(":");
                if(username.equals(data[0]) && password.equals(data[1])){
                    return true;
                }
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }
}
