/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import javax.crypto.SecretKey;
import modules.FileUtilities;
import modules.FormatUtilities;
import modules.NetworkUtilities;
import modules.SecurityDES;
import modules.SecurityMD5;
import modules.SecurityRSA;

/**
 *
 * @author Patrick
 */
public class g {
   public static String mac;
   public static String name;
   public static String localkeydir = System.getProperty("user.dir")+"\\key\\";
   public static String localcontactdir = System.getProperty("user.dir")+"\\contact\\";
   public static String localdecryptdir = System.getProperty("user.dir")+"\\file\\";
   
   public static boolean thread[] = {false, false, false, false};
   
   public static FileUtilities fi = new FileUtilities();
   public static FormatUtilities fo = new FormatUtilities();
   public static NetworkUtilities ne = new NetworkUtilities();
   
   public static SecurityDES sd;
   public static SecurityMD5 sm;
   public static SecurityRSA sr;
   
   public static String file;
   
   public static String path;
   
   public static boolean pane = false;
   
   public static String OpenKeyWarning = "Please make sure your private key and public key are named with the same text, with .key and .pub extension respectively.\nPlease select either said RSA private or public key";
}
