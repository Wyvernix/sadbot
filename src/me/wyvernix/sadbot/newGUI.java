package me.wyvernix.sadbot;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class newGUI extends JFrame {
    private JPanel topPanel;
    public static JTextPane tPane;
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static JTextArea txtrAlertsGoHere;

    public newGUI() {
    	super("SadBot x EnergyBot");
    	
    	ImageIcon img = new ImageIcon("res\\img\\ico.png");
    	
    	this.setIconImage(img.getImage());
    	
    	this.setSize(810, 290);
    	this.setMinimumSize(new Dimension(810, 290));
    	this.setResizable(false);
    	
    	topPanel = new JPanel();        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            

        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));

        

        getContentPane().add(topPanel);

        pack();
        
                tPane = new JTextPane();  
                tPane.setBorder(eb);
                
                //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                tPane.setMargin(new Insets(5, 5, 5, 5));
                
                    	JPanel container = new JPanel();
                    	//    	container.setPreferredSize(new Dimension(350, 256));
                    	    	container.add(tPane);
                    	    	JScrollPane jsp = new JScrollPane(container);
                    	    	jsp.getVerticalScrollBar().setUnitIncrement(12);
                    	    	jsp.getHorizontalScrollBar().setUnitIncrement(12);
                    	    	
                    	    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
                    	    	tabbedPane.setPreferredSize(new Dimension(136, 250));
                    	    	topPanel.add(tabbedPane);
                    	    	
                    	    	JPanel panel = new JPanel();
                    	    	tabbedPane.addTab("Controls", null, panel, null);
                    	    	
                    	    	JLabel lblNewLabel = new JLabel("~ CAKE ~");
                    	    	panel.add(lblNewLabel);
                    	    	
                    	    	JButton btnShutdown = new JButton("Shutdown");
                    	    	btnShutdown.addActionListener(new ActionListener() {
                    	    		public void actionPerformed(ActionEvent arg0) {
                    	    			BotManager.shutdown();
                    	    		}
                    	    	});
                    	    	btnShutdown.setToolTipText("Safely stop Bots");
                    	    	panel.add(btnShutdown);
                    	    	
                    	    	JButton btnReconnect = new JButton("Reconnect");
                    	    	btnReconnect.addActionListener(new ActionListener() {
                    	    		public void actionPerformed(ActionEvent e) {
                    	    			BotManager.reconnect();
                    	    		}
                    	    	});
                    	    	panel.add(btnReconnect);
                    	    	
                    	    	JLabel lblNewLabel_1 = new JLabel("~ ~ ALERTS ~ ~");
                    	    	panel.add(lblNewLabel_1);
                    	    	
                    	    	txtrAlertsGoHere = new JTextArea();
                    	    	txtrAlertsGoHere.setEditable(false);
                    	    	txtrAlertsGoHere.setFont(new Font("Monospaced", Font.BOLD, 16));
                    	    	txtrAlertsGoHere.setWrapStyleWord(true);
                    	    	txtrAlertsGoHere.setLineWrap(true);
                    	    	txtrAlertsGoHere.setPreferredSize(new Dimension(100, 100));
                    	    	txtrAlertsGoHere.setText("ALERTS GO HERE :3");
                    	    	panel.add(txtrAlertsGoHere);
                    	    	jsp.setPreferredSize(new Dimension(650, 250));
                    	    	topPanel.add(jsp);
                    	    	
                    	    	tPane.setEditable(false);
        
                    	    	
                    	    	appendToPane("Starting Program!                                                                                                 \n", Color.RED);
        setVisible(true);   
    }

    private static int isUpdating = 0;
    
    public static void appendToPane(String msg, Color c) {
		
    	isUpdating++;
    	
    	Date date = new Date();

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tPane.getDocument().getLength();

        tPane.setEditable(true);
        
        tPane.setCaretPosition(len);
        tPane.setCharacterAttributes(aset, false);
        tPane.replaceSelection(dateFormat.format(date) + " " + msg);
        
        isUpdating--;
        
        if (isUpdating == 0) {
        	tPane.setEditable(false);
        }
    }
    
    public static void logError(Exception e) {
    	StringWriter sw = new StringWriter();
    	e.printStackTrace(new PrintWriter(sw));
    	appendToPane(sw.toString(), Color.RED);
    	alert("EXCEPTION!");
    }
    
    public static void alert(final String msg) {
//    	new Thread() {
//    		@Override
//    		public void run() {    
//    			txtrAlertsGoHere.setText(msg);
//    	    	txtrAlertsGoHere.setBackground(Color.red);
//    	    	try {
//    				Thread.sleep(3000);
//    			} catch (InterruptedException e) {
//    				e.printStackTrace();
//    			}
//    	    	txtrAlertsGoHere.setText("None :3");
//    	    	txtrAlertsGoHere.setBackground(Color.white);
//        }
//    	}.run();
    	
    	Runnable r = new Runnable() {
            public void run() {
            	txtrAlertsGoHere.setText(msg);
    	    	txtrAlertsGoHere.setBackground(Color.red);
    	    	try {
    				Thread.sleep(3000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    	    	txtrAlertsGoHere.setText("None :3");
    	    	txtrAlertsGoHere.setBackground(Color.white);
            }
        };

        new Thread(r).start();
    	
    }

    public static void initGui() {
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run() {
                    new newGUI();
                }
            });
    }
}