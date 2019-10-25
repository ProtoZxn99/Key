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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;


public class SecurityRSA {
//    public static void main(String [] args) throws Exception {
//        // generate public and private keys
//        KeyPair keyPair = buildKeyPair();
//        PublicKey pubKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//        
//        // sign the message
//        byte [] signed = encrypt(privateKey, "This is a secret message");     
//        System.out.println(new String(signed));  // <<signed message>>
//        
//        // verify the message
//        byte[] verified = decrypt(pubKey, signed);                                 
//        System.out.println(new String(verified));     // This is a secret message
//    }

//        Generate public key and private key
//        KeyPair keyPair = buildKeyPair();
//        PublicKey pubKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
    private PrivateKey pr;
    private PublicKey pu;
    int size = 8192;
    
    public SecurityRSA() throws NoSuchAlgorithmException{
        KeyPair k = buildKeyPair();
        this.pr = k.getPrivate();
        this.pu = k.getPublic();
    }
    
    public SecurityRSA(int KeySize) throws NoSuchAlgorithmException{
        this.size = KeySize;
        KeyPair k = buildKeyPair();
        this.pr = k.getPrivate();
        this.pu = k.getPublic();
    }
    
    public SecurityRSA(String keyPath) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException{
        this.pr = loadPrivate(keyPath+".key");
        this.pu = loadPublic(keyPath+".pub");
    }
    
    public SecurityRSA(KeyPair k) throws NoSuchAlgorithmException{
        this.pr = k.getPrivate();
        this.pu = k.getPublic();
    }
    
    public SecurityRSA(PrivateKey pr, PublicKey pu) throws NoSuchAlgorithmException{
        this.pr = pr;
        this.pu = pu;
    }
    
    public void setPr(PrivateKey pr){
        this.pr = pr;
    }
    
    public void setPu(PublicKey pu){
        this.pu = pu;
    }
    
    public PrivateKey getPr() {
        return pr;
    }

    public PublicKey getPu() {
        return pu;
    }
    
    public KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = size;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);      
        return keyPairGenerator.genKeyPair();
    }
    
//      Sign the message with private key
    public byte[] encrypt(PrivateKey privateKey, String message) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  

        return cipher.doFinal(message.getBytes());  
    }
    
    public byte[] encrypt(String message) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA"); 
        cipher.init(Cipher.ENCRYPT_MODE, pr);
          
        return cipher.doFinal(message.getBytes());  
    }
    
    public byte[] encrypt(byte [] message) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA"); 
        cipher.init(Cipher.ENCRYPT_MODE, pr);
          
        return cipher.doFinal(message);  
    }
    
    public byte[] encrypt(PublicKey publicKey, byte [] message) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA"); 
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
          
        return cipher.doFinal(message);  
    }
    
//      Verify message
    public byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        
        return cipher.doFinal(encrypted);
    }
    
    public byte[] decrypt(byte [] encrypted) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, pr);
        
        return cipher.doFinal(encrypted);
    }
    
    public byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        return cipher.doFinal(encrypted);
    }
  
    public void savePrivate(String path, String name) throws IOException{
        FileOutputStream out = new FileOutputStream(path+name+".key");
        out.write(pr.getEncoded());
        out.close();
    }
    
    public PrivateKey loadPrivate(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        Path p = Paths.get(path);
        byte[] bytes = Files.readAllBytes(p);

        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pvt = kf.generatePrivate(ks);
        return pvt;
    }
    
    public void savePublic(String path, String name) throws IOException{
        FileOutputStream out1 = new FileOutputStream(path+name+".pub");
        out1.write(pu.getEncoded());
        out1.close();
    }
    
    public PublicKey loadPublic(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        Path p = Paths.get(path);
        byte[] b = Files.readAllBytes(p);

        /* Generate public key. */
        X509EncodedKeySpec ks = new X509EncodedKeySpec(b);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pub = kf.generatePublic(ks);
        return pub;
    }
}
