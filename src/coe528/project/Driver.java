
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

/**
 *
 * @author hrutvik
 */
public class Driver extends Application {

    Stage window;
    Scene loginScene, managerScene, customerScene;
    private static String fileName;

    // static String fileName;
//String fileName="hrutvik.csv";

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        Driver d = new Driver();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLbl = new Label("Username:");
        GridPane.setConstraints(usernameLbl, 0, 0);

        Label passwordLbl = new Label("Password:");
        GridPane.setConstraints(passwordLbl, 0, 1);

        TextField usernameTf = new TextField();
        usernameTf.setPromptText("Username");
        GridPane.setConstraints(usernameTf, 1, 0);

        TextField passwordTf = new TextField();
        passwordTf.setPromptText("Password");
        GridPane.setConstraints(passwordTf, 1, 1);

        Button logInBtn = new Button();
        logInBtn.setText("Login");
        GridPane.setConstraints(logInBtn, 1, 2);

        logInBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String user = usernameTf.getText();
                String pass = passwordTf.getText();

                if (user.equals("admin") && pass.equals("admin")) {
                    window.setScene(managerScene);
                } else {

                    fileName = user + ".csv";

                    SilverState silverState = null;

                    File file = new File(fileName);

                    Customer currentCustomer = d.readFile(file);
                    currentCustomer.changeState();
                    customerScene(d, file, currentCustomer);
                    System.out.print(fileName);
                    window.setScene(customerScene);

                }

            }
        });

        grid.getChildren().addAll(usernameLbl, usernameTf, passwordLbl, passwordTf, logInBtn);
        loginScene = new Scene(grid, 300, 200);

        GridPane managerGrid = new GridPane();
        managerGrid.setPadding(new Insets(10, 10, 10, 10));
        managerGrid.setVgap(8);
        managerGrid.setHgap(10);

        Label managerLbl = new Label("Manager's Window");
        GridPane.setConstraints(managerLbl, 0, 0);

