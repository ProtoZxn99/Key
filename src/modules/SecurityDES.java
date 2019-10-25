package modules;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrick
 */
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

    public class SecurityDES {
      SecretKey key;
      Cipher ecipher;
      Cipher dcipher;

// public static void main(String[] argv) throws Exception {
//    SecretKey key = KeyGenerator.getInstance("SecurityDES").generateKey();
//    DesEncrypter encrypter = new DesEncrypter(key);
//    String encrypted = encrypter.encrypt("Don't tell anybody!");
//    String decrypted = encrypter.decrypt(encrypted);
//  }
    public SecurityDES() throws Exception {
        key = buildKey();
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
     }
  
    public SecurityDES(SecretKey key) throws Exception {
      this.key = key;
      ecipher = Cipher.getInstance("DES");
      dcipher = Cipher.getInstance("DES");
      ecipher.init(Cipher.ENCRYPT_MODE, this.key);
      dcipher.init(Cipher.DECRYPT_MODE, this.key);
    }
    
    public SecurityDES(byte [] key) throws Exception {
        this.key = byteKey(key);
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, this.key);
        dcipher.init(Cipher.DECRYPT_MODE, this.key);
     }

    public SecretKey byteKey(byte [] key){
        return new SecretKeySpec(key, "DES");
    }
    
    public SecretKey getKey() {
        return key;
    }
  
    public String encrypt(String str) throws Exception {
      // Encode the string into bytes using utf-8
      byte[] utf8 = str.getBytes("UTF8");

      // Encrypt
      byte[] enc = ecipher.doFinal(utf8);

      // Encode bytes to base64 to get a string
      return new sun.misc.BASE64Encoder().encode(enc);
    }
//    
//    public byte [] encrypt(byte [] b) throws Exception {
//      // Encode the string into bytes using utf-8
//      byte[] utf8 = b;
//
//      // Encrypt
//      byte[] enc = ecipher.doFinal(utf8);
//
//      return enc;
//    }

    public String decrypt(String str) throws Exception {
      // Decode base64 to get bytes
      byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

      byte[] utf8 = dcipher.doFinal(dec);

      // Decode using utf-8
      return new String(utf8, "UTF8");
    }
    
    public byte [] decrypt(byte [] b) throws Exception {
      // Decode base64 to get bytes
      byte[] dec = b;

      byte[] res = dcipher.doFinal(dec);

      return res;
    }
//    }
    
    public SecretKey buildKey() throws NoSuchAlgorithmException{
      KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
      SecretKey Key = keygenerator.generateKey();   
      return Key;
    }
}

