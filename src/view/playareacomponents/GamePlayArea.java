package view.playareacomponents;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.components.CenterViewPile;
import view.components.PlayerViewPile;
import view.components.ViewDeck;
import view.components.ViewPile;


import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

public class GamePlayArea {

    private static final String UI_TEXT = "resources.UIText";
    private static final String PILE_POSITION = "resources.PilePositions";
    private static final int X_POS = 0;
    private static final int Y_POS = 1;
    private static final int MIN_WIDTH = 100;
    private static final String NEXT = "next";
    private static final String DELIMITER = ",";


    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);
    private ResourceBundle pilePositions = ResourceBundle.getBundle(PILE_POSITION);

    private Pane mainPlayArea;
    private ViewDeck myDeck;
    private HBox fanCards1;
    private HBox fanCards2;
    private VBox gameButtons;
    private ScoreHUD score;
    private Button next;
    private BorderPane myView;
    private List<PlayerViewPile> myPlayers;
    private List<CenterViewPile> myCenterPiles;
    private boolean fannable;


    public GamePlayArea()
    {
        initViewNodes();
        initGameButtons();

        mainPlayArea = new Pane();
        mainPlayArea.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        myView.setCenter(mainPlayArea);

    }

    private void initViewNodes() {
        myView = new BorderPane();
        fanCards1 = new HBox();
        fanCards2 = new HBox();
        gameButtons = new VBox();
    }

    private void initGameButtons() {
        gameButtons.setMinWidth(MIN_WIDTH);
        next = new Button(visualText.getString(NEXT));
        gameButtons.getChildren().add(next);
        gameButtons.setAlignment(Pos.CENTER);
    }

    public void setNextAction(EventHandler nextAction)
    {
        next.setOnAction(nextAction);
    }
    public void setUpPlay(ViewDeck deck,List<CenterViewPile> center, List<PlayerViewPile> players,List<PlayerViewPile> playerPairs, boolean fan)
    {
        myDeck = deck;
        myPlayers = players;
        myCenterPiles = center;
        fannable =fan;

        score = new ScoreHUD(players,playerPairs);

        myView.setRight(gameButtons);
        myView.setLeft(score.getView());


    }

    public void updatePlayView(int currentPlayer,int selectedPlayer)
    {
        mainPlayArea.getChildren().clear();
        highlightCurrentUser(currentPlayer);

        addPiles(myCenterPiles);
        addPiles(myPlayers);

        if(fannable)
        {
            fanCards(currentPlayer, selectedPlayer);
        }

        score.updateView();

    }

    private void fanCards(int currentPlayer, int selectedPlayer) {
        clearFanned();
        if(selectedPlayer!=currentPlayer)
        {
            fanTop(selectedPlayer,false);
        }
        fanBottom(currentPlayer,true);
    }

    private void highlightCurrentUser(int currentPlayer) {
        for(PlayerViewPile p: myPlayers)
        {
            p.resetView();
            if(p.getID() == currentPlayer)
            {
                p.setCurrentUser();
            }
        }
    }

    private void clearFanned()
    {
        for(PlayerViewPile p: myPlayers)
        {
            p.hideFannedCards();
            myView.setTop(null);
            myView.setBottom(null);
        }
    }

    private void setPilePositions(List<? extends ViewPile> piles)
    {
        for(ViewPile p: piles)
        {
            String[] pilePosition = pilePositions.getString(Integer.toString(p.getID())).split(DELIMITER);
            int xPos = Integer.parseInt(pilePosition[X_POS]);
            int yPos = Integer.parseInt(pilePosition[Y_POS]);
            p.setPosition(new Point(xPos,yPos));
        }
    }

    private void addPiles(List<? extends ViewPile> piles)
    {
        for(ViewPile p: piles)
        {
            mainPlayArea.getChildren().add(p.getView());
        }
        setPilePositions(piles);
    }


    private void fanTop(int playerID,boolean facing)
    {
        for(PlayerViewPile player:myPlayers)
        {
            if(player.getID() == playerID)
            {
                myView.setTop(player.showFannedCards(facing));
            }
        }
    }

    private void fanBottom(int playerID,boolean facing)
    {
        for(PlayerViewPile player:myPlayers)
        {
            if(player.getID() == playerID)
            {
                myView.setBottom(player.showFannedCards(facing));
            }

        }
    }

    public Node getView()
    {
        return myView;
    }

    public void showGroupings()
    {
        score.displayPairs();
    }


}
