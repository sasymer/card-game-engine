package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.Pile;
import engine.User;
import engine.UserStatus;

/**
 * Gives top card of center pile to current user
 *
 * @author Libba
 */
public class CenterCardToUser extends Action {

  public CenterCardToUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    User user = userStatus.getCurrentUser();
    Pile centerPile = cardStatus.getCenterPile();

    if (centerPile.getNumCards() > 0) {
      Card c = centerPile.removeTopCard();
      user.getPile().addCardToTop(c);
    }
  }
}
