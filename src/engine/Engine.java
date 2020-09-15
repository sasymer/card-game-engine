package engine;
import java.util.List;

public interface Engine{

   public List<Card> userTopCards();
   //update piles after rules are checked
   public List<Pile> updatePiles();

   public void setActionsForTurn(List<String> actions);

   public void setUserStatus(UserStatus userStatus);

   public void setCardStatus(CardStatus cardStatus);


   //all things below this are new

   public void setNumUserCards(int num);

   public void setUpUsers(int numUsers);

   public List<User> getUsers();

   public void undo();

   public void doTurn();

   public void executeAction(String action);
}