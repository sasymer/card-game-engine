package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Idea was that in GoFish when a user runs out of cards we give them more cards until the center pile is empty
 * @author Libba
 */
public class ReFillCards extends Action {

  public ReFillCards(UserStatus userStatus, CardStatus cardStatus){
    super(userStatus,cardStatus);
  }

  @Override
  public void act() {
      if(userStatus.getCurrentUser().getPile().getNumCards()==0 && cardStatus.getCenterPile().getNumCards()>0){
        for(int i=0;i<userStatus.getUserNumCard();i++){
          if (cardStatus.getCenterPile().getNumCards() > 0) {
            userStatus.getCurrentUser().getPile().addCardToTop(cardStatus.getCenterPile().removeTopCard());
        }
      }
    }
  }
}
