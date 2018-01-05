
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient extends JFrame implements Runnable
{
        private static final long serialVersionUID = 1L;
        private JTextField name = new JTextField("Enter a name");
        private JTextField text = new JTextField();
        private JTextArea clientTextArea = new JTextArea();
        
        private Socket socket;
        private DataOutputStream dataout;
        private DataInputStream datain;
        
        //main
        public static void main(String[] args) {
            new ChatClient();
        }
        //Constructor
        public ChatClient() {
            
                JPanel panel1 = new JPanel();
                panel1.setLayout(new BorderLayout());
                panel1.add(new JLabel("Enter text"), BorderLayout.WEST);
                panel1.add(text, BorderLayout.CENTER);

                JPanel panel2 = new JPanel();
                panel2.setLayout(new BorderLayout());
                panel2.add(new JLabel("Name"), BorderLayout.WEST);
                panel2.add(name, BorderLayout.CENTER);

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.add(panel1, BorderLayout.SOUTH);
                panel.add(panel2, BorderLayout.NORTH);

                setLayout(new BorderLayout());
                add(panel, BorderLayout.NORTH);
                add(new JScrollPane(clientTextArea), BorderLayout.CENTER);
                clientTextArea.setEditable(false); 
                text.addActionListener(new ButtonClickListener()); 
                
                setTitle("Multi Chat Client");
                setSize(500,300);
                setLocationRelativeTo(null); 
                setLocation(1200,400);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true); 

            try {
                    socket = new Socket("localhost", 8000);
                    datain = new DataInputStream(socket.getInputStream());
                    dataout = new DataOutputStream(socket.getOutputStream());
                    new Thread(this).start();
                }catch(IOException ex) {
                    clientTextArea.append(ex.toString() + "\n");
                }
        }
        
        private class ButtonClickListener implements ActionListener {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try{
                        String data = name.getText().trim() + ": " + text.getText().trim(); //Get the text from the text field
                        dataout.writeUTF(data); // Send the text to the server
                        text.setText("");
                    }catch (IOException ex){
                        System.err.println(ex);
                }
            }
        }
        
        public void run(){
            try{
                    while(true){
                        String text1 = datain.readUTF(); // Get message
                        clientTextArea.append(text1 + "\n"); //Display to the text area
                    }
        }catch(IOException ex){
            System.err.println(ex);
        }
    }
}
 
