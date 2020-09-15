package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.List;

/**
 * Remove 4 matching cards from user hand and put into user matches
 *
 * Used if you want to remove matches by full suit, rather than by pairs
 * of like cards. Gives user more options for how they want to play games:
 * do you want the game to go faster? Use RemovePairsUser. If you want
 * to play the more traditional version where you wait until you have
 * a full suit, then use this.
 *
 * Useful for go fish and old maid in particular
 *
 * @author Sanna
 */
public class RemoveQuadsUser extends Action {
  private static final int QUAD = 4;

  public RemoveQuadsUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<User> users = userStatus.getUsers();
    users.forEach((user) -> user.createPairsFromHand(QUAD));
  }
}
