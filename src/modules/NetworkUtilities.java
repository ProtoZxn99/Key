/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author Patrick
 */
public class NetworkUtilities {
    public Inet4Address getLocalIP4() throws UnknownHostException{
        Inet4Address ip = (Inet4Address) Inet4Address.getLocalHost();
        return ip;
    }
    
    public byte [] getMAC() throws UnknownHostException, SocketException{
        InetAddress ip = (InetAddress)getLocalIP4();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        return mac;
    }
    
    public String getMACAddress() throws UnknownHostException, SocketException{
        byte [] mac = getMAC();
        StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
            }
        return sb.toString();
    }
    
    public ArrayList<String> getAllMACAddress() throws SocketException {
    final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        ArrayList<String> list = new ArrayList<>();
        while (e.hasMoreElements()) {
            final byte [] mac = e.nextElement().getHardwareAddress();
            if (mac != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++)
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    list.add(sb.toString());
            }
        }
        return list;
    }
}
