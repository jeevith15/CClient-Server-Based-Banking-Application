
package ds_client1;
import java.net.*;                  //importing the required libraries
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DS_CLIENT1 {       //Client class
    DataInputStream in;         //declarations
    DataOutputStream out ;
    Socket s = null;
   public void connection(){        //method for establishing connection between server and client 
        try {
            int serverPort = 7896;       //storing the server port number
            s = new Socket("127.0.0.1", serverPort);    //creating a new socket with DNS and server port as arguments
            in = new DataInputStream( s.getInputStream());      //creating new buffer for inputstreams from the client-server socket
            out = new DataOutputStream( s.getOutputStream());   //creating new buffer for outputstreams from the client-server socket
        } catch (Exception ex) {
            Logger.getLogger(DS_CLIENT1.class.getName()).log(Level.SEVERE, null, ex);  // if anything goes wrong print the exception found
        }   
   }
   /*
   public void main(String[] args) {
      // arguments supply message and hostname of destination
        
        try{
        
        out.writeUTF("Hello"); // UTF is a string encoding; 
        String data = in.readUTF();
        System.out.println("Received: "+ data+"\n") ;
        //Code Starts
        System.out.println("\nWelocome to Online Banking Application\n");
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter Username:");
        String username = input.nextLine();
        out.writeUTF(username); 
        String data1 = in.readUTF();
        System.out.println("\nReceived: "+ data1) ;
        
        System.out.println("\nEnter Account No:");
        Integer accNo = input.nextInt();
        out.writeUTF(Integer.toString(accNo)); 
        String data2 = in.readUTF();
        System.out.println("\nReceived: "+ data2) ;
        
        while(true){
        System.out.println("\n1.Balance Enquiry");
        System.out.println("\n2.Deposit");
        System.out.println("\n3.Transfer");
        System.out.println("\n4.Exit");
        Integer choice= input.nextInt();
        
        switch(choice){
            case 1:
                System.out.println("\nOption 1 Selected");
                 out.writeUTF("1"); 
                String choice1 = in.readUTF();
                System.out.println("\nReceived: "+ choice1) ;
                Integer balance = in.readInt();
                 System.out.println("\nBalance is: "+ balance) ;
                break;
            case 2:
                System.out.println("\nOption 2 Selected");
                 out.writeUTF("2"); 
                String choice2 = in.readUTF();
                System.out.println("\nReceived: "+ choice2) ;
                System.out.println("\nEnter the Amount:");
                Integer amount= input.nextInt();
                out.writeInt(amount);
                Integer newbalance = in.readInt();
                System.out.println("Amount deposit successful") ;
                System.out.println("\nBalance is: "+ newbalance) ;
                break;
            case 3:
                System.out.println("\nOption 3 Selected");
                 out.writeUTF("3"); 
                String choice3 = in.readUTF();
                System.out.println("\nReceived: "+ choice3) ;
                System.out.println("\nEnter the amount: ") ;
                Integer amountT = input.nextInt();
                out.writeInt(amountT);
                System.out.println("\nEnter the accountNo: ") ;
                Integer accno= input.nextInt();
                out.writeInt(accno);
                Integer balanceT1= in.readInt();
                 System.out.println("\nThe balance of ur account is:"+balanceT1) ;
                 Integer balanceT2= in.readInt();
                 System.out.println("\nThe balance of Transfererd account"+accno+" is:"+balanceT2) ; 
                break;
            case 4:
                System.exit(0);
        }        
        }
        }catch (UnknownHostException e){
        System.out.println("Sock:"+e.getMessage());
        } catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch (IOException e){System.out.println("IO:"+e.getMessage());
        } finally {if(s!=null) try {s.close();}catch (IOException e){}}
    }
   */
   
   public void login(String username, int accNo){       //method for login for user 
        try {
            out.writeUTF(username);                    //send the username to the server as request to login
            out.writeUTF(String.valueOf(accNo));       //send the Account no to the server as request to login
            System.out.println("\nLogin Successful"+username);  //print success message after sending data            
        } catch (Exception ex) {
            Logger.getLogger(DS_CLIENT1.class.getName()).log(Level.SEVERE, null, ex);   // if anything goes wrong print the exception found
        }
   }

   public void balance(){                                   //method for balance enquiry
       synchronized(this){                                  //this helps to synchronize the process and blocks other requests from other users
        try {
            out.writeUTF("1");                              //sending the choice for the switch case
            int newbalance = in.readInt();                  //reading the new balance from the inputstream of client and storing it
            System.out.println("\nCurrent Balance is:"+ newbalance);        //printing the balance
            return;
            } catch (Exception ex) {
            Logger.getLogger(DS_CLIENT1.class.getName()).log(Level.SEVERE, null, ex);   // if anything goes wrong print the exception found
        }
        return;
       }
    }
   
   public void deposit(int amount){                  //method for depositing money with amount as argument
       synchronized(this){                          //this helps to synchronize the process and blocks other requests from other users
       try {
            out.writeUTF("2");                       //sending the choice for the switch case
            out.writeInt(amount);                    //sending the amount to be deposited into the inputstream of server
            int newbalance = in.readInt();           //reading the new balance from the inputstream of client and storing it
            System.out.println("\nNew Balance after deposit is:"+ newbalance);  //printing the balance
            return ;
        } catch (Exception ex) {    
            Logger.getLogger(DS_CLIENT1.class.getName()).log(Level.SEVERE, null, ex);    // if anything goes wrong print the exception found
        }
        return ;
    }
   }
   
    public void withdrawal(int amount){         //method for withdrawal money with amount as argument
       synchronized(this){                       //this helps to synchronize the process and blocks other requests from other users
       try {
            out.writeUTF("3");                   //sending the choice for the switch case
            out.writeInt(amount);               //sending the amount to be deposited into the inputstream of server
            int newbalance = in.readInt();       //reading the new balance from the inputstream of client and storing it
            System.out.println("\nNew Balance after withdrawal is:"+ newbalance); //printing the balance
            return ;
        } catch (Exception ex) {
            Logger.getLogger(DS_CLIENT1.class.getName()).log(Level.SEVERE, null, ex);   // if anything goes wrong print the exception found
        }
        return ;
    }
   }
   
   public void transfer(int amount,int accno){      //method for transferring money with amount and account no as argument
       synchronized(this){                          //this helps to synchronize the process and blocks other requests from other users
       try {
           out.writeUTF("4");                   //sending the choice for the switch case
           out.writeInt(amount);                //sending the amount to be transfered into the inputstream of server
           out.writeInt(accno);                 //sending the Acc no ofthe transferring account into the inputstream of server
            int newbalance= in.readInt();       //reading the new balance from the inputstream of client and storing it
            System.out.println("\nNew Balance after transfer is:"+ newbalance); //printing the balance
           return ;
            } catch (Exception ex) {
            Logger.getLogger(DS_CLIENT1.class.getName()).log(Level.SEVERE, null, ex);   // if anything goes wrong print the exception found
        }
        return ;
       }
   }    
}   


