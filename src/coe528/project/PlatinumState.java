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
public class PlatinumState implements CustomerState {
        Customer customer;
    
  

   public PlatinumState() {
    }
    
   

     public void changeState(Customer customer){
        if(customer.getBalance()>=20000){
            customer.setState(new PlatinumState());
        }
        if(customer.getBalance()<20000 && customer.getBalance()>=10000){
            customer.setState(new GoldState());

        }       
        if(customer.getBalance()<10000){
            customer.setState(new SilverState());

        }
     }
 
    public void purchase(double cost, Customer customer){
                customer.getBalance();

        customer.setBalance(customer.getBalance()-cost);
        
    }
        @Override
    public String toString(){
        return "platinum";
    }
    

    
    
}


