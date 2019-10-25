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
public class DESDecryption {
    public DESDecryption(String path, String key) throws IOException, Exception{
        
        byte [] document_3x = g.fi.ReadBytes(path);
        
        while(key.length()<8){
            key+="0";
        }
        
        byte [] keyb = Arrays.copyOfRange(key.getBytes(), 0, 8);
        g.sd = new SecurityDES(keyb);
        
        String document = g.sd.decrypt(new String(document_3x));
        
        g.fi.WriteBytes(g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path)), g.fo.base64Decode(document));
        JOptionPane.showMessageDialog(null, "Done, decrypted file is located at "+g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path)));
        Desktop.getDesktop().open(new File(g.localdecryptdir+g.fi.getFileName(g.fi.getPathName(path))));
    }
}
