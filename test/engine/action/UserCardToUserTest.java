package engine.action;

import static org.junit.jupiter.api.Assertions.*;

import engine.Card;
import engine.CardStatus;
import engine.Dealer;
import engine.MainGame;
import engine.User;
import engine.UserStatus;
import org.junit.jupiter.api.Test;

/**
 * Tests user card to user action using old maid set up
 */
class UserCardToUserTest {

  private MainGame setUpAGameForOldMaid() {
    MainGame game = new MainGame();
    game.setUpUsers(2);
    Dealer dealer = new Dealer(game.getUsers(), 0);
    dealer.setUpDeck(1);
    assertEquals(52, dealer.getDeck().getNumCards());
    dealer.removeMaid();
    assertEquals(51, dealer.getDeck().getNumCards());
    dealer.dealDeckToUsers();
    return game;
  }

  @Test
  void oldMaidTransferTest() {
    MainGame game = setUpAGameForOldMaid();
    UserStatus userStatus = new UserStatus(game.getUsers());
    CardStatus cardStatus = new CardStatus(game.getUsers());
    game.setCardStatus(cardStatus);
    game.setUserStatus(userStatus);

    for (User user : game.getUsers()) {
      System.out.println("User has cards: " + user.getNumCards());
    }

    UserCardToUser action = new UserCardToUser(userStatus, cardStatus);
    action.act();

   // cardStatus.setSelectedCard(userStatus.getSelectedUser().getPile().getCardAt(0));
    userStatus.setSelectedUser(1);
    assertEquals(1, userStatus.getSelectedUser().getID());

    SelectNextUser selectNextUser = new SelectNextUser(userStatus, null);
    SelectRandomUserCard selectRandomUserCard = new SelectRandomUserCard(userStatus, cardStatus);

    selectNextUser.act();
    selectRandomUserCard.act();
    User selectedUser = userStatus.getSelectedUser();
    Card selectedCard = cardStatus.getSelectedCard();

    User currentUser = userStatus.getCurrentUser();
    assertEquals(0, currentUser.getID());

    assertTrue(currentUser.hasCard(selectedCard));
    assertFalse(selectedUser.hasCard(selectedCard));
  }
}