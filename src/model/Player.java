/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import model.enums.CardState;
import model.enums.PlayerState;
import model.enums.Suit;
import model.hands.Hand;

/**
 * Represents a Player in a Poker Game
 * 
 * @author astro_000
 *
 */
public class Player implements Serializable {
	/** The players current best hand */
	private Hand bestHand;
	/** Name of the player */
	private String username;
	/** Amount of money the player currently has */
	private int cash;
	/** Current Bet */
	private int curBet;
	/** The player's dealt cards */
	private ArrayList<Card> dealtCards;
	/** The current status of the player */
	private PlayerState state;
	/** The current Game */
	private Game theGame;

	/**
	 * Constructs a new Player object with the given name and starting cash
	 * 
	 * @param username
	 *            String representing name
	 * @param cash
	 *            int representing cash amount
	 */
	private Player(String username, int cash, Game theGame) {
		this.username = username;
		this.cash = cash;
		this.state = PlayerState.PLAYING;
		this.theGame = theGame;
		dealtCards = new ArrayList<Card>();
	}

	/** Generate best possible hand from an array list of card */
	public void generateBestHand(ArrayList<Card> cards) {
		/** Set the length of the list of cards */
		int length = cards.size();
		/** Create a new list of hands that will be sorted */
		ArrayList<Hand> handList = new ArrayList<Hand>();
		/** Create a new list of 5 cards that will be examined */
		ArrayList<Card> hand = new ArrayList<Card>();

		/** For every possible combination of 5 cards */
		for (int card1 = 0; card1 < length; card1++) {
			hand.add(cards.get(card1));
			for (int card2 = card1 + 1; card2 < length; card2++) {
				hand.add(cards.get(card2));
				for (int card3 = card2 + 1; card3 < length; card3++) {
					hand.add(cards.get(card3));
					for (int card4 = card3 + 1; card4 < length; card4++) {
						hand.add(cards.get(card4));
						for (int card5 = card4 + 1; card5 < length; card5++) {
							hand.add(cards.get(card5));

							bestHandCheck(cards, handList, hand);
							hand.remove(4);
						}
						hand.remove(3);
					}
					hand.remove(2);
				}
				hand.remove(1);
			}
			hand.remove(0);
		}
		/** Sort the list of the possible hands from low to high */
		Collections.sort(handList, Collections.reverseOrder());
		/** Set bestHand to the firstHand in the list */
		this.bestHand = handList.get(0);
	}

