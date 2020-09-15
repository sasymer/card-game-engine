package engine.action;

import engine.Card;
import engine.CardStatus;
import engine.Pile;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Written to introduce further flexibility of actions (expansion of CheckLargest)
 *
 * Similar code to CheckLargestCard, but implemented in separate action form
 * to increase flexibility
 *
 * @author Libba
 */
public class CheckSmallest extends Action {

  private int min = Integer.MAX_VALUE;

  public CheckSmallest(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }

  private Card smallest() {
    Card minCard = null;
    for (int i = 0; i < userStatus.getNumUsers(); i++) {
      Pile userCenter = cardStatus.getCenterPile(i);
      Card topCard = userCenter.getTopCard();
      if (topCard.getNumber() < min) {
        minCard = topCard;
        min = topCard.getNumber();
      }
    }
    return minCard;
  }

  private List<User> findUsersWithCard(Card card) {
    List<User> withCard = new ArrayList<>();

    for (int i = 0; i < userStatus.getNumUsers(); i++) {
      Pile userCenter = cardStatus.getCenterPile(i);
      if (userCenter.getTopCard().getNumber() == card.getNumber()) {
        withCard.add(userStatus.getUsers().get(i));
      }
    }
    return withCard;
  }

  @Override
  public void act() {
    Card minCard = smallest();
    List<User> minCardUsers = findUsersWithCard(minCard);

    if (minCardUsers.size() == 1) {
      User winner = minCardUsers.get(0);
      userStatus.setSelectedUser(winner.getID());
      CenterPileToUser centerPileToUser = new CenterPileToUser(userStatus, cardStatus);
      centerPileToUser.act();
    } else {
      userStatus.setTiedUsers(minCardUsers);
      GoToWar war = new GoToWar(userStatus, cardStatus);
      war.act();
    }
  }
}



