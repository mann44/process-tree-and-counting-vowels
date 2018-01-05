
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatServer extends JFrame{
	private static final long serialVersionUID = 1L;

        //Text area for displaying contents
        private JTextArea serverTextArea = new JTextArea();
        private Hashtable<Socket, DataOutputStream> Streams = new Hashtable<Socket, DataOutputStream>();
        private ServerSocket serverSocket; //Server socket
  
        //main
        public static void main(String[] args) {
            new ChatServer();
        }
        //Constructor
        public ChatServer() {
            //Place text area on the frame
            setLayout(new BorderLayout());
            add(new JScrollPane(serverTextArea), BorderLayout.CENTER);
            setTitle("Multi Chat Server");
            setSize(650, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true); 
            setLocation(1150,100);
            serverTextArea.setEditable(false); 
            listen();
        }

        private void listen() {
            try {
                    serverSocket = new ServerSocket(8000); // Create a server socket
                    serverTextArea.append("Multi Thread Server started at " + new Date() + '\n');

                    while (true){
                        Socket socket = serverSocket.accept(); //Listen for a new connection request
                        serverTextArea.append("Connection from " + socket + " at " + new Date() + '\n');
                        DataOutputStream dataout = new DataOutputStream(socket.getOutputStream());
                        Streams.put(socket, dataout);
                        new ServerThread(this, socket);
                    }
                }
                catch(IOException ex) {
                    System.err.println(ex);
                }
        }

    //Used to get the output streams
    Enumeration<DataOutputStream> getOutputStreams(){
        return Streams.elements();
    }

    //Used to send message to all clients
    void sendToAll(String message){
      //Go through hashtable and send message to each output stream
      for(Enumeration<?> e = getOutputStreams(); e.hasMoreElements();)
      {
        DataOutputStream dout = (DataOutputStream)e.nextElement();
        try{
                dout.writeUTF(message); // Write message
            }catch (IOException ex){
              System.err.println(ex);
            }
        }
    }


  class ServerThread extends Thread {
        private ChatServer server;
        private Socket socket;
        public ServerThread(ChatServer server, Socket socket) {
          this.socket = socket;
          this.server = server;
          start();
        }
        public void run() {
            try {

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                while (true) {
                  String data = inputStream.readUTF();
                  server.sendToAll(data);
                  serverTextArea.append(data +"\n");
                }
            }catch(IOException e) {
              System.err.println(e);
            }
        }
    }
}
    

