/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Patrick
 */
public class DigitalSignatureEncryption {
    
    public DigitalSignatureEncryption(String path, String mac){
        try {
            Algorithm a0;
            Algorithm a1;
            Algorithm a2;
            
            
            BuildRSA b = new BuildRSA();      
            b.start();
            
            
            byte [] document = g.fi.ReadBytes(path);
            
            a0 = new Algorithm(1, null, document, 1, true);
            a0.start();
            
            a1 = new Algorithm(1, null, g.mac.getBytes(), 2, true);
            a1.start();
            
            a2 = new Algorithm(1, null, mac.getBytes(), 3, true);
            a2.start();
            
            
            while(g.thread[0] || g.thread[1]){
                Thread.sleep(10);
            }
            
            
            
            
            byte [] document_hash = a0.resources.getBytes();
            
            a0 = new Algorithm(3, null, document_hash, 0, true);
            a0.start();
            
            String content = g.fo.base64Encode(document);
            
            while(g.thread[0]){
                Thread.sleep(10);
            }
            
            String header = a0.resources;
            
            String header_length = header.length() + "P";
            
            String document_1 = header_length+header+content;
            
            
            
            
            a0 = new Algorithm(2, document_1, null, 0, true);
            a0.start();
            
            while(g.thread[0] || g.thread[2] ||g.thread[3]){
                Thread.sleep(10);
            }
            
            String sender = a2.resources;
            
            String receiver = a1.resources;
            
            content = a0.resources;
           
            if(g.fi.findFile(g.localkeydir+mac+".pub")){
                header = g.fo.base64Encode(g.sr.encrypt(g.sr.loadPublic(g.localkeydir+mac+".pub"), g.sd.getKey().getEncoded()));
            }
            
            header_length = header.length()+"P";
            
            
            String document_2 = sender+receiver+header_length+header+content;
            
            g.fi.WriteFile(g.localdecryptdir+g.fi.getFileName(path)+".old", document_2);
            JOptionPane.showMessageDialog(null, "Done, file encrypted at "+g.localdecryptdir+g.fi.getFileName(path)+".old");
            Desktop.getDesktop().open(new File(g.localdecryptdir));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error");
        }
    }
}
