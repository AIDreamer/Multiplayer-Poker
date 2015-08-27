/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.enums.CardState;
import model.enums.GameState;
import model.enums.PlayerState;
import model.hands.Hand;

/**
 * Represents the main structure of a poker game
 * 
 * @author astro_000
 *
 */
public class Game {

	/** Constant for BigBlind */
	final static int BIG_BLIND = 4;
	/** Constant for SmallBlind */
	final static int SMALL_BLIND = 2;

	/** Represents the current state of the game */
	private GameState curState;

	/** Contains all players currently inGame */
	private ArrayList<Player> players = new ArrayList<Player>();
	/** Contains all the current pots */
	private ArrayList<Pot> pots = new ArrayList<Pot>();
	/** Represents current player */
	private int currentPlayer;

	/** Represents small blind */
	private int smallBlind;
	/** Represents big blind */
	private int bigBlind;
	/** Represents big blind position */
	private int bigBlindPos;
	/** Represents the current position of the raising player */
	private int raisingPos;
	/** Represents the biggest bet of all time */
	private int highestBet;
	/** Represents all cards on table */
	private Card[] tableCards = new Card[5];
	/** Current Deck in play */
	private Deck deck;
	/** Scans input from user */
	private Scanner scan;

	/**
	 * Constructs a new Game objects
	 */
	public Game() {
		this.deck = new Deck();
		this.raisingPos = 0;
		this.bigBlindPos = 0;
		this.bigBlind = BIG_BLIND;
		this.smallBlind = SMALL_BLIND;
		this.curState = GameState.NEW_GAME;
		scan = new Scanner(System.in);
	}

	/**
	 * Uses console for testing game functionality
	 */
	public void testingConsole() {
		/** Makes a new game */
		Player son = Player.createPlayer("Son", 100000, this);
		Player bobby = Player.createPlayer("Bobby", 50000, this);
		Player jason = Player.createPlayer("Jason", 10, this);
		Player sune = Player.createPlayer("Sune", 1995, this);
		while (true) {
			/** Deal the cards to each player */
			this.reset();
			this.dealCards();
			do {
				if (this.getCurrentPlayer().getState() == PlayerState.PLAYING) {
					playGameConsole();
				}
				currentPlayer = (currentPlayer + 1) % players.size();
			} while (raisingPos != currentPlayer);

			this.theFlop();

			do {
				if (this.getCurrentPlayer().getState() == PlayerState.PLAYING) {
					playGameConsole();
				}
				currentPlayer = (currentPlayer + 1) % players.size();
			} while (raisingPos != currentPlayer);

			this.theTurn();

			do {
				if (this.getCurrentPlayer().getState() == PlayerState.PLAYING) {
					playGameConsole();
				}
				currentPlayer = (currentPlayer + 1) % players.size();
			} while (raisingPos != currentPlayer);

			this.theRiver();

			do {
				if (this.getCurrentPlayer().getState() == PlayerState.PLAYING) {
					playGameConsole();
				}
				currentPlayer = (currentPlayer + 1) % players.size();
			} while (raisingPos != currentPlayer);

			this.theShowdown();
			String selection = contPlayingDialog();
			if (selection.compareToIgnoreCase("y") != 0) {
				break;
			}
		}
		System.out.println("Thank you Bobby!");
	}

	/**
	 * Runs the game for each player
	 */
	public void playGameConsole() {
		curPlayerDialog();
		int input = scan.nextInt();
		scan.nextLine();
		/** Check for invalid raise */
		while (input == 2 && this.highestBet >= getCurrentPlayer().getCash()) {
			input = cantRaiseDialog();
		}
		switch (input) {
		case 1:
			System.out.println("Let's call!");
			this.getCurrentPlayer().setCurBet(highestBet);
			break;
		case 2:
			System.out.println("Let's raise! What would you like to raise to?");
			int raise = scan.nextInt();
			scan.nextLine();
			/** Check for all-in raise */
			allInRaiseCheck(raise);
			break;
		case 3:
			System.out.println("Aww. Baby gonna fold!");
			getCurrentPlayer().setState(PlayerState.FOLDED);
			break;
		}
	}

