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

    public static void networkSetup(){
        try {
            Socket sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Connected");
        } catch(IOException e){
            e.printStackTrace();
        }
    }


}
