package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.List;

/**
 * Changes to the next user turn based on the active users
 *
 * @author Sanna, Libba
 */
public class ChangeTurn extends Action {

  private static final int ZERO = 0;
  private static final int ONE = 1;

  public ChangeTurn(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<User> active = userStatus.getActiveUsers();
    int current = userStatus.getCurrentUser().getID();
    int next = current + ONE;
    for (int i = 0; i < active.size(); i++) {
      if (active.get(i).getID() == current) {
        next = i + ONE;
      }
    }
    if (next >= active.size()) {
      next = ZERO;
    }
    userStatus.setCurrentUser(active.get(next).getID());
  }
}
