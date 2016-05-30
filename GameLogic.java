import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {
	private int currentRound;
	private int currentSquare;
	private char gameBoard[];
	private int scoreBoard[];
	private int SecretSquare;
	private boolean PickedSecretSquare;
	private String PrizesGiven[] = new String[2];
	private boolean CurrentPlayer;
	private QuestionBank myQuestionBank;
	private TicTacToeGui Display;
	private boolean Multiplayer;

	private Boolean CorrectAnswer;
	private int roundsWonP1;
	private int roundsWonP2;
	private Timer TimerObject;

	public GameLogic() {
		Display = new TicTacToeGui(this);
		Display.setVisible(true);
	}

	private void StartGame() {
		PickedSecretSquare = false;
		PrizesGiven = new String[2];

		CurrentPlayer = true;
		currentRound = 1;
		currentSquare = -1;
		gameBoard = new char[9];
		scoreBoard = new int[2];
		
		String args[] = new String[1];
		args[0] = new String("QuestionDB.txt");
		myQuestionBank = new QuestionBank(args);

		UpdateBoardBasedOnAnswer();
		Display.changeToGameMode(getShape().charAt(0));
		StartRounds();
	}

	private void StartRounds() {
		UpdateGameboard(1);
		SecretSquareSetup();
		PlayerTurnStart();
	}


	public void PlayerTurnStart() {
		if (!Multiplayer  && !CurrentPlayer) {
			Random rand = new Random();
			int square = rand.nextInt(8);
			while (!CheckSquareEmpty(square)) {
				System.out.println("syck");
				Random rand1 = new Random();
				square = rand1.nextInt(8);			
			}

			Display.disableBoard();
			Random randTime = new Random();
			try{
				Thread.sleep((long)(randTime.nextInt((10 - 2)+1) + 2 * 1000));
			}
			catch (InterruptedException e){ }
			PickSquare(square);
		    GetQuesiton();
       }
		// a human player actually picks a square on the gui which should
		// trigger picksquare and getQuestion
	}

	// Purpose: gui call this method after player give answer
	private void ContinueTurn() {
		StartTimer();
		if (!Multiplayer && !CurrentPlayer) { // If a computer is going...
			// Wait some time to allow the human to follow what is happening
			Random randTime = new Random();
			Display.questionButtonEnabler(false); // Prevent a human from answering
			try{
				Thread.sleep((long)(randTime.nextInt((45 - 2)+1) + 2 * 1000));
			}
			catch (InterruptedException e){}
			// Select a random Answer
			Random randAnswer = new Random();
			int answer = randAnswer.nextInt(1); // 0 is agree 1 is disagree
			boolean response = true;
			if (answer == 1)
				response = false;
			Display.machineMessage(AnswerQuestion(response));
			Display.questionButtonEnabler(true); // Allow a human to answer in the future
			// the computer randomly selects agree or diagree and calls AnswerQuestion
			// A human should click a button, which will do call AnswerQuestion with thier selection
		}
	}

	// Purpose: end turn of the current player (act as a wrapper method)
	private void endTurn() {
		Timer nextStepTimer= new Timer();
		int winner = RoundWonCheck();

		if (winner == 0) { // If there is no winner
			SwitchPlayer(true);
			UpdateBoardBasedOnAnswer();
			// Call the player turn start after 2 seconds within a new thread, so that messages display in correct order on gui
			TimerTask nextTurnTimer = new TimerTask() { public void run() { PlayerTurnStart();}};
			nextStepTimer.schedule(nextTurnTimer,2);
		} else {
			CurrentPlayer = !CurrentPlayer;
			UpdateBoardBasedOnAnswer(); // We want it to send the info to the gui pretending it is already the next turn
			CurrentPlayer = !CurrentPlayer;

			UpdateScoreBoard();
			int gameWinner = GameWonCheck(1);
			if (gameWinner == -1) {
				Display.ToRoundOverFrame(CurrentPlayer, scoreBoard);
				SwitchPlayer(true);
		   		TimerTask nextRoundTimer = new TimerTask() { public void run() { StartRounds(); }};
				nextStepTimer.schedule(nextRoundTimer,2);

			} else {
				Display.changeToPlayGameorCheckScoreMode(scoreBoard); //show game stats
			}
		}
		
		// check if there is a winner or board is full
		// if a player won or it is full, check the round number and either
		// display the final score or the round score
		// otherwise switchplayers(GUI must then start the turn )
		// checkIfBoard is full
	}

	// Purpose: to update the scoreBoard with whoever won
	private void UpdateScoreBoard() {
		int player = 0;
		if (CurrentPlayer == false)
			player = 1;
		scoreBoard[player]++;
		UpdateRounds();
	}

	// Purpose: set numbers of player
	public void setMultiplayer(boolean m) {
		Multiplayer = m;
		StartGame();
	}

	// Purpose: Update Round number
	private void UpdateRounds() {
		currentRound++;
	}

	// Purpose: Set up Secret Square location randomly
	private void SecretSquareSetup() {
		if (!PickedSecretSquare) {
		
			Random rand = new Random();
			this.SecretSquare = rand.nextInt((8 - 0) + 1) + 0;
		}
		System.out.println("Secret square:"+SecretSquare);
	}

	// Purpose: get current round
	public int GetRoundNumber() {
		return currentRound;
	}

	// Purpose: to get current player play item on board
	public String getShape() {
		String s;
		if (CurrentPlayer == true)
			s = "X"; // player 1
		else
			s = "O"; // player 2
		return s; 
	}

	// Purpose: Get a prize from prize bank
	public void getPrize() {
		if(CurrentPlayer==true){PrizesGiven[1]="prize";}
		else {PrizesGiven[0]="prize";}
		}

	private void SwitchPlayer(Boolean Player) {
		String p = "player2";
		if(CurrentPlayer==true){
			p = "player1";
		}
		System.out.println("old player is " + p );
		CurrentPlayer = !CurrentPlayer;
		p = "player2";
		if(CurrentPlayer==true){
			p = "player1";
		}
		System.out.println("Current player is " + p );
		
	}
	
	public boolean PickSquare(int square) {
		if (CheckSquareEmpty(square)) {
			currentSquare = square;
			return true;
		}
		return false;
	}

	private void StartTimer() {
		TimerObject = new Timer();
		TimerTask timerup = new TimerTask() { public void run() { TimeUp(); }};
		int seconds = 30;
		TimerObject.schedule(timerup,seconds*1000);

		TimerTask updateTimer = new TimerTask() {
   			int i = 0;
   			public void run() { i++; Display.setTimer("Time Left: " + (seconds-i) + " seconds"); }
   		};
		TimerObject.schedule(updateTimer,0, 1000);

	}

	// Purpose: to end the turn
	private Boolean TimeUp() {
		// This was never used in the activity diagram and but was clearly intended by the class diagram, 
		System.out.println("THIS WAS GENERATED BASED OFF OF A TIMER");
		 // answer the question saying the player disagrees
		boolean answerCorrect = AnswerQuestion(false);
		Display.timerRanOutMessage(answerCorrect);
		return answerCorrect;
	}

	private void KillTimer() {
		System.out.println("TIMER IS CANCLED");
		TimerObject.cancel();
		TimerObject.purge();
	}

	private boolean CheckSquareEmpty(int square) {
		if (gameBoard[square] == 'o' || gameBoard[square] == 'x')
			return false;
		else
			return true;	
	}


	private int RoundWonCheck() {
		if(checkWinner()==1)
		{
			if(CurrentPlayer==true){
				return 2;
			}
			else return 1;
		}
		return 0;
	}

	// Purpose: give answer if parameter is boolean
	// We forgot this in the acitivty diagram it goes before checkANSWER, 
	public boolean AnswerQuestion(Boolean answer) {
		KillTimer();
		boolean returnVal;
		
		if (answer.equals(CorrectAnswer) ) {
			System.out.println("right Answer");
			returnVal = true;
			Display.displayQuestionResult(1, CurrentPlayer);
			SetSquare(getShape().charAt(0));// .charAt(0) 
			if(SecretSquare== currentSquare){
				PickedSecretSquare = true;
				getPrize();
			}
			// now we need to set the square to the current players value
		} else{
			System.out.println("wrong Answer");
			returnVal = false;
			char tempGameBoard[] = new char[9];
			for(int i=0; i<gameBoard.length; i++){
				tempGameBoard[i] = gameBoard[i];
			}
			// switch the current player temporarily so the next functions work properly
			CurrentPlayer = !CurrentPlayer;
			SetSquare(getShape().charAt(0));
			int currentPlayerint = 1;
			if(CurrentPlayer){
				currentPlayerint = 2;
			}
			// now we need to see if the opponent can get this position without winning
			if(RoundWonCheck()==currentPlayerint){
				System.out.println("player " + currentPlayerint + " would win on the other player's disagree");
				for(int i=0; i<gameBoard.length; i++){
					gameBoard[i] = tempGameBoard[i];
				}
				Display.displayQuestionResult(2, !CurrentPlayer);
			}
			else{
				Display.displayQuestionResult(3, !CurrentPlayer);
			}
			CurrentPlayer = !CurrentPlayer;
		}
		endTurn();
		return returnVal;
	}

	public void GetQuesiton() {
		LinkedList<String> QuestionString = myQuestionBank.getAquestion();
		String Question = (String) QuestionString.getFirst();
		String correctAnswerString = (String) QuestionString.getLast();
		if (correctAnswerString.equals("agree") ) {
			CorrectAnswer = true;
		} else {
			CorrectAnswer = false;
		}
		Display.ToQuestionFrame(Question);
		ContinueTurn();
	}

	private void SetSquare(char c){
		gameBoard[currentSquare]=c;
	}

	private void UpdateGameboard(int currentsquare) {
		for (int i = 0; i < 9; i++) {
			gameBoard[i] = 'n';
		}
	}

	private int checkWinner() {
			char[][] positions = new char[3][3];
			int x=0;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					positions[i][j]=gameBoard[x];
					x++;
				}
			}
			// this checks if you have 5 pieces on the board
			int gameoverx=0;
			int gameovero=0;
			for(int i=0;i<9;i++)
			{
				if(gameBoard[i]=='X'){
					gameoverx++;
				}
				if(gameBoard[i]=='O'){
					gameovero++;
				}
			}
			if(gameovero>=5 || gameoverx>=5)return 1;
			
			// this does the rows
			for(int i=0;i<3;i++){
				gameoverx=0;
				gameovero=0;
				for(int j=0;j<3;j++){
					if(positions[i][j]=='X')gameoverx++;
					if(positions[i][j]=='O')gameovero++;
					if(gameoverx==3){
						return 1;	 
					}
					if(gameovero==3){
						return 1;			
					}
				}
			}
			//this does the columns 
			for(int i=0;i<3;i++){
				gameoverx=0;
				gameovero=0;
				for(int j=0;j<3;j++)
				{
					if(positions[j][i]=='X')gameoverx++;
					if(positions[j][i]=='O')gameovero++;
					if(gameoverx==3)
					{
						return 1;			
					}
					if(gameovero==3)
					{
						return 1;			
					}
				}
			}
			// this does the top left to bottom right diagonal1 test  
			gameoverx=0;
			gameovero=0;
			for(int i=0;i<3;i++)
			{
				if(positions[i][i]=='X')gameoverx++;
				if(positions[i][i]=='O')gameovero++;
				if(gameoverx==3)
				{
					return 1;	
				}							
				if(gameovero==3)
				{
					return 1;		
				}
			}
			// this is the diagonal test for the top right to bottom left 
			gameoverx=0;
			gameovero=0;
			for(int i=2;i>-1;i--)
			{
				
				if(positions[2-i][i]=='X')gameoverx++;
				if(positions[2-i][i]=='O')gameovero++;
				if(gameoverx==3)
				{
					return 1;			
				}
				if(gameovero==3)
				{
					return 1;			
				}
			}

			return 0;
	}

	private void UpdateBoardBasedOnAnswer() {
		Display.setButtons(gameBoard);
		Display.changeToGameMode(getShape().charAt(0));

	}

	private int GameWonCheck(int player) {
		if (scoreBoard[0] == 2) {
			return 0;
		} else if (scoreBoard[1] == 2) {
			return 1;
		} else {
			return -1;
		}
	}

}

// end GameLogic
