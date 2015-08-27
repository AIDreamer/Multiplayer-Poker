/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */
package model;

import java.io.Serializable;

import model.enums.CardState;
import model.enums.Suit;

/**
 * Represents a Card which has a suit and a value
 * 
 * @author astro_000
 *
 */
public class Card implements Comparable<Card>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -395868020224925984L;

	public static Card createCard(Suit suit, int value) {
		return new Card(suit, value);
	}

	/** Integer from 2 to 14 representing the card value */
	private Integer value;
	/** Suit of Card object */
	private Suit suit;
	/** Represents the state of the Card */
	private CardState state;

	/**
	 * Constructs a Card object from the given suit and value
	 * 
	 * @param suit
	 *            Suit value
	 * @param value
	 *            Card Value
	 */
	private Card(Suit suit, int value) {
		this.suit = suit;
		this.value = value;
		this.state = CardState.FACE_DOWN;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(CardState state) {
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public CardState getState() {
		return state;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the suit
	 */
	public Suit getSuit() {
		return suit;
	}

	@Override
	public int compareTo(Card checkCard) {
		return getValue() - checkCard.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + value + "_" + suit;
	}

}