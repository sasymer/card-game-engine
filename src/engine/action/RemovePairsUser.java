package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.List;

/**
 * Used to remove pairs from all user hands
 *
 * @author Sanna
 */
public class RemovePairsUser extends Action {

  private static final int PAIR = 2;
  public RemovePairsUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<User> users = userStatus.getUsers();
    users.forEach((user) -> user.createPairsFromHand(PAIR));
  }
}
