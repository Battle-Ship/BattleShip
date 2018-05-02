package MultiplayerInterface;

import MultiplayerGame.*;
import lv.odo.battleship.demo.Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.UIManager;

public class MenuMulti {

	private JFrame frame;
	private JTextField txtEnterIp;
	private JTextArea textArea;
	Main mainMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuMulti window = new MenuMulti();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuMulti(JFrame frame, Main mainMenu) {
		this.frame = frame;
		this.mainMenu = mainMenu;
		initialize();
	}
	
	/**
	 * Create the application.
	 */
	public MenuMulti() {
		frame = new JFrame();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, MainMulti.WINDOW_WIDTH, MainMulti.WINDOW_HEIGHT+100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(197, 308, 299, 73);
		frame.getContentPane().add(textArea);
		textArea.setText("Create or conncet to server");
		
		txtEnterIp = new JTextField();
		txtEnterIp.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnterIp.setBounds(147, 142, 157, 29);
		txtEnterIp.setText("Enter IP");
		frame.getContentPane().add(txtEnterIp);
		txtEnterIp.setColumns(10);
		
		final JLabel lblBattleshipMultiplayer = new JLabel("BattleShip Multiplayer");
		lblBattleshipMultiplayer.setFont(new Font("Dialog", Font.BOLD, 35));
		lblBattleshipMultiplayer.setForeground(new Color(0, 0, 128));
		lblBattleshipMultiplayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblBattleshipMultiplayer.setBounds(139, 29, 460, 42);
		frame.getContentPane().add(lblBattleshipMultiplayer);
		
		final JButton btnCreateServer = new JButton("");
        btnCreateServer.setBackground(new Color(224, 255, 255));
		ImageIcon create = new ImageIcon("images/create.jpg");
	    btnCreateServer.setIcon(create);
		btnCreateServer.setBounds(351, 183, 226, 95);
		btnCreateServer.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
		btnCreateServer.setBorder(null);
		frame.getContentPane().add(btnCreateServer);
		btnCreateServer.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
				   String ip = null;
				   Enumeration e = null;
				try {
					e = NetworkInterface.getNetworkInterfaces();
				} catch (SocketException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				   while(e.hasMoreElements())
				   {
				       NetworkInterface n = (NetworkInterface) e.nextElement();
				       Enumeration ee = n.getInetAddresses();
				       while (ee.hasMoreElements())
				       {
				           InetAddress i = (InetAddress) ee.nextElement();
				           String ipString = i.getHostAddress();
				           if(ipString.startsWith("192")){
				        		   System.out.println(ipString);
				        		   ip = ipString;
				           }
				       }
				   }
				   try {
					Server server = new Server(ip); 
					System.out.println(
							"Running Server: " + "Host=" + ip + " Port=" + server.getPort());
					server.start();
					textArea.append("\nRunning Server: " + "IP=" + ip);
				   } catch (Exception e1) {
					e1.printStackTrace();
					textArea.append("\nCannot create server");
					System.out.println("Cannot create server");
				   }
			   }
		});
		
		
		final JButton btnConnectToServer = new JButton("");
		btnConnectToServer.setBounds(113, 183, 226, 95);
		btnConnectToServer.setBackground(new Color(224, 255, 255));
		frame.getContentPane().add(btnConnectToServer);
		ImageIcon connect = new ImageIcon("images/connect.jpg");
		btnConnectToServer.setIcon(connect);
		btnConnectToServer.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
		btnConnectToServer.setBorder(null);
		
		final JButton btnExit = new JButton("");
		btnExit.setBounds(233, 381, 216, 104);
		btnExit.setBackground(new Color(224, 255, 255));
		ImageIcon exit = new ImageIcon("images/exit.png");
		btnExit.setIcon(exit);
		btnExit.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
		btnExit.setBorder(null);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				new Main(frame);
			}
		});
		frame.getContentPane().add(btnExit);
		btnConnectToServer.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
			      String textIp = txtEnterIp.getText();
			      String ip = getIp(textIp);
			      if(ip.equals("Incorrect IP")) {
			    	  System.out.println(ip);
			    	  textArea.append("\n" + ip);
			      }else {
			    	  System.out.println("Connecting to " + ip);  
			    	  textArea.append("\nConnecting to " + ip);
			    	  InetAddress ipAddress = null;
					try {
						ipAddress = InetAddress.getByName(ip);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	  try {
						MainMulti game = new MainMulti(frame, ipAddress);
						frame.getContentPane().remove(btnConnectToServer);
						frame.getContentPane().remove(lblBattleshipMultiplayer);
						frame.getContentPane().remove(textArea);
						frame.getContentPane().remove(txtEnterIp);
						frame.getContentPane().remove(btnCreateServer);
						frame.getContentPane().remove(btnExit);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      }
			      
			   }
});
	}
	
	public String getIp(String ip) {
		 String patternIp = "\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b";
	     Pattern pattern = Pattern.compile(patternIp);
	     Matcher matcher = pattern.matcher(ip);
	     if(matcher.find()) {
	    	return matcher.group();
	     }
	     else {
	    	return "Incorrect IP";
	     }
	}
}
