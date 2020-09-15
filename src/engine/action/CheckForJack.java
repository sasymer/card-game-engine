package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.GameConstant;
import engine.UserStatus;
import java.util.ResourceBundle;

/**
 *
 * This class would be called in slapjack after the user has clicked the center pile, signifying a slap
 * This class has been made more data driven, by allowing users to specify what card they want to slap,
 * in order to personalize the game more.
 *
 * If the card on top of the pile is the specified card (usually jack), the user who "slapped"
 * gets the whole pile. If it is not, the user burns a card.
 *
 * @author Libba, Sanna
 */
public class CheckForJack extends Action {

  private int jackNumber = GameConstant.getSlapCard();

  public CheckForJack(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    Card cardHit = cardStatus.getCenterPile().getTopCard();
    if(cardHit.getNumber()==jackNumber){
      CenterPileToUser centerPileToUser = new CenterPileToUser(userStatus,cardStatus);
      centerPileToUser.act();
    } else{
      UserCardToCenterBottom userCardToCenterBottom = new UserCardToCenterBottom(userStatus,cardStatus);
      userCardToCenterBottom.act();
    }
  }
}
