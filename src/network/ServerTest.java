package network;

/**
 * CSCI 205 - Software Design and Engineering
 * Name(s): Robert Cowen
 *
 * Work:	BASS_FinalProject
 * Created: Dec 1, 2014, 10:17:40 PM}
 */

import javax.swing.JFrame;

/**
 * Runs a server application for testing
 * 
 * @author astro_000
 *
 */
public class ServerTest {
	/**
	 * Runs server test
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		/** Initializes and runs server */
		Server application = new Server();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setResizable(false);
		application.setVisible(true);
		application.runServer();
	}

}