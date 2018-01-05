import java.io.*;

	import java.net.*;
	import java.util.*;
	import java.awt.*;
	import javax.swing.*;
@SuppressWarnings("serial")
public class Server extends JFrame {
	


	  // Text area for displaying contents
	  private JTextArea jTextA = new JTextArea();

	  public static void main(String[] args) {
	    new Server();
	  }

	  public Server() {
	    // Place text area on the frame
	    setLayout(new BorderLayout());
	    add(new JScrollPane(jTextA), BorderLayout.CENTER);
	    setTitle("Server");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!

	    try {
	      // Create a server socket
	      @SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(8000);
	      jTextA.append("Server started at " + new Date() + '\n');
	        // Listen for a connection request
	      Socket socket = serverSocket.accept();

	      // Create data input and output streams
	      DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
	      DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
	      jTextA.append("server working\n");
	      while (true) {
	       jTextA.append("Server inside from while \n");
	                
	        double loanamount = inputFromClient.readDouble();
	        int NumberOfYears  = inputFromClient.readInt();
	        float totalInterestRate = inputFromClient.readFloat();
	        
	        // Compute area
	        Loan l = new Loan(totalInterestRate, NumberOfYears, loanamount);
	        double monthlyPayment = l.getMonthlyPayment();
	        double totalPayment = l.getTotalPayment();
	        // Send area back to the client
	        outputToClient.writeDouble(monthlyPayment);
	        outputToClient.writeDouble(totalPayment);
	        jTextA.append("Loan amount received from client: " +String.valueOf(loanamount)+'\n');
	        jTextA.append("Interest Rate received from client: " +String.valueOf(totalInterestRate) + '\n');
	        jTextA.append("Total Number of  years reserived from client: " + String.valueOf(NumberOfYears) + '\n');
	        jTextA.append("Monthly Payment : " + String.valueOf(monthlyPayment) + '\n');
	        jTextA.append("Total  Payment : " + String.valueOf(totalPayment) + '\n');
	      }
	    }
	    catch(IOException ex) {
	      System.err.println(ex);
	    }
	  }
}