	/**
	 * Face up three cards on the table and recalculate best hand everybody has
	 */
	public void theFlop() {
		tableCards[0].setState(CardState.FACE_UP);
		tableCards[1].setState(CardState.FACE_UP);
		tableCards[2].setState(CardState.FACE_UP);
		ArrayList<Card> examinedCards = new ArrayList<Card>();
		examinedCards.add(tableCards[0]);
		examinedCards.add(tableCards[1]);
		examinedCards.add(tableCards[2]);
		for (Player player : players) {
			if (player.getState() != PlayerState.FOLDED) {
				examinedCards.add(player.getDealtCards().get(0));
				examinedCards.add(player.getDealtCards().get(1));
				player.generateBestHand(examinedCards);
				examinedCards.remove(4);
				examinedCards.remove(3);
			}
		}
	}

	/**
	 * Face up three cards on the table and recalculate best hand everybody has
	 */
	public void theTurn() {
		tableCards[3].setState(CardState.FACE_UP);
		ArrayList<Card> examinedCards = new ArrayList<Card>();
		examinedCards.add(tableCards[0]);
		examinedCards.add(tableCards[1]);
		examinedCards.add(tableCards[2]);
		examinedCards.add(tableCards[3]);
		for (Player player : players) {
			if (player.getState() != PlayerState.FOLDED) {
				examinedCards.add(player.getDealtCards().get(0));
				examinedCards.add(player.getDealtCards().get(1));
				player.generateBestHand(examinedCards);
				examinedCards.remove(5);
				examinedCards.remove(4);
			}
		}
	}

	/**
	 * Face up three cards on the table and recalculate best hand everybody has
	 */
	public void theRiver() {
		tableCards[4].setState(CardState.FACE_UP);
		ArrayList<Card> examinedCards = new ArrayList<Card>();
		examinedCards.add(tableCards[0]);
		examinedCards.add(tableCards[1]);
		examinedCards.add(tableCards[2]);
		examinedCards.add(tableCards[3]);
		examinedCards.add(tableCards[4]);
		for (Player player : players) {
			if (player.getState() != PlayerState.FOLDED) {
				examinedCards.add(player.getDealtCards().get(0));
				examinedCards.add(player.getDealtCards().get(1));
				player.generateBestHand(examinedCards);
				examinedCards.remove(6);
				examinedCards.remove(5);
			}
		}
	}

	/**
	 * finds the list of player among the players' best hand
	 * 
	 * @return a list of winning players
	 */
	public ArrayList<Player> findBestHand() {
		ArrayList<Player> winningPlayers = new ArrayList<Player>();
		ArrayList<Hand> handsList = new ArrayList<Hand>();

		for (Player player : currentPlayers()) {
			handsList.add(player.getBestHand());
		}
		Collections.sort(handsList, Collections.reverseOrder());
		for (Player player : players) {
			if (handsList.get(0).compareTo(player.getBestHand()) == 0
					&& player.getState() != PlayerState.FOLDED) {
				winningPlayers.add(player);
			}
		}
		return winningPlayers;
	}

	/**
	 * Face up three cards on the table and recalculate best hand everybody has
	 */
	public void theShowdown() {
		getCurrentPlayer().flipCards();
		for (Player player : players) {
			player.flipCards();
		}
		flipAllTableCards();
		ArrayList<Player> winners = findBestHand();
		int winningCash = pots.get(0).getCash() / winners.size();
		winningPlayerDialog(winners, winningCash);
		for (Player player : winners) {
			player.setCash(player.getCash() - player.getCurBet());
			player.addCash(winningCash);
		}
		for (Player loser : players) {
			if (!winners.contains(loser)) {
				loser.setCash(loser.getCash() - loser.getCurBet());
				if (loser.getCash() <= 0)
					loser.setState(PlayerState.OUT_OF_GAME);
			}
		}

	}

