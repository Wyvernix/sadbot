package me.wyvernix.sadbot;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class newGUI extends JFrame {
    private JPanel topPanel;
    public static JTextPane tPane;
    private static JScrollPane jsp;
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static JTextArea txtrAlertsGoHere;
	public static DefaultListModel<String> sbUsers = new DefaultListModel<String>();
	public static DefaultListModel<String> ebUsers = new DefaultListModel<String>();
	public static JSplitPane splitPane;

	private Font proFont = new Font("DejaVu Sans Mono", Font.PLAIN, 10);
	
	private static PrintStream logfile;
	
	public newGUI() {
    	super("SadBot x EnergyBot");
		
		try {
			logfile = new PrintStream(new FileOutputStream("logs\\output-"+new Date().getTime()+".bot.log"));
		} catch (FileNotFoundException e2) {
			System.err.println("this failed, the world is over");
		}
    	////////////
    	UIManager.put("Button.font", proFont);
    	UIManager.put("ToggleButton.font", proFont);
    	UIManager.put("RadioButton.font", proFont);
    	UIManager.put("CheckBox.font", proFont);
    	UIManager.put("ColorChooser.font", proFont);
    	UIManager.put("ComboBox.font", proFont);
    	UIManager.put("Label.font", proFont);
    	UIManager.put("List.font", proFont);
    	UIManager.put("MenuBar.font", proFont);
    	UIManager.put("MenuItem.font", proFont);
    	UIManager.put("RadioButtonMenuItem.font", proFont);
    	UIManager.put("CheckBoxMenuItem.font", proFont);
    	UIManager.put("Menu.font", proFont);
    	UIManager.put("PopupMenu.font", proFont);
    	UIManager.put("OptionPane.font", proFont);
    	UIManager.put("Panel.font", proFont);
    	UIManager.put("ProgressBar.font", proFont);
    	UIManager.put("ScrollPane.font", proFont);
    	UIManager.put("Viewport.font", proFont);
    	UIManager.put("TabbedPane.font", proFont);
    	UIManager.put("Table.font", proFont);
    	UIManager.put("TableHeader.font", proFont);
    	UIManager.put("TextField.font", proFont);
    	UIManager.put("PasswordField.font", proFont);
    	UIManager.put("TextArea.font", proFont);
    	UIManager.put("TextPane.font", proFont);
    	UIManager.put("EditorPane.font", proFont);
    	UIManager.put("TitledBorder.font", proFont);
    	UIManager.put("ToolBar.font", proFont);
    	UIManager.put("ToolTip.font", proFont);
    	UIManager.put("Tree.font", proFont);
    	/////////////
    	
    	ImageIcon img = new ImageIcon("res\\img\\ico.png");
    	
    	this.setIconImage(img.getImage());
    	
    	this.setSize(810, 290);
    	this.setMinimumSize(new Dimension(810, 290));
    	this.setResizable(false);
    	
    	topPanel = new JPanel();        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().add(topPanel);

        pack();
                    	    	        tPane = new JTextPane();
//                    	    	        tPane.setToolTipText("Nothing to see here");
                    	    	        
                    	    	        //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
//                    	    	        tPane.setMargin(new Insets(5, 5, 0, 5));
                    	    	        DefaultCaret caret = (DefaultCaret)tPane.getCaret();
                    	    	        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                    	    	        
                    	    	        tPane.setEditable(false);
                    	    	        
                    	    	jsp = new JScrollPane(tPane);
                    	    	
//                    	    	jsp.setAutoscrolls(true);
//                    	    	tPane.setAutoscrolls(true);
                    	    	
                    	    	jsp.getVerticalScrollBar().setUnitIncrement(12);
                    	    	jsp.getHorizontalScrollBar().setUnitIncrement(12);
                    	    	
                    	    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//                    	    	tabbedPane.setFont(new Font("ProFontWindows", Font.PLAIN, 12));
                    	    	tabbedPane.setPreferredSize(new Dimension(136, 250));
                    	    	topPanel.add(tabbedPane);
                    	    	
                    	    	JPanel panel = new JPanel();
                    	    	tabbedPane.addTab("Controls", null, panel, null);
                    	    	
                    	    	JLabel lblNewLabel = new JLabel("~ COMMANDS~");
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
                    	    	btnReconnect.setToolTipText("Reconnect Bots");
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
                    	    	txtrAlertsGoHere.setFont(new Font("ProFontWindows", Font.BOLD, 16));
                    	    	txtrAlertsGoHere.setWrapStyleWord(true);
                    	    	txtrAlertsGoHere.setLineWrap(true);
                    	    	txtrAlertsGoHere.setPreferredSize(new Dimension(100, 100));
                    	    	txtrAlertsGoHere.setText("ALERTS GO HERE :3");
                    	    	panel.add(txtrAlertsGoHere);
                    	    	
								JList<String> sadbotUsers = new JList<String>(sbUsers);
                    	    	
                    	    	JList<String> energybotUsers = new JList<String>(ebUsers);
                    	    	
                    	    	JScrollPane sscroll = new JScrollPane(sadbotUsers);
                    	    	JScrollPane escroll = new JScrollPane(energybotUsers);
                    	    	
                    	    	splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sscroll, escroll);
                    	    	splitPane.setDividerSize(2);
                    	    	splitPane.setResizeWeight(0.5);
                    	    	tabbedPane.addTab("Users", null, splitPane, null);
                    	    	
                    	    	jsp.setPreferredSize(new Dimension(650, 250));
                    	    	jsp.setSize(650, 250);
                    	    	topPanel.add(jsp);
                    	    	
                    	    	appendToPane("Starting Program!\n", Color.RED);
        setVisible(true);   
        
    }
    
    public synchronized static void appendToPane(final String msg, final Color c) {
    	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StyleContext sc = StyleContext.getDefaultStyleContext();
		        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

//		        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "ProFontWindows");
//		        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		        tPane.setEditable(true);
		        
		        tPane.setCaretPosition(tPane.getDocument().getLength());
		        tPane.setCharacterAttributes(aset, false);
		        tPane.replaceSelection(dateFormat.format(new Date()) + " " + msg);
		        logfile.println(       dateFormat.format(new Date()) + " " + msg);
		        
		        if (tPane.getText().length() > 50000) {
		        	try {
						tPane.getDocument().remove(0, msg.length());
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
		        }
		        
//		        tPane.setCaretPosition(tPane.getDocument().getLength());
		        try {
		        	tPane.setEditable(false);
		        } catch (Exception e) {
		        	System.err.println("Swing is lame");
		        }
		        jsp.scrollRectToVisible(new Rectangle(tPane.getBounds().height-1, 10, 1, 1));
			}
    	});
    }
    
    public static void logError(Exception e) {
    	StringWriter sw = new StringWriter();
    	e.printStackTrace(new PrintWriter(sw));
    	appendToPane(sw.toString(), Color.RED);
    	alert("EXCEPTION!");
    }
    
    public synchronized static void alert(final String msg) {
    	return;
//    	
//    	//TODO persistant
//    	Runnable r = new Runnable() {
//            public void run() {
//            	txtrAlertsGoHere.setText(msg);
//    	    	txtrAlertsGoHere.setBackground(Color.red);
//    	    	try {
//    				Thread.sleep(3000);
//    			} catch (InterruptedException e) {
//    				e.printStackTrace();
//    			}
//    	    	txtrAlertsGoHere.setText("None :3");
//    	    	txtrAlertsGoHere.setBackground(Color.white);
//            }
//        };
//        new Thread(r, "Alert").start();
    }
}