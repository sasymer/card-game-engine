package engine.action;

import engine.CardStatus;
import engine.UserStatus;

public class LastUserActive extends Action {

  private static final int ONE_LEFT = 1;

  public LastUserActive(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    if (userStatus.getActiveUsers().size() == ONE_LEFT) {
      userStatus.setWinnerID(userStatus.getActiveUsers().get(0).getID());
    }
  }


}
