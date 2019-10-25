/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modules.SecurityRSA;

/**
 *
 * @author Patrick
 */
public class BuildRSA extends Thread{
    @Override
    public void run(){
        g.thread[0] = true;
        if(g.fi.findFile(g.localkeydir+g.mac+".key")&&g.fi.findFile(g.localkeydir+g.mac+".pub")){
            try {
                g.sr = new SecurityRSA(g.localkeydir+g.mac);
            } catch (IOException ex) {
                Logger.getLogger(BuildRSA.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(BuildRSA.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(BuildRSA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                g.sr = new SecurityRSA(8192);
                g.sr.savePrivate(g.localkeydir, g.mac);
                g.sr.savePublic(g.localkeydir, g.mac);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(BuildRSA.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BuildRSA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        g.thread[0] = false;
    }
}
