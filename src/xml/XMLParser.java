package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XMLParser {
    private final DocumentBuilder DOCUMENT_BUILDER;

    public XMLParser() {
        DOCUMENT_BUILDER = getDocumentBuilder();
    }

    public Rules getRules(File dataFile) {
        Element root = getRootElement(dataFile);
        Map<String, String> config = new HashMap<>();
        for (String variable : Configuration.variableList) {
            config.put(variable, getTextValue(root, variable));
        }

        return new Rules(config, makeActionLists(root));
    }

    public Load getLoad (File dataFile) {
        Element root = getRootElement(dataFile);
        Map<Integer, List<Integer>> pileLists = new HashMap<>();
        for (String pileType : Load.pileList) {
            List<Integer> cards = new ArrayList<>();
            NodeList nList = root.getElementsByTagName(pileType);

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String pile = element.getTextContent();
                    int id = Integer.parseInt(element.getAttribute("id"));

                    if (!pile.split(",")[0].equals("")) {
                        for (String cardID : pile.split(",")) {
                            cards.add(Integer.parseInt(cardID));
                        }
                    }

                    List<Integer> cardsCopy = new ArrayList<>(cards);
                    cards.clear();
                    pileLists.put(id, cardsCopy);
                }
            }
        }
        return new Load(pileLists);
    }

    private Element getRootElement(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        } catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    private String getTextValue(Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            throw new XMLException("TagNotFound");
        }
    }

    private Map<String, List<String>> makeActionLists(Element root) {
        Map<String, List<String>> actionLists = new HashMap<>();
        for (String actionType : Actions.actionList) {
            List<String> actions = new ArrayList<>();
            NodeList nList = root.getElementsByTagName(actionType);

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String action = element.getTextContent();
                    actions.add(action);
                }
            }
            actionLists.put(actionType, actions);
        }
        return actionLists;
    }

    private DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}