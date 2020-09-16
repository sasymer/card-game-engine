
package view;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import view.MainView;
import view.popup.ChooseGameScreen;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class NewGameChooserTest  extends util.DukeApplicationTest {
    private MainView main;
    private Stage chooseGames;
    private Node root;

    @Override
    public void start(Stage stage) throws Exception {
        main = new MainView(stage);
        root = main.getView();


    }

    @Test
    public void displaysChooseGameScreen()
    {
        Button makeGame = from(root).lookup("#newgame").query();
        clickOn(makeGame);
        chooseGames = main.getChooseGamesStage();
        assertTrue(chooseGames.isShowing());

    }

    @Test
    public void checkGameButtons()
    {
        Button makeGame = from(root).lookup("#newgame").query();
        clickOn(makeGame);
        Node choosing = main.getChooseGameScreen();
        Button slapjack = from(choosing).lookup("#Slapjack").query();
        Button war = from(choosing).lookup("#War").query();
        Button warFour = from(choosing).lookup("#Warfour").query();
        assertEquals("Slapjack",slapjack.getText());
        assertEquals("War",war.getText());
        assertEquals("Warfour",warFour.getText());



    }


}


