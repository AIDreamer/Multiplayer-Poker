package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import model.enums.Suit;
import model.hands.Flush;
import model.hands.FourOfAKind;
import model.hands.FullHouse;
import model.hands.Hand;
import model.hands.Pair;
import model.hands.Straight;
import model.hands.StraightFlush;
import model.hands.ThreeOfAKind;
import model.hands.TwoPair;

import org.junit.Test;

public class CardUtilityTest {

	@Test
	public void testCreateAPair() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 7));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Pair pair = CardUtility.createAPair(hand2);
		assertTrue(pair instanceof Pair);
		assertTrue(pair.getPair() == 11);
	}

	@Test
	public void testCreateTwoPair() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 7));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		TwoPair twoPair = CardUtility.createTwoPair(hand2);
		assertTrue(twoPair instanceof TwoPair);
		assertTrue(twoPair.getHighPair() == 11);
		assertTrue(twoPair.getLowPair() == 7);
		
		/** Test a failed two pair creation */
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		TwoPair twoPair2 = CardUtility.createTwoPair(hand3);
		assertTrue(twoPair2 == null);
	}
	
	@Test
	public void testCreateThreeOfAKind() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 7));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		ThreeOfAKind threeOfAKind = CardUtility.createThreeOfAKind(hand2);
		assertTrue(threeOfAKind instanceof ThreeOfAKind);
		assertTrue(threeOfAKind.getKind() == 11);
		
		/** Test a failed three of a kind creation */
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		ThreeOfAKind threeOfAKind2 = CardUtility.createThreeOfAKind(hand3);
		assertTrue(threeOfAKind2 == null);
	}
	
	@Test
	public void testCreateStraight() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 10));
		hand2.add(Card.createCard(Suit.HEART, 9));
		hand2.add(Card.createCard(Suit.DIAMOND, 8));
		hand2.add(Card.createCard(Suit.SPADE, 7));
		Straight straight = CardUtility.createStraight(hand2);
		assertTrue(straight instanceof Straight);
		assertTrue(straight.getHigh() == 11);
		
		/** Test a failed two pair creation */
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		Straight straight2 = CardUtility.createStraight(hand3);
		assertTrue(straight2 == null);
		
		/** Test a special case creation */
		
		ArrayList<Card> hand4 = new ArrayList<Card>();
		hand4.add(Card.createCard(Suit.CLUB, 5));
		hand4.add(Card.createCard(Suit.CLUB, 4));
		hand4.add(Card.createCard(Suit.HEART, 2));
		hand4.add(Card.createCard(Suit.DIAMOND, 3));
		hand4.add(Card.createCard(Suit.SPADE, 14));
		Straight straight3 = CardUtility.createStraight(hand4);
		assertTrue(straight3 instanceof Straight);
		assertTrue(straight3.getHigh() == 5);
	}
	
	@Test
	public void testCreateFlush() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.CLUB, 14));
		Flush flush = CardUtility.createFlush(hand2);
		assertTrue(flush instanceof Flush);
		System.out.println(flush);
		
		/** Test a failed two pair creation */
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		Flush flush2 = CardUtility.createFlush(hand3);
		assertTrue(flush2 == null);
	}
	
	@Test
	public void testCreateFullHouse() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 7));
		hand2.add(Card.createCard(Suit.SPADE, 7));
		FullHouse fullHouse = CardUtility.createFullHouse(hand2);
		assertTrue(fullHouse instanceof FullHouse);
		assertTrue(fullHouse.getThreeKind() == 11);
		assertTrue(fullHouse.getPair() == 7);
		
		/** Test a failed two pair creation */
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 7));
		FullHouse fullHouse2 = CardUtility.createFullHouse(hand3);
		assertTrue(fullHouse2 == null);
	}
	
	@Test
	public void testCreateFourOfAKind() {
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 11));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		FourOfAKind fourOfAKind = CardUtility.createFourOfAKind(hand2);
		assertTrue(fourOfAKind instanceof FourOfAKind);
		assertTrue(fourOfAKind.getKind() == 11);
		
		/** Test a failed two pair creation */
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		FourOfAKind fourOfAKind2 = CardUtility.createFourOfAKind(hand3);
		assertTrue(fourOfAKind2 == null);
	}
	
	@Test
	public void testCreateStraightFlush() {
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 10));
		hand2.add(Card.createCard(Suit.CLUB, 9));
		hand2.add(Card.createCard(Suit.CLUB, 8));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.CLUB, 6));
		StraightFlush straightFlush = CardUtility.createStraightFlush(hand2);
		assertTrue(straightFlush instanceof StraightFlush);
		assertTrue(straightFlush.getHigh() == 10);
		/** Test a failed two pair creation */
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 7));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 8));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		StraightFlush straightFlush2 = CardUtility.createStraightFlush(hand3);
		assertTrue(straightFlush2 == null);
	}
	
	@Test
	public void testSortHand() {
		ArrayList<Hand> hands = new ArrayList<Hand>();
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 10));
		hand2.add(Card.createCard(Suit.CLUB, 9));
		hand2.add(Card.createCard(Suit.CLUB, 8));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.CLUB, 6));
		Hand straightFlush = CardUtility.createStraightFlush(hand2);
		assertTrue(straightFlush instanceof StraightFlush);
	    //assertTrue(straightFlush.getHigh() == 10);
		
		ArrayList<Card> hand3 = new ArrayList<Card>();
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.CLUB, 11));
		hand3.add(Card.createCard(Suit.HEART, 11));
		hand3.add(Card.createCard(Suit.DIAMOND, 11));
		hand3.add(Card.createCard(Suit.SPADE, 14));
		Hand fourOfAKind = CardUtility.createFourOfAKind(hand3);
		assertTrue(fourOfAKind instanceof FourOfAKind);
		//assertTrue(fourOfAKind.getKind() == 11);
		
		ArrayList<Card> hand4 = new ArrayList<Card>();
		hand4.add(Card.createCard(Suit.CLUB, 10));
		hand4.add(Card.createCard(Suit.CLUB, 9));
		hand4.add(Card.createCard(Suit.CLUB, 8));
		hand4.add(Card.createCard(Suit.CLUB, 7));
		hand4.add(Card.createCard(Suit.CLUB, 11));
		Hand straightFlush2 = CardUtility.createStraightFlush(hand4);
		assertTrue(straightFlush2 instanceof StraightFlush);
	    //assertTrue(straightFlush.getHigh() == 10);
		
		hands.add(fourOfAKind);
		hands.add(straightFlush);
		hands.add(straightFlush2);
		Collections.sort(hands,Collections.reverseOrder());
		System.out.println(hands);
	}

}
