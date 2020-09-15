package view.popup;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ChoosePlayerScreen extends VBox {

    private static final String UI_TEXT = "resources.UIText";
    private static final String PLAYER_SIZE_LABEL = "PlayerSizeLabel";
    private static final int SPACING = 15;
    private static final String NUM_PLAYERS = "numPlayers";
    private static final String DELIMITER = ",";

    private ResourceBundle playerSizes  = ResourceBundle.getBundle("resources.PlayerNumbers");
    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);

    private Stage myStage;
    private int numPlayers;
    private Label myPrompt;

    public ChoosePlayerScreen(Stage mainStage,String gameName)
    {
        myStage = mainStage;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(SPACING);

        createPrompt();
        createButtons(gameName);


    }

    private void createButtons(String gameName) {
        for(String name: playerSizes.getString(gameName).split(DELIMITER))
        {
            this.getChildren().add(makeButton(name));
        }
    }

    private void createPrompt() {
        myPrompt =  new Label(visualText.getString(NUM_PLAYERS));
        myPrompt.setId(PLAYER_SIZE_LABEL);
        this.getChildren().add(myPrompt);
    }

    private Button makeButton(String numPlayers)
    {
        Button gameButton =  new Button(numPlayers);
        gameButton.setOnAction(e-> {choosePlayers(numPlayers);});
        return gameButton;
    }

    private void choosePlayers(String playerNum )
    {
        numPlayers = Integer.parseInt(playerNum);
        myStage.close();
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }
}
