package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Check user's hand for matches, if there is a match make a separate pile and
 * add to user's list of pairs
 */
public class MakeUserPair extends Action {

  public MakeUserPair(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    User selected = userStatus.getSelectedUser();
    Card selectedCard = cardStatus.getSelectedCard();
    if(selected.getPile().contains(selectedCard)) {
      userStatus.getCurrentUser().createPairForUser(cardStatus.getSelectedCard());
    }
  }
}
