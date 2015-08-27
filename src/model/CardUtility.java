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

import model.hands.Flush;
import model.hands.FourOfAKind;
import model.hands.FullHouse;
import model.hands.HighCard;
import model.hands.Pair;
import model.hands.Straight;
import model.hands.StraightFlush;
import model.hands.ThreeOfAKind;
import model.hands.TwoPair;

/**
 * Gives helper functions to be used when constructing Hand objects
 * 
 * @author astro_000
 *
 */
public final class CardUtility {

	private static final int HIGH_LEVEL = 5;

	/**
	 * Create a Pair from a set of hand
	 * 
	 * @param hand
	 * @return a Pair if it qualifies, null otherwise
	 */
	public static HighCard createHighCard(ArrayList<Card> hand) {
		/** Sort the card from high to low */
		Collections.sort(hand, Collections.reverseOrder());
		/** Return a HighCard hand */
		return new HighCard(hand);
	}

	/**
	 * Create a Pair from a set of hand
	 * 
	 * @param hand
	 * @return a Pair if it qualifies, null otherwise
	 */
	public static Pair createAPair(ArrayList<Card> hand) {
		/** Sort the card from high to low */
		Collections.sort(hand, Collections.reverseOrder());
		int pairCheck = pairCheck(hand, 2);
		if (pairCheck != 0)
			return new Pair(hand, pairCheck);
		else
			return null;
	}

	/**
	 * Create a two pair from a set of 5 card
	 * 
	 * @param hand
	 * @return a two pair if it qualifies, null otherwise
	 */
	public static TwoPair createTwoPair(ArrayList<Card> hand) {
		Collections.sort(hand, Collections.reverseOrder());
		/** Set up a num to record the value of the two pairs */
		int highPairNum = highLowPairCheck(hand, 2, 15);

		/** If can't find a pair, return null object */
		if (highPairNum == 0) {
			return null;
		}

		int lowPairNum = highLowPairCheck(hand, 2, highPairNum - 1);

		/** If can't find a pair, return null object */
		if (lowPairNum != 0) {
			return new TwoPair(hand, lowPairNum, highPairNum);
		} else
			return null;
	}

	/**
	 * Create a Three Of A Kind from a set of hand
	 * 
	 * @param hand
	 * @return a Three Of A Kind if it qualifies, null otherwise
	 */
	public static ThreeOfAKind createThreeOfAKind(ArrayList<Card> hand) {
		/** Sort the card from low to high */
		Collections.sort(hand, Collections.reverseOrder());
		/** Checks for pair of three cards */
		int pairCheck = pairCheck(hand, 3);
		if (pairCheck != 0)
			return new ThreeOfAKind(hand, pairCheck);
		else
			return null;
	}

	/**
	 * Create a Straight from a set of hand
	 * 
	 * @param hand
	 * @return a Straight if it qualifies, null otherwise
	 */
	public static Straight createStraight(ArrayList<Card> hand) {
		/** Sort the card from low to high */
		Collections.sort(hand, Collections.reverseOrder());
		/** Check special case A,5,4,3,2 */
		if (specialCaseCheck(hand)) {
			return new Straight(hand, HIGH_LEVEL);
		}
		/** Check if 5 cards form a straight */
		if (straightCheck(hand)) {
			return new Straight(hand, hand.get(0).getValue());
		}
		return null;
	}

	/**
	 * Create a Flush from a set of hand
	 * 
	 * @param hand
	 * @return a Flush if it qualifies, null otherwise
	 */
	public static Flush createFlush(ArrayList<Card> hand) {
		/** Sort the card from low to high */
		Collections.sort(hand, Collections.reverseOrder());
		/** Check if all cards have the same suit */
		if (flushCheck(hand)) {
			return new Flush(hand);
		}
		return null;
	}

	/**
	 * Create a Straight Flush from a set of hand
	 * 
	 * @param hand
	 * @return a StraightFlush if it qualifies, null otherwise
	 */
	public static StraightFlush createStraightFlush(ArrayList<Card> hand) {
		/** Sort the card from high to low */
		Collections.sort(hand, Collections.reverseOrder());
		/** Check to see if it passes the flush test */
		if (flushCheck(hand)) {
			// Allow to move on to the straight test
		} else {
			return null;
		}
		/** Check special case A,5,4,3,2 */
		if (specialCaseCheck(hand)) {
			return new StraightFlush(hand, HIGH_LEVEL);
		}
		/** Check if 5 cards form a straight */
		if (straightCheck(hand)) {
			return new StraightFlush(hand, hand.get(0).getValue());
		}
		/** Return null if it is not a straightFlush */
		return null;
	}

