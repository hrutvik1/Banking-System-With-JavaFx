/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

/**
 *
 * @author hrutvik
 */
public interface CustomerState {
 


    public void changeState(Customer customer);

    public void purchase(double cost, Customer customer);

     
}
