package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Load {
    private static final String PILES_FILE = "resources.XMLPiles";
    private static ResourceBundle pileTypes = ResourceBundle.getBundle(PILES_FILE);
    public static final List<String> pileList = new ArrayList<>(pileTypes.keySet());

    private Map<Integer, List<Integer>> myPileLists;

    public Load (Map<Integer, List<Integer>> pileLists) {
        myPileLists = pileLists;
    }

    public Map<Integer, List<Integer>> getPileLists() {
        return myPileLists;
    }
}
