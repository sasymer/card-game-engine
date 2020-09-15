package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Pile is iterable so that you can do a for each loop to loop over the cards in this pile
 *
 * @@author Libba, Sanna
 */
public class Pile implements Iterable<Card> {

  private static final int ONE = 1;
  private List<Card> cards;
  private Memory memory;

  private int id;

  public Pile() {
    cards = new ArrayList<Card>();
    memory = new Memory();
  }

  public Pile(List<Card> cards) {
    this.cards = cards;
    memory = new Memory();
  }

  public void setMemory(Pile oldCards) {
    Pile store = oldCards;
    memory.setMemory(store);
  }

  public void setPileID(int id) {
    this.id = id;
  }

  public int getPileID() {
    return id;
  }

  public List<Card> getMemory() {
    return memory.getMemory();
  }

  public List<Card> getCards() {
    return this.cards;
  }

  public Card getTopCard() {
    return cards.get(cards.size() - ONE);
  }

  public Card removeTopCard() {
    return cards.remove(cards.size() - ONE);
  }

  public void addCardToTop(Card newCard) {
    cards.add(newCard);
  }

  public boolean hasNumber(Card card) {
    for (Card c : cards) {
      if (c.getNumber() == card.getNumber()) {
        return true;
      }
    }
    return false;
  }

  public void addCardToBottom(Card newCard) {
    cards.add(0, newCard);
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }

  protected Card removeSpecificCard(Card card) {
    return cards.remove(cards.indexOf(card));
  }

  public boolean contains(Card card) {
    return cards.contains(card);
  }

  public void emptyPile() {
    cards = new ArrayList<>();
  }

  public int getNumCards() {
    return cards.size();
  }

  public Card getCardAt(int index) {
    return cards.get(index);
  }

  protected Pile createPair(Card card) {
    Pile pair = new Pile();
    pair.addCardToTop(cards.remove(cards.indexOf(card)));
    pair.addCardToTop(cards.remove(cards.indexOf(card)));
    return pair;
  }

  @Override
  public Iterator<Card> iterator() {
    return cards.iterator();
  }
}
