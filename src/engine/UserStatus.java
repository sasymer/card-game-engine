package engine;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class UserStatus extends CurrentStatus {

  private static final int ADD_ONE = 1;
  private int currentUserID, selectedUserID, winnerID, loserID;
  private List<User> tiedUsers;
  private List<User> activeUsers;
  private User currentUser, selectedUser;
  private BooleanProperty selectedUserProp = new SimpleBooleanProperty();
  private boolean newUserSelected = false;
  private boolean turnFinished = true;
  private int userNumCard;

  public UserStatus(List<User> users) {
    super(users);
    activeUsers = new ArrayList<>(users);
    tiedUsers = new ArrayList<>();
    currentUserID = 0;
    selectedUserID = 0;
    setCurrentUser(0);
    setSelectedUser(0);
    winnerID = Integer.MIN_VALUE;
    loserID = Integer.MIN_VALUE;
    userNumCard = 0;
  }

  public int getNextUserID() {
    int next = currentUser.getID() + ADD_ONE;
    if (next == users.size()) {
      next = 0;
    }
    return next;
  }

  public BooleanProperty getSelectedUserProp() {
    return selectedUserProp;
  }

  public List<User> getUsers() {
    return users;
  }

  public List<User> getActiveUsers() {
    return activeUsers;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public User getSelectedUser() {
    return selectedUser;
  }

  public int getNumUsers() {
    return users.size();
  }

  public void setTiedUsers(List<User> tied) {
    tiedUsers = tied;
  }

  public List<User> getTiedUsers() {
    return tiedUsers;
  }

  public void setCurrentUser(int id) {
    currentUser = users.get(id);
  }

  public void setSelectedUser(int id) {
    selectedUserProp.set(!selectedUserProp.get());
    newUserSelected = true;
    selectedUser = users.get(id);
  }

  public void removeUser(int id) {
    activeUsers.remove(id);
  }

  public void setUserNumCard(int num) {
    this.userNumCard = num;
  }

  public int getUserNumCard() {
    return userNumCard;
  }

  public void setWinnerID(int id) {
    winnerID = id;
  }

  public int getWinnerID() {
    return winnerID;
  }

  public int getLoserID() {
    return loserID;
  }

  public void setLoserID(int id) {
    loserID = id;
    User loser = users.get(id);
    activeUsers.remove(loser);
  }

  public boolean isTurnFinished() {
    return turnFinished;
  }

  public void setTurnFinished(boolean isFinished) {
    turnFinished = isFinished;
  }

}
