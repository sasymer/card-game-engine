package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to check if all users have empty hands, except for one user
 *
 * @author Sanna
 */
public class CheckAllEmptyHandsButOne extends Action {

  private static final int STARTING_INDEX = -1;
  private static final int ONE = 1;

  public CheckAllEmptyHandsButOne(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<Integer> userCardNumbers = new ArrayList<>();
    for (User user : userStatus.getUsers()) {
      userCardNumbers.add(user.getNumCards());
    }

    int numGreaterZero = 0;
    int loserID = STARTING_INDEX;
    for (int i : userCardNumbers) {
      if (i > 0) {
        numGreaterZero++;
        loserID = i;
      }
    }

    //game should end
    if (numGreaterZero == ONE) {
      userStatus.setLoserID(loserID);
    }
  }
}
