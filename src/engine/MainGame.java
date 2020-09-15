package engine;

import engine.action.Action;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Main source of communication between controller and back end
 *
 * @author Libba, Sanna
 */

public class MainGame implements Engine {

  private static final String ACTIONLOCATION = GameConstant.getActionLocation();
  private static final String USERSTATUS = "UserStatus";
  private static final String CARDSTATUS = "CardStatus";
  private static final String DEFAULT_USER_NAME = GameConstant.getDefaultUsername();
  private static final String PERIOD = ".";
  private ResourceBundle actionData = GameConstant.getActionData();
  private ResourceBundle errorMessage = GameConstant.getErrorMessages();

  private List<User> users;
  private int numUsers, numUserCards;
  private List<String> actionList;
  private UserStatus userStatus;
  private CardStatus cardStatus;
  private List<Pile> piles;


  public MainGame() {
    users = new ArrayList<>();
    actionList = new ArrayList<>();
    piles = new ArrayList<>();
  }

  public void resetGame() {
    users = new ArrayList<>();
    setUpUsers(numUsers);
  }

  @Override
  public void setNumUserCards(int num) {
    numUserCards = num;
  }

  @Override
  public void setUpUsers(int numUsers) {
    this.numUsers = numUsers;
    for (int i = 0; i < numUsers; i++) {
      User user = new User(DEFAULT_USER_NAME + (i + 1), i);
      users.add(user);
    }
  }

  @Override
  public List<User> getUsers() {
    return users;
  }

  @Override
  public void undo(){
    for (Pile pile:piles){
      pile.emptyPile();
      for(Card card:pile.getMemory()){
        pile.addCardToTop(card);
      }
    }
  }

  @Override
  public void setActionsForTurn(List<String> actions) {
    actionList = actions;
  }

  @Override
  public void setUserStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }

  @Override
  public void setCardStatus(CardStatus cardStatus) {
    this.cardStatus = cardStatus;
  }

  /**
   * This can be called by controller to execute one user's turn
   */
  @Override
  public void doTurn() {
    setPileMemory();
    for (String action : actionList) {
      executeAction(action);
    }
  }

  /**
   * This method works to execute one action with an input String that is the name of the action
   */
  @Override
  public void executeAction(String action) {
    List<CurrentStatus> actionInfo = new ArrayList<>();
    String actionDataNeeded = actionData.getString(action);
    action = ACTIONLOCATION + PERIOD + action;
    Action actionGiven = null;
    try {
      if (actionDataNeeded.contains(USERSTATUS)) {
        actionInfo.add(0, userStatus);
      } else {
        actionInfo.add(0, null);
      }
      if (actionDataNeeded.contains(CARDSTATUS)) {
        actionInfo.add(1, cardStatus);
      } else {
        actionInfo.add(1, null);
      }
      actionGiven = (Action) Class.forName(action).getConstructors()[0]
          .newInstance(actionInfo.get(0), actionInfo.get(1));
      actionGiven.act();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
     errorMessage.getString("noMethod");
    }
  }

  @Override
  public List<Card> userTopCards() {
    ArrayList<Card> topCards = new ArrayList<Card>();
    for (User user : userStatus.getUsers()) {
      topCards.add(user.getPile().getTopCard());
    }
    return topCards;
  }

  @Override
  public List<Pile> updatePiles() {
    ArrayList<Pile> updatedUserPiles = new ArrayList<>();
    for (User user : userStatus.getUsers()) {
      updatedUserPiles.add(user.getPile());
    }
    return updatedUserPiles;
  }

  private void setPileMemory(){
    for (User user: userStatus.getUsers()){
      piles.add(user.getPile());
      piles.add(user.getCenterPile());
    }
    piles.add(cardStatus.getCenterPile());
    for (Pile pile:piles) {
      if(pile!=null) pile.setMemory(pile);
    }
  }

}


