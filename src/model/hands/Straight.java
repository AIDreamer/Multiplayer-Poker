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
 * Represents a hand with a Straight
 * 
 * @author astro_000
 *
 */
public class Straight extends Hand {

	/** Represents the highest value in the sequence */
	private int high;

	/**
	 * Constructs a Straight object based off of the given cards
	 * 
	 * @param cards
	 */
	public Straight(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(4);
		determineHigh();
	}

	/**
	 * Constructs a Straight object based off of the given cards
	 * 
	 * @param cards
	 */
	public Straight(ArrayList<Card> cards, int high) {
		super(cards);
		super.setLevel(4);
		this.high = high;
	}

	/**
	 * @return the high
	 */
	public int getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(int high) {
		this.high = high;
	}

	/**
	 * Determines and sets the value of the held high
	 */
	public void determineHigh() {
		/** Represents the card with the highest value */
		int frontCard = getCards().get(0).getValue();
		/** Represents the card with the lowest value */
		int backCard = getCards().get(4).getValue();
		/** Checks to see if the Ace will be played high or low */
		if (frontCard == 14 && backCard == 2) {
			setHigh(5);
		} else {
			setHigh(frontCard);
		}
	}

	@Override
	public int compareTo(Hand checkHand) {
		/** Check hand type */
		int check = super.compareTo(checkHand);
		if (check == 0) {
			/** Check the highest pair */
			if (this.getHigh() != ((Straight) checkHand).getHigh()) {
				return this.getHigh() - ((Straight) checkHand).getHigh();
			}
			return 0;
		}
		/**
		 * Would return zero if they are equal, though this shouldn't be
		 * possible
		 */
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Straight";
	}

}