	/**
	 * Checks the given ArrayList of cards and makes the appropriate hand
	 * 
	 * @param cards
	 * @param handList
	 * @param hand
	 * @param card1
	 * @param card2
	 * @param card3
	 * @param card4
	 * @param card5
	 */
	@SuppressWarnings("unchecked")
	private void bestHandCheck(ArrayList<Card> cards, ArrayList<Hand> handList,
			ArrayList<Card> hand) {

		ArrayList<Card> cloneHand = (ArrayList<Card>) hand.clone();

		/**
		 * Determine the type of hand from the list of 5 chosen cards
		 */
		Hand newHand = CardUtility.createStraightFlush(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createFourOfAKind(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createFullHouse(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createFlush(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createStraight(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createThreeOfAKind(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createTwoPair(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createAPair(cloneHand);
		if (newHand == null)
			newHand = CardUtility.createHighCard(cloneHand);

		handList.add(newHand);
	}

	/**
	 * take the money from the player and return the amount of money bet
	 * 
	 * @param money
	 * @return the amount of money that the person bet
	 */
	public int betMoney(int money) {
		if (money >= this.getCash()) {
			this.setState(PlayerState.ALL_IN);
			int moneyBet = this.getCash();
			this.setCash(0);
			return moneyBet;
		} else {
			this.cash -= money;
			return money;
		}
	}

	/**
	 * Flips over the current players cards
	 */
	public void flipCards() {
		for (Card card : this.dealtCards) {
			if (card.getState() == CardState.FACE_DOWN)
				card.setState(CardState.FACE_UP);
			else
				card.setState(CardState.FACE_DOWN);
		}
	}

	/**
	 * Returns a boolean representing whether the player is still able to make
	 * moves
	 * 
	 * @return
	 */
	public boolean isPlaying() {
		return this.state == PlayerState.PLAYING;
	}

	/**
	 * Returns the current state of the Player
	 * 
	 * @return the state
	 */
	public PlayerState getState() {
		return state;
	}

	/**
	 * Sets the current value of the player
	 * 
	 * @param state
	 *            the state to set
	 */
	public void setState(PlayerState state) {
		this.state = state;
	}

	/**
	 * Returns the current best hand
	 * 
	 * @return the bestHand
	 */
	public Hand getBestHand() {
		return bestHand;
	}

	/**
	 * Sets the value of best hand
	 * 
	 * @param bestHand
	 *            the bestHand to set
	 */
	public void setBestHand(Hand bestHand) {
		this.bestHand = bestHand;
	}

	/**
	 * Returns the players cash
	 * 
	 * @return the cash
	 */
	public int getCash() {
		return cash;
	}

	/**
	 * Sets the players cash
	 * 
	 * @param cash
	 *            the cash to set
	 */
	public void setCash(int cash) {
		this.cash = cash;
	}

	/**
	 * Adds cash to player
	 * 
	 * @param cash
	 *            add the cash to set
	 */
	public void addCash(int cash) {
		this.cash += cash;
	}

	/**
	 * Returns the players cards
	 * 
	 * @return the dealtCards
	 */
	public ArrayList<Card> getDealtCards() {
		return dealtCards;
	}

	/**
	 * Sets the players dealt cards
	 * 
	 * @param dealtCards
	 *            the dealtCards to set
	 */
	public void setDealtCards(ArrayList<Card> dealtCards) {
		this.dealtCards = dealtCards;
	}

	/**
	 * Sets the players dealt cards
	 * 
	 * @param card1
	 * @param card2
	 *            the two card that we add to the player list
	 */
	public void setDealtCards(Card card1, Card card2) {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(card1);
		cards.add(card2);
		this.setDealtCards(cards);
	}

	/**
	 * Returns the players name
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the current bet
	 * 
	 * @return the curBet
	 */
	public int getCurBet() {
		return curBet;
	}

	/**
	 * Sets the value of the current bet
	 * 
	 * @param curBet
	 *            the curBet to set
	 */
	public void setCurBet(int curBet) {
		/** If ammount trying to add is greater than the players cash */
		if (curBet >= this.getCash()) {

			/** The player goes all in */
			this.setState(PlayerState.ALL_IN);
			int moneyBet = this.getCash();
			this.curBet = moneyBet;
			this.theGame.getPots().get(0).addCash(moneyBet);
		} else {
			/** If not, just sets the players bet */
			this.curBet = curBet;
			this.theGame.getPots().get(0).addCash(curBet);
		}
	}

	/**
	 * Runs a player test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Card> sevenCards = new ArrayList<Card>();
		sevenCards.add(Card.createCard(Suit.SPADE, 10));
		sevenCards.add(Card.createCard(Suit.CLUB, 8));
		sevenCards.add(Card.createCard(Suit.SPADE, 11));
		sevenCards.add(Card.createCard(Suit.DIAMOND, 8));
		sevenCards.add(Card.createCard(Suit.SPADE, 14));
		sevenCards.add(Card.createCard(Suit.SPADE, 12));
		sevenCards.add(Card.createCard(Suit.SPADE, 13));
		Player player = createPlayer("Son", 50000, new Game());
		player.generateBestHand(sevenCards);
		System.out.println(player.getBestHand());
	}

	/** Creates a new player object */
	public static Player createPlayer(String username, int cash, Game theGame) {
		return new Player(username, cash, theGame);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player [username=" + username + ", cash=" + cash
				+ ", dealtCards=" + dealtCards + "]";
	}
}
