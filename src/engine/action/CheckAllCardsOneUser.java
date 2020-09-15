package engine.action;

import engine.CardStatus;
import engine.GameConstant;
import engine.User;
import engine.UserStatus;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is a win condition action, to check if the win condition of the game has been
 * satisfied.
 * <p>
 * Specifically, this class checks if a user has all the cards in game play. This action is used in
 * games like war and slapjack, in which the goal is to end with all the cards.
 *
 * @author Sanna, Libba
 */
public class CheckAllCardsOneUser extends Action {

  private static final ResourceBundle DECK_DATA = GameConstant.getDeckData();
  public static final int DECK_SIZE = Integer.parseInt(DECK_DATA.getString("size"));

  public CheckAllCardsOneUser(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    List<User> users = userStatus.getUsers();
    for (User user : users) {
      if (user.getNumCards() == DECK_SIZE) {
        userStatus.setWinnerID(user.getID());
      }
      if (user.getNumCards() == 0) {
        userStatus.setLoserID(user.getID());
      }
    }
  }
}
