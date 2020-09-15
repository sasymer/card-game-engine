package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Transfers selected card to selected user.
 * Uses listeners to detect clicks in front end for card and user selection.
 *
 * @author Sanna, Libba
 */
public class UserCardToUser extends Action {

  javafx.beans.value.ChangeListener<Boolean> userListener;
  javafx.beans.value.ChangeListener<Boolean> cardListener;

  public UserCardToUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  private void setCardListener() {
    cardListener = (obs, oldValue, newValue) -> {
      userCardToUser();
    };
    cardStatus.getSelectedCardChange().addListener(cardListener);
  }

  @Override
  public void act() {
    userListener = (obs, oldValue, newValue) -> {
      setCardListener();
    };
    userStatus.getSelectedUserProp().addListener(userListener);
  }

  private void userCardToUser() {
    User currentUser = userStatus.getCurrentUser();
    User selectedUser = userStatus.getSelectedUser();
    Card cardToTransfer = cardStatus.getSelectedCard();

    if (selectedUser.hasCard(cardToTransfer) && userStatus.getActiveUsers().contains(currentUser)) {
      selectedUser.removeCardChosen(cardToTransfer);
      currentUser.addCardToPile(cardToTransfer);
    }
    userStatus.setTurnFinished(true);
    userStatus.getSelectedUserProp().removeListener(userListener);
    cardStatus.getSelectedCardChange().removeListener(cardListener);

    for (User user: userStatus.getUsers()){
      if(user.getPile().getNumCards()==0) userStatus.removeUser(userStatus.getUsers().get(user.getID()).getID());
    }

    ChangeTurn turn = new ChangeTurn(userStatus, null);
    turn.act();
  }
}
