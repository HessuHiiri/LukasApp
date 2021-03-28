import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class Modules {
    public static void getIP(){
        JFrame frame;
        JLabel label;
        JButton button;
        JTextField txtField;
        JTextArea output;

        frame = new JFrame();
        txtField = new JTextField();
        output = new JTextArea();
        label = new JLabel("IP:");
        button = new JButton("Get Ip");

        txtField.setBounds(50, 50, 150, 20);
        label.setBounds(300, 30, 250, 20);
        output.setBounds(300, 50, 500, 400);
        button.setBounds(50, 100, 80, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String host = txtField.getText();
                    String ip = java.net.InetAddress.getByName(host).getHostAddress();
                    output.append(host + "\t" + ip + "\n");
                    txtField.setText("");
                } catch (UnknownHostException ex){
                    ex.printStackTrace();
                }
            }
        });
        output.setEditable(false);

        frame.add(output);
        frame.add(label);
        frame.add(button);
        frame.add(txtField);
        frame.setSize(1000, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
