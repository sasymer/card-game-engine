package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.Pile;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanna
 */
public class CheckLargestCard extends Action {

  private static final int ONE_WINNER = 1;
  private int maxNum = Integer.MIN_VALUE;

  public CheckLargestCard(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  private Card largestCard() {
    Card maxCard = null;
    for (int i = 0; i < userStatus.getNumUsers(); i++) {
      Pile userCenter = cardStatus.getCenterPile(i);
      Card topCard = userCenter.getTopCard();
      if (topCard.getNumber() > maxNum) {
        maxCard = topCard;
        maxNum = topCard.getNumber();
      }
    }
    return maxCard;
  }

  private List<User> findUsersWithCard(Card card) {
    List<User> withCard = new ArrayList<>();
    for (int i = 0; i < userStatus.getNumUsers(); i++) {
      Pile userCenter = userStatus.getUsers().get(i).getCenterPile();
      if (userCenter.getTopCard().getNumber() == card.getNumber()) {
        withCard.add(userStatus.getUsers().get(i));
      }
    }

    return withCard;
  }

  @Override
  public void act() {
    Card maxCard = largestCard();
    List<User> maxCardUsers = findUsersWithCard(maxCard);

    if (maxCardUsers.size() == ONE_WINNER) {
      User winner = maxCardUsers.get(0);
      userStatus.setSelectedUser(winner.getID());
      CenterPileToUser centerPileToUser = new CenterPileToUser(userStatus, cardStatus);
      centerPileToUser.act();
    } else {
      userStatus.setTiedUsers(maxCardUsers);
      GoToWar war = new GoToWar(userStatus, cardStatus);
      war.act();
    }
  }
}
