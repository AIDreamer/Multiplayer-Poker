/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Random;

import model.enums.Suit;

/**
 * Represents a deck of cards
 * @author astro_000
 *
 */
public class Deck {

	/**Represents the number of possible card values*/
	private static final int NUM_VALUES = 15;
	/**Represents the number of possible Suits*/
	private static final int NUM_SUITS = 4;
	/**Holds all of the cards in the Deck*/
	private ArrayList<Card> cardList = new ArrayList<Card>();
	/**Instantiate a Random Generator*/
	private static Random randomGenerator = new Random();
	
	/**
	 * Constructs a Deck object containing 52 cards
	 */
	public Deck(){
		Card card;
		//Alternates through all 4 Suits
		for(int suit = 0; suit<NUM_SUITS; suit++){
			//Alternates through all possible card values
			for(int value = 2; value<NUM_VALUES; value++){
				//Makes card and adds to cardList
				card = Card.createCard(Suit.values()[suit], value);
				cardList.add(card);
			}
		}
	}
	
	/**
	 * take a card out of the Deck
	 * @return a Card if there are cards available, null otherwise
	 */
	public Card takeRandomCard() {
		if (cardList.size() == 0) {
			return null;
		}
		/**Generate a random position*/
		int pos = randomGenerator.nextInt(cardList.size());
		return cardList.remove(pos);
		
	}
	
	/**
	 * Returns the current size of the Deck
	 * @return int represents size of Deck
	 */
	public int getNumCards(){
		return this.cardList.size();
	}
}
