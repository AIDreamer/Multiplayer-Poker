/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */
package model.hands;

import java.util.ArrayList;
import java.util.Collections;

import model.Card;

/**
 * Represents a playable hand containing 5 cards
 * @author astro_000
 *
 */
public class Hand implements Comparable<Hand> {
	
	/**We set the size of hand to 5*/
	public static final int SIZE = 5;

	/**Represents the cards in the hand*/
	private ArrayList<Card> cards;
	/**Represents the level of the hand*/
	private int level;
	
	/**
	 * Constructs a hand object based off of the given card array
	 * @param cards Cards in hand (5 total)
	 */
	public Hand(ArrayList<Card> cards){
		this.cards = cards;
		//Sorts our list of cards
		Collections.sort(this.cards, Collections.reverseOrder());
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	@Override
	public int compareTo(Hand checkHand) {
		return this.getLevel() - checkHand.getLevel();
	}

	@Override
	public String toString() {
		return "[" + cards + "], level=" + level;
	}
	
	

}
