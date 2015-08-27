package network;

/**
 * CSCI 205 - Software Design and Engineering
 * Name(s): Robert Cowen
 *
 * Work:	BASS_FinalProject
 * Created: Dec 1, 2014, 10:17:40 PM}
 */

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Card;
import model.Game;
import model.Player;
import model.enums.CardState;
import model.enums.GameState;
import model.enums.PlayerState;

/**
 * Represents a server which multiple clients can connect to to play poker
 * 
 * @author astro_000
 *
 */
@SuppressWarnings("serial")
public class Server extends JFrame implements ActionListener {
	/** TextArea used to display information about the current server connection */
	private TextArea outputBox;
	/** Used for testing output connection between server and clients */
	private JButton outputButton;

	/**
	 * Contains a reference to all Server Sockets being used to connect clients
	 * to the server
	 */
	private ArrayList<ServerSocket> serverList;
	/**
	 * Contains a reference to all Sockets being used to connect clients to the
	 * server
	 */
	private ArrayList<Socket> socketList;
	/**
	 * Contains a reference to all input streams being used to connect clients
	 * to the server
	 */
	private ArrayList<Scanner> inputStreamList;
	/**
	 * Contains a reference to all output streams being used to connect clients
	 * to the server
	 */
	private ArrayList<Formatter> outputStreamList;

	/**
	 * Represents the number of players ( and clients ) in the game session run
	 * by server
	 */
	private int numPlayers;
	/** Represents current Game */
	private Game theGame;
	/** Stores the current String being prepped for output */
	private String outputString;

	/**
	 * Constructs a new server object with a textbox used to display relevant
	 * information
	 */
	public Server() {
		/** Sets the title of the window */
		super("Poker Server");

		/** Sets up dialog box */
		outputBox = new TextArea();
		outputBox.setEditable(false);
		add(outputBox, BorderLayout.CENTER);

		/** Sets up output button used for testing Server output connections */
		outputButton = new JButton("Test Output");
		outputButton.addActionListener(this);
		add(outputButton, BorderLayout.SOUTH);
		/** Set to invisible while running the server normally */
		outputButton.setVisible(false);

		/** Initializes output */
		outputString = "";

		/** Initializes all ArrayLists storing client connection information */
		serverList = new ArrayList<ServerSocket>();
		socketList = new ArrayList<Socket>();
		inputStreamList = new ArrayList<Scanner>();
		outputStreamList = new ArrayList<Formatter>();

		/**
		 * Prompts user to input the number of connections in the game and
		 * stores this value
		 */
		/** Prompts user for server port to connect to */
		numPlayers = JOptionPane.showOptionDialog(null,
				"Please select the number of players", "Num Player Selection",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				new ImageIcon(""), new String[] { "2", "3", "4", "5", "6", "7",
						"8", "9", "10" }, JOptionPane.INITIAL_VALUE_PROPERTY);
		numPlayers += 2;

		/** Sets up a new Game */
		theGame = new Game();
		theGame.reset();

		/** Sets size of server window */
		setSize(300, 150);
		/** Make sure the window can't be resized */
		this.setResizable(false);

		/** Displays starting message */
		outputBox.append(" WELCOME TO POKER-KING!\n\n");
	}

	/**
	 * Runs the server and connects all sockets
	 */
	public void runServer() {

		/** Creates server sockets */
		initializeServerSockets();

		/** Sets up Game */
		this.theGame.dealCards();
		theGame.setCurState(GameState.START_GAME);

		/** Connects sockets */
		connectSockets();

		/** Updates clients on connection */
		allConnectedInfo();

		/** Sets up client's view for the start of the game */
		updateClientViews();

		/** Runs Game */
		playGame();
	}

