package engine;

import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;

//TODO: add this to the user status and card status
//actually add it to current status so it can be used by both
/**
 * This static class is designed to hold static information that is
 * relevant to many aspects of the game, and has a constant state.
 *
 * It will hold the number of cards in the original deck as well as
 * data about action parameters
 *
 * I created this class as a response to TA advice on the last project--
 * static classes should be used if many components use similar data
 * that has a constant state.
 *
 * This way, you don't need to add extra instance variables to classes,
 * creating duplicate code.
 *
 * Also, this makes it so that if you change names of resources, you only have
 * to change code in this class-- not in other classes
 *
 * @author Sanna
 */
public class GameConstant {

  private static final String ACTION_LOCATION = "engine.action";
  private static final String RESOURCES = "resources.";
  private static final String ACTION_DATA_LOCATION = RESOURCES + "ActionData";
  private static final ResourceBundle ACTION_DATA = ResourceBundle.getBundle(ACTION_DATA_LOCATION);
  private static final String DECK_DATA_LOCATION = RESOURCES + "DeckData";
  private static final ResourceBundle DECK_DATA = ResourceBundle.getBundle(DECK_DATA_LOCATION);
  private static final String ERROR_LOCATION = RESOURCES + "ErrorMessages";
  private static final ResourceBundle ERROR_MESSAGES = ResourceBundle.getBundle(ERROR_LOCATION);
  private static final String USERNAME_LOCATION = RESOURCES + "Username";
  private static final ResourceBundle USERNAME = ResourceBundle.getBundle(USERNAME_LOCATION);
  private static final String SPACE = " ";

  public static ResourceBundle getActionData() {
    return ACTION_DATA;
  }

  public static ResourceBundle getDeckData() {
    return DECK_DATA;
  }

  public static ResourceBundle getErrorMessages() {
    return ERROR_MESSAGES;
  }

  public static String getActionLocation() {
    return ACTION_LOCATION;
  }

  public static String getResourceLocation() {
    return RESOURCES;
  }

  public static int getOldMaidCard() {
    return Integer.parseInt(DECK_DATA.getString("oldmaid"));
  }

  public static int getSlapCard() {
    return Integer.parseInt(DECK_DATA.getString("slapcard"));
  }

  public static int getCardsForWar() {
    return Integer.parseInt(DECK_DATA.getString("cardsForWar"));
  }

  public static int getDeckMultiplier() {
    return Integer.parseInt(DECK_DATA.getString("deckMultiplier"));
  }

  public static int getSuitMultiplier() {
    return Integer.parseInt(DECK_DATA.getString("suitMultiplier"));
  }

  public static String getDefaultUsername() {
    return USERNAME.getString("username") + SPACE;
  }

}
