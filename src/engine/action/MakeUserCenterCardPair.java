package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.UserStatus;

public class MakeUserCenterCardPair extends Action {

  private static final int A_PAIR = 2;

  public MakeUserCenterCardPair(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);

  }

  @Override
  public void act() {
    userStatus.getCurrentUser().addCardToPile(cardStatus.getCenterPile().removeTopCard());
    userStatus.getCurrentUser().createPairsFromHand(A_PAIR);
  }
}
