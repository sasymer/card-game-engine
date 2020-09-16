package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;


public class MultipleDeck {

  @Test
  public void makeMultiDeck(){
    MainGame mainGame = new MainGame();
    mainGame.setUpUsers(2);
    List<User> users = mainGame.getUsers();
    Dealer dealer  = new Dealer(users, 0);
    dealer.setUpDeck(2);
    assertEquals(52*2,dealer.getDeck().getNumCards());
  }

}
