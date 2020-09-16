package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class WarDrawTest {

  ArrayList<User> users;
  ArrayList<String> actions = new ArrayList<>();
  UserStatus userStatus;
  CardStatus cardStatus;
  MainGame mainGame;

  void turn() {
    actions.add("UserCardToCenter");
    mainGame.setCardStatus(cardStatus);
    mainGame.setUserStatus(userStatus);
    mainGame.setActionsForTurn(actions);
    mainGame.doTurn();
  }

  @Test
  void testUserWar() {
    mainGame = new MainGame();
    users = new ArrayList<>();
    mainGame.setUpUsers(2);
    users = (ArrayList<User>) mainGame.getUsers();
    Dealer dealer = new Dealer(users, 5);
    List<Card> deck = dealer.setUpDeck(1);
    assertEquals(52, deck.size());
    dealer.dealDeckToUsers();
    cardStatus = new CardStatus(users);
    userStatus = new UserStatus(users);
    cardStatus.makeCenterPiles(false);
    users.forEach((user) -> assertEquals(26, user.getPile().getNumCards()));

    while (users.get(0).getPile().getNumCards() != 0 && users.get(1).getPile().getNumCards() != 0) {
      turn();
    }
  }


}