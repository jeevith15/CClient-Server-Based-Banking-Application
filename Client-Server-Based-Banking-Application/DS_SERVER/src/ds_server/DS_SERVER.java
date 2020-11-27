package ds_server;

import java.net.*;          //importing required libraries
import java.io.*;
import java.sql.*;

public class DS_SERVER {

    public static void main(String[] args) throws ClassNotFoundException {  //main class
        try {
            int serverPort = 7896;                                      //storing the server port number
            ServerSocket listenSocket = new ServerSocket(serverPort);   //creating new object of the server socket
            while (true) {                                      //true while loop for listening for new connections all the time.
                Socket clientSocket = listenSocket.accept();        //accepting the socket once the client requests
                Connection c = new Connection(clientSocket);        // creating a new object of connection for the client joined. 
            }
        } catch (Exception e) {                                      
            System.out.println("Listen :" + e.getMessage());         // if anything goes wrong print the exception found
        }
    }
}

class Connection extends Thread {           //connection class extending thread. 
                                            //whenever new client joins the server, a new thread is created for that client 
    DataInputStream in;                     //Declarations
    DataOutputStream out;                   //they are used in passing the information between client and servers
    Socket clientSocket;

    public Connection(Socket aClientSocket) {   
        try {
            clientSocket = aClientSocket;           //assigning the clientsocket to new variable
            in = new DataInputStream(clientSocket.getInputStream());    //creating new buffer for inputstreams from the client-server socket
            out = new DataOutputStream(clientSocket.getOutputStream());  //creating new buffer for outputstreams from the client-server socket
            this.start();                               //starts the connection and responds to the users requests after processing them 
        } catch (Exception e) {
            System.out.println("Connection:" + e.getMessage());     // if anything goes wrong print the exception found
        }
    }

