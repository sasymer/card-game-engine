package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Used to find the user with the largest sum of card numbers
 * Sets this user as the winner
 *
 * @author Sanna
 */
public class MakeLargestSumWinner extends Action {

  private static final int STARTING_VALUE = -1;

  public MakeLargestSumWinner(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  @Override
  public void act() {
    if (cardStatus.getCenterPile().getNumCards() == 0) {
      int winnerID = STARTING_VALUE;
      int maxSum = STARTING_VALUE;
      for (User user : userStatus.getUsers()) {
        int userSum = 0;
        for (Card card : user.getPile()) {
          userSum += card.getNumber();
        }
        if (userSum > maxSum) {
          winnerID = user.getID();
          maxSum = userSum;
        }
      }
      userStatus.setWinnerID(winnerID);
    }
  }
}
