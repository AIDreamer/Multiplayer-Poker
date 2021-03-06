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
 * Represents a hand with a four of a kind
 * 
 * @author astro_000
 *
 */
public class FourOfAKind extends Hand {

	/** Value of the three of a kind */
	private int kind;

	/**
	 * Constructs a FourOfAKind object based off of the cards given
	 * 
	 * @param cards
	 */
	public FourOfAKind(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(7);
		determineKind();
	}

	/**
	 * Constructs a FourOfAKind object based off of the cards given
	 * 
	 * @param cards
	 */
	public FourOfAKind(ArrayList<Card> cards, int kind) {
		super(cards);
		super.setLevel(7);
		this.kind = kind;
	}

	/**
	 * @return the kind
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(int kind) {
		this.kind = kind;
	}

	/**
	 * Determines and sets the value of the held kind
	 */
	public void determineKind() {
		int check = 0;
		int temp;
		int kind = 0;
		for (int i = 0; i < Hand.SIZE; i++) {
			temp = getCards().get(i).getValue();
			if (check == temp) {
				kind = check;
			}
			check = temp;
		}
		setKind(kind);
	}

	@Override
	public int compareTo(Hand checkHand) {
		/** Check hand type */
		int check = super.compareTo(checkHand);
		if (check == 0) {
			/** Check the highest pair */
			if (this.getKind() != ((FourOfAKind) checkHand).getKind()) {
				return this.getKind() - ((FourOfAKind) checkHand).getKind();
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
		return "Four Of A Kind";
	}

}
