package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;

public class CheckLeastPair extends Action {

  private int minCards = Integer.MAX_VALUE;
  private int winnerID = -1;

  public CheckLeastPair(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    for (User user : userStatus.getUsers()) {
      if (user.getNumPairs() < minCards) {
        minCards = user.getNumPairs();
        winnerID = user.getID();
      }
    }
    userStatus.setWinnerID(winnerID);
  }
}
