/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Jincowboy
 */

public class Source extends UnicastRemoteObject implements SourceService{
    private static final long serialVersionUID = 1L;

    JFrame window; // This is Main Window

    JTextField txtOrderNumber;
    JLabel lblOrderNumber;

    JPanel panParameters;

    JTextField txtIpMalware;
    JLabel lblIpMalware;
    JTextField txtIpCC;
    JLabel lblIpCC;
    JTextField txtInitialKeySize;
    JLabel lblInitialKeySize;

    JPanel panResults;
    
    JTextField txtSending;
    JLabel lblSending;
    JTextField txtTotalTime;
    JLabel lblTotalTime;
    
    JPanel panProcess;

    JLabel lblReceptionOrder;
    JLabel imgReceptionOrder;
    JLabel lblGeneratingIntertwinedParticles;
    JLabel imgGeneratingIntertwinedParticles;
    JLabel lblEstablishingConnectionWithMalware;
    JLabel imgEstablishingConnectionWithMalware;
    JLabel lblEstablishingConnectionWithCC;
    JLabel imgEstablishingConnectionWithCC;
    JLabel lblOpeningCCChannel;
    JLabel imgOpeningCCChannel;
    JLabel lblOpeningMalwareChannel;
    JLabel imgOpeningMalwareChannel;
    JLabel lblSendingParticles;
    JLabel imgSendingParticles;

    JPanel panInterlacedParticles;

    JTable tblInterlacedParticles;
    int ROW_COUNT = 2;
    int COL_COUNT = 33;

    String [] strInterlacedParticles = {"", ""};
    Image checkimg = null;

    String [] particles = {"|↓>", "|↑>"};

    int totaltime = 0;
    int issentparticles = 0;

