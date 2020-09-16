package engine.action;

import static org.junit.jupiter.api.Assertions.*;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChangeTurnTest {

  private List<User> active = new ArrayList<>();
  private UserStatus userStatus;

  private void setUp() {
    active.add(new User("p1", 0));
    active.add(new User("p2", 1));
    active.add(new User("p3", 2));
    userStatus = new UserStatus(active);
  }

  @Test
  void setUsers() {
    setUp();
    assertEquals(3, active.size());
    assertEquals(0, userStatus.getCurrentUser().getID());
  }

  @Test
  void change() {
    setUsers();
    ChangeTurn changer = new ChangeTurn(userStatus, null);
    changer.act();
    assertEquals(1, userStatus.getCurrentUser().getID());
    changer.act();
    assertEquals(2, userStatus.getCurrentUser().getID());
    changer.act();
    assertEquals(0, userStatus.getCurrentUser().getID());
  }

  @Test
  void loserThenChange() {
    setUsers();
    assertEquals(0, userStatus.getCurrentUser().getID());
    userStatus.setLoserID(1); //take userID 1 out of active users
    assertEquals(1, userStatus.getLoserID());
    assertEquals(2, userStatus.getActiveUsers().size());
    ChangeTurn changer = new ChangeTurn(userStatus, null);
    changer.act();
    //1 has been removed so we expect 2 to be set current next
    assertEquals(2, userStatus.getCurrentUser().getID());

    userStatus.setLoserID(2);
    assertEquals(1, userStatus.getActiveUsers().size());
    assertEquals(0, userStatus.getActiveUsers().get(0).getID());
    //at this point current is 2, but 2 also lost so is not in active users
    changer.act();
    assertEquals(0, userStatus.getCurrentUser().getID());
  }
}