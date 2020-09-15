package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Libba
 */
public abstract class CurrentStatus {
  List<User> users = new ArrayList<>();

  public CurrentStatus(List<User> users) {
    this.users = users;
  }

}
