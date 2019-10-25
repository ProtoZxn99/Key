/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modules.NetworkUtilities;
import modules.SecurityDES;
import modules.SecurityMD5;
import modules.SecurityRSA;

/**
 *
 * @author Patrick
 */
public class Load extends Thread{
    @Override
    public void run(){
        try {
            ArrayList<String>finding = (ArrayList)g.fi.searchExtension(g.localkeydir, ".key");
            if(finding.size()>0){
                g.mac = g.fi.getPathName(finding.get(0));
            }
            else{
                try {
                    g.mac = g.ne.getAllMACAddress().get(0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Atleast a networking device is needed for the program to operate.");
                    System.exit(0);
                }
                if(g.mac == null){
                    JOptionPane.showMessageDialog(null, "Atleast a networking device is needed for the program to operate.");
                    System.exit(0);
                }
                else{
                    g.fi.WriteFile(g.localcontactdir+"/"+g.mac+".txt", "local");
                }
            }
            
            g.name = g.fi.ReadFileFirstLine(g.localcontactdir+"/"+g.mac+".txt");
            
            NewJFrame1.setjTextField1(g.name);
            
            g.sd = new SecurityDES();
            g.sm = new SecurityMD5();
            g.sr = new SecurityRSA((PrivateKey)null,(PublicKey)null);
                
            g.thread[0]=false;
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
                Logger.getLogger(Load.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
