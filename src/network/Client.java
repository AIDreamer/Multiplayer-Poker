package network;

/**
 * CSCI 205 - Software Design and Engineering
 * Name(s): Robert Cowen
 *
 * Work:	BASS_FinalProject
 * Created: Dec 1, 2014, 10:17:40 PM}
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.MainView;

/**
 * Represents an individual client connected to the server game
 * 
 * @author astro_000
 *
 */
public class Client implements ActionListener, ChangeListener {
	/** Stream for input */
	private InputStream inStream;

	/** Used to read in information from the input stream */
	private BufferedReader input;
	/** Used to output information through the output stream */
	private Formatter output;

	/** Represents host IP address to connect to */
	private String hostServer;
	/** Represents server port to connect to */
	private int serverPort;

	/** Represents Game GUI for display */
	private MainView theView;
	/** Represents current player num */
	private int playerNum;
	/** Represents current player's turn */
	private int curPlayer;
	/** Represents the current number of players in the game */
	private int numPlayers;
	/** Represents the current bet of the player client */
	private int curBet;
	/** Represents the current cash held by the player client */
	private int curCash;

	/** For connecting to server */
	private Socket client;

	/**
	 * Constructs a Client object based off of the given host, port, and view
	 * 
	 * @param host
	 * @param portNum
	 * @param theView
	 */
	public Client(String host, int portNum, MainView theView) {

		/** Initializes values */
		hostServer = host;
		serverPort = portNum;
		this.theView = theView;
		this.playerNum = portNum - 1111;

		/** Displays message on update box */
		theView.getUpdateBox().append("YOU ARE PLAYER " + playerNum + "\n");

		/** Adds actionListeners to all buttons in view */
		for (JButton button : theView.getButtonList()) {
			button.addActionListener(this);
		}

		/** Initializes button in starting state */
		theView.getButtonList().get(1).setVisible(false);

		/** Adds listener to slider component */
		theView.getRaiseSlider().addChangeListener(this);

		/** Updates view */
		theView.update(theView.getGraphics());
	}

	/**
	 * Runs the client and sets up all necessary items
	 */
	public void runClient() {

		try {
			/** Sets up socket and connects to the server */
			connectToServer();
			/** Creates and connects streams */
			getStreams();
		} catch (EOFException eofException) {
			theView.getUpdateBox().append("ERROR INITIALIZING");
		} catch (IOException ioException) {
			theView.getUpdateBox().append("ERROR INITIALIZING");
		}

		try {
			/**
			 * Waits for input from the server to initialize the games starting
			 * state
			 */
			waitForInputStream();
		} catch (IOException e) {
			theView.getUpdateBox().append("ERROR GETTING INPUT");
		}

	}

	/**
	 * Makes connection to the host server
	 * 
	 * @throws IOException
	 */
	private void connectToServer() throws IOException {
		/**
		 * Makes new socket and connects it to the server at the specified ID
		 * and port
		 */
		client = new Socket(InetAddress.getByName(hostServer), serverPort);

		/** Displays connection information in the update box */
		theView.getUpdateBox().append(
				"Connected with: " + InetAddress.getByName(hostServer) + "\n");
	}

	/**
	 * Creates all streams to connect client with server
	 * 
	 * @throws IOException
	 */
	private void getStreams() throws IOException {
		/** Initializes output stream */
		output = new Formatter(client.getOutputStream());
		output.flush();

		/** Initializes the input stream */
		inStream = client.getInputStream();
		input = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
	}

	/**
	 * Waits for input from server and then processes it
	 * 
	 * @throws IOException
	 */
	private void waitForInputStream() throws IOException {

		/** Initializes input string */
		String inputString = "";

		try {
			/** Disables buttons while waiting for input from the server */
			theView.getButtonList().get(0).setVisible(false);
			theView.getButtonList().get(1).setVisible(false);
			theView.getButtonList().get(2).setVisible(false);
			theView.getNotYourTurn().setVisible(true);
			theView.update(theView.getGraphics());

			while (!input.ready()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					theView.getUpdateBox().append(
							"ERROR WAITING FOR INPUT FROM SERVER\n");
				}
			}

		} catch (IOException e1) {
			theView.getUpdateBox().append(
					"ERROR WAITING FOR INPUT FROM SERVER\n");
		}

		/** Reads in input data and sends it to be processed */
		inputString = input.readLine();
		processInput(inputString);

		/**
		 * If the client is the current player, allow them to use their input
		 * buttons again
		 */
		if (curPlayer == playerNum
				&& inputString.compareTo("All Players Connected") != 0) {
			theView.getNotYourTurn().setVisible(false);
			theView.getButtonList().get(0).setVisible(true);
			theView.getButtonList().get(1).setVisible(true);
			theView.getButtonList().get(2).setVisible(true);
			theView.update(theView.getGraphics());
		} else {
			theView.getButtonList().get(1).setVisible(true);
		}

