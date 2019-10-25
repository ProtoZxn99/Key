/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class Algorithm extends Thread{
    public int method;
    public String resources;
    public byte [] resourceb;
    public boolean encrypt = true;
    public int thread;
    public PublicKey pub;

    public Algorithm(int method, String resources, byte[] resourceb, int thread, boolean encrypt) {
        this.method = method;
        this.resources = resources;
        this.resourceb = resourceb;
        this.thread = thread;
        this.encrypt = encrypt;
        g.thread[thread] = true;
    }
    
    
    @Override
    public void run(){
        if(encrypt){
            if(method==1){
                try {
                    resources = g.sm.hash(resourceb);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(method==2){
                try {
                    resources = g.sd.encrypt(resources);
                } catch (Exception ex) {
                    Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(method==3){
                try {
                    resources = g.fo.base64Encode(g.sr.encrypt(resourceb));
                } catch (Exception ex) {
                    Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        else{
            if(method==1){
                resources = null;
            }
            else if(method==2){
                try {
                    resources = g.sd.decrypt(resources);
                } catch (Exception ex) {
                    Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(method==3){
                    try {
                        resourceb = g.sr.decrypt(g.fo.base64Decode(resources));
                    } catch (Exception ex) {
                        Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        g.thread[thread] = false;
    }
}
