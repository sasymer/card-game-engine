package engine;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class GoFish {

  private int numUsers = 2;
  private int numDecks = 1;
  private int numUserCards = 7;
  private UserStatus userStatus;
  private CardStatus cardStatus;
  private ArrayList<String> actions = new ArrayList<>();
  private MainGame mainGame;

  private void setActions() {
    actions.add("ChangeTurn");
    actions.add("RemovePairsUser");
    actions.add("SelectRandomUserCard");
    actions.add("AskForCard");
    actions.add("SelectNextUser");
    actions.add("RemovePairsUser");
    actions.add("CheckAllEmptyHand");

  }

  private void setDeckandDeal(){
    setActions();
    mainGame = new MainGame();
    mainGame.setActionsForTurn(actions);
    mainGame.setUpUsers(numUsers);
    Dealer dealer = new Dealer(mainGame.getUsers(), numUserCards);
    dealer.setUpDeck(numDecks);
    dealer.makeUserPiles();
    testDealNumber();
    cardStatus = new CardStatus(mainGame.getUsers());
    cardStatus.setCenterPile(dealer.getDeck());
    cardStatus.makeCenterPiles(true);
    userStatus = new UserStatus(mainGame.getUsers());
  }

  @Test
  private void testDealNumber() {
    mainGame.getUsers().forEach((user) -> assertEquals(7, user.getPile().getNumCards()));
  }

  boolean gameOver(){
    return cardStatus.getCenterPile().getNumCards()!=0 ;
  }

  @Test
  public void goFishTest(){
    setDeckandDeal();
    mainGame.setUserStatus(userStatus);
    mainGame.setCardStatus(cardStatus);
    userStatus.setCurrentUser(0);
    userStatus.setSelectedUser(0);
    while(!gameOver()){
      mainGame.doTurn();
    }
  }


}
