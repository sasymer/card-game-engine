package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Libba
 */
public class SlapJackWith4Users {

  private UserStatus userStatus;
  private CardStatus cardStatus;
  private ArrayList<String> actions = new ArrayList<>();
  private List<User> users;
  private MainGame mainGame;

  private void setActions(){
    actions.add("UserCardToCenter");
    actions.add("CheckAllCardsOneUser");
    actions.add("ChangeTurn");
  }

  private void setDeckandDeal(){
    mainGame = new MainGame();
    users = new ArrayList<>();
    mainGame.setUpUsers(4);
    users = mainGame.getUsers();
    Dealer dealer = new Dealer(users, 5);
    List<Card> deck = dealer.setUpDeck(1);
    dealer.makeUserPiles();
    users.forEach((user) -> assertEquals(5, user.getPile().getNumCards()));
    cardStatus = new CardStatus(users);
    userStatus = new UserStatus(users);
    Pile center = new Pile();
    cardStatus.setCenterPile(center);
  }

  private void slapJack(){
    if(cardStatus.getCenterPile().getNumCards()!=0) mainGame.executeAction("CheckForJack");
  }

  private boolean gameOver(){
    for (User user : mainGame.getUsers()) {
      if (user.emptyHand())
        return true;
    }
    return false;
  }

  @Test
  public void slapJackWith4(){
    int count = 0;
    setDeckandDeal();
    mainGame.setUserStatus(userStatus);
    mainGame.setCardStatus(cardStatus);
    setActions();
    mainGame.setActionsForTurn(actions);
    while (!gameOver()) {
      mainGame.doTurn();
      if (count % 3 == 0) slapJack();
      count++;
    }
  }
  }




