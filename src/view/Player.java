package view;

import javafx.beans.property.Property;
import javafx.event.EventHandler;
import view.components.CenterViewPile;
import view.components.PlayerViewPile;

import java.util.List;
import java.util.Map;

public interface Player{

  //update view
  public void updateScene(int currentPlayerID, int selectedPlayerID);

  //set up view
  public void setUpScreen(EventHandler key, EventHandler next, Map<String,EventHandler> actions, List<CenterViewPile> center, List<PlayerViewPile> players, List<PlayerViewPile> playerPairs, boolean fannable);

  public void showError(String message);

  public Property getClickedPileProperty();

  public Property getClickedCardProperty();

  public void showRules();

  public void showEndGame(int playerID, String condition);



  //get card info that user clicked on
 //@Deprecated public Pile getPileSelected();

  //reflect the pile changes in the front end
//@Deprecated public void updatePileOrder();

  // save the game
 // @Deprecated public void saveState();

  //load the game
 // @Deprecated public void loadState();
    }