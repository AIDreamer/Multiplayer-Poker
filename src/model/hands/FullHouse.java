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
 * Represents a hand with a Full House
 * 
 * @author astro_000
 *
 */
public class FullHouse extends Hand {

	/** Represents the value of the three of a kind in the hand */
	private int threeKind;
	/** Represents the value of the pair in the hand */
	private int pair;

	/**
	 * Constructs a hand containing a Full House based off the cards given
	 * 
	 * @param cards
	 */
	public FullHouse(ArrayList<Card> cards) {
		super(cards);
		super.setLevel(6);
		determineHand();
	}

	/**
	 * Constructs a hand containing a Full House based off the cards given
	 * 
	 * @param cards
	 * @param threeKind
	 * @param pair
	 */
	public FullHouse(ArrayList<Card> cards, int threeKind, int pair) {
		super(cards);
		super.setLevel(6);
		this.pair = pair;
		this.threeKind = threeKind;
	}

	/**
	 * @return the threeKind
	 */
	public int getThreeKind() {
		return threeKind;
	}

	/**
	 * @return the pair
	 */
	public int getPair() {
		return pair;
	}

	/**
	 * Determines and sets the value of the the three of a kind and pair
	 */
	public void determineHand() {
		int frontCard = getCards().get(0).getValue();
		int midCard = getCards().get(2).getValue();
		int lastCard = getCards().get(4).getValue();

		if (frontCard == midCard) {
			this.threeKind = frontCard;
			this.pair = lastCard;
		} else {
			this.threeKind = lastCard;
			this.pair = frontCard;
		}
	}

	@Override
	public int compareTo(Hand checkHand) {
		/** Check hand type */
		int check = super.compareTo(checkHand);
		if (check == 0) {
			/** Check the highest pair */
			if (this.getThreeKind() != ((FullHouse) checkHand).getThreeKind()) {
				return this.getThreeKind()
						- ((FullHouse) checkHand).getThreeKind();
			} else if (this.getPair() != ((FullHouse) checkHand).getPair()) {
				return this.getPair() - ((FullHouse) checkHand).getPair();
			} else
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
		return "Full House";
	}

}
