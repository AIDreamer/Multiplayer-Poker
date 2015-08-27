/**
 * CSCI 205 - Software Design and Engineering
 * Name(s): Robert Cowen
 *
 * Work:	BASS_FinalProject
 * Created: Dec 1, 2014, 10:17:40 PM}
 */
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Card;
import model.Game;
import model.Player;
import model.enums.CardState;
import model.enums.GameState;
import model.enums.PlayerState;
import view.MainView;

/**
 * Connects the View and the Model for the Poker Game
 * 
 * @author astro_000
 *
 */
public class PokerController implements ActionListener, ChangeListener {

	/** Instantiate the Game */
	private Game theGame;
	/** Instantiate the view */
	private MainView theView;

	/**
	 * Constructs the Controller
	 * 
	 * @throws InterruptedException
	 */
	public PokerController(MainView theView) throws InterruptedException {
		this.theView = theView;
		this.theGame = new Game();

		for (JButton button : theView.getButtonList()) {
			button.addActionListener(this);
		}

		theView.getRaiseSlider().addChangeListener(this);

		/** TODO - This is where we will include addNewPlayer code */
		Player son = Player.createPlayer("Son", 100000, theGame);
		Player bobby = Player.createPlayer("Bobby", 50000, theGame);
		Player jason = Player.createPlayer("Jason", 10, theGame);
		Player sune = Player.createPlayer("Sune", 1995, theGame);

		theGame.addPlayer(son);
		theGame.addPlayer(bobby);
		theGame.addPlayer(jason);
		theGame.addPlayer(sune);

		this.theGame.reset();
		this.theGame.dealCards();

		updateViewFromModel();
	}

	/**
	 * Updates the view's components from the model
	 */
	public void updateViewFromModel() {
		/** Updates all player data */
		updatePlayerData();

		/** Displays all table cards */
		updateTableCards();

		/** Updates current bet amount */
		theView.getCurBetText().setText(theGame.getHighestBet() + "");

		/** Updates current pot amount */
		theView.getPotAmount().setText("" + theGame.getPots().get(0).getCash());

		/** Updates the raise amount based off of the current value of slider */
		theView.getRaiseAmount().setText(
				"" + theView.getRaiseSlider().getValue());
	}

	/**
	 * Iterates through all player info and updates each component
	 */
	private void updatePlayerData() {
		/** Iterates through all players */
		for (int player = 0; player < theGame.getPlayers().size(); player++) {
			/** Represents the current player */
			Player curPlayer = theGame.getPlayers().get(player);

			/** Updates all name text fields */
			theView.getNameList().get(player).setText(curPlayer.getUsername());

			/** Updates money text fields */
			theView.getMoneyList().get(player)
					.setText(curPlayer.getCash() + "");

			/** Changes the color of the current player */
			curPlayerIndicator(player);

			/** Sets all of the curBets to each Players current bet */
			theView.getBetList().get(player)
					.setText("" + curPlayer.getCurBet());

			/** Updates the current player's card Images */
			updatePlayerCards(player, curPlayer, 0, theView.getCard1List());
			/** Updates the current player's second card image */
			updatePlayerCards(player, curPlayer, 1, theView.getCard2List());
		}
	}

	/**
	 * Updates the given player's specified card Image
	 * 
	 * @param player
	 * @param curPlayer
	 * @param cardNum
	 * @param cardList
	 */
	private void updatePlayerCards(int player, Player curPlayer, int cardNum,
			ArrayList<JLabel> cardList) {
		/** Represents current card */
		Card curDealtCard = curPlayer.getDealtCards().get(cardNum);
		/** Creates String representing player's card */
		String cardString = getCardImage(curDealtCard);
		/** sets icon to current string */
		cardList.get(player).setIcon(new ImageIcon(cardString));
	}

	/**
	 * Updates all cards currently on Table
	 */
	private void updateTableCards() {
		/** Iterates through all table cards */
		for (int tableCard = 0; tableCard < theGame.getTableCards().length; tableCard++) {

			/** Represents the path of the card image */
			String tableString = getCardImage(theGame.getTableCards()[tableCard]);

			/** Adds image to table */
			theView.getTableList().get(tableCard)
					.setIcon(new ImageIcon(tableString));
		}
	}

	/**
	 * Returns the full path for the image associated with the given card
	 * 
	 * @param tableCard
	 * @return String representing the location of the Card image
	 */
	private String getCardImage(Card card) {
		/** Represents the path of the given card's image */
		String tableString;
		/** If the card isn't face down */
		if (card.getState() != CardState.FACE_DOWN) {
			/** Find the path of it's corresponding image */
			tableString = getClass().getResource(".").getPath() + "resources/"
					+ card.toString() + ".png";
		}
		/** If card is face down */
		else {
			/** Sets image to face down card */
			tableString = getClass().getResource(".").getPath()
					+ "resources/FACE_DOWN.png";
		}
		return tableString;
	}

