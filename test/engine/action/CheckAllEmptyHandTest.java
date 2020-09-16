package engine.action;

import static org.junit.jupiter.api.Assertions.*;

import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CheckAllEmptyHandTest {

  private UserStatus userStatus;
  private List<User> users = new ArrayList<>();

  private void setUp() {
    users.add(new User("p1", 0));
    users.add(new User("p2", 1));
    userStatus = new UserStatus(users);
  }

  @Test
  void check() {
    setUp();
    assertEquals(2, userStatus.getActiveUsers().size());
    CheckAllEmptyHand checkAllEmptyHand = new CheckAllEmptyHand(userStatus, null);
    checkAllEmptyHand.act();
    assertEquals(0, userStatus.getActiveUsers().size());
  }
}