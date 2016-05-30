import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User {
   String Player1Name=null;
   String Player2Name=null;
   int P1GW;
   int P2GW;
   int P1GP;
   int P2GP;
   Scanner scanner;
   
   /*
   * constructor: initializes scanner
   */
   User(String in) throws FileNotFoundException{
      scanner=new Scanner(new FileReader(in));
   }//constructor
   
   /*
   * method makeNewUser
   * @param String name: name of new user
   * makes a new user, sets new user to user class variable based on if someone logged in before or not
   */
   public void makeNewUser(String name){
      if(Player1Name==null){
         Player1Name=name;
         P1GW=0;
         P1GP=0;
      }//if
      
      else{
         Player2Name=name;
         P2GW=0;
         P2GP=0;
      }//else
   }//makeNewUser
   
   /*
   * method checkUser
   * @param int whichplayer: gameLogic inputs whether this user tried to log in 1st or second
   * @param String name: username of person logging in
   * takes username inputted and sees if they are in the DB, returns true and puts their info in the appropriate
   * variables, returns false if they are not in the DB
   */
   public boolean CheckUser(int whichplayer,String name){
      while(scanner.hasNextLine()){
         String line = scanner.nextLine();
         String[] result = line.split(" ");
         
         if(result[0].equals(name)){
            
            if(whichplayer==1){
               Player1Name=name;
               P1GW= Integer.parseInt(result[2]);
               P1GP= Integer.parseInt(result[1]);
               return true;
            }//if whichplayer=1
            
            else{
               Player2Name=name;
               P2GW= Integer.parseInt(result[2]);
               P2GP= Integer.parseInt(result[1]);
               return true;
            }//else
         }//if result=inputted name
      }//while scanner has next line
      return false;
   }//checkUser
   
   /*
   * method checkScore
   * @param whichPlayer: gameLogic inputs what player is checking their score
   * returns games won of either player 1 or player 2
   */
   public int checkScore(int whichPlayer){
      if(whichPlayer==1){
         return P1GW;
      }//if
      else{
         return P2GW;
      }//else
   }//checkScore
   
   /*
   * method updateScore
   * @param whichPlayer: gameLogic inputs who won the game
   * updates the user class variables based on who won
   */
   public void updateScore(int whichPlayer){
      if (whichPlayer==1){
         P1GW++;
         P1GP++;
         P2GP++;
      }//if
      
      else{
         P2GW++;
         P2GP++;
         P1GP++;
      }//if
   }//updateScore
   
   /*
   * method updateDB
   * reads each line finding the players in the DB and updates their score. if they were a new user,
   * they will be added to the end of the DB
   */
   public void updateDB() throws IOException{
      scanner=new Scanner(new FileReader("UserDB.txt"));
      String newtext = "";
      boolean P1UP=false;
      boolean P2UP=false;
      
      while(scanner.hasNextLine()){
         String line = scanner.nextLine();
         String[] result = line.split(" ");
         
         if (result[0].equals(Player1Name)){
            line=Player1Name+" "+P1GP+" "+P1GW;
            P1UP=true;
            newtext += line;
         }//if
         
         else if(result[0].equals(Player2Name)){
            line=Player2Name+" "+P2GP+" "+P2GW;
            P2UP=true;
            newtext +=line;
         }//elseif
         
         else{
            newtext += line;
         }//else
         
         if(scanner.hasNextLine()){
            newtext=newtext+"\r\n";
         }//if
      }//while
      
      if(P1UP==false&&Player1Name!=null){
         newtext=newtext+"\r\n"+Player1Name+" "+P1GP+" "+P1GW;
      }// if p1up false
      
      if(P2UP==false&&Player2Name!=null){
         newtext=newtext+"\r\n"+Player2Name+" "+P2GP+" "+P2GW;
      }//if p2up false
      
      FileWriter writer = new FileWriter("UserDB.txt");
      writer.write(newtext);
      writer.close();
   }//updateDB
}//User class
