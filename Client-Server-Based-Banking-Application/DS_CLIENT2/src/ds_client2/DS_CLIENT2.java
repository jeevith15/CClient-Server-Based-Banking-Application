package ds_client2;
import java.net.*;
import java.io.*;
import java.util.*;
public class DS_CLIENT2 {
   public static void main(String[] args) {
      // arguments supply message and hostname of destination
        Socket s = null;
        try{
        int serverPort = 7896;
        s = new Socket("127.0.0.1", serverPort);
        DataInputStream in = new DataInputStream( s.getInputStream());
        DataOutputStream out = new DataOutputStream( s.getOutputStream());
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
                Integer newbalance= in.readInt();
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
        } finally {if(s!=null) try {s.close();}catch (IOException e){/*close failed*/}}
    }
    
}
