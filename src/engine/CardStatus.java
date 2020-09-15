package engine;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Stores current data relating to the status of cards and piles in the game.
 * Used in actions relating to cards.
 *
 * @author Libba, Sanna
 */
public class CardStatus extends CurrentStatus {

  private Card selectedCard;
  private CenterArea center;
  private boolean oneCenter;
  private int numUsers;
  private BooleanProperty selectedCardChange = new SimpleBooleanProperty();

  public CardStatus(List<User> users) {
    super(users);
    numUsers = users.size();
    selectedCard = new Card();
    center = new CenterArea();
  }

  public BooleanProperty getSelectedCardChange() {
    return selectedCardChange;
  }

  public void makeCenterPiles(boolean oneCenter) {
    this.oneCenter = oneCenter;
    if (!oneCenter) {
      setupCenterArea();
    }
    else {
      Pile centerPile = getCenterPile();
      for (User user : users) {
        user.setCenterPile(centerPile);
      }
    }
  }

  public Card getSelectedCard() {
    return selectedCard;
  }

  public CenterArea getCenter() {
    return center;
  }

  public Pile getCenterPile() {
    return center.getPile(0);
  }

  public Pile getCenterPile(int id) {
    return center.getPile(id);
  }

  public void setCenterPile(Pile center) {
    this.center.setPile(center);
  }

  public void setCenterPile(Pile center, int id) {
    this.center.setPile(center, id);
  }

  public Card setSelectedCard(Card card) {
    selectedCard = card;
    selectedCardChange.set(!selectedCardChange.get()); // change the boolean property
    return card;
  }

  public void addCardToCenter(Card card) {
    center.getPile(0).addCardToTop(card);
  }

  public void addCardToCenter(Card card, int id) {
    center.getPile(id).addCardToTop(card);
  }

  private void setupCenterArea() {
    for (int i = 0; i < numUsers; i++) {
      addCenterPile(i);
    }
  }

  private void addCenterPile(int id) {
    Pile newPile = new Pile();
    center.addPileToCenter(newPile, id);
    users.get(id).setCenterPile(newPile);
  }

}
