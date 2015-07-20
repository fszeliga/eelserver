package els.commView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 */
public class ELSGUI extends JFrame implements ListSelectionListener, ActionListener, Comparable<Object>, Runnable {

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void run() {

    }
/*
    public static final String DEFAULT_FONT = "Courier";
    public static final int DEFAULT_FONT_SIZE = 12;

   // private JScrollPane mOutputScroll;



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


    }

	// ========================================================
    private void createGUI() {



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
/*
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

        notifyAllButton = new JButton("Notify all");
        notifyAllButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                //server.updateAllOnEvent();
            }
        });
        rebootBoxButton = new JButton("Reboot Box");
        rebootBoxButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //server.resetBox();
            }
        });

        sensorListButton = new JButton("Sensor List");
        sensorListButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //server.printSensorList();
            }
        });

        testButton = new JButton("Test Button");
        testButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //server.testFunction();
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
                    //server.sendUserMessage(userMessage.getText());
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
                    //server.sendUserMessage(userMessage.getText());
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

*/
}
