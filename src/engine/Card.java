package engine;

/**
 * Represents the card object, the most fundamental part of the engine's games Keeps track of card's
 * number, suit, and id (ids are used to keep track of cards in front end, mostly)
 *
 * @author Sanna
 */
public class Card {

  private boolean facingUp;
  private int number;
  private int suit;
  private int id;

  public Card() {
    this(false, 0, 0, 0);
  }

  public Card(boolean facing, int number, int suit, int id) {
    facingUp = facing;
    this.number = number;
    this.suit = suit;
    this.id = id;
  }

  /**
   * Flip card to other side
   */
  public void flip() {
    facingUp = !facingUp;
  }

  /**
   * @return the card's number
   */
  public int getNumber() {
    return number;
  }

  /**
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * @return suit
   */
  public int getSuit() {
    return suit;
  }

  /**
   * Overriden toString, helpful for printing cards in testing
   * @return a string representing the card: its suit and number
   */
  @Override
  public String toString() {
    return suit + " " + number;
  }
}
