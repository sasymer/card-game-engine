package view.playareacomponents;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Toolbar {

    private static final String UI_TEXT = "resources.UIText";
    private static final String TOOL_BUTTONS = "resources.ToolbarButtons";
    private static final int VALUE = 5;

    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);
    private ResourceBundle toolButtons = ResourceBundle.getBundle(TOOL_BUTTONS);

    private HBox myView;
    private List<Button> toolbarButtons;
    private Map<String,EventHandler> buttonActions;

    public Toolbar(Map<String,EventHandler> actions)
    {
        myView = new HBox();
        toolbarButtons =  new ArrayList<>();
        buttonActions = actions;
        for(String button: toolButtons.keySet())
        {
            toolbarButtons.add(makeButton(button));

        }
        myView.setSpacing(VALUE);
        init();
    }

    private Button makeButton(String buttonName)
    {
        Button tool = new Button(visualText.getString(buttonName));
        tool.setOnAction(buttonActions.get(buttonName));
        return tool;
    }

    private void init() {

        myView.getChildren().addAll(toolbarButtons);

    }

    public Node getView()
    {
        return myView;
    }
}
