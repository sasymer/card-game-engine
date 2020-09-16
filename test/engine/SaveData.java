package engine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


public class SaveData {

  /**
   * This is a controller action ... ignore for backend
   */
  @Test
  public void save(){
    ArrayList<User> users = new ArrayList<>();
    users.add(new User("me",0));
    MainGame mainGame = new MainGame();
    UserStatus userStatus = new UserStatus(users);
    CardStatus cardStatus = new CardStatus(users);
    userStatus.setCurrentUser(0);
    mainGame.setUserStatus(userStatus);
    mainGame.setCardStatus(cardStatus);
 //   mainGame.executeAction("SaveGame");
  }

}
