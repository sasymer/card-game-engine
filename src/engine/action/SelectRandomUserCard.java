package engine.action;

import engine.CardStatus;
import engine.CurrentStatus;
import engine.Pile;
import engine.User;
import engine.UserStatus;

/**
 * Used to select a random card from the selected user's hand
 *
 * @author Sanna
 */
public class SelectRandomUserCard extends Action {

  public SelectRandomUserCard(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);

  }
  @Override
  public void act() {
    User selectedUser = userStatus.getSelectedUser();
    if(selectedUser.getPile().getNumCards()!=0) {
      Pile pile = selectedUser.getPile();
      int random = (int) (Math.random() * pile.getNumCards());
      cardStatus.setSelectedCard(pile.getCardAt(random));
    }
  }
}
