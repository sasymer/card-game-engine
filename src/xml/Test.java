package xml;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Test {
    public static void main (String[] args) {
        XMLParser parser = new XMLParser();
        Rules rules = parser.getRules(new File("data/slapjack/slapjack.xml"));
        Configuration config = rules.getConfiguration();
        Actions actions = rules.getActions();
        Load load = parser.getLoad(new File("data/slapjack/slapjack_load.xml"));
        Map<Integer, List<Integer>> pileLists = load.getPileLists();

        System.out.println(ResourceBundle.getBundle("resources.XMLConfiguration").keySet());
        System.out.println(ResourceBundle.getBundle("resources.XMLActions").keySet());
        System.out.println(ResourceBundle.getBundle("resources.XMLPiles").keySet());
        System.out.println();

        System.out.println("Decks: " + config.getDecks());
        System.out.println("Num User Cards: " + config.getNumUserCards());
        System.out.println("hasSingleCenterPile: " + config.hasSingleCenterPile());
        System.out.println("displayUserPairs: " + config.displayUserPairs());
        System.out.println("checkCurrent: " + config.checkCurrent());
        System.out.println("flippable: " + config.isFlippable());
        System.out.println("hasPairings: " + config.hasPairings());
        System.out.println("fannable: " + config.isFannable());
        System.out.println("hasKeys: " + config.hasKeys());
        System.out.println("needCheckFinish: " + config.needCheckFinish());
        System.out.println("checkEndCondition: " + config.getEndCondition());
        System.out.println("deck style: " + config.getDeckStyle());
        System.out.println();

        System.out.println(actions.getSetupMethods());
        System.out.println(actions.getTurnActions());
        System.out.println(actions.getSpecialActions());
        System.out.println();

        for(Integer key : pileLists.keySet()) {
            String value = pileLists.get(key).toString();
            System.out.println(key + " " + value);
        }
    }
}
