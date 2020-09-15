package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Configuration {
    private static final String CONFIGURATION_FILE = "resources.XMLConfiguration";
    private static ResourceBundle configurationVariables = ResourceBundle.getBundle(CONFIGURATION_FILE);
    public static final List<String> variableList = new ArrayList<>(configurationVariables.keySet());

    private static final String METHODS_FILE = "resources.XMLMethods";
    private static ResourceBundle Methods = ResourceBundle.getBundle(METHODS_FILE);

    private Map<String, String> myConfigValues;

    public Configuration (Map<String, String> configValues) {
        myConfigValues = configValues;
    }

    public int getDecks() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName(); //in case the getter method name changes
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName)));
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public int getNumUserCards() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName)));
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean hasSingleCenterPile() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean displayUserPairs() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean centerShowing() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean checkCurrent() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean isFlippable() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean hasPairings() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean isFannable() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean userSelectable() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean hasKeys() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean needCheckFinish() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public boolean getEndCondition() {
        try {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return Integer.parseInt(myConfigValues.get(Methods.getString(methodName))) == 1;
        }
        catch(NumberFormatException e) {
            throw new XMLException("BadContent");
        }
    }

    public String getDeckStyle() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        return myConfigValues.get(Methods.getString(methodName));
    }

    public String getVariable(String key) {
        return myConfigValues.get(key);
    }
}