	/**
	 * Sends a data update to all clients representing the current view of the
	 * Game
	 */
	private void updateClientViews() {
		/** Sets up each clients view properly */
		for (int i = 0; i < numPlayers; i++) {
			/** If Game is done, display message about end game */
			if (theGame.getCurState() != GameState.COMPLETED)
				displayMessage("\nSending end game data to update Client: " + i
						+ "\n");
			/** If not, display message about normal update */
			else
				displayMessage("\nSending data to update Client: " + i + "\n");

			/** updates outputString for client view */
			updateViewFromModelData(i);
			outputString += theGame.getCurrentPlayerInt() + " ";
			outputString += theGame.getCurState() + " ";
			outputString += theGame.getPlayers().size() + " ";

			/** Sends output data and resets the output string */
			sendData(i, outputString);
			outputString = "";
		}
	}

	/**
	 * Sends a String notifying clients that all connections have been made
	 */
	private void allConnectedInfo() {
		/** Iterates through all client connections */
		for (int i = 0; i < numPlayers; i++) {
			sendData(i, "All Players Connected");
		}
	}

	/**
	 * Iterates through all Sockets and connects each to the Server
	 */
	private void connectSockets() {
		/** Connects socket to each serverSocket */
		for (int i = 0; i < numPlayers; i++) {
			try {
				/** Makes connections and streams */
				waitForConnection(i);
				getStreams(i);

				/** Updates the outputBox */
				this.update(this.getGraphics());
			} catch (Exception e) {
				/** Displays Error Message */
				displayMessage("Error making Connection at port " + (1111 + i)
						+ "\n");
			}
		}
	}

	/**
	 * Creates and stores Players and Server Socket's based off the number of
	 * players (connections) in the game
	 */
	private void initializeServerSockets() {
		/** Runs through the number of specified players */
		for (int i = 0; i < numPlayers; i++) {
			try {
				/**
				 * Makes new Player associated with this connection and adds to
				 * Game
				 */
				Player newPlayer = Player.createPlayer(i + "", 10000, theGame);
				theGame.addPlayer(newPlayer);

				/**
				 * Creates and stores new Server Socket associated with the new
				 * Player
				 */
				serverList.add(new ServerSocket(1111 + i, 100));
			} catch (IOException e) {
				/** Displays Error Message */
				displayMessage("Error making Server Socket at port "
						+ (1111 + i) + "\n");
			}
		}
	}

	/**
	 * Runs the poker Game for clients
	 */
	private void playGame() {
		/** Displays message updating on information sent */
		displayMessage("\n------ALL INFO SENT TO START GAME------\n");

		/** Keep playing while the game isn't finished */
		while (theGame.getCurState() != GameState.COMPLETED) {
			/** Iterate through every player client */
			for (int i = 0; i < numPlayers; i++) {

				/** If the current player is still able to make moves */
				if (theGame.getPlayers().get(i).isPlaying()) {

					/** Obtains the button input from the current player */
					String button = processInput(i);
					/** Processes button input */
					buttonActionCheck(button);
					/** Checks any update to game state */
					gameStateCheck();
				}

				/** Updates views of clients */
				updateClientViews();
			}
		}

		/** Processes end game state */
		endGame();
	}

	/**
	 * Prompts the server user whether they would like to continue game.
	 * Restarts if yes, closes connections if no.
	 */
	private void endGame() {
		/** Prompts server user for new game */
		int answer = JOptionPane.showOptionDialog(null, "Make New Game?",
				"Poker New Game", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, new ImageIcon(""), new String[] {
						"Yes", "No" }, JOptionPane.DEFAULT_OPTION);

		/** If new game selected */
		if (answer == 0) {

			/** Resets Game data */
			this.theGame.reset();
			this.theGame.dealCards();
			theGame.setCurState(GameState.START_GAME);

			/** Updates views of clients */
			updateClientViews();

			/** Restarts game */
			playGame();

		} else {
			/** Closes all client connections */
			for (int j = 0; j < numPlayers; j++)
				this.closeConnection(j);
		}
	}

