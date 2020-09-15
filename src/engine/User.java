package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

  private int idNumber;
  private String name;
  private Pile myHand, myCenterPile;
  private List<Pile> matches;
  private List<Card> justPlayed;

  public User(String name, int idNumber) {
    this.name = name;
    this.idNumber = idNumber;
    matches = new ArrayList<>();
    justPlayed = new ArrayList<>();
    myHand = new Pile();
    myCenterPile = new Pile();
  }

  public int getID() {
    return idNumber;
  }

  public void setCenterPile(Pile center) {
    myCenterPile = center;
    myCenterPile.setPileID(idNumber);
  }

  public Pile getCenterPile() {
    return myCenterPile;
  }

  protected void setPile(Pile pile) {
    myHand = pile;
  }

  public Pile getPile() {
    return myHand;
  }

  /**
   * Adds card to bottom of user pile (as if add to a queue)
   *
   * @param card
   */
  public void addCardToPile(Card card) {
    myHand.addCardToBottom(card);
  }

  public void removeCardChosen(Card card) {
    myHand.removeSpecificCard(card);
  }

  /**
   * Assumption: only called if already known that user has card with this number
   *
   * @param num
   */
  public Card removeCardWithNum(int num) {
    Card toRemove = null;
    for (Card card : myHand) {
      if (card.getNumber() == num) {
        toRemove = card;
      }
    }
    removeCardChosen(toRemove);
    return toRemove;
  }

  public int getNumCards() {
    return myHand.getNumCards();
  }

  public boolean hasCardNumber(Card card) {
    return myHand.hasNumber(card);
  }

  public void addToJustPlayed(Card card) {
    justPlayed.add(card);
  }

  public boolean justPlayedCardNumber(Card card) {
    for (Card c : justPlayed) {
      if (c.getNumber() == card.getNumber()) {
        return true;
      }
    }
    return false;
  }

  public void clearJustPlayed() {
    justPlayed.clear();
  }

  public void createPairsFromHand(int numCards) {
    for (int i = 0; i < 2; i++) {
      Map<Integer, List<Card>> map = makeMapOfCards();
      makeMatches(map, numCards);
    }
  }

  public void createPairForUser(Card card) {
    Pile pair = myHand.createPair(card);
    matches.add(pair);
  }

  public boolean hasCard(Card c) {
    return myHand.contains(c);
  }

  public boolean emptyHand() {
    return myHand.getNumCards() == 0;
  }

  public int getNumPairs() {
    return matches.size();
  }

  public List<Pile> getPairs() {
    return matches;
  }

  @Override
  public String toString() {
    return name + " " + idNumber;
  }

  private Map<Integer, List<Card>> makeMapOfCards() {
    Map<Integer, List<Card>> map = new HashMap<>();
    for (Card card : myHand) {
      int num = card.getNumber();
      map.putIfAbsent(num, new ArrayList<Card>());
      List<Card> list = map.get(num);
      list.add(card);
      map.put(num, list);
    }
    return map;
  }

  private void makeMatches(Map<Integer, List<Card>> map, int numCards) {
    for (Integer in : map.keySet()) {
      List<Card> cardList = map.get(in);
      if (cardList.size() >= numCards) {
        Pile pair = new Pile();
        for (int i = 0; i < numCards; i++) {
          pair.addCardToTop(cardList.get(i));
          myHand.removeSpecificCard(cardList.get(i));
        }
        matches.add(pair);
      }
    }
  }
}

