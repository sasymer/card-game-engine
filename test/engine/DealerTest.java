package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DealerTest {

  @Test
  void testMakeDeck() {
    Dealer dealer = new Dealer(null, 0);
    List<Card> deck = dealer.setUpDeck(1);
    assertEquals(52, deck.size());
  }

  private List<User> setUsers() {
    List<User> users = new ArrayList<>();
    users.add(new User("me", 0));
    users.add(new User("you", 1));
    return users;
  }

  @Test
  void testMakeUserPiles() {
    List<User> users = setUsers();
    Dealer dealer = new Dealer(users, 5);
    List<Card> deck = dealer.setUpDeck(1);
    dealer.makeUserPiles();
    users.forEach((user) -> assertEquals(5, user.getPile().getNumCards()));
  }

  @Test
  void userSetUp() {
    List<User> users = setUsers();
    assertEquals(0, users.get(0).getID());
    assertEquals(1, users.get(1).getID());
  }

}