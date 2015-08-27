/**
 * 
 */
package model.hands;

import java.util.ArrayList;

import model.Card;

/**
 * Represents a hand with a high card
 * 
 * @author astro_000
 *
 */
public class HighCard extends Hand {

	/**
	 * Constructs a high card hand
	 * 
	 * @param cards
	 */
	public HighCard(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.hands.Hand#compareTo(model.hands.Hand)
	 */
	@Override
	public int compareTo(Hand checkHand) {
		int check = super.compareTo(checkHand);
		if (check == 0) {
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
		return "High Card";
	}

}
