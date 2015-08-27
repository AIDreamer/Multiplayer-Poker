/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */
package model;

import java.util.ArrayList;

/**
 * Represents a pot of money in play
 * @author astro_000
 *
 */
public class Pot {

	/**Holds all players associated with pot*/
	private ArrayList<Player> players = new ArrayList<Player>();
	/**Amount of cash in pot*/
	private int cash;
	
	/**
	 * Constructs a Pot object with the specified players and cash amount
	 * @param players
	 * @param cash
	 */
	public Pot(ArrayList<Player> players){
		this.players = players;
		this.cash = 0;
	}

	/**
	 * @return the cash
	 */
	public int getCash() {
		return cash;
	}

	/**
	 * @param cash the cash to add to the pot
	 */
	public void addCash(int cash) {
		this.cash += cash;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
}
