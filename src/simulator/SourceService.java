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
public interface SourceService extends Remote {
    boolean getKeyLength(String malwareKeyLength) throws RemoteException;
    String getKeyLength4CC() throws RemoteException;
    boolean getMalwareIP(String malwareIP) throws RemoteException;
    boolean getCCIP(String ccIP) throws RemoteException;
    boolean getSending(String sending) throws RemoteException;
    boolean getTotalTime(String totaltime) throws RemoteException;
    String getEntangledParticles(int MCC) throws RemoteException;
    boolean afterParticlesSendingDone(int executetime, int type) throws RemoteException;
    boolean isInterlacedParticlesSent() throws RemoteException;
}