	/**
	 * Create a Full House from a set of 5 card
	 * 
	 * @param hand
	 * @return a Full House if it qualifies, null otherwise
	 */
	public static FullHouse createFullHouse(ArrayList<Card> hand) {
		Collections.sort(hand, Collections.reverseOrder());
		/** Set up a num to record the value of the two pairs */
		int threeKind = pairCheck(hand, 3);

		if (threeKind == 0) {
			return null;
		}

		/** Set up a num to record the value of the two pairs */
		int pairCheck = pairCheck(hand, 2, threeKind);

		if (pairCheck != 0) {
			return new FullHouse(hand, threeKind, pairCheck);
		} else
			return null;
	}

	/**
	 * Create a Four Of A Kind from a set of hand
	 * 
	 * @param hand
	 * @return a Four Of A Kind if it qualifies, null otherwise
	 */
	public static FourOfAKind createFourOfAKind(ArrayList<Card> hand) {
		/** Sort the card from high to low */
		Collections.sort(hand, Collections.reverseOrder());
		/** Set up a num to record the value of the 4 pairs */
		int fourKind = pairCheck(hand, 4);

		if (fourKind != 0) {
			return new FourOfAKind(hand, fourKind);
		} else
			return null;
	}

	/**
	 * Checks to see if every card in the hand is of the same suit
	 * 
	 * @param hand
	 *            ArrayList containing all cards
	 * @return boolean
	 */
	private static boolean flushCheck(ArrayList<Card> hand) {
		return hand.get(1).getSuit() == hand.get(0).getSuit()
				&& hand.get(2).getSuit() == hand.get(0).getSuit()
				&& hand.get(3).getSuit() == hand.get(0).getSuit()
				&& hand.get(4).getSuit() == hand.get(0).getSuit();
	}

	/**
	 * Checks to see if the given hand is a straight
	 * 
	 * @param hand
	 *            ArrayList containing cards
	 * @return boolean
	 */
	private static boolean straightCheck(ArrayList<Card> hand) {
		return hand.get(0).getValue() == hand.get(1).getValue() + 1
				&& hand.get(1).getValue() == hand.get(2).getValue() + 1
				&& hand.get(2).getValue() == hand.get(3).getValue() + 1
				&& hand.get(3).getValue() == hand.get(4).getValue() + 1;
	}

	/**
	 * Checks to see if the given hand has the given number of pairs
	 * 
	 * @param hand
	 * @param numPairs
	 * @return
	 */
	private static int pairCheck(ArrayList<Card> hand, int numPairs) {
		int countSoFar = 0;
		/** Set up a numSoFar to record the current */
		int numSoFar = 15;
		/** Loop through the card to see if there are any cards duplicated */
		for (Card card : hand) {
			if (card.getValue() != numSoFar) {
				numSoFar = card.getValue();
				countSoFar = 1;
			} else if (card.getValue() == numSoFar) {
				countSoFar++;
			}
			/** If there is, then return numSoFar as the pairNum */
			if (countSoFar == numPairs) {
				return numSoFar;
			}
		}
		return 0;
	}

	/**
	 * Checks to see if the given hand has the given number of pairs
	 * 
	 * @param hand
	 * @param numPairs
	 * @param threeKind
	 * @return
	 */
	private static int pairCheck(ArrayList<Card> hand, int numPairs,
			int threeKind) {
		/** Loop again to check for the pair */
		int countSoFar = 0;
		int numSoFar = 15;
		for (Card card : hand) {
			if (card.getValue() == threeKind) {
				// Do nothing as if the card is not there
			} else if (card.getValue() != numSoFar) {
				numSoFar = card.getValue();
				countSoFar = 1;
			} else if (card.getValue() == numSoFar) {
				countSoFar++;
			}
			/**
			 * If there is, then create the low pair with that card as the
			 * pairNum
			 */
			if (countSoFar == numPairs) {
				return numSoFar;
			}
		}
		return 0;
	}

	/**
	 * Does a high/low pair check
	 * 
	 * @param hand
	 * @param numPairs
	 * @param numSoFar
	 * @return
	 */
	private static int highLowPairCheck(ArrayList<Card> hand, int numPairs,
			int numSoFar) {
		int countSoFar = 0;
		/** Loop through the card to see if there are any cards duplicated */
		for (Card card : hand) {
			if (card.getValue() < numSoFar) {
				numSoFar = card.getValue();
				countSoFar = 1;
			} else if (card.getValue() == numSoFar) {
				countSoFar++;
			}
			/** If there is, then create the pair with that card as the pairNum */
			if (countSoFar == numPairs) {
				return numSoFar;
			}
		}
		return 0;
	}

	/**
	 * Checks for the special case of A,5,4,3,2
	 * 
	 * @param hand
	 * @return
	 */
	private static boolean specialCaseCheck(ArrayList<Card> hand) {
		return hand.get(0).getValue() == 14 && hand.get(1).getValue() == 5
				&& hand.get(2).getValue() == 4 && hand.get(3).getValue() == 3
				&& hand.get(4).getValue() == 2;
	}
}