	/**
	 * Checks the given input button to see what should be done
	 * 
	 * @param button
	 */
	private void buttonActionCheck(String button) {
		/** Player in next playing position */
		int nextPlayer = theGame.determineNextPlayer();

		/** As long as the game is still playing */
		if (theGame.isPlaying()) {
			/** If call selected */
			if (button.compareTo("Call") == 0) {
				processCall(nextPlayer);
			}

			/** If Raise selected */
			else if (button.contains("Raise")) {
				processRaise(button, nextPlayer);
			}

			/** If Fold selected */
			else if (button.compareTo("Fold") == 0) {
				processFold();
			}

			/** Updates game to next player */
			changeToNextPlayer(nextPlayer);

			/** Flips current player cards to visible */
			theGame.getCurrentPlayer().flipCards();
		}
	}

	/**
	 * Processes a Fold input from the client
	 */
	private void processFold() {
		/** Displays message about input */
		displayMessage("Processing Fold Button\n");

		/** Sets player state to folded */
		theGame.getCurrentPlayer().setState(PlayerState.FOLDED);
	}

	/**
	 * Processes a Raise input from the client
	 * 
	 * @param button
	 * @param nextPlayer
	 */
	private void processRaise(String button, int nextPlayer) {
		/** Displays message about input */
		displayMessage("Processing Raise Button\n");

		/** If Raise input, initialize raise amount */
		int raiseAmount = Integer.parseInt(button.split(" ")[1]);
		theGame.allInRaiseCheck(raiseAmount);
	}

	/**
	 * Processes a Call input from the client
	 * 
	 * @param nextPlayer
	 * @param turnPlayerSize
	 */
	private void processCall(int nextPlayer) {
		/** Displays update message */
		displayMessage("Processing Call Button\n");

		/** Represents players who can play */
		ArrayList<Player> turnPlayers = theGame.currentTurnPlayers();
		int turnPlayerSize = turnPlayers.size();

		/**
		 * If the player's current bet is different than the highest bet
		 */
		if (theGame.getCurrentPlayer().getCurBet() != theGame.getHighestBet())
			/** Set the player's current bet to the highest bet */
			theGame.getCurrentPlayer().setCurBet(theGame.getHighestBet());

		/** If only one player left playing */
		if (turnPlayerSize == 1)
			doShowdown();
	}

	/**
	 * Checks the current state of the game to see if a new round has commenced
	 */
	private void gameStateCheck() {
		/** If only one player is left, they are the winner */
		if (theGame.currentPlayers().size() <= 1) {
			doShowdown();
		}
		/** If the game just started, start Flop */
		else if (theGame.getCurState() == GameState.START_GAME
				&& theGame.isNewRound()) {
			/** Changes game and view for the Flop */
			theGame.getCurrentPlayer().flipCards();
			theGame.setCurState(GameState.FLOP);
			theGame.theFlop();

			/** Displays message about new round */
			displayMessage("\n---DOING FLOP---\n");

		}
		/** If flop is over, start Turn */
		else if (theGame.getCurState() == GameState.FLOP
				&& theGame.isNewRound()) {

			/** Displays message about new round */
			displayMessage("\n---DOING TURN---\n");

			/** Changes game and view for the Turn */
			theGame.setCurState(GameState.TURN);
			theGame.theTurn();
		}
		/** If Turn is over start River */
		else if (theGame.getCurState() == GameState.TURN
				&& theGame.isNewRound()) {

			/** Displays message about new round */
			displayMessage("\n---DOING RIVER---\n");

			/** Changes game and view for the Turn */
			theGame.setCurState(GameState.RIVER);
			theGame.theRiver();
		}
		/** If River is over, do showdown and call winner */
		else if (theGame.getCurState() == GameState.RIVER
				&& theGame.isNewRound()) {
			doShowdown();
		}
	}

	/**
	 * Does the showdown for the game, including all updates associated with
	 */
	private void doShowdown() {
		/** Displays message about new round */
		displayMessage("\nDOING THE SHOWDOWN\n");

		/** runs the showdown to determine winner */
		theGame.theShowdown();
		theGame.setCurState(GameState.COMPLETED);

		/** finds winning hands */
		ArrayList<Player> winners = theGame.findBestHand();

		/** Displays message about end game */
		outputString += "\n------GAME OVER-----\n\nThe winner(s) are: ";

		/** Processes end game output data */
		endGameOutput(winners);

		/** Sets up everyone's views for the end of the game */
		for (int i = 0; i < numPlayers; i++) {
			displayMessage("\nSending end-game info to Client: " + i + "\n");

			sendData(i, outputString);
		}

		/** Resets output */
		outputString = "";
	}

