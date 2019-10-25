/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class FindName extends Thread{
    public String sender; 
    public boolean senderright = false;
    public String resultname = null;
    public FindName(String sender){
        this.sender = sender;
        g.thread[0] = true;
    }
    @Override
    public void run(){
        String [] datapub = g.fi.getAllFiles(g.localkeydir);
            for (String string : datapub) {
                try {
                    if(g.sm.hash(g.fi.getPathName(g.fi.getFileName(string))).equals(sender)){
                        resultname = string;
                        senderright = true;
                    }
                } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(FindName.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            g.thread[0] = false;
            
    }
}
