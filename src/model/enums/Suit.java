/**
 * CSCI205 - Software Design and Engineering
 * Name: Robert Cowen & Son Pham
 * 
 * Work: BASS_FinalProject
 * Created: Nov 18, 2014, 04:53:00 PM
 */

package model.enums;

/**
 * Represents the suit value of the card object
 * @author astro_000
 *
 */
public enum Suit{
	DIAMOND("DIAMOND"), SPADE("SPADE"), HEART("HEART"), CLUB("CLUB");
	
	/**Value of Suit*/
	String value;
	
	/**
	 * Constructs Suit
	 * @param value
	 */
	Suit(String value){
		this.value=value;
	}
	
	/**
	 * Returns value of Suit
	 * @return String
	 */
	public String getValue(){
		return value;
	}
}