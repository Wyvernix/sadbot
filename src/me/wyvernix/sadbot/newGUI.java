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

@SuppressWarnings("serial")
public class newGUI extends JFrame {
    private JPanel topPanel;
    public static JTextPane tPane;
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public newGUI() {
    	super("SadBot x EnergyBot");
        topPanel = new JPanel();        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            

        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));

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
    	jsp.setPreferredSize(new Dimension(711, 246));
    	topPanel.add(jsp);

        appendToPane("Starting Program!                                                                                                 \n", Color.RED);

        getContentPane().add(topPanel);

        pack();
        
        tPane.setEditable(false);
        
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