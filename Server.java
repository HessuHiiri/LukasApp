/* @Author Luka Alhonen, luka.alhonen@protonmail.com
 *
 * The main class for the server
 * Creates connections to clients
 *
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        try {
            ServerSocket servSock = new ServerSocket(20000);
            while(true) {
                // Everytime a new connection is made a new thread and a new instance of ClientHandler is started
                Socket clientSock = servSock.accept();
                Thread t = new Thread(new ClientHandler(clientSock));
                t.start();
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }


}
