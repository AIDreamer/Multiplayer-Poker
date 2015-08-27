/**
 * 
 */
package model.hands;

import java.util.ArrayList;

import model.Card;

/**
 * Represents a hand with two pairs
 * 
 * @author astro_000
 *
 */
public class TwoPair extends Hand {

	/** Represents the value of the highest pair */
	private int highPair;
	/** Represents the value of the lower pair */
	private int lowPair;

	/**
	 * Constructs a TwoPair object based off of the given Cards
	 */
	public TwoPair(ArrayList<Card> hand) {
		super(hand);
		super.setLevel(2);
		determinePair();
	}

	/**
	 * Constructs a TwoPair object based off of the given Cards
	 */
	public TwoPair(ArrayList<Card> hand, int lowPairNum, int highPairNum) {
		super(hand);
		super.setLevel(2);
		setPair(lowPairNum, highPairNum);
	}

	/**
	 * @param pair
	 *            the pair to set
	 */
	public void setPair(int lowPair, int highPair) {
		this.lowPair = lowPair;
		this.highPair = highPair;
	}

	/**
	 * @return the highPair
	 */
	public int getHighPair() {
		return highPair;
	}

	/**
	 * @return the lowPair
	 */
	public int getLowPair() {
		return lowPair;
	}

	/**
	 * Determines and sets the value of the held pair
	 */
	public void determinePair() {
		int check = 0;
		int temp;
		int highPair = 0;
		int lowPair = 0;
		for (int i = 0; i < Hand.SIZE; i++) {
			/** Sets temp value to check */
			temp = getCards().get(i).getValue();
			/** If the previous card was the same value */
			if (check == temp) {
				/**
				 * Check to see if this pair is higher than the previous highest
				 */
				if (check > highPair) {
					/** Swap low and high */
					lowPair = highPair;
					highPair = check;
				}
			}
			check = temp;
		}
		/** Sets the values for low and high */
		setPair(lowPair, highPair);
	}

	@Override
	public int compareTo(Hand checkHand) {
		/** Check hand type */
		int check = super.compareTo(checkHand);
		if (check == 0) {
			/** Check the highest pair */
			if (this.getHighPair() != ((TwoPair) checkHand).getHighPair()) {
				return this.getHighPair() - ((TwoPair) checkHand).getHighPair();
			}
			/** Check the lowest pair */
			else if (this.getLowPair() != ((TwoPair) checkHand).getLowPair()) {
				return this.getLowPair() - ((TwoPair) checkHand).getLowPair();
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
		return "Two Pair";
	}

}
