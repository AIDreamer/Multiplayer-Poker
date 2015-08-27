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
 * Represents a hand containging a flush
 * 
 * @author astro_000
 *
 */
public class Flush extends Hand {

	/**
	 * Constructs a Flush object
	 * 
	 * @param cards
	 */
	public Flush(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(5);
	}

	@Override
	public int compareTo(Hand checkHand) {
		/** Check hand type */
		int check = super.compareTo(checkHand);
		if (check == 0) {
			/** Else check the remaining high cards */
			for (int i = 0; i < Hand.SIZE; i++) {
				check = this.getCards().get(i)
						.compareTo(checkHand.getCards().get(i));
				if (check != 0) {
					return check;
				}
			}
			return 0;
		}
		return check;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Flush";
	}

}
