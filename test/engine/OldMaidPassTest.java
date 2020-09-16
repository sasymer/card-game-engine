package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

//currently fails

public class OldMaidPassTest {

  private UserStatus userStatus;
  private CardStatus cardStatus;
  private ArrayList<String> actions = new ArrayList<>();
  private List<User> users;
  private MainGame mainGame;
  private List<Card> deck;

  @Test
  private void setUp(){
    setUpActions();
    mainGame = new MainGame();
    mainGame.setUpUsers(2);
    mainGame.setActionsForTurn(actions);
    Dealer dealer = new Dealer(mainGame.getUsers(), 0);
    deck = dealer.setUpDeck(1);
    deck = dealer.removeMaid();
    dealer.dealDeckToUsers();
    cardStatus = new CardStatus(mainGame.getUsers());
    userStatus = new UserStatus(mainGame.getUsers());
    mainGame.setCardStatus(cardStatus);
    mainGame.setUserStatus(userStatus);
    cardStatus.setCenterPile(dealer.getDeck());
    assertEquals(0,cardStatus.getCenterPile().getNumCards());
    userStatus.setCurrentUser(0);
    userStatus.setSelectedUser(0);
  }

  private void setUpActions() {
    actions.add("RemovePairsUser");
    actions.add("UserCardToUser");
    actions.add("SelectNextUser");
    actions.add("CheckAllEmptyHandsButOne");
  }

  boolean gameOver(){
    return userStatus.getLoserID()>=0;
  }


  @Test
  public void oldMaid(){
    setUp();
    while(gameOver()) {
      mainGame.doTurn();
    }
}

}
