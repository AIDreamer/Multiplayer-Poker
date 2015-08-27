/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */
package model.hands;

import java.util.ArrayList;

import model.Card;

/**
 * Represents a Royal Flush hand, the best hand possible
 * 
 * @author astro_000
 *
 */
public class RoyalFlush extends Hand {

	/**
	 * Constructs a RoyalFlush object based off of the given cards
	 */
	public RoyalFlush(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(9);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Royal Flush";
	}

}
