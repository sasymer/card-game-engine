package view;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import view.components.CenterViewPile;
import view.components.PlayerViewPile;
import view.components.ViewCard;
import view.components.ViewDeck;
import view.playareacomponents.GamePlayArea;
import view.playareacomponents.Toolbar;

import java.util.*;

public class GamePlayerView extends BorderPane implements Player{

    private static final String UI_TEXT = "resources.UIText";
    private static final String RULES_TEXT = "resources.Rules";
    private static final String ENDGAME_MESSAGES = "resources.EndGame";
    private static final String CONTROLS = "resources.PlayerKeys";
    private static final String PLAYER_NUMBER = "playerNumber";
    private static final String PRESS = "press";
    private static final String GAMEOVER = "gameover";
    private static final String ERROR = "error";
    private static final String HOWTO = "howto";
    private static final String RULES_ERROR = "rulesError";
    private static final String RULES = "rules";
    private static final int OFFSET = 1;
    private static final String GAMEEND = "gameend";
    private static final String WRONG = "wrong";


    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);
    private ResourceBundle rulesText = ResourceBundle.getBundle(RULES_TEXT);
    private ResourceBundle endGameText= ResourceBundle.getBundle(ENDGAME_MESSAGES);
    private ResourceBundle controlsText= ResourceBundle.getBundle(CONTROLS);


    private GamePlayArea myGamePlay;
    private Toolbar myToolbar;
    private ViewDeck myDeck;
    private SimpleIntegerProperty clickedCardID;
    private SimpleIntegerProperty clickedPileID;
    private String myGameName;
    private String myCompressedGameName;

    public GamePlayerView(String gameName)
    {
        myGameName = gameName;
        myGamePlay = new GamePlayArea();
        clickedCardID = new SimpleIntegerProperty();
        clickedPileID = new SimpleIntegerProperty();
        myCompressedGameName = myGameName.replaceAll("\\s","");


    }

    public void setUpScreen(EventHandler key, EventHandler next, Map<String,EventHandler> buttonActions, List<CenterViewPile> center, List<PlayerViewPile> players, List<PlayerViewPile> playerPairs, boolean fannable)
    {
        setKeyChecker(key);
        myGamePlay.setNextAction(next);

        myGamePlay.setUpPlay(myDeck,center,players,playerPairs,fannable);

        addListeners(players);

        myToolbar = new Toolbar(buttonActions);

        this.setTop(myToolbar.getView());
        this.setCenter(myGamePlay.getView());
    }

    public void updateScene(int currentPlayerID, int selectedPlayerId)
    {
        myGamePlay.updatePlayView(currentPlayerID,selectedPlayerId);
    }

    private void setKeyChecker(EventHandler key)
    {
        this.addEventHandler(KeyEvent.KEY_PRESSED,key);

    }

    public void setDeck(ViewDeck deck){
        myDeck = deck;
    }

    private void addListeners(List<PlayerViewPile> players)
    {
        for(ViewCard c: myDeck)
        {
            c.setOnMouseClicked(e->clickedCard(c));
        }

        for(PlayerViewPile p:players )
        {
            p.getView().setOnMouseClicked(e->clickedPile(p));
        }
    }

    public void showGroupings()
    {
        myGamePlay.showGroupings();
    }

    private void clickedPile(PlayerViewPile player)
    {
        clickedPileID.setValue(player.getID());
        player.flipTopCard();
    }

    private void clickedCard(ViewCard clicked) {

        clickedCardID.setValue(clicked.getID());

    }

    public Property getClickedPileProperty()
    {
        return clickedPileID;
    }

    public Property getClickedCardProperty()
    {
        return clickedCardID;
    }


    public void showRules() {
        String title = visualText.getString(RULES);
        String header = visualText.getString(HOWTO);
        String content = rulesText.getString(myCompressedGameName) + "\n" + getControlsText();

        makeAlert(AlertType.INFORMATION,title,header,content);
    }

    private void makeAlert(AlertType type, String title, String header, String content)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private String getControlsText()
    {
        String controls = "";
        for(String playerNumber: controlsText.keySet())
        {
            controls += String.format(visualText.getString(PLAYER_NUMBER),Integer.parseInt(playerNumber)+OFFSET);
            controls += visualText.getString(PRESS) + controlsText.getString(playerNumber) + "\n";
        }

        return controls;
    }

    public void showError(String message) {

        String title = visualText.getString(ERROR);
        String header = visualText.getString(WRONG);
        String content = message;

        makeAlert(AlertType.ERROR,title,header,content);
    }

    public void showEndGame(int playerID, String condition)
    {

        String title = visualText.getString(GAMEOVER);
        String header = visualText.getString(GAMEEND);
        String content = String.format(endGameText.getString(myCompressedGameName+condition), playerID+ OFFSET);

        makeAlert(AlertType.INFORMATION,title,header,content);

    }



}
