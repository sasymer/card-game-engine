package xml;

import java.util.*;

public class Rules {

    private Configuration myConfiguration;
    private Actions myActions;

    public Rules (Map<String, String> configValues, Map<String, List<String>> actionLists) {
        myConfiguration = new Configuration(configValues);
        myActions = new Actions(actionLists);
    }

    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public Actions getActions() {
        return myActions;
    }
}