package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.List;

/**
 * Checks if game is over by seeing if all hands are empty If all hands are empty, calls
 * CheckMostPair action to see which user has the most pairs (and wins)
 * <p>
 * Dependencies: needs CheckMostPair action, also userStatus
 *
 * @author Sanna
 */
public class CheckAllEmptyHand extends Action {

  public CheckAllEmptyHand(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<User> users = userStatus.getUsers();
    boolean allEmpty = true;
    for (User user : users) {
      if (!user.emptyHand()) {
        allEmpty = false;
      } else{
        userStatus.setLoserID(user.getID());
      }
    }

    if (allEmpty) {
      CheckMostPair checkMostPair = new CheckMostPair(userStatus, null);
      checkMostPair.act();
    }
  }
}