    Source() throws RemoteException{
        super();
        
        window = new JFrame("Source - Quantum Communication Protocol E91");
        window.setSize(600, 480); // Height And Width Of Window
        window.setLocationRelativeTo(null); // Move Window To Center

        lblOrderNumber = new JLabel("Order Number");
        lblOrderNumber.setBounds(30, 20, 90, 30);
        // lblOrderNumber.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        window.add(lblOrderNumber);

        txtOrderNumber = new JTextField();
        txtOrderNumber.setBounds(125, 20, 80, 30);
        txtOrderNumber.setEnabled(false);
        // txtOrderNumber.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        // txtOrderNumber.setEnabled(false);
        window.add(txtOrderNumber);

        Border borderPara = BorderFactory.createTitledBorder("Parameters");
        panParameters = new JPanel();
        panParameters.setBorder(borderPara);
        panParameters.setBounds(30,50,200,150);
        panParameters.setLayout(null);
        
        lblIpMalware = new JLabel("Ip Malware");
        lblIpMalware.setBounds(10,20,80,30);
        panParameters.add(lblIpMalware);

        txtIpMalware = new JTextField();
        txtIpMalware.setBounds(95,20,90,30);
        txtIpMalware.setEnabled(false);
        panParameters.add(txtIpMalware);

        lblIpCC = new JLabel("Ip C&C");
        lblIpCC.setBounds(10,60,50,30);
        panParameters.add(lblIpCC);

        txtIpCC = new JTextField();
        txtIpCC.setBounds(65,60,120,30);
        txtIpCC.setEnabled(false);
        panParameters.add(txtIpCC);

        lblInitialKeySize = new JLabel("Initial Key Size");
        lblInitialKeySize.setBounds(10,100,100,30);
        panParameters.add(lblInitialKeySize);

        txtInitialKeySize = new JTextField();
        txtInitialKeySize.setBounds(115,100,70,30);
        txtInitialKeySize.setEnabled(false);
        panParameters.add(txtInitialKeySize);

        window.add(panParameters);

        Border borderResu = BorderFactory.createTitledBorder("Results");
        panResults = new JPanel();
        panResults.setBorder(borderResu);
        panResults.setBounds(30,210,200,100);
        panResults.setLayout(null);

        lblSending = new JLabel("Sending");
        lblSending.setBounds(10,20,50,30);
        panResults.add(lblSending);

        txtSending = new JTextField();
        txtSending.setBounds(65,20,70,30);
        txtSending.setEnabled(false);
        panResults.add(txtSending);

        lblTotalTime = new JLabel("Total Time(ms)");
        lblTotalTime.setBounds(10,60,100,30);
        panResults.add(lblTotalTime);

        txtTotalTime = new JTextField();
        txtTotalTime.setBounds(115,60,70,30);
        txtTotalTime.setEnabled(false);
        panResults.add(txtTotalTime);

        window.add(panResults);

        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("resources/check.png"));
        } catch (IOException ex) {
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;

        Border borderProc = BorderFactory.createTitledBorder("Process");
        panProcess = new JPanel();
        panProcess.setBorder(borderProc);
        panProcess.setBounds(240,50,330,260);
        panProcess.setLayout(null);

        lblReceptionOrder = new JLabel("Reception Order");
        lblReceptionOrder.setBounds(10,20,100,25);
        panProcess.add(lblReceptionOrder);

        imgReceptionOrder = new JLabel("");
        imgReceptionOrder.setBounds(250,20,70,25);
        // imgReceptionOrder.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgReceptionOrder);

        lblGeneratingIntertwinedParticles = new JLabel("Generating Intertwined Particles");
        lblGeneratingIntertwinedParticles.setBounds(10,55,200,25);
        panProcess.add(lblGeneratingIntertwinedParticles);

        imgGeneratingIntertwinedParticles = new JLabel("");
        imgGeneratingIntertwinedParticles.setBounds(250,55,70,25);
        // imgGeneratingIntertwinedParticles.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgGeneratingIntertwinedParticles);

        lblEstablishingConnectionWithMalware = new JLabel("Establishing Connection With Malware");
        lblEstablishingConnectionWithMalware.setBounds(10,90,250,25);
        panProcess.add(lblEstablishingConnectionWithMalware);

        imgEstablishingConnectionWithMalware = new JLabel("");
        imgEstablishingConnectionWithMalware.setBounds(250,90,70,25);
        // imgEstablishingConnectionWithMalware.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgEstablishingConnectionWithMalware);

        lblEstablishingConnectionWithCC = new JLabel("Establishing Connection With C&C");
        lblEstablishingConnectionWithCC.setBounds(10,125,200,25);
        panProcess.add(lblEstablishingConnectionWithCC);

        imgEstablishingConnectionWithCC = new JLabel("");
        imgEstablishingConnectionWithCC.setBounds(250,125,70,25);
        // imgEstablishingConnectionWithCC.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgEstablishingConnectionWithCC);

        lblOpeningCCChannel = new JLabel("Opening C&C Channel");
        lblOpeningCCChannel.setBounds(10,160,150,25);
        panProcess.add(lblOpeningCCChannel);

        imgOpeningCCChannel = new JLabel("");
        imgOpeningCCChannel.setBounds(250,160,70,25);
        // imgOpeningCCChannel.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgOpeningCCChannel);

        lblOpeningMalwareChannel = new JLabel("Opening Malware Channel");
        lblOpeningMalwareChannel.setBounds(10,195,150,25);
        panProcess.add(lblOpeningMalwareChannel);

        imgOpeningMalwareChannel = new JLabel("");
        imgOpeningMalwareChannel.setBounds(250,195,70,25);
        // imgOpeningMalwareChannel.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgOpeningMalwareChannel);

        lblSendingParticles = new JLabel("Sending Particles");
        lblSendingParticles.setBounds(10,230,150,25);
        panProcess.add(lblSendingParticles);

        imgSendingParticles = new JLabel("");
        imgSendingParticles.setBounds(250,230,70,25);
        // imgSendingParticles.setIcon(new ImageIcon(checkimg));
        panProcess.add(imgSendingParticles);

        window.add(panProcess);

        Border borderInter = BorderFactory.createTitledBorder("Interlaced Particles");
        panInterlacedParticles = new JPanel();
        panInterlacedParticles.setBorder(borderInter);
        panInterlacedParticles.setBounds(30,320,540,125);
        panInterlacedParticles.setLayout(new BorderLayout());

        COL_COUNT = 1;
        String [][] data4Table = new String[ROW_COUNT][COL_COUNT];
        String[] header = new String[COL_COUNT];

        header[0] = "";
        data4Table[0][0] = "Malware";
        data4Table[1][0] = "Command&Control";
        /* for ( int i=1; i<COL_COUNT; i++ ) {
            header[i] = String.valueOf(i);
            data4Table[0][i] = "";
            data4Table[1][i] = "";
        } */

        TableModel model = new DefaultTableModel(data4Table, header)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };

        tblInterlacedParticles = new JTable(model);
        tblInterlacedParticles.getColumnModel().getColumn(0).setPreferredWidth(120);
        final TableColumnModel columnModel = tblInterlacedParticles.getColumnModel();
        for (int column = 1; column < tblInterlacedParticles.getColumnCount(); column++) {
            int width = 30;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        tblInterlacedParticles.setRowHeight(0, 30);
        tblInterlacedParticles.setRowHeight(1, 30);
        tblInterlacedParticles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // tblInterlacedParticles.setAutoCreateColumnsFromModel(false);

        // tblInterlacedParticles.setBounds(0,0,100,100);
        panInterlacedParticles.add(new JScrollPane(tblInterlacedParticles));
        window.add(panInterlacedParticles);

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If Click into The Red Button => End The Processus
        window.setVisible(true);
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    @Override
    public String getKeyLength4CC() {
        return this.txtInitialKeySize.getText();
    }
    
    @Override
    public boolean getKeyLength(String malwareKeyLength) {
        this.txtInitialKeySize.setText(malwareKeyLength);

        totaltime = 0;
        
        imgGeneratingIntertwinedParticles.setIcon(new ImageIcon(checkimg));
        // Generating Interlaced Particles.
        this.strInterlacedParticles[0] = "";
        this.strInterlacedParticles[1] = "";
        for (int i=0; i<Integer.parseInt(malwareKeyLength); i++) {
            if ( getRandomBoolean() ) {
                this.strInterlacedParticles[0] = this.strInterlacedParticles[0]+"1";
                this.strInterlacedParticles[1] = this.strInterlacedParticles[1]+"0";
            }
            else {
                this.strInterlacedParticles[0] = this.strInterlacedParticles[0]+"0";
                this.strInterlacedParticles[1] = this.strInterlacedParticles[1]+"1";
            }
        }

        COL_COUNT = 1;
        String [][] data4Table = new String[ROW_COUNT][COL_COUNT];
        String[] header = new String[COL_COUNT];

        header[0] = "";
        data4Table[0][0] = "Malware";
        data4Table[1][0] = "Command&Control";
        /* for ( int i=1; i<COL_COUNT; i++ ) {
            header[i] = String.valueOf(i);
            data4Table[0][i] = "";
            data4Table[1][i] = "";
        } */

        TableModel model1 = new DefaultTableModel(data4Table, header)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };

        tblInterlacedParticles.setModel(model1);
        tblInterlacedParticles.getColumnModel().getColumn(0).setPreferredWidth(120);
        final TableColumnModel columnModel1 = tblInterlacedParticles.getColumnModel();
        for (int column = 1; column < tblInterlacedParticles.getColumnCount(); column++) {
            int width = 30;
            columnModel1.getColumn(column).setPreferredWidth(width);
        }
        tblInterlacedParticles.setRowHeight(0, 30);
        tblInterlacedParticles.setRowHeight(1, 30);
        tblInterlacedParticles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        COL_COUNT = Integer.parseInt(malwareKeyLength)+1;

        /* if ( tblInterlacedParticles.getColumnCount()>1 ) {
            while (tblInterlacedParticles.getColumnCount()>1) {
                TableColumn colToDelete = tblInterlacedParticles.getColumnModel().getColumn(tblInterlacedParticles.getColumnCount() - 1);
                tblInterlacedParticles.removeColumn(colToDelete);
                tblInterlacedParticles.validate();
            }
        } */

        DefaultTableModel model = (DefaultTableModel)tblInterlacedParticles.getModel();

        for ( int i=1; i<COL_COUNT; i++ ) {
            model.addColumn(String.valueOf(i));
        }
        
        tblInterlacedParticles.getColumnModel().getColumn(0).setPreferredWidth(120);
        final TableColumnModel columnModel = tblInterlacedParticles.getColumnModel();
        for (int column = 1; column < tblInterlacedParticles.getColumnCount(); column++) {
            int width = 30;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        tblInterlacedParticles.setRowHeight(0, 30);
        tblInterlacedParticles.setRowHeight(1, 30);
        tblInterlacedParticles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        return true;
    }

    @Override
    public boolean getMalwareIP(String malwareIP) {
        this.txtIpMalware.setText(malwareIP);

        txtOrderNumber.setText("0");
        imgReceptionOrder.setIcon(new ImageIcon(checkimg));
        imgEstablishingConnectionWithMalware.setIcon(new ImageIcon(checkimg));
        imgOpeningMalwareChannel.setIcon(new ImageIcon(checkimg));

        issentparticles = 0;
        return true;
    }

    @Override
    public boolean getCCIP(String ccIP) {
        this.txtIpCC.setText(ccIP);
        
        imgEstablishingConnectionWithCC.setIcon(new ImageIcon(checkimg));
        imgOpeningCCChannel.setIcon(new ImageIcon(checkimg));
        return true;
    }

    @Override
    public boolean getSending(String sending) {
        this.txtSending.setText(sending);
        return true;
    }

    @Override
    public boolean getTotalTime(String totaltime) {
        this.txtTotalTime.setText(totaltime);
        return true;
    }

    @Override
    public String getEntangledParticles(int MCC) {
        imgSendingParticles.setIcon(new ImageIcon(checkimg));
        getSending("OK");
        return this.strInterlacedParticles[MCC];
    }

    @Override
    public boolean afterParticlesSendingDone(int executetime, int type) {
        totaltime = totaltime+executetime;
        txtTotalTime.setText(String.valueOf(totaltime));

        DefaultTableModel model = (DefaultTableModel)tblInterlacedParticles.getModel();

        for ( int i=1; i<COL_COUNT; i++ ) {
            model.setValueAt(particles[Integer.parseInt(strInterlacedParticles[type].substring(i-1, i))], type, i);
        }
        
        issentparticles = issentparticles+type+1;
        return true;
    }

    @Override
    public boolean isInterlacedParticlesSent() {
        return (issentparticles==3);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        // Source mainobj = new Source();

        /* DatagramSocket socket = new DatagramSocket();
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);

        String ip = socket.getLocalAddress().getHostAddress(); */

        /* if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Security manager installed.");
        } else {
            System.out.println("Security manager already exists.");
        } */

        // SourceService server = new Source();
        // System.setProperty("java.rmi.server.hostname", ip);
        // String rmiObjectName = "rmi://" + ip + ":1099/SourceRMI";
        // String rmiObjectName = "//" + ip + "/SourceRMI";
        // Registry registry = LocateRegistry.createRegistry(1099);

        /* if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        } */
        // registry.rebind("SourceRMI", server);
        // Naming.rebind(rmiObjectName, server);
        /* SourceService server = new Source();
        SourceService stub = (SourceService) UnicastRemoteObject.exportObject((SourceService) server, 0);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("SourceService", stub);
        System.err.println("Server ready"); */
        try { // special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created at port 1099");
        } catch (RemoteException e) {
            // do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }

        /* if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        } */

        SourceService server = new Source();
        String hostname = "0.0.0.0";
        String bindLocation = "//" + hostname + ":1099/SourceRMI";
        try {
            System.setProperty("java.security.policy","file:./security.policy");
            Naming.bind(bindLocation, server);
            System.out.println("Source Server is ready at:" + bindLocation);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Source Server failed: " + e);
        }
        System.err.println("Source Server ready");
    }

}