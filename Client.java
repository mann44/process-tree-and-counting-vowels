

	import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

	/**
	 *
	 * @author manthan
	 */
	@SuppressWarnings("serial")
	public class Client extends JFrame{
	      // Text field for receiving loan amount Number of years annual rate
	  private JTextField jL = new JTextField();
	  private JTextField jLayer = new JTextField();
	  private JTextField JLayerRate = new JTextField();
	  // Text area to display total rate
	  private JTextArea jTextA = new JTextArea();
	  public static JButton jbtsubmit = new JButton("submit");
	  // IO streams
	  private DataOutputStream toServer;
	  private DataInputStream fromServer;

	      
	  public static void main(String[] args) {
	    new Client();
	  }

	  public Client() {
	    // Panel p to hold the label and text field
	    JPanel pone = new JPanel(new BorderLayout());
	    pone.setLayout(new GridLayout(3,1));
	    pone.add(new JLabel("loan amount"));
	    pone.add(new JLabel("Number of Years"));
	    pone.add(new JLabel("Annual Interest Rate"));
	    JPanel ptwo = new JPanel(new BorderLayout());
	    ptwo.setLayout(new GridLayout(3,1));
	    ptwo.add(jL);
	    ptwo.add(jLayer);
	    ptwo.add(JLayerRate);
	    JPanel pthree = new JPanel(new BorderLayout());
	    pthree.setLayout(new GridLayout(1,1));
	    pthree.add(jbtsubmit);
	    JPanel pfinal = new JPanel(new BorderLayout());
	    pfinal.setLayout(new GridLayout(1,1));
	    setLayout(new BorderLayout());
	    pfinal.add(pone);
	    pfinal.add(ptwo);
	    pfinal.add(pthree);
	    add(pfinal, BorderLayout.NORTH);
	    add(new JScrollPane(jTextA), BorderLayout.CENTER);

	    /*
	    JPanel p = new JPanel();
	    p.setLayout(new GridLayout(4,2));
	    p.add(new JLabel("loan amount"));
	    p.add(jL);
	    p.add(new JLabel("Number of Years"));
	    p.add(jLayer);
	    p.add(new JLabel("Annual Interest Rate"));
	    p.add(JLayerRate);
	    p.add(jbtsubmit);
	    setLayout(new BorderLayout());
	    add(p, BorderLayout.NORTH);
	    add(new JScrollPane(jTextA), BorderLayout.CENTER);

	   */
	    setTitle("Client");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!

	    try {
	      // Create a socket to connect to the server
	      @SuppressWarnings("resource")
		Socket socket = new Socket("localhost", 8000);
	      fromServer = new DataInputStream(socket.getInputStream());
	      toServer = new DataOutputStream(socket.getOutputStream());
	    }
	    catch (IOException ex) {
	      jTextA.append(ex.toString() + '\n');
	    }
	    
	    jbtsubmit.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            try{
	                jTextA.append("Loan amount"+jL.getText()+ "\n");
	                jTextA.append("Total number of year"+jLayer.getText()+ "\n");
	                jTextA.append("Annual Interest Rate"+JLayerRate.getText()+ "\n");
	                @SuppressWarnings("unused")
					Loan l = new Loan(Float.parseFloat(JLayerRate.getText().trim()),Integer.parseInt(jLayer.getText().trim()),Double.parseDouble((jL.getText().trim())));
	                toServer.writeDouble(Double.parseDouble(jL.getText().trim()));
	                toServer.writeInt(Integer.parseInt(jLayer.getText().trim()));
	                toServer.writeFloat(Float.parseFloat(JLayerRate.getText().trim()));
	                jTextA.append("Monthly payment" + String.valueOf(fromServer.readDouble()) + "\n");
	                jTextA.append("Total payment" +String.valueOf(fromServer.readInt())+ "\n");
	            }
	            catch(Exception e)
	            {
	                System.out.println(e);
	            }
	        }
	    });
	  }


}
