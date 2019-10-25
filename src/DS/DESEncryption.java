/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import modules.SecurityDES;

/**
 *
 * @author Patrick
 */
public class DESEncryption {
    public DESEncryption(String path, String key) throws IOException, Exception{
        
        byte [] document = g.fi.ReadBytes(path);
        
        while(key.length()<8){
            key+='0';
        }
        
        byte [] keyb = Arrays.copyOfRange(key.getBytes(), 0, 8);
        g.sd = new SecurityDES(keyb);
        
        String encrypted = g.sd.encrypt(g.fo.base64Encode(document));
        
        g.fi.WriteFile(g.localdecryptdir+g.fi.getFileName(path)+".old", encrypted);
        
        JOptionPane.showMessageDialog(null, "Done, file encrypted at "+g.localdecryptdir+g.fi.getFileName(path)+".old");
        Desktop.getDesktop().open(new File(g.localdecryptdir));
    }
}