	/**
	 * Processes end game output data and adds to output string
	 * 
	 * @param winners
	 */
	private void endGameOutput(ArrayList<Player> winners) {
		/** Processes the names off all winners */
		for (int pos = 0; pos < winners.size(); pos++) {
			outputString += winners.get(pos).getUsername();
			if ((pos + 1) != winners.size())
				outputString += ",";
		}

		/** New line */
		outputString += "\n";

		/** Processes the hand that each winner had */
		for (Player player : winners) {
			outputString += "Player won with: "
					+ player.getBestHand().toString() + "\n";
		}

		/** determines each winners won cash */
		int winningCash = theGame.getPots().get(0).getCash() / winners.size();
		outputString += "The winner(s) won: " + winningCash + "\n";
	}

	/**
	 * Flips current players cards and changes to next player position
	 * 
	 * @param nextPlayer
	 */
	private void changeToNextPlayer(int nextPlayer) {
		displayMessage("\nCHANGING TO PLAYER " + nextPlayer + "\n");
		theGame.changeToNextPlayer(nextPlayer);
	}

	/**
	 * Updates the client's view components from the model
	 */
	public void updateViewFromModelData(int player) {

		/** Updates all player data */
		updatePlayerData(player);

		/** Displays all table cards */
		updateTableCards();

		/** theView.getCurBetText().setText */
		outputString += theGame.getHighestBet() + " ";

		/** theView.getPotAmount().setText( */
		outputString += theGame.getPots().get(0).getCash() + " ";

	}

	/**
	 * Iterates through all player info and updates each component
	 */
	private void updatePlayerData(int realPlayer) {
		/** Iterates through all players */
		for (int player = 0; player < theGame.getPlayers().size(); player++) {
			/** Represents the current player */
			Player curPlayer = theGame.getPlayers().get(player);

			/** For updating players username */
			outputString += curPlayer.getUsername() + " ";

			/** For updating players cash */
			outputString += curPlayer.getCash() + " ";

			/** For updating players current bet */
			outputString += curPlayer.getCurBet() + " ";

			/** If the game's not over */
			if (theGame.getCurState() != GameState.COMPLETED) {

				/** Updates the current player's card Images */
				outputString += playerCardImage(player, curPlayer, 0,
						realPlayer) + " ";

				/** Updates the current player's second card image */
				outputString += playerCardImage(player, curPlayer, 1,
						realPlayer) + " ";
			} else {
				/** Updates the current player's card Images to show everyone */
				outputString += playerCardImage(player, curPlayer, 0, player)
						+ " ";

				/**
				 * Updates the current player's second card image to show
				 * everyone
				 */
				outputString += playerCardImage(player, curPlayer, 1, player)
						+ " ";
			}
		}
	}

	/**
	 * Returns the given player's specified card Image
	 * 
	 * @param player
	 * @param curPlayer
	 * @param cardNum
	 * @param curClient
	 */
	private String playerCardImage(int player, Player curPlayer, int cardNum,
			int curClient) {
		String cardString = "";

		/** If the player is the current client */
		if (player == curClient) {
			/** Represents current card */
			Card curDealtCard = curPlayer.getDealtCards().get(cardNum);
			/** Creates String representing player's card */
			cardString = "resources/" + curDealtCard.toString() + ".png";
		}
		/** Add face down card */
		else {
			cardString = "resources/FACE_DOWN.png";
		}

		return cardString;
	}

