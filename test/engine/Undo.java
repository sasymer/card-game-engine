package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author Libba
 */
public class Undo {

  @Test
  public void undoMove(){
    MainGame mainGame = new MainGame();
    List<User> users = new ArrayList<>();
    users.add(new User("me",0));
    Dealer dealer = new Dealer(users, 5);
    dealer.setUpDeck(1);
    dealer.makeUserPiles();
    assertEquals(5,users.get(0).getPile().getNumCards());
    Pile center = dealer.makePile(6);
    assertEquals(6,center.getNumCards());
    CardStatus cardStatus = new CardStatus(users);
    users.get(0).setCenterPile(center);
    cardStatus.setCenterPile(center);
    UserStatus userStatus = new UserStatus(users);

    userStatus.setCurrentUser(0);

    mainGame.setCardStatus(cardStatus);
    mainGame.setUserStatus(userStatus);
    ArrayList action = new ArrayList();
    action.add("UserCardToCenter");
    mainGame.setActionsForTurn(action);
    mainGame.doTurn();

   // assertEquals(5,userStatus.getCurrentUser().getPile().getMemory());
    mainGame.undo();
  }
}
