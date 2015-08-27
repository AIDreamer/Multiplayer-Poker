/**
 * 
 */
package model.hands;

import java.util.ArrayList;

import model.Card;

/**
 * Represents a hand with a pair
 * 
 * @author astro_000
 *
 */
public class Pair extends Hand {

	/** Keep track the number of the pair */
	private int pair;

	/**
	 * Constructs a Pair object based off of the set of cards given
	 * 
	 * @param cards
	 */
	public Pair(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(1);
		determinePair();
	}

	/**
	 * Creates a new pair from the given hand and pairNum
	 * 
	 * @param hand
	 *            ArrayList of Cards
	 * @param pairNum
	 *            number of the highest pair
	 * @return Pair object
	 */
	public Pair(ArrayList<Card> hand, int pairNum) {
		super(hand);
		super.setLevel(1);
		setPair(pairNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.hands.Hand#compareTo(model.hands.Hand)
	 */
	/**
	 * @return the pair
	 */
	public int getPair() {
		return pair;
	}

	/**
	 * @param pair
	 *            the pair to set
	 */
	public void setPair(int pair) {
		this.pair = pair;
	}

	/**
	 * Determines and sets the value of the held pair
	 */
	public void determinePair() {
		int check = 0;
		int temp;
		int pair = 0;
		for (int i = 0; i < Hand.SIZE; i++) {
			temp = getCards().get(i).getValue();
			if (check == temp) {
				pair = check;
			}
			check = temp;
		}
		setPair(pair);
	}

	@Override
	public int compareTo(Hand checkHand) {
		/** Check hand type */
		int check = super.compareTo(checkHand);
		if (check == 0) {
			/** Check the pair */
			if (this.getPair() != ((Pair) checkHand).getPair()) {
				return this.getPair() - ((Pair) checkHand).getPair();
			}
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
		return "Pair";
	}

}
