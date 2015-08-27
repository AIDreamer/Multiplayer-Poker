/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */

package model.hands;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import model.Card;
import model.enums.Suit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HandTest {

	@Before
	public void setUp() throws Exception {
	
	}

	@Test
	public void testTwoHands() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 5));
		hand1.add(Card.createCard(Suit.CLUB, 8));
		hand1.add(Card.createCard(Suit.HEART, 10));
		hand1.add(Card.createCard(Suit.DIAMOND, 7));
		hand1.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new ThreeOfAKind(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 3));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new Pair(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testHighCard() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 5));
		hand1.add(Card.createCard(Suit.CLUB, 8));
		hand1.add(Card.createCard(Suit.HEART, 10));
		hand1.add(Card.createCard(Suit.DIAMOND, 7));
		hand1.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new HighCard(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 3));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new HighCard(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) > 0);
	}
	
	@Test
	public void testPair() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 11));
		hand1.add(Card.createCard(Suit.CLUB, 8));
		hand1.add(Card.createCard(Suit.HEART, 11));
		hand1.add(Card.createCard(Suit.DIAMOND, 7));
		hand1.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new Pair(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 5));
		hand2.add(Card.createCard(Suit.DIAMOND, 3));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new Pair(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testTwoPair() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 11));
		hand1.add(Card.createCard(Suit.CLUB, 8));
		hand1.add(Card.createCard(Suit.HEART, 11));
		hand1.add(Card.createCard(Suit.DIAMOND, 8));
		hand1.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new TwoPair(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 7));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new TwoPair(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testThreeOfAKind() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 11));
		hand1.add(Card.createCard(Suit.CLUB, 8));
		hand1.add(Card.createCard(Suit.HEART, 11));
		hand1.add(Card.createCard(Suit.DIAMOND, 11));
		hand1.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new ThreeOfAKind(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 11));
		hand2.add(Card.createCard(Suit.CLUB, 7));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 11));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new ThreeOfAKind(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testStraight() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 14));
		hand1.add(Card.createCard(Suit.CLUB, 13));
		hand1.add(Card.createCard(Suit.HEART, 12));
		hand1.add(Card.createCard(Suit.DIAMOND, 11));
		hand1.add(Card.createCard(Suit.SPADE, 10));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new Straight(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.CLUB, 4));
		hand2.add(Card.createCard(Suit.HEART, 3));
		hand2.add(Card.createCard(Suit.DIAMOND, 2));
		hand2.add(Card.createCard(Suit.SPADE, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new Straight(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testFlush() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 14));
		hand1.add(Card.createCard(Suit.CLUB, 4));
		hand1.add(Card.createCard(Suit.CLUB, 11));
		hand1.add(Card.createCard(Suit.CLUB, 3));
		hand1.add(Card.createCard(Suit.CLUB, 8));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new Flush(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.DIAMOND, 10));
		hand2.add(Card.createCard(Suit.DIAMOND, 4));
		hand2.add(Card.createCard(Suit.DIAMOND, 3));
		hand2.add(Card.createCard(Suit.DIAMOND, 2));
		hand2.add(Card.createCard(Suit.DIAMOND, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new Flush(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testFullHouse() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.HEART, 13));
		hand1.add(Card.createCard(Suit.CLUB, 13));
		hand1.add(Card.createCard(Suit.HEART, 12));
		hand1.add(Card.createCard(Suit.DIAMOND, 12));
		hand1.add(Card.createCard(Suit.SPADE, 12));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new FullHouse(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.HEART, 5));
		hand2.add(Card.createCard(Suit.HEART, 11));
		hand2.add(Card.createCard(Suit.DIAMOND, 11));
		hand2.add(Card.createCard(Suit.SPADE, 11));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new FullHouse(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testFourOfAKind() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.HEART, 12));
		hand1.add(Card.createCard(Suit.CLUB, 13));
		hand1.add(Card.createCard(Suit.HEART, 13));
		hand1.add(Card.createCard(Suit.DIAMOND, 13));
		hand1.add(Card.createCard(Suit.SPADE, 13));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new FullHouse(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.CLUB, 12));
		hand2.add(Card.createCard(Suit.HEART, 12));
		hand2.add(Card.createCard(Suit.DIAMOND, 12));
		hand2.add(Card.createCard(Suit.SPADE, 12));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new FullHouse(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	@Test
	public void testStraightFlush() {
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(Card.createCard(Suit.CLUB, 14));
		hand1.add(Card.createCard(Suit.CLUB, 13));
		hand1.add(Card.createCard(Suit.CLUB, 12));
		hand1.add(Card.createCard(Suit.CLUB, 11));
		hand1.add(Card.createCard(Suit.CLUB, 10));
		Collections.sort(hand1, Collections.reverseOrder());
		Hand firstHand = new Straight(hand1);
		//System.out.println(hand1);
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(Card.createCard(Suit.CLUB, 5));
		hand2.add(Card.createCard(Suit.CLUB, 4));
		hand2.add(Card.createCard(Suit.CLUB, 3));
		hand2.add(Card.createCard(Suit.CLUB, 2));
		hand2.add(Card.createCard(Suit.CLUB, 14));
		Collections.sort(hand2, Collections.reverseOrder());
		Hand secondHand = new Straight(hand2);
		//System.out.println(hand2);
		
		assertTrue(secondHand.compareTo(firstHand) < 0);
	}
	
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
	}

}