	/**
	 * flips all cards on table over
	 */
	public void flipAllTableCards() {
		tableCards[0].setState(CardState.FACE_UP);
		tableCards[1].setState(CardState.FACE_UP);
		tableCards[2].setState(CardState.FACE_UP);
		tableCards[3].setState(CardState.FACE_UP);
		tableCards[4].setState(CardState.FACE_UP);
	}

	/**
	 * Presents a dialog with the winning player information
	 * 
	 * @param winners
	 * @param winningCash
	 */
	private void winningPlayerDialog(ArrayList<Player> winners, int winningCash) {
		System.out.print("The winner(s) are: ");
		for (int pos = 0; pos < winners.size(); pos++) {
			System.out.print(winners.get(pos).getUsername());
			if ((pos + 1) != winners.size())
				System.out.print(",");
		}
		System.out.println();
		for (Player player : winners) {
			System.out.println("Player won with: "
					+ player.getBestHand().toString());
		}
		System.out.println("The winner(s) won: " + winningCash);
	}

	/**
	 * Prompts user with dialog to confirm the continuation of the game
	 * 
	 * @return
	 */
	private String contPlayingDialog() {
		System.out.println("Do you want to keep playing");
		String s = scan.next();
		scan.nextLine();
		return s;
	}

	/**
	 * Displays Dialog associated with the current player
	 */
	private void curPlayerDialog() {
		System.out.println("Current highest bet: " + getHighestBet());
		System.out.println("Current Player: " + getCurrentPlayer());
		System.out.println("1) Call 2) Raise 3) Fold");
	}

	/**
	 * Checks for an all-in raise and changes highestBet and raisingPos
	 * accordingly
	 * 
	 * @param raise
	 */
	public void allInRaiseCheck(int raise) {
		if (raise > getCurrentPlayer().getCash()
				&& getCurrentPlayer().getCash() > this.highestBet) {
			this.highestBet = getCurrentPlayer().getCash();
			this.getCurrentPlayer().setCurBet(highestBet);
			this.raisingPos = currentPlayer;
			/** Check for normal raise */
		} else {
			this.raisingPos = currentPlayer;
			this.highestBet = raise;
			this.getCurrentPlayer().setCurBet(raise);
		}
	}

	/**
	 * Dialog associated with not being able to raise anymore
	 * 
	 * @return
	 */
	private int cantRaiseDialog() {
		int input;
		System.out.print("You cannot raise anymore!!!!");
		System.out.println("Current Player: " + getCurrentPlayer());
		System.out.println("1) Call 2) Raise 3) Fold");
		input = scan.nextInt();
		scan.nextLine();
		return input;
	}

	/**
	 * Deals all the cards to the players and the table
	 */
	public void dealCards() {
		/** Adds cards to the players */
		for (Player player : players) {
			player.setDealtCards(this.deck.takeRandomCard(),
					this.deck.takeRandomCard());
		}

		/** Adds cards to the table */
		for (int pos = 0; pos < tableCards.length; pos++) {
			tableCards[pos] = this.deck.takeRandomCard();
		}
	}

	/**
	 * Resets the deck, removes all cards from each player, and sets the pot to
	 * 0, makes big blind and small blind bets for respective players
	 */
	public void reset() {
		/** Reset table */
		this.deck = new Deck();
		this.highestBet = 0;

		/** Reset all smoking pot */
		this.pots = new ArrayList<Pot>();
		pots.add(new Pot(players));

		/** Reset player's status */
		for (Player player : players) {
			if (player.getState() != PlayerState.OUT_OF_GAME)
				player.setState(PlayerState.PLAYING);
			player.setDealtCards(null);
			player.setCurBet(0);
		}

	}

	/**
	 * Get the current player
	 * 
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}

	/**
	 * Get the current player
	 * 
	 * @return the current player
	 */
	public int getCurrentPlayerInt() {
		return currentPlayer;
	}

	/**
	 * Adds Player to the current game
	 * 
	 * @param player
	 *            Player object to be added
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Removes Player from the current game
	 * 
	 * @param player
	 *            Player object to be removed
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * Returns the value of the game's small blind
	 * 
	 * @return the smallBlind
	 */
	public int getSmallBlind() {
		return smallBlind;
	}

