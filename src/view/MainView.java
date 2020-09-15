package view;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.popup.ChooseGameScreen;
import view.popup.ChoosePlayerScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainView {

    private static final String UI_TEXT = "resources.UIText";
    private static final String THEMES = "resources.Themes";
    private static final String MUSIC_INFO = "resources.Music";

    private static final int ICON_SIZE = 25;
    private static final double SIDE_ANCHOR = 10.0;
    private static final double TOP_ANCHOR = 3.0;
    private static final String MAXVOLUME = "maxvolume";
    private static final String MUSICFILE = "musicfile";
    private static final int POPUP_WIDTH = 400;
    private static final int POPUP_HEIGHT = 400;
    private static final String NEWGAME = "newgame";
    private static final String LIGHT = "light";
    private static final String DARK = "dark";
    private static final int SPACING = 10;
    private static final String PATH = "src/resources/";
    private static final String VOLUMEGRAPHIC = "volumegraphic";
    private static final String MUTEGRAPHIC = "mutegraphic";


    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);
    private ResourceBundle musicInfo = ResourceBundle.getBundle(MUSIC_INFO);
    private ResourceBundle themeInfo = ResourceBundle.getBundle(THEMES);

    private TabPane myGameViews;
    private Stage myStage;
    private Button makeGame;
    private List<Button> themeColorButtons;
    private Button lightMode;
    private Button darkMode;
    private ToggleButton music;
    private String currentTheme;
    private Scene myScene;
    private HBox mainButtons;
    private HBox themeButtons;
    private AnchorPane allButtons;
    private BorderPane myBorder;
    private Media backgroundMusic;
    private MediaPlayer musicPlayer;
    private ImageView musicGraphic;
    private ImageView muteGraphic;
    private Stage chooseGames;
    private ChooseGameScreen gameChoices;

    public MainView(Stage stage)
    {
        myStage = stage;
        themeColorButtons = new ArrayList<>();

        initViewNodes();
        initNewGameButton();
        initMusicButton();
        initThemeButtons();

        currentTheme = getClass().getResource(themeInfo.getString(DARK)).toExternalForm();

        //Brings focus to current game when switching tab
        myGameViews.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            newValue.getContent().requestFocus();

        });

        initDisplay();


    }

    private void initThemeButtons() {
        for(String themeColor:themeInfo.keySet())
        {
            themeColorButtons.add(makeThemeButton(themeColor,themeInfo.getString(themeColor)));
        }
    }

    private void initNewGameButton() {
        makeGame = new Button(visualText.getString(NEWGAME));
        makeGame.setId(NEWGAME);
        makeGame.setOnAction(e->addGame());
    }

    private void initViewNodes() {
        myBorder = new BorderPane();
        myGameViews = new TabPane();
        themeButtons = new HBox();
        mainButtons = new HBox();
        allButtons = new AnchorPane();
    }

    private void initMusicButton() {
        music = new ToggleButton();

        Image volumeIcon = new Image(this.getClass().getClassLoader().getResourceAsStream(musicInfo.getString(VOLUMEGRAPHIC)));
        musicGraphic = makeIconView(volumeIcon);

        Image muteIcon = new Image(this.getClass().getClassLoader().getResourceAsStream(musicInfo.getString(MUTEGRAPHIC)));
        muteGraphic = makeIconView(muteIcon);

        music.setGraphic(musicGraphic);
        music.selectedProperty().addListener(observable -> {setMusic();});
    }

    private ImageView makeIconView(Image icon)
    {
        ImageView graphic =  new ImageView(icon);
        graphic.setFitHeight(ICON_SIZE);
        graphic.setFitWidth(ICON_SIZE);
        return graphic;
    }

    private void setMusic()
    {
        musicPlayer.setVolume(music.isSelected()?0:Double.parseDouble(musicInfo.getString(MAXVOLUME)));
        music.setGraphic(music.isSelected()?muteGraphic:musicGraphic);
    }

    private void addGame() {
        //Display popup to choose game
        chooseGames = new Stage();
        gameChoices = new ChooseGameScreen(chooseGames);
        Scene choosingGames = new Scene(gameChoices, POPUP_WIDTH, POPUP_HEIGHT);
        choosingGames.getStylesheets().add(currentTheme);
        chooseGames.setScene(choosingGames);
        chooseGames.showAndWait();

        String gameName = gameChoices.getGameName();
        File gameConfig = gameChoices.getGameConfigFile();

        if(!(gameName == null))
        {
            //Display popup to choose number of players
            int numPlayers = getNumPlayers(chooseGames, gameName);
            makeGame(gameName, gameConfig, numPlayers);
        }
    }

    private void makeGame(String gameName, File gameConfig, int numPlayers) {

        GamePlayerView player = new GamePlayerView(gameName);
        if(!(numPlayers == 0) && !(gameConfig == null)) //Prevents IllegalArgumentException
        {
            makeTab(gameName, gameConfig, numPlayers, player);
        }
    }

    private int getNumPlayers(Stage chooseGames, String gameName) {
        ChoosePlayerScreen choosePlayers = new ChoosePlayerScreen(chooseGames,gameName);
        Scene choosingPlayerSize = new Scene(choosePlayers, POPUP_WIDTH, POPUP_HEIGHT);
        choosingPlayerSize.getStylesheets().add(currentTheme);
        chooseGames.setScene(choosingPlayerSize);
        chooseGames.showAndWait();

        return choosePlayers.getNumPlayers();
    }

    private void makeTab(String gameName, File gameConfig, int numPlayers, GamePlayerView player) {
        Controller control = new Controller(player,gameConfig,numPlayers);
        Tab game = new Tab(gameName,player);
        myGameViews.getTabs().add(game);
        game.getContent().requestFocus();
    }

    private Button makeThemeButton(String themeColor,String themeFile)
    {
       Button themeButton = new Button(visualText.getString(themeColor));
       themeButton.setOnAction(e->changeTheme(themeFile));
       themeButton.setId(themeColor);
       return themeButton;
    }

    private void changeTheme(String themeFile)
    {
        currentTheme = themeFile;
        setTheme();
    }

    private void initDisplay() {

        addAllButtons();

        themeButtons.setSpacing(SPACING);
        setAnchors();

        myBorder.setTop(allButtons);
        myBorder.setCenter(myGameViews);

        myScene = new Scene(myBorder);
        setTheme();

        myStage.setScene(myScene);
        myStage.setMaximized(true);
        myStage.show();

        startMusic();

    }

    private void addAllButtons() {
        mainButtons.getChildren().addAll(makeGame);
        themeButtons.getChildren().addAll(themeColorButtons);
        themeButtons.getChildren().add(music);
        allButtons.getChildren().addAll(mainButtons,themeButtons);
    }

    private void setAnchors() {
        AnchorPane.setLeftAnchor(mainButtons, SIDE_ANCHOR);
        AnchorPane.setTopAnchor(mainButtons, TOP_ANCHOR);
        AnchorPane.setTopAnchor(themeButtons, TOP_ANCHOR);
        AnchorPane.setRightAnchor(themeButtons, SIDE_ANCHOR);
    }

    private void startMusic()
    {
        backgroundMusic = new Media(new File(PATH + musicInfo.getString(MUSICFILE)).toURI().toString());
        musicPlayer = new MediaPlayer(backgroundMusic);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.setVolume(Double.parseDouble(musicInfo.getString(MAXVOLUME)));
        musicPlayer.play();
    }

    private void setTheme() {

        myScene.getStylesheets().clear();
        myScene.getStylesheets().add(currentTheme);

    }

    //added for view testing
    public Stage getChooseGamesStage()
    {
        return chooseGames;
    }

    public Node getChooseGameScreen()
    {
        return gameChoices;
    }

    public Node getView()
    {
        return myBorder;
    }


}
