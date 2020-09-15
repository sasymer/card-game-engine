package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Used to check which user has the most pairs of cards, to see who wins
 *
 * @author Sanna
 */
public class CheckMostPair extends Action {

  private int maxCards = Integer.MIN_VALUE;
  private int winnerID = -1;

  public CheckMostPair(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    for (User user : userStatus.getUsers()) {
      if (user.getNumPairs() > maxCards) {
        maxCards = user.getNumPairs();
        winnerID = user.getID();
      }
    }
    userStatus.setWinnerID(winnerID);
  }
}
