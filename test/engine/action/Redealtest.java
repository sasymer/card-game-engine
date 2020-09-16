package engine.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.Card;
import engine.CardStatus;
import engine.Dealer;
import engine.MainGame;
import engine.Pile;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Libba
 */
public class Redealtest {

  private UserStatus userStatus;
  private CardStatus cardStatus;
  private ArrayList<String> actions = new ArrayList<>();
  private List<User> users;
  private MainGame mainGame;

  private void setActions(){
    actions.add("Redeal");
  }

  private void setDeckandDeal(){
    mainGame = new MainGame();
    users = new ArrayList<>();
    mainGame.setUpUsers(4);
    users = mainGame.getUsers();
    Dealer dealer = new Dealer(users, 10);
    List<Card> deck = dealer.setUpDeck(1);
    dealer.makeUserPiles();
    users.forEach((user) -> assertEquals(10, user.getPile().getNumCards()));
    cardStatus = new CardStatus(users);
    userStatus = new UserStatus(users);
    Pile center = dealer.makePile(10);
    assertEquals(10,center.getNumCards());
    cardStatus.setCenterPile(center);
  }

  @Test
  public void redeal(){
    setDeckandDeal();
    mainGame.setUserStatus(userStatus);
    mainGame.setCardStatus(cardStatus);
    setActions();
    mainGame.setActionsForTurn(actions);
    mainGame.doTurn();
  }

}
