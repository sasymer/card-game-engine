package view.playareacomponents;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.components.PlayerViewPile;
import view.components.ViewCard;
import view.components.ViewPile;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreHUD {

    private static final String UI_TEXT = "resources.UIText";
    private static final String SCORE_HUD = "ScoreHUD";
    private static final int WIDTH = 150;
    private static final String SCORE_LABEL = "ScoreLabel";
    private static final String PLAYER_PAIRS_LABEL = "playerPairsLabel";
    private static final String PLAYER_SCORE_LABEL = "playerScoreLabel";
    private static final int OFFSET = 1;

    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);
    private VBox myView;
    private VBox score;
    private VBox groupingsPane;
    private List<PlayerViewPile> playerPiles;
    private List<PlayerViewPile> playerPairs;
    private List<TitledPane> groupingPlayers;

    public ScoreHUD(List<PlayerViewPile> players,List<PlayerViewPile> pairs)
    {
        myView = new VBox();
        myView.setMaxWidth(WIDTH);
        myView.setMinWidth(WIDTH);
        myView.setId(SCORE_HUD);

        playerPiles = players;
        playerPairs = pairs;


        score = new VBox();
        score.setAlignment(Pos.CENTER);
        myView.setAlignment(Pos.CENTER);
        myView.getChildren().addAll(score);

        initGroupings();
    }

    private void initGroupings()
    {
        groupingsPane = new VBox();
        groupingsPane.setMaxWidth(WIDTH);
        groupingPlayers = new ArrayList<TitledPane>();

        for(ViewPile p:playerPiles)
        {
            makeGroupingsPanes(p);
        }

    }

    private void makeGroupingsPanes(ViewPile p) {
        TitledPane playerPairs = new TitledPane();
        playerPairs.setId(Integer.toString(p.getID()));
        playerPairs.setText(String.format(visualText.getString(PLAYER_PAIRS_LABEL),p.getID()+ OFFSET));
        ListView<HBox> pairs = new ListView<>();
        playerPairs.setContent(pairs);
        groupingPlayers.add(playerPairs);
        groupingsPane.getChildren().add(playerPairs);
    }

    public void displayPairs()
    {
        initGroupings();
        myView.getChildren().add(groupingsPane);
    }

    private void updatePairs()
    {
        for(TitledPane player: groupingPlayers)
        {
            ListView<HBox> pairings = (ListView<HBox>) player.getContent();
            pairings.getItems().clear();
            for(ViewPile p: playerPairs) {
                if (Integer.toString(p.getID()).equals(player.getId())) {
                    HBox pairImages = new HBox();
                    addPairs(p, pairImages);
                    pairings.getItems().add(pairImages);
                }
            }
        }
    }

    private void addPairs(ViewPile p, HBox pairImages) {
        for(ViewCard c: p.getCards())
        {
            c.setFaceUp(true);
            c.resetPos();
            pairImages.getChildren().add(c);
        }
    }

    private void updateScore()
    {
        score.getChildren().clear();
        for(ViewPile p:playerPiles)
        {
            Label scoreLabel = new Label();
            scoreLabel.setText(makeScoreLabel(p.getID(),p.size()));
            scoreLabel.setId(SCORE_LABEL);
            score.getChildren().add(scoreLabel);
        }
    }

    public void updateView()
    {
        updateScore();
        updatePairs();
    }

    private String makeScoreLabel(int playerNumber, int numCards)
    {
        return String.format(visualText.getString(PLAYER_SCORE_LABEL),playerNumber+ OFFSET,numCards);
    }

    public Node getView()
    {
        return myView;
    }

}
