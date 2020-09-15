package engine.action;

import engine.CardStatus;
import engine.GameConstant;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes in the number of cards to go to war with from a properties file Checks this number against
 * the number of cards each user has and takes the minimum Users lay down their cards and the
 * largest card is checked
 */
public class GoToWar extends Action {

  private int minNumCards;
  private int numberOfCardsForWar = GameConstant.getCardsForWar();

  public GoToWar(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
    minNumCards = min();
  }


  @Override
  public void act() {
    List<User> tiedUsers = userStatus.getTiedUsers();

    //if one user has no cards left, can't go to war, set winner
    if (minNumCards == 0) {
      return;
    }

    for (int i = 0; i < minNumCards; i++) {
      for (User user : tiedUsers) {
        userStatus.setCurrentUser(user.getID());
        UserCardToCenter cardToCenter = new UserCardToCenter(userStatus, cardStatus);
        cardToCenter.act(); //put card in center
      }
    }
    CheckLargestCard checkLargestCard = new CheckLargestCard(userStatus, cardStatus);
    checkLargestCard.act();
    userStatus.setTiedUsers(new ArrayList<>());
  }

  private int min() {
    for (User user : userStatus.getTiedUsers()) {
      if (user.getPile().getCards().size() - 1 < numberOfCardsForWar) {
        numberOfCardsForWar = user.getPile().getCards().size();
      }
    }
    return numberOfCardsForWar;
  }
}
