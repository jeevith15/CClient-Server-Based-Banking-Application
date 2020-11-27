
package ds_client1;

public class r {                //main class
     public static void main(String[] args){
         
          DS_CLIENT1 user1=new DS_CLIENT1();     //creating a new object of the DS_CLIENT1 class for user 1
     user1.connection();                    //using connection method to establish connecion between server and client object
     user1.login("user1",1);                // calling login method with username and password as argument
     user1.balance();                       
     user1.transfer(200,2);
     user1.deposit(50);
     user1.withdrawal(100);
     user1.deposit(500);
    
     DS_CLIENT1 user2=new DS_CLIENT1();     //creating a new object of the DS_CLIENT1 class for user 2
     user2.connection();                    //using connection method to establish connecion between server and client object
     user2.login("user2",2);                // calling login method with username and password as argument
     user2.balance();
     user2.withdrawal(200);
     user2.deposit(100);
     user2.transfer(100,1);
//         threads tt=new threads();  //creating new objects of the class threads
//        tt.run();                   //executing the threads        
 }
}

//class threads{
//    DS_CLIENT1 user1=new DS_CLIENT1();    //creating a new object of the DS_CLIENT1 class for user 1
//    DS_CLIENT1 user2=new DS_CLIENT1();    //creating a new object of the DS_CLIENT1 class for user 2
//    public void run(){                    //function to send requests to server
//        t1 t11=new t1();                  //new thread object for user 1
//        t2 t22=new t2();                  //new thread object for user 2 
//        user1.connection();               //using connection method to establish connecion between server and client object
//        user1.login("user1",1);           // calling login method with username and password as argument
//        user2.connection();               //using connection method to establish connecion between server and client object
//        user2.login("user2",2);           // calling login method with username and password as argument
//        t11.start();                      //starts processing the thread object of user 1
//        t22.start();                       //starts processing the thread object of user 2
//    }
//    class t1 extends Thread{              // sub class to specify the user 1 requests
//        public void run(){
//            user1.balance();
//            user1.transfer(300,2);
//            user1.deposit(200);
//            user1.withdrawal(100);
//            
//        }
//    }
//    class t2 extends Thread{               // sub class to specify the user 2 requests
//        public void run(){
//            user2.balance();
//            user2.withdrawal(500);
//            user2.deposit(200);
//            user2.transfer(500,1)   ;
//        }
//    }
//}




//    