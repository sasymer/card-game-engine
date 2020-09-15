package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.List;

/**
 * Used to select the next user.
 *
 * This action could be useful in games like Old Maid, that rely
 * on choosing a card from the next user
 *
 * @author Sanna
 */
public class SelectNextUser extends Action {

  private static final int ADD_ONE = 1;

  public SelectNextUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    int current;
    List<User> active = userStatus.getActiveUsers();
    current = userStatus.getCurrentUser().getID();
    int next = current + ADD_ONE;
    for (int i = 0; i < active.size(); i++) {
      if (active.get(i).getID() == current) {
        next = i + ADD_ONE;
      }
    }
    if (next == active.size()) {
      next = 0;
    }
    userStatus.setSelectedUser(active.get(next).getID());
  }
}
