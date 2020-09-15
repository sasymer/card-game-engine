package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Actions {
    private static final String ACTION_FILE = "resources.XMLActions";
    private static ResourceBundle actionTypes = ResourceBundle.getBundle(ACTION_FILE);
    public static final List<String> actionList = new ArrayList<>(actionTypes.keySet());

    private static final String METHODS_FILE = "resources.XMLMethods";
    private static ResourceBundle Methods = ResourceBundle.getBundle(METHODS_FILE);

    private Map<String, List<String>> myActionLists;

    public Actions (Map<String, List<String>> actionLists) {
        myActionLists = actionLists;
    }

    public List<String> getSpecialActions() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName(); //in case the getter method name changes
        return myActionLists.get(Methods.getString(methodName));
    }

    public List<String> getTurnActions() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        return myActionLists.get(Methods.getString(methodName));
    }

    public List<String> getSetupMethods() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        return myActionLists.get(Methods.getString(methodName));
    }

    public List<String> getList(String key) {
        return myActionLists.get(key);
    }
}
