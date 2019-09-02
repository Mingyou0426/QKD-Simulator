/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.transform.Source;

/**
 *
 * @author Jincowboy
 */
public class CommandControl extends UnicastRemoteObject implements CCService {
    private static final long serialVersionUID = 1L;
    
    JFrame window; // This is Main Window
    JFrame winDataProcess;
    JFrame winDecipheredModule;

    JMenuBar menuBar = new JMenuBar(); // Window menu bar
    JMenu passMenu = new JMenu("Password");
    JMenu extMenu = new JMenu("Extensions");
    JMenuItem itemInputKeyLength = new JMenuItem("Input Key Length");
    JMenuItem itemReceiveEncryptedMsg = new JMenuItem("Receive encrypted message");

    JPanel pan4InputKeyLength = new JPanel();
    JTextField txtInputKeyLength = new JTextField();
    String strInputKeyLength = "";
    Object[] opt4Button = { "Accept" };

    JLabel lblDataProcess;
    JTable tblDataProcess;
    JPanel panDataProcess;

    int ROW_COUNT = 4;
    int COL_COUNT = 33;

    JTextField txtUseoftheAgreedKey;
    JLabel lblUseoftheAgreedKey;
    JTextField txtUseoftheHashobtainedbyCommandControl;
    JLabel lblUseoftheHashobtainedbyCommandControl;
    JTextField txtInitialKeySize;
    JLabel lblInitialKeySize;
    JTextField txtFinalKeySize;
    JLabel lblFinalKeySize;
    JTextField txtPercentage;
    JLabel lblPercentage;

    JTextField txtIPTransmitter;
    JLabel lblIPTransmitter;
    JTextField txtKey;
    JLabel lblKey;
    JTextField txtLastMsgSent;
    JLabel lblLastMsgSent;
    JTextField txtMessageSent;
    JLabel lblMessageSent;
    JButton btnUpdate;

    String strMalwareIP = "";
    String strSourceIP = "";
    String strEntangledParticles = "";

    private static SourceService lookup4Source = null;

    String strBase4CC = "";
    String [] schemes = {"(-)", "(/)", "(|)", "(\\)"};

    String strKeyGenerated = "";
    String strLastMsgSent = "";
    String strMsgSent = "";

