import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class QuestionBank {
	
	private int size;
	private Hashtable<Integer, LinkedList> myHashTable;
	//private LinkedList current;
	
	//constructor
	QuestionBank(String [] args){
		
		makeQuestionBank(args);
	
	}
	
	public Hashtable<Integer, LinkedList> getmyHashTable(){
		return myHashTable;
	}
	
	
	public int getsize(){
		return myHashTable.size();
	}
	
	//takes the text file and nakes the hashtable of questions and answers 
	public void makeQuestionBank(String [] args){
		int key = 0;
		String splitString;
		myHashTable = new Hashtable<Integer, LinkedList>();
		
		
		try{
			Scanner inFile = new Scanner(new FileReader(args[0]));
			
			while (inFile.hasNext()){
				
				LinkedList<String> myLinkedList = new LinkedList<String>();

				splitString  = inFile.nextLine();
				String []tempArray = splitString.split("/");
				
	
				for (int i =0; i<tempArray.length; i++)
					myLinkedList.add(tempArray[i]);
				
				myHashTable.put(key, myLinkedList);
				key++;
										
			}
			
			size = myHashTable.size();
			inFile.close();
			
		}//try
		
		catch(FileNotFoundException e){
			System.out.println("QuestionDB.txt File not Found!");
			
		}//catch
		
		
	}
	
	//gets a random question when asked for and remove the question from the hash table
	//LinkedList.getFirst() stores the question
	//LinkedList.getLast() stores the correct answer, if the answer is "agree" and the player gives "agree",
	//only that time the answer will be counted as correct. same for "disagree"
	public LinkedList getAquestion(){
		
	
		if (myHashTable.isEmpty()){
			System.out.println("File Error");
			System.exit(0);
		}
		
		int randomNum;
		Random randomGen = new Random();
		LinkedList removedQues = null;
		
		while (removedQues == null){
			
			randomNum = randomGen.nextInt(size);
			removedQues = myHashTable.remove(randomNum);

		}
		
		//current = removedQues;
		return removedQues;	
	}
	
	//checks if an answer is right or wrong given by the player
	public boolean checkAnswer(String answer, LinkedList myLinkedList){
		
		return myLinkedList.contains(answer.toLowerCase());
			
	}
	
}
