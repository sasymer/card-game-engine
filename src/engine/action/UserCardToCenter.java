package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 *
 * @author Libba
 */
public class UserCardToCenter extends Action {

  public UserCardToCenter(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    User currentUser = userStatus.getCurrentUser();
    if(currentUser.getPile().getNumCards()!=0) {
      Card selectedCard = currentUser.getPile().removeTopCard();
      currentUser.getCenterPile().addCardToTop(selectedCard);
    }
  }
}
