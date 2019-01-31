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
public class Customer {
    
    // OVERVIEW: Responsibility of this class is to represent the Customer object 
    // and customers' behaviour i.e withdraw/deposit money, update balance and purchase
    // This is a mutable class.
      
    // The Rep Invariant is:
    // balance>=0
    
    // A typical customer has a username, password, role, balance, and state/level 
    // The Abstraction Function, AF(c) is:
    // AF(c)={c.username,c.password, c.role, c.balance, c.state}

    
    private CustomerState state; 
    private static double balance;
    private String username;
    private String password;
    private String role;

    public Customer(String username, String password, String role, double balance, String initialState) {
        this.state = new SilverState();    //initial state set when maanger is creating customer (so its not defult null and blowing up)
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.role = role;
    } 
    
    
//REQUIRES:deposit>=0
//EFFECTS:c hanges balance based on 'deposit' amount
//MODIFIES: balance
    public void depositMoney(double deposit) {
        balance = balance + deposit;
        changeState();
    }

//REQUIRES:withdraw>=0
//EFFECTS: changes balance based on 'withdraw' amount
//MODIFIES: balance
    public void withdrawMoney(double withdraw) {
        balance = balance - withdraw;
        changeState();
    }

//EFFECTS: returns balance 
    public double getBalance() {
        return balance;
    }

//REQUIRES: b>=0
//EFFECTS: sets balance to 'b'
//MODIFIES: balance
    public void setBalance(double b) {
        balance = b;
    }

//REQUIRES:cost>=0
//EFFECTS: allows concreate classes to make purchase based on 'cost'
//MODIFIES: balance
    public void purchase(double cost) {
        this.state.purchase(cost, this);
        changeState();

    }

//EFFECTS: Sets 'state' to a new 'customerState'  
//MODIFIES: the this.state (CustomerState object)
    public void setState(CustomerState customerState) {
        this.state = customerState;
    }

//EFFECTS: allows concreate classes to change state
    public void changeState() { // changing of states is done in concrete classes
        this.state.changeState(this);
    }
    
//EFFECTS: returns string representation of current state
    public String currentState(){
        return this.state.toString();
    }

//EFFECTS:returns username string
    public String getUsername() {
        return username;
    }

//EFFECTS: returns password string
    public String getPassword() {
        return password;
    }
//EFFECTS: Returns true if the rep 
// invariant holds for this;
// otherwise returns false
    public boolean repOK(){        
        if(balance>=0){
        return true;
        }
        else{
        return false;
        }
    }
    
//EFFECTS: implements abstraction function. Return's string representation of customer object
    public String toString(){
        return "customer's username is: "+ username+" customer password is: "+ password+" customer role is: "+role+" customer's balance is: "+ balance +" customer's state is: "+state;
    }
}
