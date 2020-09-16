package engine.action;

import static org.junit.jupiter.api.Assertions.*;

import engine.User;
import engine.UserStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SelectRandomOtherUserTest {

  private List<User> users = new ArrayList<>();
  private UserStatus userStatus;

  private void setUp() {
    users.add(new User("p1", 0));
    users.add(new User("p2", 1));
    users.add(new User("p3", 2));
    userStatus = new UserStatus(users);
  }

  @Test
  void test() {
    setUp();
    assertEquals(0, userStatus.getSelectedUser().getID());

    SelectRandomUserCard selectRandomUserCard = new SelectRandomUserCard(userStatus, null);
    selectRandomUserCard.act();

    //selected user must be an active user
    assertTrue(userStatus.getActiveUsers().contains(userStatus.getSelectedUser()));
  }


}