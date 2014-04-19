package me.wyvernix.sadbot;

import java.awt.*;

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
        
//        tPane.setEditable(false);
        
        setVisible(true);   
    }

    public static void appendToPane(String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tPane.getDocument().getLength();
        tPane.setCaretPosition(len);
        tPane.setCharacterAttributes(aset, false);
        tPane.replaceSelection(msg);
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