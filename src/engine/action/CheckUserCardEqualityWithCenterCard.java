package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.UserStatus;

/**
 * Compares a center card to a user card
 *
 * @author Libba
 */
public class CheckUserCardEqualityWithCenterCard extends Action {

  public CheckUserCardEqualityWithCenterCard(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);

  }

  @Override
  public void act() {
    if (cardStatus.getCenterPile().getNumCards() != 0) {
      Card one = cardStatus.getCenterPile().getTopCard();
      Card two = userStatus.getCurrentUser().getPile().getTopCard();
      if (one.getNumber() == two.getNumber()) {
        MakeUserCenterCardPair makeUserCenterCardPair = new MakeUserCenterCardPair(userStatus,
            cardStatus);
        makeUserCenterCardPair.act();
      }
    }
  }
}
