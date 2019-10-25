/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DS;

import java.io.Serializable;

/**
 *
 * @author Patrick
 */
public class Contact implements Comparable<Object>, Serializable{
    private String name;
    private String mac;
    
    public Contact(String name, String mac){
        this.name = name;
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    @Override
    public int compareTo(Object o) {
        Contact c = (Contact)o;
        return this.getName().compareTo(c.getName());
    }
    
    @Override
    public String toString() {
        return name+" ("+mac+")";
    }
}
