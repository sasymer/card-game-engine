package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.List;

/**
 * Used in automated games like Old Maid or Go Fish automatic, if you want the backend
 * to do all the logic rather than having front end users select users and cards.
 *
 * @author Sanna
 */
public class SelectRandomOtherUser extends Action {

  public SelectRandomOtherUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<User> active = userStatus.getActiveUsers();
    int numUsers = active.size();
    int randomUser = (int)(Math.random() * numUsers);
    int randomID = active.get(randomUser).getID();
    while (randomID == userStatus.getCurrentUser().getID()) {
      randomUser = (int)(Math.random() * numUsers);
      randomID = active.get(randomUser).getID();
    }

    userStatus.setSelectedUser(randomID);
  }
}
