/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modules.SecurityRSA;

/**
 *
 * @author Patrick
 */
public class RSAEncryption {
    public RSAEncryption(String path, String mac) throws IOException, Exception{

        
        BuildRSA br = new BuildRSA();
        br.start();
        
        while (g.thread[0]) {            
            Thread.sleep(10);
        }
        
        byte [] document = g.fi.ReadBytes(path);
        
        Algorithm a0 = new Algorithm(1, null, mac.getBytes(), 0, true);
        a0.start();
        
        while (g.thread[0]) {            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(RSAEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String target = a0.resources;
        
        int pack = 0;
        if(g.fi.findFile(g.localkeydir+mac+".pub")){
            g.sr.loadPublic(g.localkeydir+mac+".pub");
            int size = g.sr.getPu().getEncoded().length/2-11;
            ArrayList<Byte>b = new ArrayList<>();
            byte[] temp;
            int i = 0;
            int iterations = (int) Math.ceil(Double.parseDouble(document.length+"")/size);
            
            while (i<iterations-1) {
                temp = Arrays.copyOfRange(document, i*size, ((i+1)*size));
                byte [] encrypted = g.sr.encrypt(g.sr.getPu(), temp);
                for (byte c : encrypted) {
                    b.add(c);
                }
                i++;
            }
            
            temp = Arrays.copyOfRange(document, i*size, document.length);
            byte [] encrypted = g.sr.encrypt(g.sr.loadPublic(g.localkeydir+mac+".pub"), temp);
            pack = encrypted.length;
            for (byte c : encrypted) {
                b.add(c);
            }
            
            
            byte [] data = new byte [b.size()];
            
            i = 0;
            for (byte c : b) {
                data[i] = c;
                i++;
            }
            g.fi.WriteFile(g.localdecryptdir+g.fi.getFileName(path)+".old", target+pack+"P"+g.fo.base64Encode(data));
            JOptionPane.showMessageDialog(null, "Done, file encrypted at "+g.localdecryptdir+g.fi.getFileName(path)+".old");
            Desktop.getDesktop().open(new File(g.localdecryptdir));
        }
        
        
    }
}
