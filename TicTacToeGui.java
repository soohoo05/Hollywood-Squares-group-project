import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class TicTacToeGui extends JFrame  {
	
	private JLabel gamesWon;
	private JLabel gamesPlayed;
	private JLabel turnMessage;
	private JButton exitButton;
	private JTextField loginTextField;
	private JButton topLeftButton;
	private JButton topMiddleButton;
	private JButton topRightButton;
    private JButton middleLeftButton;
	private JButton middleRightButton;
	private JButton middleMiddleButton;
	private JButton bottomMiddleButton;
	private JButton bottomLeftButton;
	private JButton bottomRightButton;
	private JButton agreeButton;
	private JButton disagreeButton;
	private JButton PVPButton;
	private JButton PVEButton;
	private JLabel timerMessage;
	private JLabel questionMessage;
	private JButton checkScoreButton;
	private GameLogic gl;
	private JPanel masterPane;
	private JButton[] buttonsLeft = new JButton[9];
	private CardLayout mainFrame;
	private User loginAccount;
	private String userName;
	private char winner;
	public void setButtons(char[] gameBoard)
	{
		for(int i=0; i<9;i++)
		{
			if(gameBoard[i]=='X'){
				buttonsLeft[i].setText("X");
				buttonsLeft[i].setEnabled(false);
			}
			else if(gameBoard[i]=='O'){
				buttonsLeft[i].setText("O");
				buttonsLeft[i].setEnabled(false);
			}
			else{
				buttonsLeft[i].setText("");
				buttonsLeft[i].setEnabled(true);
			}
		}
	}

	public void displayQuestionResult(int winCase, boolean currentPlayer)
	{
			char player =' ';
			if(currentPlayer==true)player='X';
			else player='O';
			if(winCase==1){
				JOptionPane.showMessageDialog(null,"that was corret and "+player+" got the square");
			}
			if(winCase==2){
				JOptionPane.showMessageDialog(null,player+" was incorrect, and nobody got the square");
			}
			if(winCase==3)
			{
				JOptionPane.showMessageDialog(null,player+" was incorrect, so the other player got the square");
			}
	}

	public void disableBoard()
	{
		for(int i =0;i<9;i++){
			buttonsLeft[i].setEnabled(false);
		}
	}

	public void  questionButtonEnabler(boolean on)
	{
		if(on==true){
			agreeButton.setEnabled(true);
			disagreeButton.setEnabled(true);
		}
		else{
			agreeButton.setEnabled(false);
			disagreeButton.setEnabled(false);
		}
	}

	public void timerRanOutMessage(boolean rightOrWrong)
	{
		if(rightOrWrong==true){
			JOptionPane.showMessageDialog(null,"disagree was chosen because time ran out, you were right anyway!");
		}
		else{
		JOptionPane.showMessageDialog(null,"disagree was chosen because time ran out, you were wrong :(");
		}	
	}

	public void machineMessage(boolean rightOrWrong)
	{
		if(rightOrWrong==true){
			JOptionPane.showMessageDialog(null,"the computer was corrent corrent on its guess");
		}
		else{
			JOptionPane.showMessageDialog(null,"the computer was incorrent corrent on its guess");
		}
	}

	public TicTacToeGui(GameLogic gameLogic)
	{	
		gl= gameLogic;
		try {loginAccount = new User("UserDB.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		mainFrame= new CardLayout(0,0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 336);
	    masterPane = new JPanel();
		masterPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		masterPane.setLayout(mainFrame);
		setContentPane(masterPane);
		topLeftButton = new JButton("");
		topMiddleButton= new JButton("");
		topRightButton= new JButton("");
		middleLeftButton= new JButton("");
		middleRightButton= new JButton("");
		middleMiddleButton= new JButton("");
		bottomMiddleButton= new JButton("");
		bottomLeftButton= new JButton("");
		bottomRightButton= new JButton("");
		buttonsLeft[0] = topLeftButton;
		buttonsLeft[1] = topMiddleButton;
		buttonsLeft[2] = topRightButton;
		buttonsLeft[3] = middleLeftButton;
		buttonsLeft[4] = middleMiddleButton;
		buttonsLeft[5] = middleRightButton;
		buttonsLeft[6] = bottomLeftButton;
		buttonsLeft[7] = bottomMiddleButton;
		buttonsLeft[8] = bottomRightButton;
		changeToLoginScreen();
		setVisible(true);

		topLeftButton.addActionListener(e ->{
		    	gl.PickSquare(0);
		    	gl.GetQuesiton();
		    	
		    });
			topMiddleButton.addActionListener(e ->{
				gl.PickSquare(1);
				gl.GetQuesiton();
		    });
			topRightButton.addActionListener(e ->{
				gl.PickSquare(2);
				gl.GetQuesiton();
		    });
			middleLeftButton.addActionListener(e ->{
				gl.PickSquare(3);
				gl.GetQuesiton();
		    });
			middleMiddleButton.addActionListener(e ->{
				gl.PickSquare(4);
				gl.GetQuesiton();
		    });
			middleRightButton.addActionListener(e ->{
				gl.PickSquare(5);
				gl.GetQuesiton();
		    });
			bottomLeftButton.addActionListener(e ->{
				gl.PickSquare(6);
				gl.GetQuesiton();
		    });
			bottomMiddleButton.addActionListener(e ->{
				gl.PickSquare(7);
				gl.GetQuesiton();
		    });
			bottomRightButton.addActionListener(e ->{
				gl.PickSquare(8);
				gl.GetQuesiton();
		    });
	}
	
	public void changeToGameMode(char player)
	{
		JPanel gameModePanel = new JPanel();
		gameModePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		gameModePanel.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(5, 30, 424, 238);
		gameModePanel.add(panel);
		panel.setLayout(new GridLayout(3,3));
		for(int i =0; i<9; i++)
		{
			panel.add(buttonsLeft[i]);
		}
		turnMessage = new JLabel("Player "+ player +" please go");
		turnMessage.setBounds(5, 5, 424, 14);
		gameModePanel.add(turnMessage);
		gameModePanel.add(panel);
		masterPane.add(gameModePanel);
		mainFrame.addLayoutComponent(gameModePanel, "gameModePanel");
		mainFrame.show(masterPane, "gameModePanel");
	}
	
    public void changeToPlayerSelect(GameLogic gameLogic)
	{
		JPanel playerSelectPanel = new JPanel();
		playerSelectPanel.setLayout(null);
		
		PVPButton = new JButton("Single Player");
		PVPButton.addActionListener(e ->{
			gl.setMultiplayer(false);
		});
		PVPButton.setBounds(10, 101, 121, 53);
		playerSelectPanel.add(PVPButton);
		
		PVEButton = new JButton("Two Players");
		PVEButton.setBounds(313, 101, 121, 53);
		PVEButton.addActionListener(e ->{
			gl.setMultiplayer(true);
		});
		playerSelectPanel.add(PVEButton);
		JLabel lblNewLabel = new JLabel("Choose what game mode you want");
		lblNewLabel.setBounds(134, 22, 250, 79);
		playerSelectPanel.add(lblNewLabel);
		masterPane.add(playerSelectPanel);
		mainFrame.addLayoutComponent(playerSelectPanel, "playerSelectPanel");
		mainFrame.show(masterPane, "playerSelectPanel");
	}
	
	

	public void ToQuestionFrame(String question2)
	{
		JPanel questionPanel = new JPanel();
		questionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		questionPanel.setLayout(new BorderLayout(0, 0));
		
		timerMessage = new JLabel("TIMER");
		timerMessage.setFont(new Font("Tahoma", Font.BOLD, 21));
		questionPanel.add(timerMessage, BorderLayout.NORTH);
		
		disagreeButton = new JButton("Disagree");
		questionPanel.add(disagreeButton, BorderLayout.WEST);
		
		agreeButton = new JButton("  Agree ");
		questionPanel.add(agreeButton, BorderLayout.EAST);
		String Question = question2;
		JTextArea questionPlace = new JTextArea();
		questionPlace.setText(Question);
		questionPlace.setEditable(false);
		questionPlace.setLineWrap(true);
		questionPlace.setToolTipText("");
		questionPlace.setForeground(Color.BLACK);
		questionPlace.setBackground(Color.WHITE);
		questionPanel.add(questionPlace, BorderLayout.CENTER);
		
		disagreeButton.addActionListener(e ->{
			gl.AnswerQuestion(false);
		});
		agreeButton.addActionListener(e ->{
			gl.AnswerQuestion(true);
		});
		masterPane.add(questionPanel);
		mainFrame.addLayoutComponent(questionPanel, "questionPanel");
		mainFrame.show(masterPane, "questionPanel");
		
	}
	public void ToRoundOverFrame(boolean currentPlayer, int[] scoreBoard)
	{
		JPanel roundOverPanel = new JPanel();
		roundOverPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		roundOverPanel.setLayout(null);
		winner =' ';
		if(currentPlayer==true){
			winner='1';
		}
		if(currentPlayer==false){
			winner='2';
		}
		JLabel lblNewLabel = new JLabel("player "+winner+" has won "+"\n"+"the score is: "+scoreBoard[0]+" to "+scoreBoard[1]);
		lblNewLabel.setBounds(10, 24, 414, 35);
		roundOverPanel.add(lblNewLabel);
		
		JButton contineButton = new JButton("continue");
		contineButton.setBounds(335, 194, 89, 57);
		roundOverPanel.add(contineButton);
		contineButton.addActionListener(e ->{
			for(int i=0; i<9; i++){
				buttonsLeft[i].setEnabled(true);
				buttonsLeft[i].setText("");
			}
			if(winner=='1')changeToGameMode('O');
			if(winner=='2')changeToGameMode('X');
		});
	
		exitButton.setBounds(10, 194, 89, 57);
		roundOverPanel.add(exitButton);
		JLabel secretSqaureMessage = new JLabel("Says who won the secret square or says nobody won it");
		secretSqaureMessage.setBounds(10, 82, 414, 57);
		roundOverPanel.add(secretSqaureMessage);
		masterPane.add(roundOverPanel);
		mainFrame.addLayoutComponent(roundOverPanel, "roundOverPanel");
		mainFrame.show(masterPane, "roundOverPanel");
	}
	
	public void changeToLoginScreen(){
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPanel.setLayout(null);
		
		exitButton = new JButton("EXIT");
		exitButton.addActionListener(e ->{
			System.exit(0);
		});
		
		JTextField textField = new JTextField();
		textField.setBounds(10, 47, 414, 20);
		loginPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(10, 22, 81, 14);
		loginPanel.add(lblUserName);
		
		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.setBounds(10, 196, 130, 55);
		loginPanel.add(btnNewButton);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(317, 196, 107, 55);
		loginPanel.add(btnLogin);
		
		btnLogin.addActionListener(e ->
		{
			userName = textField.getText();
			if(loginAccount.CheckUser(1,userName)==true)
			{
				ToStatsFrame();
			}
			else{
				JOptionPane.showMessageDialog(null, "User not found");
				try{loginAccount = new User("UserDB.txt");}
				catch(Exception r){}
			}
		});
		
		btnNewButton.addActionListener(e ->
		{
			userName = textField.getText();
			if(loginAccount.CheckUser(1,userName)==true)
			{
				JOptionPane.showMessageDialog(null, "Account already exists");
				try {loginAccount = new User("UserDB.txt");} 
				catch (Exception e1) {e1.printStackTrace();}
			}
			else if(userName.isEmpty()==true)
			{
				JOptionPane.showMessageDialog(null, "requires user name");
				try {loginAccount = new User("UserDB.txt");} 
				catch (Exception e1) {e1.printStackTrace();}
			}
			else if(userName.isEmpty()!=true)
			{
				loginAccount.makeNewUser(userName);
				try {
					loginAccount.updateDB();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Account "+userName+" has been made");
				try {
					loginAccount = new User("UserDB.txt");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		masterPane.add(loginPanel);
		mainFrame.addLayoutComponent(loginPanel, "loginPanel");
		mainFrame.show(masterPane, "loginPanel");
	}

	public void setTimer(String x){
		timerMessage.setText(x);
	}

	public void changeToPlayGameorCheckScoreMode(int[] scoreBoard)
	{
		JPanel PGOCSM_Panel = new JPanel();
		PGOCSM_Panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		PGOCSM_Panel.setLayout(null);
		
		JButton playAgainButton = new JButton("Play Again?");
		playAgainButton.addActionListener(e -> {
				changeToPlayerSelect(gl);		
	
		});
		playAgainButton.setBounds(303, 172, 121, 79);
		PGOCSM_Panel.add(playAgainButton);
		
        winner=' ';
        int updateAccount =0;
        if(scoreBoard[0]==2){
        	winner='1';
        	updateAccount=1;
        }
        if(scoreBoard[1]==2){
        	winner='2';
        	updateAccount=2;
        }
      	loginAccount.updateScore(updateAccount);
      	try{loginAccount.updateDB();}
      	catch(Exception e){e.printStackTrace();}
		JLabel lblNewLabel = new JLabel("player "+winner+" won the game!");
		lblNewLabel.setBounds(134, 22, 184, 79);
		PGOCSM_Panel.add(lblNewLabel);
		
		JButton toStatsButton = new JButton("Check Stats");
		toStatsButton.setBounds(10, 172, 121, 79);
		PGOCSM_Panel.add(toStatsButton);
		toStatsButton.addActionListener(e ->{
			ToStatsFrame();
		});
		masterPane.add(PGOCSM_Panel);
		mainFrame.addLayoutComponent(PGOCSM_Panel, "PGOCSM_Panel");
		mainFrame.show(masterPane, "PGOCSM_Panel");
	}
	public void ToStatsFrame()
	{
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		statsPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Wins:");
		lblNewLabel.setBounds(10, 45, 89, 14);
		statsPanel.add(lblNewLabel);
		
		int winNumber = loginAccount.P1GW;
		int totalGames = loginAccount.P1GP;
		int losses = totalGames-winNumber;
		gamesWon = new JLabel(Integer.toString(winNumber));
		gamesWon.setBounds(258, 45, 166, 14);
		statsPanel.add(gamesWon);
		
		JLabel lblNewLabel_2 = new JLabel("User name");
		lblNewLabel_2.setBounds(10, 11, 414, 14);
		statsPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Loses:");
		lblNewLabel_3.setBounds(10, 70, 89, 14);
		statsPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel(Integer.toString(losses));
		lblNewLabel_4.setBounds(258, 70, 166, 14);
		statsPanel.add(lblNewLabel_4);
		
	    gamesPlayed = new JLabel("Total games played");
	    gamesPlayed.setBounds(10, 95, 188, 14);
	    statsPanel.add(gamesPlayed);
		
		JLabel lblNewLabel_6 = new JLabel(Integer.toString(totalGames));
		lblNewLabel_6.setBounds(258, 95, 166, 14);
		statsPanel.add(lblNewLabel_6);
		
		exitButton.setBounds(335, 184, 99, 78);
		statsPanel.add(exitButton);
		
		JButton btnNewButton_1 = new JButton("Play Game");
		btnNewButton_1.setBounds(0, 184, 99, 78);
		statsPanel.add(btnNewButton_1);
		statsPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(e ->
		{
			changeToPlayerSelect(gl);
		});
		masterPane.add(statsPanel);
		mainFrame.addLayoutComponent(statsPanel, "statsPanel");
		mainFrame.show(masterPane,"statsPanel" );
		
	}
}