	/**
	 * Sets the value of the current small blind
	 * 
	 * @param smallBlind
	 *            the smallBlind to set
	 */
	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}

	/**
	 * Returns the value of the current big blind
	 * 
	 * @return the bigBlind
	 */
	public int getBigBlind() {
		return bigBlind;
	}

	/**
	 * Sets the value of the big blind
	 * 
	 * @param bigBlind
	 *            the bigBlind to set
	 */
	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}

	/**
	 * Returns the current big blind position
	 * 
	 * @return the bigBlindPos
	 */
	public int getBigBlindPos() {
		return bigBlindPos;
	}

	/**
	 * Returns the current raising position
	 * 
	 * @return the raisingPos
	 */
	public int getRaisingPos() {
		return raisingPos;
	}

	/**
	 * Returns an ArrayList containing all current players
	 * 
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the value of the current player
	 * 
	 * @param currentPlayer
	 *            the currentPlayer to set
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Sets the value of the current highest bet
	 * 
	 * @param highestBet
	 *            the highestBet to set
	 */
	public void setHighestBet(int highestBet) {
		this.highestBet = highestBet;
	}

	/**
	 * Returns the value of the current highest bet
	 * 
	 * @return the highestBet
	 */
	public int getHighestBet() {
		return highestBet;
	}

	/**
	 * Returns the current players in the game
	 * 
	 * @return
	 */
	public ArrayList<Player> currentPlayers() {
		ArrayList<Player> curPlayers = new ArrayList<Player>();

		for (Player player : this.getPlayers()) {
			if (player.getState() != PlayerState.FOLDED)
				curPlayers.add(player);
		}

		return curPlayers;
	}

	/**
	 * Returns the current players who can make turns
	 * 
	 * @return
	 */
	public ArrayList<Player> currentTurnPlayers() {
		ArrayList<Player> curPlayers = new ArrayList<Player>();

		for (Player player : this.getPlayers()) {
			if (player.isPlaying())
				curPlayers.add(player);
		}

		return curPlayers;
	}

	/**
	 * Returns a list of all money pots
	 * 
	 * @return the pots
	 */
	public ArrayList<Pot> getPots() {
		return pots;
	}

	/**
	 * Returns the array containing all cards on the table
	 * 
	 * @return the tableCards
	 */
	public Card[] getTableCards() {
		return tableCards;
	}

	/**
	 * Returns the current state of the game
	 * 
	 * @return the curState
	 */
	public GameState getCurState() {
		return curState;
	}

	/**
	 * Sets the current state of the game
	 * 
	 * @param curState
	 *            the curState to set
	 */
	public void setCurState(GameState curState) {
		this.curState = curState;
	}

	/**
	 * Determines the next player position
	 * 
	 * @return
	 */
	public int determineNextPlayer() {
		return (getCurrentPlayerInt() + 1) % getPlayers().size();
	}

	/**
	 * Flips current players cards and changes to next player position
	 * 
	 * @param nextPlayer
	 */
	public void changeToNextPlayer(int nextPlayer) {
		getCurrentPlayer().flipCards();
		setCurrentPlayer(nextPlayer);

		/**
		 * If next player can't make a turn, then find the next playable player
		 */
		for (int curPos = 0; curPos < this.getPlayers().size(); curPos++) {
			if (getCurrentPlayer().getState() != PlayerState.PLAYING) {
				setCurrentPlayer(determineNextPlayer());
			} else {
				break;
			}
		}

	}

	/**
	 * returns a boolean representing whether or not the game is in play
	 * 
	 * @param pokerController
	 *            TODO
	 * @param startState
	 * @return
	 */
	public boolean isPlaying() {
		return getCurState() != GameState.COMPLETED
				&& getCurState() != GameState.NEW_GAME;
	}

	/**
	 * Returns a boolean representing whether or not it is time to switch to a
	 * new round
	 * 
	 * @return boolean
	 */
	public boolean isNewRound() {
		return getRaisingPos() == getCurrentPlayerInt();
	}
}
