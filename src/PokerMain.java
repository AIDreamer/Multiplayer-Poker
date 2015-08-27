import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import network.Client;
import network.Server;
import view.MainView;
import controller.PokerController;

/**
 * CSCI 205 - Software Design and Engineering
 * Name(s): Robert Cowen
 *
 * Work:	BASS_FinalProject
 * Created: Dec 2, 2014, 11:24:31 AM}
 */

/**
 * Runs the Poker-King Game
 * 
 * @author astro_000
 *
 */
public class PokerMain {

	/**
	 * Creates a PokerMain object
	 */
	public PokerMain() {
	}

	/**
	 * Runs the Poker-King Game
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		/**
		 * Prompts user to select whether they would like a network or local
		 * game
		 */
		int localChoice = JOptionPane.showOptionDialog(null,
				"Would you like to play a local game or a network game?",
				"Poker Game Start", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, new ImageIcon(""), new String[] {
						"Local", "Network" }, "Local");

		/** If local */
		if (localChoice == 0) {
			makeLocalGame();
		}
		/** If network */
		else if (localChoice == 1) {
			/**
			 * Prompts user to select whether they would like to make a new
			 * client or server
			 */
			int serverChoice = JOptionPane.showOptionDialog(null,
					"Would you like to make a server or a client?",
					"Poker Game Start", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, new ImageIcon(""),
					new String[] { "Server", "Client" }, "Server");

			/** If Client */
			if (serverChoice == 1) {
				makeClient(args);
			}
			/** If Server */
			else if (serverChoice == 0) {
				makeServer();
			}
		}
	}

	/**
	 * Makes a local game
	 */
	private static void makeLocalGame() {
		/** Initializes and runs local game */
		MainView frame = new MainView();
		try {
			PokerController ctrl = new PokerController(frame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.setVisible(true);
	}

	/**
	 * Makes a server object
	 */
	private static void makeServer() {
		/** Initializes and runs server */
		Server application = new Server();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setResizable(false);
		application.setVisible(true);
		application.runServer();
	}

	/**
	 * Makes a client object
	 * 
	 * @param args
	 */
	private static void makeClient(final String[] args) {
		/** initializes and runs client */
		Client application;
		MainView theView = new MainView();

		/** Prompts user for host IP Address */
		String hostServer = JOptionPane.showInputDialog(
				"Please enter host IP (EX: 127.0.0.1):", "127.0.0.1");

		/** Prompts user for server port to connect to */
		int portNumber = JOptionPane.showOptionDialog(null,
				"Please select port number", "Port Selection",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				new ImageIcon(""), new String[] { "1", "2", "3", "4", "5", "6",
						"7", "8", "9", "10" },
				JOptionPane.INITIAL_VALUE_PROPERTY);
		portNumber += 1111;

		/** If there are no command line arguments */
		if (args.length == 0)
			application = new Client(hostServer, portNumber, theView); // localhost
		else
			application = new Client(args[0], Integer.parseInt(args[1]),
					theView);

		theView.setVisible(true);
		application.runClient();
	}
}