//add customer
        Button addCustomerBtn = new Button("Add Customer");
        GridPane.setConstraints(addCustomerBtn, 0, 1);
        addCustomerBtn.setOnAction(e2 -> addCustomer());
        //del customer
        Button deleteCustomerBtn = new Button("Deleted Customer");
        GridPane.setConstraints(deleteCustomerBtn, 0, 2);
        deleteCustomerBtn.setOnAction(e2 -> deleteCustomer());
        //logout 
        Button logOutBtn = new Button("Log out");
        GridPane.setConstraints(logOutBtn, 0, 3);
        logOutBtn.setOnAction(e2 -> window.close());

        managerGrid.getChildren().addAll(managerLbl, deleteCustomerBtn, addCustomerBtn, logOutBtn);
        managerScene = new Scene(managerGrid, 200, 200);

        window.setScene(loginScene);
        window.setTitle("BANK");
        window.show();

    }

    public void customerScene(Driver d, File file, Customer currentCustomer) {

        GridPane customerGrid = new GridPane();
        customerGrid.setPadding(new Insets(10, 10, 10, 10));
        customerGrid.setVgap(8);
        customerGrid.setHgap(10);

        //
// DISPLAY STATE
        Label currentAccountLbl = new Label(currentCustomer.getUsername() + "'s account");
        GridPane.setConstraints(currentAccountLbl, 0, 0);

        //logout
        Button logOutBtnCustomer = new Button("Log out");
        GridPane.setConstraints(logOutBtnCustomer, 2, 4);

        logOutBtnCustomer.setOnAction(e2 -> {
            d.writeFile(file, currentCustomer.getUsername(), currentCustomer.getPassword(), "customer", currentCustomer.getBalance(), currentCustomer.currentState());
            window.close();
        });

        // get balance
        Button getBalanceBtn = new Button("View Balance");
        GridPane.setConstraints(getBalanceBtn, 2, 0);

        getBalanceBtn.setOnAction(e2 -> {
            System.out.print(currentCustomer.getBalance());

            showBalance(currentCustomer.getBalance());

//popup showing balance
        });

        //deposit money
        Label depositLbl = new Label("Deposit entry: ");
        GridPane.setConstraints(depositLbl, 0, 1);

        TextField depositTf = new TextField();
        GridPane.setConstraints(depositTf, 1, 1);

        Button depositBtn = new Button("Deposit Money");
        GridPane.setConstraints(depositBtn, 2, 1);

        Label withdrawLbl = new Label("Withdraw entry: ");
        GridPane.setConstraints(withdrawLbl, 0, 2);

        TextField withdrawTf = new TextField();
        GridPane.setConstraints(withdrawTf, 1, 2);

        Button withdrawBtn = new Button("Withdraw Money");
        GridPane.setConstraints(withdrawBtn, 2, 2);

        Label purchaseLbl = new Label("Cost of Purchase: ");
        GridPane.setConstraints(purchaseLbl, 0, 3);

        TextField purchaseTf = new TextField();
        GridPane.setConstraints(purchaseTf, 1, 3);

        Button purhcaseBtn = new Button("Confirm Purchase");
        GridPane.setConstraints(purhcaseBtn, 2, 3);
        depositBtn.setOnAction(e2 -> {
            if (isDouble(depositTf.getText()) == false || Double.valueOf(depositTf.getText())<0) {
                errorPopup();
            } else {
                currentCustomer.depositMoney(Double.valueOf(depositTf.getText()));
                d.writeFile(file, currentCustomer.getUsername(), currentCustomer.getPassword(), "customer", currentCustomer.getBalance(), currentCustomer.currentState());
                depositConfirmation(Double.valueOf(depositTf.getText()));
                depositTf.clear();
                withdrawTf.clear();
                purchaseTf.clear();
            }
        });

        //withdraw money
        withdrawBtn.setOnAction(e2 -> {
            if (isDouble(withdrawTf.getText()) == false || Double.valueOf(withdrawTf.getText())<0) {
                errorPopup();
            } else {
                currentCustomer.withdrawMoney(Double.valueOf(withdrawTf.getText()));
                d.writeFile(file, currentCustomer.getUsername(), currentCustomer.getPassword(), "customer", currentCustomer.getBalance(), currentCustomer.currentState());
                withdrawConfirmation(Double.valueOf(withdrawTf.getText()));
                depositTf.clear();
                withdrawTf.clear();
                purchaseTf.clear();
                isDouble(withdrawTf.getText());
            }
        });

        // purchase
        purhcaseBtn.setOnAction(e2 -> {
            if (isDouble(purchaseTf.getText()) == false || Double.valueOf(purchaseTf.getText())<0) {
                errorPopup();
            } else {
                currentCustomer.purchase(Double.valueOf(purchaseTf.getText()));
                d.writeFile(file, currentCustomer.getUsername(), currentCustomer.getPassword(), "customer", currentCustomer.getBalance(), currentCustomer.currentState());
                purchaseConfirmation(Double.valueOf(purchaseTf.getText()));
                depositTf.clear();
                withdrawTf.clear();
                purchaseTf.clear();
                isDouble(purchaseTf.getText());
            }
        }
        );

        customerGrid.getChildren().addAll(currentAccountLbl, logOutBtnCustomer, getBalanceBtn, depositLbl, depositTf, depositBtn, withdrawLbl, withdrawTf, withdrawBtn, purchaseLbl, purchaseTf, purhcaseBtn);
        customerScene = new Scene(customerGrid, 500, 200);

    }

    public void writeFile(File file, String username, String password, String role, double balance, String level) {
        try { // exception handling
            File outFile = file;
            FileWriter out = new FileWriter(outFile); // FileWriter object
            BufferedWriter writeFile = new BufferedWriter(out);

            writeFile.write(username + "," + password + "," + role + "," + balance + "," + level);

            writeFile.close();
            out.close();
        } catch (Exception e) {
            System.out.println("an error occured!!");
        }
    }

    public Customer readFile(File fn) {
        int MAX_COLS = 5;

        try {
            Scanner in = new Scanner(fn);

            String line = "";
            String[] customerInfo;
            //sect.setFileAbsPath(fn.getAbsolutePath());
            while (in.hasNextLine()) {

                line = in.nextLine();
                customerInfo = line.split(",");
                if (customerInfo.length == MAX_COLS) {

                    Customer c1;

                    c1 = new Customer(customerInfo[0], customerInfo[1], customerInfo[2],
                            Double.valueOf(customerInfo[3]), customerInfo[4]);
                    return c1;
                } else {

                    //     throw new Exception("Invalid file format. \nExpected: username, password, role, balance, level");
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("errorREadFILE");
        }
        return null;
    }

    public void purchaseConfirmation(double purchase) {

        Dialog dialog = new Dialog();
        dialog.setContentText("you've purchased a $" + purchase + " item. $" + purchase + " has been deducted.");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

        // customer delted
        VBox layout = new VBox(10);

        //Add buttons
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(customerScene);

    }

    public void withdrawConfirmation(double withdraw) {

        Dialog dialog = new Dialog();
        dialog.setContentText("$" + withdraw + " has been withdrawed from your account");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

        // customer delted
        VBox layout = new VBox(10);

        //Add buttons
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(customerScene);

    }

    public void depositConfirmation(double deposit) {

        Dialog dialog = new Dialog();
        dialog.setContentText("$" + deposit + " has been deposited to your account");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

        VBox layout = new VBox(10);

        //Add buttons
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(customerScene);

    }

    public void deleteCustomer() {

        Manager manager = new Manager();

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("delete customer");
        window.setMinWidth(250);

        Label label = new Label("Enter Username of customer you want to delete");
        TextField usernameTf = new TextField();
                        usernameTf.setPromptText("Username");

        //Create two buttons
        Button deleteBtn = new Button("delete");

        //Clicking will set answer and close window
        deleteBtn.setOnAction((ActionEvent e) -> {

            String username = usernameTf.getText();

            manager.deleteCustomer(username);
            window.close();

            Dialog dialog = new Dialog();
            dialog.setContentText(username + "'s account file has been deleted.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);
            dialog.showAndWait();

            // customer delted
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label, usernameTf, deleteBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void addCustomer() {

        Manager manager = new Manager();

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Customer");
        window.setMinWidth(250);

        Label userLbl = new Label("Enter a username for the customer");

        Label passLbl = new Label("Enter a password for the customer");

        TextField usernameTf = new TextField();
                        usernameTf.setPromptText("Username");

        TextField passwordTf = new TextField();
                passwordTf.setPromptText("Password");

        //Create two buttons
        Button addBtn = new Button("Add Customer");

        //Clicking will set answer and close window
        addBtn.setOnAction((ActionEvent e) -> {

            String username = usernameTf.getText();
            String password = passwordTf.getText();

            manager.addCustomer(username, password);

            window.close();

            Dialog dialog = new Dialog();
            dialog.setContentText("A new file for " + username + "'s account has been added.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);
            dialog.showAndWait();

            // customer delted
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(userLbl, usernameTf, passLbl, passwordTf, addBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void showBalance(double balance) {

        Dialog dialog = new Dialog();
        dialog.setContentText("You currently have $" + balance + " in your account");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

        VBox layout = new VBox(10);

        //Add buttons
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(customerScene);

    }

    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void errorPopup() {
        Dialog dialog = new Dialog();
        dialog.setContentText("PLEASE ENTER A POSITIVE DECIMAL VALUE!");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        dialog.showAndWait();

        VBox layout = new VBox(10);

        //Add buttons
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(customerScene);
    }
    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