    public void run() {     // class for processing all the users requests and to reply back
        try {          
                    //Connection to database
            java.sql.Connection conn = null;        //declarations 
            PreparedStatement pst = null;
            ResultSet rs, rs1 = null;
            System.out.print("\n  Coming to try...");
            Class.forName("com.mysql.jdbc.Driver");     //using the class of mysql JDBC driver
            System.out.println("\nComing out after Registration");
                                                //creating connect with the database with username and password
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ds", "root", "root");
            System.out.println("\nComing out after Connection");

            //Code Starts
            String data1 = in.readUTF();//read the data sent by the client and store it(The first data sent by the client is username)
            System.out.println("Received: " + data1);   //print the received data
            String data2 = in.readUTF(); //read the data sent by the client and store it(The seconnd data sent by the client is account no)
            System.out.println("Received: " + data2);     //print the received data

            while (true) {      //true while loop for processing the client's all the request. 

                String choice = in.readUTF();  //read the data and store it(The data tells client's choice of request) 
                System.out.println("\nReceived Option from "+data1+" is :" + choice);   //print the choice with username 
                switch ((choice)) {             //switch case to process request based on the clients choice.
                    case "1":                   // case 1 is the balance enquiry request.
                        String SQL_query1 = "select Balance from accountdetails where Username=? and AccountNo=?";  //sql query to get balance from db
                        try {
                            pst = conn.prepareStatement(SQL_query1);    //prepare the sql query statement
                            pst.setString(1, data1);            // set the first unknown value as data1(username) 
                            pst.setString(2, data2);            // set the second unknown value as data2(account no) 
                            rs = pst.executeQuery();            //execute the query and store the resultset in rs
                            if (rs.next()) {                    //if result set exists run the below lines
                                Integer balanceE = rs.getInt("Balance");    //get the balance from result set and store it.
                                System.out.print("\nBalance is:" + balanceE);   //print the extracted balance value
                                out.writeInt(balanceE);         //send the balance value to the client as reply
                            } else {
                                System.out.println("\nError Balance Enquiry");  // if result set doesnt exist print the error message
                            }
                        } catch (Exception e) {
                            System.out.print(e);    // if anything goes wrong in the try block print the exception found
                        }
                        break;

                    case "2":               //case 2 is the deposit request
                        String SQL_query2 = "select Balance from accountdetails where Username=? and AccountNo=?"; //sql query to get balance from db
                        try {
                            pst = conn.prepareStatement(SQL_query2); //prepare the sql query statement
                            pst.setString(1, data1);                    // set the first unknown value as data1(username) 
                            pst.setString(2, data2);                     // set the second unknown value as data2(account no) 
                            rs = pst.executeQuery();                     //execute the query and store the resultset in rs
                            if (rs.next()) {                            //if result set exists run the below lines
                                Integer amount = in.readInt();          // read the amount to be deposited from the client input stream     
                                Integer balanceD = rs.getInt("Balance") + amount;//get the balance from result set, add them with amount and store it
                                System.out.print("\n New Balance is:" + balanceD); //print the final balance value after adding
                                out.writeInt(balanceD);         //send the new balance value to the client as reply
                                String SQL_uquery1 = "update accountdetails set  Balance= ? where AccountNo=?"; //sql query to update the new balance in db
                                pst = conn.prepareStatement(SQL_uquery1);   //prepare the sql query statement
                                pst.setInt(1, balanceD);                    // set the first unknown value as balanceD(new balnace after adding) 
                                pst.setString(2, data2);                    // set the second unknown value as data2(account no) 
                                pst.executeUpdate();                        //execute the update query  
                                System.out.println("\nDeposit Succesful");  //print a success message
                            } else {
                                System.out.println("\nError Depositing");      // if result set doesnt exist print the error message
                            }
                        } catch (Exception e) {
                            System.out.print(e);                         // if anything goes wrong in the try block print the exception found
                        }
                        
                        break;
                        
                      case "3":             //case 3 is the withdrawal request
                        String SQL_q = "select Balance from accountdetails where Username=? and AccountNo=?";   //sql query to get balance from db
                        try {
                            pst = conn.prepareStatement(SQL_q);     //prepare the sql query statement
                            pst.setString(1, data1);                // set the first unknown value as data1(username) 
                            pst.setString(2, data2);                // set the second unknown value as data2(account no) 
                            rs = pst.executeQuery();                //execute the query and store the resultset in rs
                            if (rs.next()) {                         //if result set exists run the below lines
                                Integer amount = in.readInt();      // read the amount to be withdrawn from the CLIENT in input stream
                                Integer balanceD = rs.getInt("Balance") - amount; //get the balance from result set, subtract them with amount and store it
                                System.out.print("\n New Balance is:" + balanceD);  //print the final balance value after subtracting
                                out.writeInt(balanceD);             //send the new balance value to the client as reply
                                String SQL_uq = "update accountdetails set  Balance= ? where AccountNo=?";  //sql query to update the new balance in db
                                pst = conn.prepareStatement(SQL_uq);        //prepare the sql query statement
                                pst.setInt(1, balanceD);                    // set the first unknown value as balanceD(new balnace after subtracting) 
                                pst.setString(2, data2);                    // set the second unknown value as data2(account no) 
                                pst.executeUpdate();                        //execute the update query 
                                System.out.println("\nWithdrawal Succesful");  //print a success message
                            } else {
                                System.out.println("\nError Withdrawal");   // if result set doesnt exist print the error message
                            }
                        } catch (Exception e) {
                            System.out.print(e);        // if anything goes wrong in the try block print the exception found
                        }       
                        break;  
                        
                    case "4":               //case 4 is the money transfer request
                        String SQL_query3 = "select Balance from accountdetails where Username=? and AccountNo=?";//sql query to get balance of current user
                        String SQL_query4 = "select Balance from accountdetails where  AccountNo=?";    //sql query to get balance of account of transfer
                        try {
                            Integer amount = in.readInt();    // read the amount to be transfered from the user in input stream
                            Integer accno = in.readInt();       // read the accno to be transfered from the user in input stream
                            pst = conn.prepareStatement(SQL_query3);    //prepare the sql statement to get balance of the user
                            pst.setString(1, data1);                 // set the first unknown value as data1(username) 
                            pst.setString(2, data2);                     // set the second unknown value as data2(account no) 
                            rs = pst.executeQuery();                     //execute the query and store the resultset in rs
                            if (rs.next()) {                             //if result set exists run the below lines 
                                Integer balanceT1 = rs.getInt("Balance") - amount;  //get the balance from result set, subtract them with amount and store it
                                System.out.print("\n New Balance of account " + data2 + " is:" + balanceT1); //print the final balance value after subtracting
                                out.writeInt(balanceT1);                        //send the new balance value to the client as reply         
                                String SQL_uquery2 = "update accountdetails set  Balance= ? where AccountNo=?"; //sql query to update the new balance in db
                                pst = conn.prepareStatement(SQL_uquery2);           //prepare the sql query statement
                                pst.setInt(1, balanceT1);                           // set the first unknown value as balanceD(new balnace after subtracting) 
                                pst.setString(2, data2);                            // set the second unknown value as data2(account no) 
                                pst.executeUpdate();                                //execute the update query 
                            } else {
                                System.out.println("\nError Deducting");          // if result set doesnt exist print the error message
                            }           
                            pst = conn.prepareStatement(SQL_query4);    //sql query to get balance of account of the amount to be transfererd
                            pst.setInt(1, accno);                       // set the first unknown value as accno(accno sent by the client) 
                            rs1 = pst.executeQuery();                  //execute the query and store the resultset in rs1
                            if (rs1.next()) {                            //if result set exists run the below lines 
                                Integer balanceT2 = rs1.getInt("Balance") + amount;    //get the balance from result set, add them with amount and store it
                                
                            String SQL_uquery3 = "update accountdetails set  Balance= ? where AccountNo=?"; //sql query to update the balance of the transfered account
                                pst = conn.prepareStatement(SQL_uquery3);           //prepare the sql query statement
                                pst.setInt(1, balanceT2);                           // set the first unknown value as balanceD(new balnace after adding) 
                                pst.setInt(2, accno);                               // set the second unknown value as accno(acc no sent by the client) 
                                pst.executeUpdate();                                //execute the update query 
                            } else {
                                System.out.println("\nError Transferring");         // if result set doesnt exist print the error message
                            }
                             System.out.println("\nTransfer Successful");       //print a success message
                        } catch (Exception e) {
                            System.out.print(e);                                // if anything goes wrong in the try block print the exception found
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // if anything goes wrong in the try block print the exception found
        } finally {
            try {
                clientSocket.close();           // close the connection after processing all the clients request.
            } catch (Exception e) {             // if anything goes wrong in the closing connection print the exception found
            }
        }
    }
}
