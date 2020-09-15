package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * This burns a card if the user slaps a pile incorrectly
 * @author Libba
 */
public class UserCardToCenterBottom extends Action {

  public UserCardToCenterBottom  (UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);

  }
  @Override
  public void act() {
    User selectedUser = userStatus.getSelectedUser();
    if(selectedUser.getPile().getNumCards()!=0){
      selectedUser.getCenterPile().addCardToBottom(selectedUser.getPile().removeTopCard());
    }
  }
}
