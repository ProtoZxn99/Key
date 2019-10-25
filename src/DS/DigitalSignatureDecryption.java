/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;
import modules.SecurityDES;

/**
 *
 * @author Patrick
 */
public class DigitalSignatureDecryption {
    public DigitalSignatureDecryption(String path){
        try {
            
            Algorithm a0;
            
            while(g.thread[0]||g.thread[1]){
                Thread.sleep(10);
            }
            
            BuildRSA b = new BuildRSA();      
            b.start();
            
            byte [] document_3_bx = g.fi.ReadBytes(path);
            
            String document_3x = new String(document_3_bx);
            
            String [] document_2x = new String[5];
            
            document_2x[0] = document_3x.substring(0, 32);
            
            document_2x[1] = document_3x.substring(32, 64);
            
            FindName fn = new FindName(document_2x[1]);
            fn.start();
            
            a0 = new Algorithm(1, null, g.mac.getBytes(), 1, true);
            a0.start();
            
            String temp = document_3x.substring(64);
            
            String [] tmp = temp.split("P", 2);
            
            document_2x[2] = tmp[0];
            
            document_2x[3] = tmp[1].substring(0, Integer.parseInt(tmp[0]));
            
            document_2x[4] = tmp[1].substring(Integer.parseInt(tmp[0]));
            
            while (g.thread[0]||g.thread[1]) {                
                Thread.sleep(10);
            }
            
            boolean receiverright = ((a0.resources).equals(document_2x[0]))?true:false;
            
            boolean senderright = fn.senderright;
            
            if(senderright&&receiverright){
                byte[] keyfor2x = g.sr.decrypt(g.sr.getPr(), g.fo.base64Decode(document_2x[3]));
                
                g.sd = new SecurityDES(keyfor2x);


                temp = g.sd.decrypt(document_2x[4]);

                tmp = temp.split("P", 2);

                String [] document_1x = new String[3];

                document_1x[0] = tmp[0];
                document_1x[1] = tmp[1].substring(0, Integer.parseInt(tmp[0]));;
                document_1x[2] = tmp[1].substring(Integer.parseInt(tmp[0]));;

                byte [] content_x = g.fo.base64Decode(document_1x[2]);

                a0 = new Algorithm(1, null, document_1x[2].getBytes(), 0, true);
                a0.start();

                String document_hash_x = null;
                if(g.fi.findFile(g.localkeydir+fn.resultname)){
                    document_hash_x = new String(g.sr.decrypt(g.sr.loadPublic(g.localkeydir+fn.resultname), g.fo.base64Decode(document_1x[1])));
                }

                while(g.thread[0]){
                    Thread.sleep(10);
                }

                boolean hashright = false;
                if(document_hash_x.equals(g.sm.hash(content_x))){
                    hashright = true;
                }
                if(hashright){
                    g.fi.WriteBytes(g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path)), content_x);
                    JOptionPane.showMessageDialog(null, "Done, decrypted file is located at "+g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path)));
                    Desktop.getDesktop().open(new File(g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path))));
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error");
        }
    }
}
