package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.UserStatus;

/**
 * Used to flip a card over
 */
public class FlipCard extends Action {

  public FlipCard(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    Card card = cardStatus.getSelectedCard();
    card.flip();
  }
}
