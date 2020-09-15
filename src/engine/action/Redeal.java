package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.Pile;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Libba
 */
public class Redeal extends Action {

  private List<Card> deck = new ArrayList<>();

  public Redeal(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    getAllCards();
    reorganize();

  }

  private void getAllCards() {
    deck = new ArrayList<>();
    for (User user : userStatus.getActiveUsers()) {
      for (Card c : user.getPile().getCards()) {
        deck.add(c);
      }
    }
    if (cardStatus.getCenterPile().getNumCards() != 0) {
      for (Card c : cardStatus.getCenterPile().getCards()) {
        deck.add(c);
      }
    }
    Pile pile = new Pile(deck);
    pile.shuffle();
    deck = pile.getCards();
  }

  private void reorganize() {

    for (User user : userStatus.getActiveUsers()) {

      int numUserCards = user.getPile().getNumCards();
      //replacing user cards with cards from the deck
      for (int i = 0; i < numUserCards; i++) {
        user.addCardToPile(deck.remove(deck.size() - 1));
        user.getPile().removeTopCard();
      }
    }

    cardStatus.setCenterPile(new Pile(deck));

  }


}