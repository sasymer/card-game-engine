package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Used in asking another user for a card. If selected user has a card with the same number
 * as the selected card, this card is transferred to the current user.
 *
 * If the card is not in the selected user's hand, the current user gets a card from the
 * center pile.
 *
 * Listeners are used to determine clicks (to select user then card)
 *
 * @author Libba, Sanna
 */
public class AskForCard extends Action {

  javafx.beans.value.ChangeListener<Boolean> userListener;
  javafx.beans.value.ChangeListener<Boolean> cardListener;

  public AskForCard(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }


  @Override
  public void act() {
    userListener = (obs, oldValue, newValue) -> {
      setCardListener();
    };
    userStatus.getSelectedUserProp().addListener(userListener);
  }

  private void setCardListener() {
    cardListener = (obs, oldValue, newValue) -> {
      askForCard();
    };
    cardStatus.getSelectedCardChange().addListener(cardListener);
  }

  private void askForCard() {
    User currentUser = userStatus.getCurrentUser();
    User selectedUser = userStatus.getSelectedUser();

    Card cardToTransfer = cardStatus.getSelectedCard();
    int cardNum = cardToTransfer.getNumber();

    if (selectedUser.getPile().hasNumber(cardToTransfer)) {
      currentUser.addCardToPile(selectedUser.removeCardWithNum(cardNum));
    } else {
      CenterCardToUser centerCardToUser = new CenterCardToUser(userStatus, cardStatus);
      centerCardToUser.act();
    }
    userStatus.getSelectedUserProp().removeListener(userListener);
    cardStatus.getSelectedCardChange().removeListener(cardListener);
    userStatus.setTurnFinished(true);

  }

}
