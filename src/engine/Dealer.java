package engine;

import java.util.*;

/**
 * This class is used for dealing cards at the start of the game. It allows for dealing cards to
 * specific players as well as making general piles.
 * <p>
 * Dependencies: the dealer must know all the users playing the game, as well as the deck of all
 * cards available at the start of the game
 *
 * @author Sanna, Libba
 */
public class Dealer {

  private static final ResourceBundle DECK_RESOURCE = GameConstant.getDeckData();
  //Default values below are used if the data file is missing
  private static final int DEFAULT_NUMBERS = 13;
  private static final int DEFAULT_SUITS = 4;
  private static final int DEFAULT_MAID = 12;
  private static final int DEFAULT_STARTING = 1;

  //These values are used for determining unique card IDs
  private static final int DECK_ID_MULTIPLIER = GameConstant.getDeckMultiplier();
  private static final int SUIT_ID_MULTIPLIER = GameConstant.getSuitMultiplier();
  private static final String NUMBERS_KEY = "numbers";
  private static final String SUITS_KEY = "suits";
  private static final String START_KEY = "start";

  private List<Card> deck;
  private List<User> users;

  private int oldMaidCard, numUserCards, numNumbers, numSuits, startingCard;

  public Dealer(List<User> allUsers, int numUserCards) {
    users = allUsers;
    this.numUserCards = numUserCards;
    setDeckValues();
  }

  public Pile getDeck() {
    Pile pile = new Pile();
    for (Card c : deck) {
      pile.addCardToTop(c);
    }
    return pile;
  }

  public List<Card> setUpDeck(int numDecks) {
    deck = new ArrayList<>();
    for (int decks = 0; decks < numDecks; decks++) {
      for (int suit = 0; suit < numSuits; suit++) {
        for (int number = startingCard; number <= numNumbers; number++) {
          int id = (decks * DECK_ID_MULTIPLIER) + (suit * SUIT_ID_MULTIPLIER) + number;
          Card c = new Card(false, number, suit, id);
          deck.add(c);
        }
      }
    }
    Collections.shuffle(deck);
    return deck;
  }

  public List<Card> removeMaid() {
    for (Card card : deck) {
      if (card.getNumber() == oldMaidCard) {
        deck.remove(card);
        break;
      }
    }
    Collections.shuffle(deck);
    return deck;
  }

  public void makeUserPiles() {
    users.forEach((user) -> user.setPile(makePile(numUserCards)));
  }

  /**
   * Allocate "size" cards to a pile, and return the pile
   *
   * @param size
   * @return newly made pile
   */
  public Pile makePile(int size) {
    int counter = 0;
    List<Card> pileCards = new ArrayList<Card>();

    while (counter < size) {
      int cardIndex = (int) (Math.random() * deck.size());
      pileCards.add(deck.remove(cardIndex));
      counter++;
    }
    return new Pile(pileCards);
  }

  public void dealDeckToUsers() {
    int userId = 0;
    Collections.shuffle(deck);
    for (Card card : deck) {
      users.get(userId).addCardToPile(card);
      userId++;
      if (userId == users.size()) {
        userId = 0;
      }
    }
    deck.clear();
  }

  public Pile setPile() {
    return new Pile(deck);
  }

  /**
   * This method is used for save and load
   */
  public void userDecksFromFile(Map<Integer, List<Integer>> userCards) {
    Set<Card> toRemove = new HashSet<>();

    for (Card card : deck) {
      int cardID = card.getId();
      for (int userID = 0; userID < users.size(); userID++) {
        if (userCards.get(userID).contains(cardID)) {
          users.get(userID).addCardToPile(card);
          toRemove.add(card);
        }
      }
    }
    for (Card card : toRemove) {
      deck.remove(card);
    }
  }

  /**
   * This method is used for save and load
   *
   * @param cards
   * @return
   */
  public Pile setPileFromFile(List<Integer> cards) {
    Set<Card> toRemove = new HashSet<>();
    Pile pile = new Pile();

    for (Card card : deck) {
      if (cards.contains(card.getId())) {
        pile.addCardToTop(card);
        toRemove.add(card);
      }
    }

    for (Card card : toRemove) {
      deck.remove(card);
    }
    return pile;
  }

  private void setDeckValues() {
    try {
      numNumbers = Integer.parseInt(DECK_RESOURCE.getString(NUMBERS_KEY));
    } catch (MissingResourceException e) {
      numNumbers = DEFAULT_NUMBERS;
    }

    try {
      numSuits = Integer.parseInt(DECK_RESOURCE.getString(SUITS_KEY));
    } catch (MissingResourceException e) {
      numSuits = DEFAULT_SUITS;
    }

    try {
      oldMaidCard = GameConstant.getOldMaidCard();
    } catch (MissingResourceException e) {
      oldMaidCard = DEFAULT_MAID;
    }

    try {
      startingCard = Integer.parseInt(DECK_RESOURCE.getString(START_KEY));
    } catch (MissingResourceException e) {
      startingCard = DEFAULT_STARTING;
    }
  }

}
