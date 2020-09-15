package controller;

import engine.Card;
import engine.Dealer;
import engine.MainGame;
import engine.UserStatus;
import engine.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import view.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import view.components.*;
import xml.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Controller {

  private final int DEFAULT_PILE_ID = 5;
  private static final String PLAYER_KEYS = "resources.PlayerKeys";
  private static final String ERROR_MESSAGES = "resources.ErrorMessages";

  private static final String XML_FILE_PATH = "data/save_xml.xml";
  private static final String TOOL_BUTTONS = "resources.ToolbarButtons";
  private static final String YES = "yes";
  private static final int CENTER_PILE_ID = 100;

  private final String DEFAULT_FILE_PACKAGE = "data" + File.separator;
  private final String FILE_EXTENSION = ".xml";


  private ResourceBundle playerKeys = ResourceBundle.getBundle(PLAYER_KEYS);
  private ResourceBundle errors = ResourceBundle.getBundle(ERROR_MESSAGES);
  private ResourceBundle toolButtons = ResourceBundle.getBundle(TOOL_BUTTONS);


  private MainGame myEngine;
  private GamePlayerView myPlayer;
  private File myConfigFile;

  private XMLParser myParser;
  private Rules myRules;
  private Configuration myConfiguration;
  private Actions myActions;

  private Dealer myDealer;
  private List<User> myUsers;
  private UserStatus myUserStatus;
  private CardStatus myCardStatus;

  private List<Card> myCards;
  private List<ViewCard> viewCards;
  private List<CenterViewPile> centerViewPiles;
  private List<PlayerViewPile> playerPiles;
  private List<PlayerViewPile> playerPairs;
  private ViewDeck myViewDeck;

  private int myPlayerSize;

  public Controller(GamePlayerView view, File config, int playerSize) {
    myConfigFile = config;
    myPlayer = view;
    myPlayerSize = playerSize;
    initEngine(myConfigFile, myPlayerSize);

    centerViewPiles = new ArrayList<>();
    playerPiles = new ArrayList<>();
    playerPairs = new ArrayList<>();
    createViewCards();

    myEngine.setActionsForTurn(myActions.getTurnActions());
    dealCardsToUsers();

    myCardStatus.makeCenterPiles(myConfiguration.hasSingleCenterPile());
    createViewDeck();
    createViewPiles();

    addListenersToPlayer();
  }

  private void addEventHandlers() {
    EventHandler<ActionEvent> nextPress = next -> {
      nextTurn();
    };
    EventHandler<KeyEvent> keyPress = key -> {
      keyPressed(key);
    };

    Map<String, EventHandler> buttonActions = new HashMap<>();
    for (String button : toolButtons.keySet()) {
      EventHandler<ActionEvent> buttonPress = press -> {
        try {
          Method action = this.getClass().getDeclaredMethod(button);
          action.invoke(this);
        } catch (NoSuchMethodException e) {
          myPlayer.showError(errors.getString("noMethod"));
        } catch (IllegalAccessException e) {
          myPlayer.showError(errors.getString("illegal"));
        } catch (InvocationTargetException e) {
          myPlayer.showError(errors.getString("invocation"));
        }
      };
      buttonActions.put(button, buttonPress);
    }
    myPlayer
        .setUpScreen(keyPress, nextPress, buttonActions, centerViewPiles, playerPiles, playerPairs,
            myConfiguration.isFannable());
    myPlayer
        .updateScene(myUserStatus.getCurrentUser().getID(), myUserStatus.getSelectedUser().getID());
  }


  private void addListenersToPlayer() {
    if (myConfiguration.userSelectable()) {
      myPlayer.getClickedPileProperty().addListener((observable, oldValue, newValue) -> {
        if (myUserStatus.getCurrentUser().getID() != (int) newValue) {
          myUserStatus.setSelectedUser((int) newValue);
        }
        myPlayer.updateScene(myUserStatus.getCurrentUser().getID(),
            myUserStatus.getSelectedUser().getID());
      });
    }
    myPlayer.getClickedCardProperty().addListener((observable, oldValue, newValue) -> {
      int val = (int) myPlayer.getClickedCardProperty().getValue();
      for (Card c : myCards) {
        if (c.getId() == val) {
          myCardStatus.setSelectedCard(c);
          break;
        }
      }
    });
    addEventHandlers();

    if (myConfiguration.hasPairings()) {
      myPlayer.showGroupings();
    }
  }

  private void rules() {
    myPlayer.showRules();
  }

  private void undo() {
    myEngine.undo();
    fillPiles();
    myPlayer
        .updateScene(myUserStatus.getCurrentUser().getID(), myUserStatus.getSelectedUser().getID());
  }

  private void initEngine(File config, int playerSize) {
    initializeVariables(config);
    setUpUsers(playerSize);
  }

  private File getDefaultFile(String fileName) {
    return new File(DEFAULT_FILE_PACKAGE + fileName + File.separator + fileName + FILE_EXTENSION);
  }

  private void nextTurn() {
    if (!gameOver()) {
      if ((myConfiguration.needCheckFinish() && myUserStatus.isTurnFinished()) || !myConfiguration
          .needCheckFinish()) {
        myUserStatus.setTurnFinished(false);
        myEngine.doTurn();
        fillPiles();
        myPlayer.updateScene(myUserStatus.getCurrentUser().getID(),
            myUserStatus.getSelectedUser().getID());
      } else { //can't go on to next turn (user didn't do something), show error
        myPlayer.showError(errors.getString("turnError"));
      }
    }
    if (gameOver()) {
      if (myConfiguration.getEndCondition()) {
        myPlayer.showEndGame(myUserStatus.getWinnerID(), "winner");
      } else {
        myPlayer.showEndGame(myUserStatus.getLoserID(), "loser");
      }
    }
  }

  private void keyPressed(KeyEvent key) {
    if (myConfiguration.hasKeys()) {
      for (String playerNum : playerKeys.keySet()) {
        if (playerKeys.getString(playerNum).equals(key.getText())) {
          int userID = Integer.parseInt(playerNum);
          myUserStatus.setSelectedUser(userID);
          checkJack();
          myPlayer.updateScene(myUserStatus.getCurrentUser().getID(),
              myUserStatus.getSelectedUser().getID());
        }
      }
    }
  }

  private void initializeVariables(File config) {
    myEngine = new MainGame();
    myParser = new XMLParser();

    try {
      myRules = myParser.getRules(config);
    } catch (XMLException e) {
      myPlayer.showError(e.getMessage());
      myConfigFile = getDefaultFile(config.getParentFile().getName());
      myRules = myParser.getRules(myConfigFile);
    }

    myConfiguration = myRules.getConfiguration();
    myActions = myRules.getActions();
  }

  private void setUpUsers(int playerSize) {
    myEngine.setUpUsers(playerSize);
    myUsers = myEngine.getUsers();
    try {
      int userCards = myConfiguration.getNumUserCards();
      myDealer = new Dealer(myUsers, userCards);
    } catch (XMLException e) {
      myPlayer.showError(e.getMessage());
      myConfigFile = getDefaultFile(myConfigFile.getParentFile().getName());

      initEngine(myConfigFile, myPlayerSize);
    }
    setUpDeck();

    myUserStatus = new UserStatus(myUsers);
    myCardStatus = new CardStatus(myUsers);

    myEngine.setUserStatus(myUserStatus);
    myEngine.setCardStatus(myCardStatus);
  }

  private void setUpDeck() {
    try {
      myCards = new ArrayList<>(myDealer.setUpDeck(myConfiguration.getDecks()));
    } catch (XMLException e) {
      myPlayer.showError(e.getMessage());
      myConfigFile = getDefaultFile(myConfigFile.getParentFile().getName());

      initEngine(myConfigFile, myPlayerSize);
    }
  }

  private void dealCardsToUsers() {
    Map<Integer, List<Integer>> pileLists = myParser.getLoad(myConfigFile).getPileLists();
    if (!pileLists.isEmpty()) {
      myCardStatus.setCenterPile(myDealer.setPileFromFile(pileLists.get(DEFAULT_PILE_ID)));
      pileLists.remove(DEFAULT_PILE_ID);

      myDealer.userDecksFromFile(pileLists);
    } else {
      dealDeckReflection();
    }
  }

  private void dealDeckReflection() {
    Class dealerClass = myDealer.getClass();
    for (String methodName : myActions.getSetupMethods()) {
      try {
        Method method = dealerClass.getMethod(methodName);
        method.invoke(myDealer);
      } catch (NoSuchMethodException e) {
        myPlayer.showError(errors.getString("noMethod"));
      } catch (IllegalAccessException e) {
        myPlayer.showError(errors.getString("illegal"));
      } catch (InvocationTargetException e) {
        myPlayer.showError(errors.getString("invocation"));
      }
    }
    if (myConfiguration.getNumUserCards() != 0) {
      myCardStatus.setCenterPile(myDealer.setPile());
    } else {
      myCardStatus.setCenterPile(new Pile());
    }
  }

  private void createViewCards() {
    viewCards = new ArrayList<>();
    for (Card card : myCards) {
      ViewCard vc = new ViewCard(false, card.getNumber(), card.getSuit(),
          myConfiguration.getDeckStyle(),
          card.getId());
      viewCards.add(vc);
    }
  }

  private void createViewDeck() {
    myViewDeck = new ViewDeck(viewCards);
    myPlayer.setDeck(myViewDeck);
  }

  private void createViewPiles() {
    centerViewPiles.add(new CenterViewPile(CENTER_PILE_ID, myConfiguration.centerShowing()));
    for (User user : myUsers) {
      playerPiles.add(new PlayerViewPile(user.getID(), myConfiguration.isFlippable()));
      playerPairs.add(new PlayerViewPile(user.getID(), myConfiguration.isFlippable()));
    }
    fillPiles();
  }

  private void fillPiles() {
    populateCenterPiles(centerViewPiles);
    populatePlayerPairs(playerPairs);
    populatePlayerPiles(playerPiles);
  }

  private void populatePlayerPairs(List<PlayerViewPile> piles) {
    piles.clear();
    for (User user : myUsers) {
      for (Pile pile : user.getPairs()) {
        PlayerViewPile pair = new PlayerViewPile(user.getID(), myConfiguration.isFlippable());
        for (Card c : pile) {
          pair.addTop(myViewDeck.getCard(c.getId()));
        }
        piles.add(pair);
      }
    }
  }

  private void populateCenterPiles(List<CenterViewPile> piles) {
    for (ViewPile p : piles) {
      p.clearPile();
      for (Card c : myCardStatus.getCenterPile().getCards()) {
        p.addTop(myViewDeck.getCard(c.getId()));
      }
    }
  }

  private void populatePlayerPiles(List<PlayerViewPile> piles) {
    for (User user : myUsers) {
      for (ViewPile p : piles) {
        if (p.getID() == user.getID()) {
          p.clearPile();
          for (Card c : user.getPile()) {
            p.addTop(myViewDeck.getCard(c.getId()));
          }
        }
      }
    }
  }

  private void checkJack() {
    try {
      myEngine.executeAction("CheckForJack");
    } catch (IndexOutOfBoundsException e) {
      myPlayer.showError("Cannot slap empty pile");
    }
    fillPiles();
  }

  private boolean gameOver() {
    if (myConfiguration.getEndCondition()) {
      return myUserStatus.getWinnerID() >= 0;
    } else {
      return myUserStatus.getLoserID() >= 0;
    }
  }

  private void replay() {
    initEngine(myConfigFile, myPlayerSize);
    myEngine.setActionsForTurn(myActions.getTurnActions());
    dealDeckReflection();
    myCardStatus.makeCenterPiles(myConfiguration.hasSingleCenterPile());
    fillPiles();
    myPlayer
        .updateScene(myUserStatus.getCurrentUser().getID(), myUserStatus.getSelectedUser().getID());
  }

  private void save() {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.newDocument();

      Element rules = document.createElement("rules");
      document.appendChild(rules);

      createConfig(document, rules);
      createActions(document, rules);
      createPiles(document, rules);

      // create the xml file
      //transform the DOM Object to an XML File
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);

      StreamResult streamResult = new StreamResult(new File(XML_FILE_PATH));
      transformer.setOutputProperty(OutputKeys.INDENT, YES);
      transformer.transform(domSource, streamResult);

      StreamResult result = new StreamResult(System.out);
      transformer.transform(domSource, result);

    } catch (ParserConfigurationException | TransformerConfigurationException e) {
      myPlayer.showError(errors.getString("saveError"));
    } catch (TransformerException e) {
      myPlayer.showError(errors.getString("saveError"));
    }

  }

  private void createConfig(Document document, Element rules) {
    ResourceBundle configurationVariables = ResourceBundle.getBundle("resources.XMLConfiguration");
    Element configuration = document.createElement("configuration");
    rules.appendChild(configuration);

    for (String configChild : configurationVariables.keySet()) {
      Element tag = document.createElement(configChild);
      tag.appendChild(document.createTextNode(myConfiguration.getVariable(configChild)));
      configuration.appendChild(tag);
    }
  }

  private void createActions(Document document, Element rules) {
    ResourceBundle actionVariables = ResourceBundle.getBundle("resources.XMLActions");
    for (String actionChild : actionVariables.keySet()) {
      Element actionParent = document.createElement(actionVariables.getString(actionChild));
      rules.appendChild(actionParent);
      for (String action : myActions.getList(actionChild)) {
        Element actionElement = document.createElement(actionChild);
        actionElement.appendChild(document.createTextNode(action));
        actionParent.appendChild(actionElement);
      }
    }
  }

  private void createPiles(Document document, Element rules) {
    ResourceBundle pileVariables = ResourceBundle.getBundle("resources.XMLPiles");
    Element piles = document.createElement("piles");
    rules.appendChild(piles);

    List<String> centerCards = new ArrayList<>();
    System.out.println(myCardStatus.getCenterPile());
    for (Card card : myCardStatus.getCenterPile()) {
      centerCards.add(Integer.toString(card.getId()));
    }
    Element centerPile = document.createElement("centerPile");
    centerPile.appendChild(document.createTextNode(String.join(",", centerCards)));
    centerPile.setAttribute("id", "5");
    piles.appendChild(centerPile);

    for (User user : myUsers) {
      List<String> playerCards = new ArrayList<>();
      for (Card card : user.getPile()) {
        playerCards.add(Integer.toString(card.getId()));
      }
      for (Pile pair : user.getPairs()) {
        for (Card card : pair) {
          playerCards.add(Integer.toString(card.getId()));
        }
      }
      Element playerPile = document.createElement("playerPile");
      playerPile.appendChild(document.createTextNode(String.join(",", playerCards)));
      playerPile.setAttribute("id", Integer.toString(user.getID()));
      piles.appendChild(playerPile);
    }
  }
}
