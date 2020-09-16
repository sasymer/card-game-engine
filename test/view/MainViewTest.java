package view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.MainView;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainViewTest extends util.DukeApplicationTest {
   private MainView main;
   private Node root;
    @Override
    public void start(Stage stage) throws Exception {
          main = new MainView(stage);
          root = main.getView();

    }

    @Test
    public void startUpVisible()
    {
        assertTrue(root.isVisible());
    }


    @Test
    public void hasMakeGameButton()
    {
        Button button = from(root).lookup(".button").query();
        assertEquals("New Game",button.getText());
    }

    @Test
    public void hasThemeButtons()
    {
        Node root = main.getView();
        Button lightButton = from(root).lookup("#light").query();
        Button darkButton = from(root).lookup("#dark").query();
        assertEquals("Light",lightButton.getText());
        assertEquals("Dark",darkButton.getText());

    }
}
