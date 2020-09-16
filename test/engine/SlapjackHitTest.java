package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SlapjackHitTest {
  private UserStatus userStatus;
  private CardStatus cardStatus;
  private ArrayList<String> actions = new ArrayList<>();
  private List<User> users;
  private MainGame mainGame;

  private void setDeckandDeal(){
    mainGame = new MainGame();
    users = new ArrayList<>();
    mainGame.setUpUsers(2);
    users= mainGame.getUsers();
    Dealer dealer = new Dealer(mainGame.getUsers(), 25);
    List<Card> deck = dealer.setUpDeck(1);
    dealer.makeUserPiles();
    users.forEach((user) -> assertEquals(25, user.getPile().getNumCards()));
    cardStatus = new CardStatus(users);
    userStatus = new UserStatus(mainGame.getUsers());
    userStatus.setCurrentUser(0);
    userStatus.setSelectedUser(0);
    cardStatus.setCenterPile(dealer.getDeck());
    cardStatus.makeCenterPiles(true);
  }

  private void turn(){
    actions.add("CheckAllCardsOneUser");
    actions.add("UserCardToCenter");
    actions.add("ChangeTurn");
    mainGame.setUserStatus(userStatus);
    mainGame.setCardStatus(cardStatus);
    mainGame.setActionsForTurn(actions);
    mainGame.doTurn();
  }

  private void checkForJack(){
    userStatus.setSelectedUser(1);
    mainGame.executeAction("CheckForJack");
    mainGame.doTurn();
  }

  private boolean gameOver(){
    for(User user:users){
      if(user.getPile().getNumCards()==0)return true;
    }
    return false;
  }

  //@Test
  public void testSlapAJack(){
    setDeckandDeal();
    int now =0;
    while(!gameOver()){
      turn();
      if (now % 4 == 0) checkForJack();
      now++;

    }
  }

}
