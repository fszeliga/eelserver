package els.commView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledDocument;

import els.commController.Client;
import els.commController.ELSServer;
import els.commController.Sensor;
import els.commController.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ELSGUI extends JFrame implements ListSelectionListener, ActionListener, Comparable<Object>, Runnable {

    /**
     *
     */

    public static final String DEFAULT_FONT = "Courier";
    public static final int DEFAULT_FONT_SIZE = 12;

    private JTextPane mOutputField;
    private JScrollPane mOutputScroll;

    private StyledDocument mOutputDocument;
    private JPanel centerPane;
    private static final long serialVersionUID = 1L;
    private JButton notifyAllButton;
    private JButton rebootBoxButton;
    private JButton sensorListButton;
    private JButton testButton;
    private ELSServer server;
    private JTextField userMessage;
    private int textfieldPosition = 0;
    private ArrayList<String> textfieldHistory = new ArrayList<String>();
    private JLabel clientsLabel;
    private JPanel panelNonControllable;
    private JPanel panelControllableSlider;
    private JPanel panelControllableButton;
    private JScrollPane helpPane;
    private JButton startStopMotion;

    public ELSGUI(ELSServer server) {
        this.server = server;

        createGUI();
        setMinimumSize(new Dimension(900, 600));
        setVisible(true);

    }

	// ========================================================
    private void createGUI() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                server.disconnectAll();
            }
        });
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 400);

        Color borderhighlightouter = new Color(220, 220, 220);
        Color borderhighlightinner = new Color(170, 170, 170);
        Color bordershadowouter = new Color(120, 120, 120);
        Color bordershadowinner = new Color(170, 170, 170);

        JPanel topPane = new JPanel();
        JPanel startPane = new JPanel();
        centerPane = new JPanel();
        //centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.LINE_AXIS));
        centerPane.setLayout(new BorderLayout());
        centerPane.setBorder(BorderFactory.createCompoundBorder(new TitledBorder("Control Panel"), BorderFactory.createLoweredBevelBorder()));
        JPanel endPane = new JPanel();
        endPane.setLayout(new BoxLayout(endPane, BoxLayout.Y_AXIS));
        JPanel bottomPane = new JPanel(new BorderLayout());

        panelControllableSlider = new JPanel(new WrapLayout());
        panelControllableSlider.setName("panelControllableSlider");
        panelControllableButton = new JPanel(new WrapLayout());
        panelControllableButton.setName("panelControllableButton");

        panelNonControllable = new JPanel();
        panelNonControllable.setName("panelNonControllable");
        panelNonControllable.setLayout(new BoxLayout(panelNonControllable, BoxLayout.Y_AXIS));

        panelControllableSlider.removeAll();
        panelControllableButton.removeAll();
        panelNonControllable.removeAll();

        for (Sensor s : server.getRecentSensors()) {
            s.createControlPanel();

            if (s.isControllable()) {
                if (s.getContinuous()) {
                    panelControllableSlider.add(s);
                } else {
                    panelControllableButton.add(s);
                }
            } else {
                panelNonControllable.add(s);
            }
        }

        centerPane.add(panelNonControllable, BorderLayout.WEST);
        centerPane.add(panelControllableSlider, BorderLayout.CENTER);
        centerPane.add(panelControllableButton, BorderLayout.EAST);

        topPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        startPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        endPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new MatteBorder(1, 0, 0, 0, Color.black)));

        //ImageIcon icon = new ImageIcon("images/middle.gif", "server connection state");
        clientsLabel = new JLabel("Connected Clients: 0");
        bottomPane.add(clientsLabel, BorderLayout.LINE_START);
	    // -----------------------------
        // Output
        mOutputField = new JTextPane();
        mOutputField.setEditable(false);
        mOutputField.setBackground(new Color(245, 245, 245));
        mOutputDocument = mOutputField.getStyledDocument();

        mOutputScroll = new JScrollPane(mOutputField);

        mOutputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mOutputScroll.setPreferredSize(new Dimension(250, 400));
        mOutputScroll.setMinimumSize(new Dimension(50, 50));
        mOutputScroll.setOpaque(false);
        mOutputScroll.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0), BorderFactory.createBevelBorder(BevelBorder.LOWERED, borderhighlightouter, borderhighlightinner, bordershadowouter, bordershadowinner)));

        notifyAllButton = new JButton("Notify all");
        notifyAllButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                server.updateAllOnEvent();
            }
        });
        rebootBoxButton = new JButton("Reboot Box");
        rebootBoxButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                server.resetBox();
            }
        });

        sensorListButton = new JButton("Sensor List");
        sensorListButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                server.printSensorList();
            }
        });

        testButton = new JButton("Test Button");
        testButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                server.testFunction();
            }
        });
        
        startStopMotion = new JButton("Start Motion");
        startStopMotion.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //String command = "netstat -anltp | grep 8080";
                String command = "sudo motion";
                String array[] = {"sudo", "motion"};
                String[] cmd= {"/bin/bash", "-c", "terminal"}; 
                String command2 = "terminal";

                
                String pas = "lesc";
                Process proc = null;
                BufferedWriter writer = null;
                
                try {
                    System.out.println("executing command");
                    proc = Runtime.getRuntime().exec(cmd);
                    System.out.println("getting writer");

                    writer = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
                    System.out.println("writing password");

                    writer.write(pas);
                    System.out.println("done");
                } catch (IOException exep) {
                    System.out.println(exep.getMessage());
                    exep.printStackTrace();
                }
                
                String line;
                BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                try {
                    while ((line = input.readLine()) != null) { System.out.println(line); }
                    input.close();

                } catch (IOException ex) {
                    Logger.getLogger(ELSGUI.class.getName()).log(Level.SEVERE, null, ex);
                }                
                
                //String result = Utils.executeCommand(cmd);
                //if(result.contains("8080"))
                //    startStopMotion.setText("Start Motion");
                //else
                //   startStopMotion.setText("Stop Motion");
            }
            
        });

        userMessage = new JTextField();
        userMessage.setColumns(20);

        userMessage.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER & !userMessage.getText().equals("")) {
                    server.sendUserMessage(userMessage.getText());
                    displayMessage(userMessage.getText());
                    if (textfieldHistory.size() != 0) {
                        textfieldHistory.remove(textfieldHistory.size() - textfieldPosition);
                    }
                    textfieldHistory.add(userMessage.getText());
                    textfieldPosition = textfieldHistory.size();
                    userMessage.setText("");
                }

                if (textfieldHistory.size() == 0) {
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    userMessage.setText(textfieldHistory.get(textfieldHistory.size() - textfieldPosition));

                    if (textfieldPosition > 1) {
                        textfieldPosition -= 1;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    userMessage.setText(textfieldHistory.get(textfieldHistory.size() - textfieldPosition));

                    if (textfieldPosition < textfieldHistory.size()) {
                        textfieldPosition += 1;
                    }
                }
            }

            public void keyTyped(KeyEvent e) {;
            }

            @Override
            public void keyReleased(KeyEvent arg0) {;
            }
        });

        JButton sendUserMessage = new JButton("Send");
        sendUserMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!userMessage.getText().equals("")) {
                    server.sendUserMessage(userMessage.getText());
                    displayMessage(userMessage.getText());
                    textfieldHistory.add(userMessage.getText());
                    textfieldPosition += 1;
                }

                userMessage.setText("");
            }

        });

        JPanel userInput = new JPanel();
        userInput.setLayout(new BorderLayout());
        userInput.add(userMessage, BorderLayout.WEST);
        userInput.add(sendUserMessage, BorderLayout.EAST);

        startPane.setLayout(new BorderLayout());
        startPane.add(mOutputScroll, BorderLayout.NORTH);
        startPane.add(userInput, BorderLayout.SOUTH);

        endPane.add(notifyAllButton);
        endPane.add(rebootBoxButton);
        endPane.add(sensorListButton);
        endPane.add(testButton);
        endPane.add(startStopMotion);

        add(topPane, BorderLayout.PAGE_START);
        add(startPane, BorderLayout.LINE_START);

        helpPane = new JScrollPane(centerPane);
	    //helpPane.add(centerPane);

        //add(centerPane, BorderLayout.CENTER);
        add(helpPane, BorderLayout.CENTER);
        add(endPane, BorderLayout.LINE_END);
        add(bottomPane, BorderLayout.PAGE_END);

    }

    private final boolean shouldScroll() {
        int min = mOutputScroll.getVerticalScrollBar().getValue() + mOutputScroll.getVerticalScrollBar().getVisibleAmount();
        int max = mOutputScroll.getVerticalScrollBar().getMaximum();

        return min == max;
    }

    public synchronized void updateClients(CopyOnWriteArrayList<Client> clients) {
        clientsLabel.setText("Connected Clients: " + clients.size());
    }

    public void addActionButton(JButton button) {
        centerPane.add(button);
    }

    public void valueChanged(ListSelectionEvent event) {

    }

    public void actionPerformed(ActionEvent event) {

    }

    public final int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    private boolean isViewAtBottom() {
        JScrollBar sb = mOutputScroll.getVerticalScrollBar();
        int min = sb.getValue() + sb.getVisibleAmount();
        int max = sb.getMaximum();
        //System.out.println(min + " " + max);
        return min == max;
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        mOutputScroll.getVerticalScrollBar().setValue(mOutputScroll.getVerticalScrollBar().getMaximum());
                    }
                });
    }

    public void run() {
        while (true) {
            if (shouldScroll()) {
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                mOutputScroll.getVerticalScrollBar().setValue(mOutputScroll.getVerticalScrollBar().getMaximum());
                            }
                        });
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void displayMessage(String message) {
        synchronized (this) {
            displayMessage(message, "");
        }
    }

    public void displayMessage(String message, String style) {
        displayMessage(message, style, true);
    }

    public void displayMessage(String message, String style, boolean prependnewline) {
        String newline = (prependnewline ? "\n" : "");
        boolean scroll = isViewAtBottom() && prependnewline;

        style = (style.equals("") ? "regular" : style);
        message = message.replace("\n", " ");

        try {
            mOutputDocument.insertString(mOutputDocument.getLength(),
                    String.format("%s%s", newline, message),
                    mOutputDocument.getStyle(style));
        } catch (Exception e) {
        }

        if (scroll) {
            scrollToBottom();
        }
    }

}
