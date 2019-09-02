/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Jincowboy
 */
public interface CCService extends Remote {
    boolean getMalwareIP(String malwareIP) throws RemoteException;
    boolean getSourceIP(String sourceIP) throws RemoteException;
    String getScheme4Malware() throws RemoteException;
    boolean getKeyGenerated(String keyGenerated) throws RemoteException;
    boolean getLastSentMsg(String lastSentMsg) throws RemoteException;
    boolean getLastMessage(String lastMessage) throws RemoteException;
}
