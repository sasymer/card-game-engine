package view.popup;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class ChooseGameScreen extends ScrollPane {

    private static final String UI_TEXT = "resources.UIText";
    private static final String DATA = "data/";
    private static final String EXTENSION = "*.xml";
    private static final String DESCRIPTION = "Text Files";
    private static final String CHOOSEGAME = "choosegame";
    private static final String CHOOSE_GAME = "ChooseGame";
    private static final int SPACING = 15;
    private static final String GAME_NAMES = "resources.GameNames";

    private ResourceBundle gameNames  = ResourceBundle.getBundle(GAME_NAMES);
    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);

    private Stage myStage;
    private VBox myContent;
    private File gameFile;
    private String chosenGameName;


    public ChooseGameScreen(Stage mainStage)
    {
        myStage = mainStage;

        myContent = new VBox();
        myContent.setAlignment(Pos.CENTER);
        myContent.setSpacing(SPACING);

        addAllButtons();
        setDisplay();
    }

    private void addAllButtons() {
        for(String name: gameNames.keySet())
        {
            myContent.getChildren().add(makeButton(name));
        }
    }

    private void setDisplay() {
        this.setContent(myContent);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setId(CHOOSE_GAME);
    }

    private Button makeButton(String buttonName)
    {
        Button gameButton =  new Button(buttonName);
        gameButton.setOnAction(e-> {chooseGame(buttonName);});
        gameButton.setId(buttonName);
        return gameButton;
    }

    private void chooseGame(String gameName)
    {
        chosenGameName = gameName;

        getGameFile(gameName);

        if(gameFile !=null)
        {
            myStage.close();
        }

    }

    private void getGameFile(String gameName) {
        FileChooser chooseGame = new FileChooser();
        chooseGame.setTitle(visualText.getString(CHOOSEGAME));
        chooseGame.setInitialDirectory(new File(DATA + gameName));
        chooseGame.getExtensionFilters().setAll(new ExtensionFilter(DESCRIPTION, EXTENSION));
        gameFile = chooseGame.showOpenDialog(myStage);
    }

    public File getGameConfigFile()
    {
        return gameFile;
    }

    public String getGameName()
    {
        return chosenGameName;
    }





}