	/**
	 * Returns the full path for the image associated with the given card
	 * 
	 * @param tableCard
	 * @return String representing the location of the Card image
	 */
	private String getCardImage(Card card, int cardNum) {
		/** Represents the path of the given card's image */
		String tableString;
		/** If the card isn't face down */
		if (card.getState() != CardState.FACE_DOWN) {
			/** Find the path of it's corresponding image */
			tableString = "resources/" + card.toString() + ".png";
		}
		/** If card is face down */
		else {
			/** Sets image to face down card */
			tableString = "resources/FACE_DOWN.png";
		}
		return tableString;
	}

	/**
	 * Updates all cards currently on Table
	 */
	private void updateTableCards() {
		/** Iterates through all table cards */
		for (int tableCard = 0; tableCard < theGame.getTableCards().length; tableCard++) {

			/** Represents the path of the card image */
			outputString += getCardImage(theGame.getTableCards()[tableCard],
					tableCard) + " ";
		}
	}

	/**
	 * Attempts to make a connection between the specified serverSocket and a
	 * new socket, if can't throws an exception
	 * 
	 * @param clientNum
	 * @throws IOException
	 */
	public void waitForConnection(int clientNum) throws IOException {
		/** Updates outputbox */
		outputBox.update(outputBox.getGraphics());

		/** Displays message indication */
		displayMessage("Waiting for client " + clientNum + " to connect\n");

		/** Waits for socket connection to specified ServerSocket and stores it */
		Socket newSocket = serverList.get(clientNum).accept();
		socketList.add(newSocket);

		/** Displays update message */
		displayMessage("Made connection with client " + clientNum
				+ " recieved from " + newSocket.getInetAddress().getHostName()
				+ "\n");
	}

	/**
	 * Creates input and output streams for every socket connection
	 * 
	 * @param clientNum
	 * @throws IOException
	 */
	public void getStreams(int clientNum) throws IOException {
		/**
		 * Creates a new Formatter connected to the specified output stream and
		 * stores it
		 */
		Formatter output = new Formatter(socketList.get(clientNum)
				.getOutputStream());
		outputStreamList.add(output);
		/** Flushes stream to ensure connection */
		outputStreamList.get(clientNum).flush();

		/**
		 * Creates a new Scanner connected to the specified input stream and
		 * stores it
		 */
		Scanner input = new Scanner(socketList.get(clientNum).getInputStream());
		inputStreamList.add(input);

		/** Displays update message */
		displayMessage("Client " + clientNum + " streams created\n");
	}

	/**
	 * Processes any input from the specified client
	 * 
	 * @param clientNum
	 * @return
	 */
	public String processInput(int clientNum) {
		/** Initializes input String */
		String inputString = "";

		/** Waits until client makes input */
		while (!inputStreamList.get(clientNum).hasNext()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				displayMessage("ERROR WAITING FOR INPUT FROM SERVER\n");
			}
		}

		/** Processes client input */
		inputString = inputStreamList.get(clientNum).nextLine();
		outputBox.append("DATA RECIEVED: " + inputString);

		/** Returns input */
		return inputString;
	}

	/**
	 * Sends the specified String message to the specified client
	 * 
	 * @param clientNum
	 * @param message
	 */
	public void sendData(int clientNum, String message) {
		/** Adds and sends output to specified client */
		outputStreamList.get(clientNum).format("%s\n", message);
		outputStreamList.get(clientNum).flush();

		/** Displays message about sent data */
		displayMessage("\nSent data to client " + clientNum);
		displayMessage("DATA SENT: " + message);
	}

	/**
	 * Closes the connection to the specified client
	 * 
	 * @param clientNum
	 */
	public void closeConnection(int clientNum) {
		displayMessage("Terminating connection with client " + clientNum);

		try {
			/** closes all connections */
			outputStreamList.get(clientNum).close();
			inputStreamList.get(clientNum).close();
			socketList.get(clientNum).close();
		} catch (IOException ioException) {
			displayMessage("ERROR CLOSING CONNECTION " + clientNum);
		}
	}

	/**
	 * Displays the specified message in the outputBox
	 * 
	 * @param message
	 */
	public void displayMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				outputBox.append(message);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		updateClientViews();

	}
}