package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.CenterArea;
import engine.Pile;
import engine.User;
import engine.UserStatus;

/**
 * Gives all cards in the center to the selected user
 *
 * @author Sanna
 */
public class CenterPileToUser extends Action {

  public CenterPileToUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    User selectedUser = userStatus.getSelectedUser();
    CenterArea center = cardStatus.getCenter();

    for (Pile pile : center) {
      for (Card card : pile) {
        if (userStatus.getActiveUsers().contains(selectedUser)) {
          selectedUser.addCardToPile(card);
        }
      }
      pile.emptyPile();
    }
    selectedUser.getPile().shuffle();
  }
}