	/**
	 * Changes the color of the text of the current player to be different than
	 * every other player
	 * 
	 * @param player
	 */
	private void curPlayerIndicator(int player) {
		/**
		 * If the current player is the same as the passed in player, and the
		 * game didn't just begin
		 */
		if (theGame.getCurrentPlayerInt() == player
				&& theGame.getCurState() != GameState.NEW_GAME) {
			/** Resets all of the passed players info to be highlighted red */
			theView.getNameList().get(player).setForeground(Color.red);
			theView.getMoneyList().get(player).setForeground(Color.red);
			theView.getBetList().get(player).setForeground(Color.red);
		} else {
			/** Sets players info to normal color scheme */
			theView.getNameList().get(player).setForeground(Color.black);
			theView.getMoneyList().get(player).setForeground(Color.white);
			theView.getBetList().get(player).setForeground(Color.white);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JButton button = (JButton) arg0.getSource();

		buttonActionCheck(button);

		gameStateCheck();

		updateViewFromModel();
	}

	/**
	 * @param button
	 */
	private void buttonActionCheck(JButton button) {
		/** Player in next playing position */
		int nextPlayer = determineNextPlayer();

		ArrayList<Player> turnPlayers = theGame.currentTurnPlayers();
		int turnPlayerSize = turnPlayers.size();

		/** As long as the game is still playing */
		if (theGame.isPlaying()) {
			/** If call selected */
			if (button.getText().compareTo("Call") == 0) {

				/**
				 * If the player's current bet is different than the highest bet
				 */
				if (theGame.getCurrentPlayer().getCurBet() != theGame
						.getHighestBet())
					/** Set the player's current bet to the highest bet */
					theGame.getCurrentPlayer().setCurBet(
							theGame.getHighestBet());
				if (turnPlayerSize == 1)
					doShowdown();
				changeToNextPlayer(nextPlayer);
			}
			/** If Raise selected */
			else if (button.getText().compareTo("Raise") == 0) {
				/** Raises for player based off current slider value */
				theGame.allInRaiseCheck(theView.getRaiseSlider().getValue());
				changeToNextPlayer(nextPlayer);
			}
			/** If Fold selected */
			else if (button.getText().compareTo("Fold") == 0) {
				/** Sets player state to folded */
				theGame.getCurrentPlayer().setState(PlayerState.FOLDED);
				changeToNextPlayer(nextPlayer);
			}

			/** Updates slider info based off of current player */
			updateSlider();
			/** Flips current player cards to visible */
			theGame.getCurrentPlayer().flipCards();
		}
	}

	/**
	 * 
	 */
	private void doShowdown() {
		this.theGame.theShowdown();
		this.theGame.setCurState(GameState.COMPLETED);
	}

	/**
	 * Flips current players cards and changes to next player position
	 * 
	 * @param nextPlayer
	 */
	private void changeToNextPlayer(int nextPlayer) {
		theGame.changeToNextPlayer(nextPlayer);
	}

	/**
	 * Determines the next player's position
	 * 
	 * @return int representing the position of the next player
	 */
	private int determineNextPlayer() {
		return theGame.determineNextPlayer();
	}

	/**
	 * Updates the slider based off the current player
	 */
	private void updateSlider() {
		/** Max that can be raised by is All In */
		int maxValue = theGame.getCurrentPlayer().getCash();
		/** Min that can be raised is by 1 */
		int minValue = theGame.getHighestBet() + 1;

		/** If the players cash is less than the current highest bet */
		if (minValue > maxValue)
			/** Set the only option to be All In */
			minValue = maxValue;

		/** Starting value set to middle of slider */
		int initValue = (maxValue + minValue) / 2;

		/**
		 * Updates the Model of the slider based off of max, min, and init
		 * values
		 */
		BoundedRangeModel raiseSliderModel = new DefaultBoundedRangeModel(
				initValue, 0, minValue, maxValue);
		theView.getRaiseSlider().setModel(raiseSliderModel);
	}

	/**
	 * Checks the current state of the game to see if a new round has commenced
	 */
	private void gameStateCheck() {
		/** If only one player is left, they are the winner */
		if (this.theGame.currentPlayers().size() <= 1) {
			doShowdown();
		}
		/** If the game just started, start Flop */
		else if (this.theGame.getCurState() == GameState.NEW_GAME) {
			/** Changes game and view for the Flop */
			theGame.getCurrentPlayer().flipCards();
			this.theGame.setCurState(GameState.FLOP);
			this.theGame.theFlop();

			/**
			 * Updates the view to show all required components for the new game
			 */
			updateViewForNewGame();

			/** Updates slider based off of first player */
			updateSlider();

		}
		/** If flop is over, start Turn */
		else if (this.theGame.getCurState() == GameState.FLOP
				&& theGame.isNewRound()) {
			/** Changes game and view for the Turn */
			this.theGame.setCurState(GameState.TURN);
			this.theGame.theTurn();
		}
		/** If Turn is over start River */
		else if (this.theGame.getCurState() == GameState.TURN
				&& theGame.isNewRound()) {
			/** Changes game and view for the Turn */
			this.theGame.setCurState(GameState.RIVER);
			this.theGame.theRiver();
		}
		/** If River is over, do showdown and call winner */
		else if (this.theGame.getCurState() == GameState.RIVER
				&& theGame.isNewRound()) {
			doShowdown();
		}
	}

	/**
	 * Updates the view to show all required components for the new game
	 */
	private void updateViewForNewGame() {
		this.theView.getButtonList().get(2).setText("Fold");
		this.theView.getButtonList().get(1).setText("Call");
		this.theView.getButtonList().get(0).setVisible(true);
		this.theView.getButtonList().get(0).setText("Raise");
		this.theView.getButtonList().get(2).setVisible(true);
		this.theView.getRaiseAmount().setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		/** Updates the raise amount based off of the current value of slider */
		theView.getRaiseAmount().setText(
				"" + theView.getRaiseSlider().getValue());
	}
}
