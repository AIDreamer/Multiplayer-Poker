package network;

/**
 * CSCI 205 - Software Design and Engineering
 * Name(s): Robert Cowen
 *
 * Work:	BASS_FinalProject
 * Created: Dec 1, 2014, 10:17:40 PM}
 */

import javax.swing.JOptionPane;

import view.MainView;

/**
 * Runs a client for testing
 * 
 * @author astro_000
 *
 */
public class ClientTest {

	/**
	 * Runs a client object
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		/** initializes and runs client */
		Client application;
		MainView theView = new MainView();

		/** Prompts user for host IP Address */
		String hostServer = JOptionPane.showInputDialog(
				"Please enter host IP (EX: 127.0.0.1):", "127.0.0.1");
		/** Prompts user for server port to connect to */
		int portNumber = Integer.parseInt(JOptionPane.showInputDialog(
				"Please enter port number (EX: 1111):", "1111"));

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