    CommandControl() throws RemoteException{
        super();
        
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Malware.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Malware.class.getName()).log(Level.SEVERE, null, ex);
        }

        final String ip = socket.getLocalAddress().getHostAddress();
        
        window = new JFrame("Command&Control - Quantum Communication Protocol E91");
        window.setSize(600, 480); // Height And Width Of Window
        window.setLocationRelativeTo(null); // Move Window To Center

        window.setJMenuBar(menuBar);
        menuBar.add(passMenu);

        passMenu.add(itemInputKeyLength);

        pan4InputKeyLength.add(new JLabel("Input Key Length:"));
        pan4InputKeyLength.add(txtInputKeyLength);
        pan4InputKeyLength.setLayout(new GridLayout(1,2));

        itemInputKeyLength.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(null, pan4InputKeyLength, "Input Key Length",
                        JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, opt4Button, null);
                if (result == JOptionPane.OK_OPTION){
                    strInputKeyLength = txtInputKeyLength.getText();
                    try {
                        if (!strInputKeyLength.trim().equals(lookup4Source.getKeyLength4CC().trim())) {
                            JOptionPane.showMessageDialog(null, "Key length must be " + lookup4Source.getKeyLength4CC().trim(), "Error", JOptionPane.OK_OPTION);
                            return;
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(CommandControl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if ( strSourceIP.equals("") ) {
                        JOptionPane.showMessageDialog(null, "Please run malware first and set cc ip on that side", "Error", JOptionPane.OK_OPTION);
                        return;
                    }

                    try {
                        long startTime = System.nanoTime();
                        strEntangledParticles=lookup4Source.getEntangledParticles(1);
                        long endTime = System.nanoTime();
                        lookup4Source.afterParticlesSendingDone((int)((endTime-startTime)/1000000), 1);

                        Random rand = new Random();
                        for ( int i=0; i<Integer.parseInt(strInputKeyLength); i++ ) {
                            // strBase4CC = strBase4CC+schemes[rand.nextInt(4)];
                            strBase4CC = strBase4CC+String.valueOf(rand.nextInt(4));
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Malware.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        menuBar.add(extMenu);
        extMenu.add(itemReceiveEncryptedMsg);
        itemReceiveEncryptedMsg.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                winDecipheredModule.setVisible(true);
            }
        });

        try {
            final Image img = ImageIO.read(getClass().getResource("resources/back.png"));
            window.setContentPane(new JPanel() {
                 @Override
                 public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img, 0, 0, null);
                 }
              });
        } catch (IOException ex) {
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If Click into The Red Button => End The Processus
        window.setVisible(true);

        winDecipheredModule = new JFrame("Deciphered Module");
        winDecipheredModule.setSize(400, 220); // Height And Width Of Window
        winDecipheredModule.setLocationRelativeTo(null); // Move Window To Center

        lblIPTransmitter = new JLabel("IP Transmitter");
        lblIPTransmitter.setBounds(10,30,120,25);
        lblIPTransmitter.setHorizontalAlignment(SwingConstants.RIGHT);
        winDecipheredModule.add(lblIPTransmitter);

        txtIPTransmitter = new JTextField();
        txtIPTransmitter.setBounds(135, 30, 250, 25);
        txtIPTransmitter.setEnabled(false);
        winDecipheredModule.add(txtIPTransmitter);

        lblKey = new JLabel("Key");
        lblKey.setBounds(10,60,120,25);
        lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
        winDecipheredModule.add(lblKey);

        txtKey = new JTextField();
        txtKey.setBounds(135, 60, 250, 25);
        txtKey.setEnabled(false);
        winDecipheredModule.add(txtKey);

        lblLastMsgSent = new JLabel("Last Msg Sent(0x)");
        lblLastMsgSent.setBounds(10,90,120,25);
        lblLastMsgSent.setHorizontalAlignment(SwingConstants.RIGHT);
        winDecipheredModule.add(lblLastMsgSent);

        txtLastMsgSent = new JTextField();
        txtLastMsgSent.setBounds(135, 90, 250, 25);
        txtLastMsgSent.setEnabled(false);
        winDecipheredModule.add(txtLastMsgSent);

        lblMessageSent = new JLabel("Message Sent");
        lblMessageSent.setBounds(10,120,120,25);
        lblMessageSent.setHorizontalAlignment(SwingConstants.RIGHT);
        winDecipheredModule.add(lblMessageSent);

        txtMessageSent = new JTextField();
        txtMessageSent.setBounds(135, 120, 250, 25);
        txtMessageSent.setEnabled(false);
        winDecipheredModule.add(txtMessageSent);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(150, 150, 100, 25);
        btnUpdate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                txtLastMsgSent.setText(strLastMsgSent);
                txtKey.setText(strKeyGenerated);

                if ( txtKey.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "Generate the key first on Malware side", "Error", JOptionPane.OK_OPTION);
                    return;
                }

                txtMessageSent.setText(AES.decrypt(strLastMsgSent, txtKey.getText()));
            }
        });
        winDecipheredModule.add(btnUpdate);

        winDecipheredModule.setLayout(null);
        winDecipheredModule.setResizable(false);
        winDecipheredModule.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // If Click into The Red Button => End The Processus
    }

    @Override
    public boolean getMalwareIP(String malwareIP) throws RemoteException {
        strMalwareIP = malwareIP;

        txtIPTransmitter.setText(strMalwareIP);
        return true;
    }

    @Override
    public boolean getSourceIP(String sourceIP) throws RemoteException {
        strSourceIP = sourceIP;
        
        try {
            String connectLocation = "//" + strSourceIP + ":1099/SourceRMI";
            try {
                System.out.println("Connecting to source at : " + connectLocation);
                lookup4Source = (SourceService) Naming.lookup(connectLocation);
            } catch (NotBoundException ex) {
                Logger.getLogger(Malware.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(Malware.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Malware.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public String getScheme4Malware() throws RemoteException {
        return strBase4CC;
    }
    
    @Override
    public boolean getKeyGenerated(String keyGenerated) throws RemoteException {
        strKeyGenerated = keyGenerated;

        // txtKey.setText(strKeyGenerated);
        return true;
    }

    @Override
    public boolean getLastSentMsg(String lastSentMsg) throws RemoteException {
        strLastMsgSent = lastSentMsg;

        // txtLastMsgSent.setText(lastSentMsg);
        return true;
    }

    @Override
    public boolean getLastMessage(String messagesent) throws RemoteException {
        strMsgSent = messagesent;

        // txtMessageSent.setText(messagesent);
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        try { // special exception handler for registry creation
            LocateRegistry.createRegistry(1199);
            System.out.println("java RMI registry created at port 1199");
        } catch (RemoteException e) {
            // do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
        CCService server = new CommandControl();
        
        String hostname = "0.0.0.0";
        String bindLocation = "//" + hostname + ":1199/CCRMI";
        try {
            System.setProperty("java.security.policy","file:./security.policy");
            Naming.bind(bindLocation, server);
            System.out.println("CC Server is ready at:" + bindLocation);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("CC Server failed: " + e);
        }
        System.err.println("CC Server ready");
    }
}