		/** Display message concerning new player */
		theView.getUpdateBox().append("CURRENT PLAYER: " + curPlayer + "\n\n");

		/** If all players aren't connected, wait for more input */
		if (curPlayer != playerNum
				&& inputString.compareTo("All Players Connected") != 0) {
			waitForInputStream();
		}
	}

	/**
	 * Processes the input data and updates the view accordingly
	 */
	private void processInput(String inputString) {

		/** If the input data is for updating the view */
		if (inputString.contains("resources/")) {
			viewUpdate(inputString);
		}
		/** If all players connected */
		else if (inputString.compareTo("All Players Connected") == 0) {
			/** Turns buttons invisible until players begin game */
			theView.getButtonList().get(0).setVisible(false);
			theView.getButtonList().get(2).setVisible(false);

		}
		/** If game is over, print end game data */
		else {
			updateEndGame(inputString);
		}

		/** Updates the view */
		theView.update(theView.getGraphics());
	}

	/**
	 * Updates the view based off of a turn made
	 * 
	 * @param inputString
	 */
	private void viewUpdate(String inputString) {
		/** Split the input by spaces between data */
		String[] processInfo = inputString.split(" ");

		/** Updates the current number of players based off of the input */
		numPlayers = Integer.parseInt(processInfo[processInfo.length - 1]);
		int curPos = 0;

		/** Updates the current player based off of the input */
		curPlayer = Integer.parseInt(processInfo[processInfo.length - 3]);
		String curState = processInfo[processInfo.length - 2];

		/** Updates the information of each player in the view */
		curPos = updatePlayerView(processInfo, curPos, curState);

		/** Updates all table cads in the view */
		curPos = updateTableCards(processInfo, curPos);

		/** Updates the value of the current bet based off of the input data */
		curBet = Integer.parseInt(processInfo[curPos++]);
		theView.getCurBetText().setText(curBet + "");

		/** Updates the pot amount in the view */
		theView.getPotAmount().setText(processInfo[curPos++]);

		/** Updates the view of the slider */
		updateSlider();
	}

	/**
	 * Updates the view for the end of the game
	 * 
	 * @param inputString
	 */
	private void updateEndGame(String inputString) {
		/** Displays ending data */
		theView.getUpdateBox().append("\n\n" + inputString + "\n");
		try {
			/** Reads in all of the end game data in */
			while (input.ready()) {
				/** Reads input */
				inputString = input.readLine();

				/** If still reading in game end data update output box */
				if (!inputString.contains("COMPLETED"))
					theView.getUpdateBox().append(inputString + "\n");
				/** otherwise process the view update */
				else if (inputString.contains("resources/")) {
					processInput(inputString);
				}
			}
		} catch (IOException e) {
			theView.getUpdateBox().append("ERROR READING IN DATA");
		}

	}

	/**
	 * Updates the view of all table cards in the view
	 * 
	 * @param processInfo
	 * @param curPos
	 * @return
	 */
	private int updateTableCards(String[] processInfo, int curPos) {
		/** Updates table card 1 in the view */
		theView.getTableList()
				.get(0)
				.setIcon(
						new ImageIcon(getClass().getResource(".").getPath()
								+ processInfo[curPos++]));

		/** Updates table card 2 in the view */
		theView.getTableList()
				.get(1)
				.setIcon(
						new ImageIcon(getClass().getResource(".").getPath()
								+ processInfo[curPos++]));

		/** Updates table card 3 in the view */
		theView.getTableList()
				.get(2)
				.setIcon(
						new ImageIcon(getClass().getResource(".").getPath()
								+ processInfo[curPos++]));

		/** Updates table card 4 in the view */
		theView.getTableList()
				.get(3)
				.setIcon(
						new ImageIcon(getClass().getResource(".").getPath()
								+ processInfo[curPos++]));

		/** Updates table card 5 in the view */
		theView.getTableList()
				.get(4)
				.setIcon(
						new ImageIcon(getClass().getResource(".").getPath()
								+ processInfo[curPos++]));
		return curPos;
	}

	/**
	 * Updates all player information in the view
	 * 
	 * @param processInfo
	 * @param curPos
	 * @param curState
	 * @return
	 */
	private int updatePlayerView(String[] processInfo, int curPos,
			String curState) {
		/** Updates player data in the view */
		for (int player = 0; player < numPlayers; player++) {
			/** Sets player name in view based off of input */
			theView.getNameList().get(player).setText(processInfo[curPos++]);

			/** If the player is the client player use curCash */
			if (player == playerNum) {
				curCash = Integer.parseInt(processInfo[curPos++]);
				theView.getMoneyList().get(player).setText(curCash + "");
			}
			/** Otherwise look at input */
			else {
				theView.getMoneyList().get(player)
						.setText(processInfo[curPos++]);
			}

			/** Updates players bet in view */
			theView.getBetList().get(player).setText(processInfo[curPos++]);

			/** Updates player's first card */
			theView.getCard1List()
					.get(player)
					.setIcon(
							new ImageIcon(getClass().getResource(".").getPath()
									+ processInfo[curPos++]));
			/** Updates the player's second card */
			theView.getCard2List()
					.get(player)
					.setIcon(
							new ImageIcon(getClass().getResource(".").getPath()
									+ processInfo[curPos++]));

			/** Changes the text color to indicate the current player */
			curPlayerIndicator(curPlayer, curState, player);

		}
		return curPos;
	}

	/**
	 * Updates the slider based off the current player
	 */
	private void updateSlider() {
		/** Max that can be raised by is All In */
		int maxValue = curCash;
		/** Min that can be raised is by 1 */
		int minValue = curBet + 1;

		/** If the players cash is less than the current highest bet */
		if (minValue > maxValue)
			/** Set the only option to be All In */
			minValue = maxValue;

		/** Starting value set to middle of slider */
		int initValue = (maxValue + minValue) / 2;

		/**
		 * Updates the Model of the slider based off of max, min, and init
		 * values
		 */
		BoundedRangeModel raiseSliderModel = new DefaultBoundedRangeModel(
				initValue, 0, minValue, maxValue);
		theView.getRaiseSlider().setModel(raiseSliderModel);
		theView.getRaiseAmount().setText(
				theView.getRaiseSlider().getValue() + "");
	}

	/**
	 * Uupdates the marker pointing to the current player
	 * 
	 * @param currentPlayer
	 * @param curState
	 * @param player
	 */
	private void curPlayerIndicator(int currentPlayer, String curState,
			int player) {
		if (currentPlayer == player && curState.compareTo("COMPLETED") != 0) {
			/** Resets all of the passed players info to be highlighted red */
			theView.getNameList().get(player).setForeground(Color.red);
			theView.getMoneyList().get(player).setForeground(Color.red);
			theView.getBetList().get(player).setForeground(Color.red);
		} else {
			/** Sets players info to normal color scheme */
			theView.getNameList().get(player).setForeground(Color.black);
			theView.getMoneyList().get(player).setForeground(Color.white);
			theView.getBetList().get(player).setForeground(Color.white);
		}
	}

	/**
	 * Closes all connections of the client
	 */
	@SuppressWarnings("unused")
	private void closeConnection() {
		try {
			/** Closes all connections */
			output.close();
			input.close();
			client.close();
		} catch (IOException ioException) {
			theView.getUpdateBox().append("ERROR CLOSING CONNECTIONS\n");
		}
	}

	/**
	 * Sends data to the server in the form of a String object
	 * 
	 * @param message
	 */
	public void sendData(String message) {
		output.format("%s\n", message);
		output.flush();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		String buttonText = button.getText();

		/** If they hit the Start button, updates buttons */
		if (buttonText.compareTo("Start") == 0) {
			this.theView.getButtonList().get(2).setText("Fold");
			this.theView.getButtonList().get(1).setText("Call");
			this.theView.getButtonList().get(0).setVisible(true);
			this.theView.getButtonList().get(0).setText("Raise");
			this.theView.getButtonList().get(2).setVisible(true);
			this.theView.getRaiseAmount().setVisible(true);

			/** Update the view from input */
			try {
				waitForInputStream();
			} catch (IOException e1) {
				theView.getUpdateBox().append("ERROR WAITING FOR INPUT\n");
			}
		}

		/** If the client is the current player */
		if (playerNum == curPlayer) {

			/** Send specially formatted data for raise */
			if (buttonText.compareTo("Raise") == 0) {
				sendData(button.getText() + " "
						+ theView.getRaiseAmount().getText());
				try {
					waitForInputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			/** Else, as long as it's not Start send normally */
			else if (buttonText.compareTo("Start") != 0) {
				sendData(button.getText());
				try {
					waitForInputStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		/** If not client players turn, display message */
		else if (playerNum != curPlayer) {
			theView.getUpdateBox().append("NOT YOUR TURN");
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		/** Updates the raise amount based off of the current value of slider */
		theView.getRaiseAmount().setText(
				"" + theView.getRaiseSlider().getValue());
	}
}