/* @Author Luka Alhonen, luka.alhonen@protonmail.com
*
* This is the main class for the client application. It sets upp a connection to the server
* and starts the login page, which snowballs onward.
*
*/

import java.io.*;
import java.net.Socket;

public class Client {
    private static PrintWriter writer;
    private static BufferedReader reader;

    public static void main(String[] args){
        networkSetup();
        LoginPage log = new LoginPage(writer, reader);
        log.logIn();
    }
    // Method for setting upp connection to server and initializing in and output streams
    public static void networkSetup(){
        try {
            Socket sock = new Socket("192.168.1.119", 20000);
            writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Connected");
        } catch(IOException e){
            e.printStackTrace();
        }
    }


}
