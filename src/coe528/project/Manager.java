/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 *
 * @author hrutvik
 */
public class Manager {

    private String user = "admin";
    private String pass = "admin";
    private String role = "manger";

    Driver d = new Driver();


    public boolean authenticate(String user, String pass) { //log in
        if (user == "admin" && pass == "admin") {
            return true;
        } else {
            return false;
        }
    }

    public void addCustomer(String username, String password) {

        d.writeFile(new File(username + ".csv"), username, password, "customer", 100, "silver");

    }

    public void deleteCustomer(String customerUsername) {

        String fileName = customerUsername + ".csv";
        File file = new File(fileName);

        file.delete();

    }

    
    
}
