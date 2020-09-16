package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Libba
 */
public class MemoryPairTest {
  private MainGame mainGame;
  private List<User> users;
  private List<Card> deck = new ArrayList<>();
  private List<String> actions = new ArrayList<>();
  private CardStatus cardStatus;
  private UserStatus userStatus;
  private List<Pile> piles = new ArrayList<>();
  private int count =0;

  void setAndDeal(){
    mainGame = new MainGame();
    users = new ArrayList<>();
    users.add(new User("me",0));
    Dealer dealer = new Dealer(users, 0);
    deck = dealer.setUpDeck(1);
    Pile pile;
    //creates 20 piles of one card
    for(int i=0; i<20;i++){
      pile = dealer.makePile(1);
      piles.add(pile);
      assertEquals(1,pile.getNumCards());
    }
    cardStatus = new CardStatus(users);
    userStatus = new UserStatus(users);
    userStatus.setCurrentUser(0);
  }

  void getCardInPile(){
    count ++;
    int randomPile = (int)(Math.random()*20);
    Pile pile = piles.get(randomPile);
    if(count%2==0){
      cardStatus.setCenterPile(pile);
    }
    else {
     userStatus.getCurrentUser().setPile(pile);
    }
  }

  void checkTwoCards(){
    getCardInPile();
    getCardInPile();
    actions.add("CheckUserCardEqualityWithCenterCard");
    mainGame.setActionsForTurn(actions);
    mainGame.setCardStatus(cardStatus);
    mainGame.setUserStatus(userStatus);
    mainGame.doTurn();
  }

  @Test
  public void memoryPair(){
    setAndDeal();
    int num = 10;
    while(num>0){
      checkTwoCards();
      num--;
    }
  }

}
