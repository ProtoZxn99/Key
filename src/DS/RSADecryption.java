/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Patrick
 */
public class RSADecryption {
    public RSADecryption(String path) throws IOException, Exception{
        
        BuildRSA b = new BuildRSA();      
        b.start();
        
        String files = new String(g.fi.ReadBytes(path));
        
        String senderd = files.substring(0,32);
        String newd = files.substring(32);
        String [] dx = newd.split("P",2);
        int pack = Integer.parseInt(dx[0]);
        newd = dx[1];
        
        while (g.thread[0]) {            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(RSAEncryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        boolean sender = false;
        
        if(g.sm.hash(g.mac).equals(senderd)){
            sender = true;
        }
        
        int size = pack;
        
        byte [] data;
        ArrayList<Byte> newdoc = new ArrayList<>();
        if(sender){
            data = g.fo.base64Decode(newd);
            byte [] chunk = new byte[size];
            int i = 0;
            int ite = (int) Math.ceil(Double.parseDouble(data.length+"")/size);
            while (i<ite-1) {
                chunk = Arrays.copyOfRange(data, i*size, (i+1)*size);
                byte [] raw = g.sr.decrypt(g.sr.getPr(), chunk);
                for (byte c : raw) {
                    newdoc.add(c);
                }
                i++;
            }
            chunk = Arrays.copyOfRange(data, i*size, data.length);
            byte [] raw = g.sr.decrypt(g.sr.getPr(), chunk);
            for (byte c : raw) {
                newdoc.add(c);
            }
            byte [] done = new byte [newdoc.size()];
            i = 0;
            for (Byte a : newdoc) {
                done[i] = a;
                i++;
            }
            g.fi.WriteBytes(g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path)), done);
            JOptionPane.showMessageDialog(null, "Done, decrypted file is located at "+g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path)));
            Desktop.getDesktop().open(new File(g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path))));
        }
        else{
            JOptionPane.showMessageDialog(null, "error");
        }
    }
}
