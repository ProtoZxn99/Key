/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Patrick
 */
public class FileUtilities {

    public void WriteFile(String fileName, String content) throws FileNotFoundException, IOException{
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.print(content);
        writer.close();
    }
    
    public void WriteFile(String fileName, byte [] content) throws FileNotFoundException, IOException{
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.print(content);
        writer.close();
    }
    
    //Take text 
    public ArrayList<String> ReadFile(String fileName) throws FileNotFoundException, IOException{
        ArrayList<String>data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        String strLine;

        //Read FileUtilities Line By Line
        while ((strLine = br.readLine()) != null)   {
          // Print the content on the console
          data.add(strLine);
        }

        //Close the input stream
        br.close();
        
        return data;
    }
    
    public String ReadFileFirstLine(String fileName) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        String strLine = br.readLine();

        //Close the input stream
        br.close();
        
        return strLine;
    }
    
    public String GetFilePath(JFrame j){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"))); 
        int result = fileChooser.showOpenDialog(j); 
        if (result == JFileChooser.APPROVE_OPTION) { 
            selectedFile = fileChooser.getSelectedFile(); 
        }
        return selectedFile.getAbsolutePath();
    }
    
    public String GetFilePath(JFrame j, String defaultpath){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setCurrentDirectory(new File(defaultpath)); 
        int result = fileChooser.showOpenDialog(j); 
        if (result == JFileChooser.APPROVE_OPTION) { 
            selectedFile = fileChooser.getSelectedFile(); 
        }
        return selectedFile.getAbsolutePath();
    }
    
    public String GetLocalFilePath(JFrame j){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); 
        int result = fileChooser.showOpenDialog(j); 
        if (result == JFileChooser.APPROVE_OPTION) { 
            selectedFile = fileChooser.getSelectedFile(); 
        }
        return selectedFile.getAbsolutePath();
    }
    
    public byte [] ReadBytes(String path) throws IOException{
        File file = new File(path);
        
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
        }
        byte[] bytes = bos.toByteArray();
        
        return bytes;
    }
    
    public void WriteBytes(String path, byte[]b){
        FileOutputStream fos = null;
        try {
            File f = new File(path);
            fos = new FileOutputStream(f);
            fos.write(b);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Save file to disk with specified file name
    public void saveArrayFile(String fileName, Object Content) throws IOException {
        ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        try {
            //Writing file to disk
            oout.writeObject(Content);
        } catch (Exception e) {
            throw new IOException("Unexpected error", e);
        } finally {
            oout.close();
        }
    }
    
    //Open specified file from disk
    public Object openArrayFile(String fileName) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        Object o;
        try {
            //Read file to an object
            o = ois.readObject();
        } catch (Exception e) {
            throw new IOException("Unexpected error", e);
        } finally {
            ois.close();
        }
        return o;
    }
    
    public String getFileName(String path){
        String name = (new File(path)).getName();
        return name;
    }
    
    public String getPathName(String path){
        if(path.lastIndexOf('.')>=0){
            path = path.substring(0,path.lastIndexOf('.'));
        }
        return path;
    }
    
    public String getExtension(String path){
        if(path.lastIndexOf('.')>=0){
            path = path.substring(path.lastIndexOf('.'));
        }
        return path;
    }
    
    public ArrayList<String> searchExtension(String dir, String extension){
        ArrayList<String>list = new ArrayList<>();
        try {
            File file = new File(dir);
            String data[] = file.list();
            for(int i=0; i<data.length; i++){
                String path = dir + data[i];
                File check = new File(path);
                if(check.isFile()){
                    if(path.endsWith(extension)){
                        list.add(data[i]);
                    }
                }
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public boolean findFile(String dir){
            boolean found = false;
            File tmp = new File(dir);
        return tmp.exists();
    }
    
    public String [] getAllFiles(String dir){
        java.io.File file = new java.io.File(dir);
        String data[] = file.list();
        return data;
    }
    
    public String [] getAllFilesNames(String dir){
        java.io.File file = new java.io.File(dir);
        String data[] = file.list();
        return data;
    }
    
    public boolean findLocalFile(String name){
            boolean found = false;
            File tmp = new File(System.getProperty("user.dir")+name);
        return tmp.exists();
    }
    
    public void copyFile(String source, String destination) throws IOException{
        File f = new File(source);
        Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
    }
    
    public void deleteFile(String path){
        File f = new File(path);
        f.delete();
    }
}
