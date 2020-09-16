package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Libba
 */
public class MemoryFoundPair {

  private MainGame mainGame;
  private List<User> users;
  private List<String> actions = new ArrayList<>();
  private CardStatus cardStatus;
  private UserStatus userStatus;
  private List<Pile> piles = new ArrayList<>();

  void setAndDeal(){
    mainGame = new MainGame();
    users = new ArrayList<>();
    users.add(new User("me",0));
    Pile pile;
    //creates 20 piles of one card
    for(int i=0; i<2;i++){
      pile = new Pile();
      pile.addCardToTop(new Card(false,1,2,23));
      piles.add(pile);
      assertEquals(1,pile.getNumCards());
    }
    cardStatus = new CardStatus(users);
    userStatus = new UserStatus(users);
    userStatus.setCurrentUser(users.get(0).getID());
  }

  void checkTwoCards(){
    cardStatus.setCenterPile(piles.get(0));
    userStatus.getCurrentUser().setPile(piles.get(1));
    actions.add("CheckUserCardEqualityWithCenterCard");
    mainGame.setActionsForTurn(actions);
    mainGame.setCardStatus(cardStatus);
    mainGame.setUserStatus(userStatus);
    mainGame.doTurn();
  }


  @Test
  public void foundPair(){
    setAndDeal();
    checkTwoCards();
  }

}
