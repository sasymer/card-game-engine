package data;

import org.junit.jupiter.api.Test;
import xml.*;

import java.io.File;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorTest {
    private static final String ERROR_FILE = "resources.XMLErrors";
    private static final ResourceBundle errorResources= ResourceBundle.getBundle(ERROR_FILE);

    private XMLParser parser;
    private File tagsFile;
    private File contentFile;
    private Configuration contentConfig;

    private void initTags() {
        parser = new XMLParser();
        tagsFile = new File("data/slapjack/slapjack_bad_tags.xml");
    }

    private void initContent() {
        parser = new XMLParser();
        contentFile = new File("data/slapjack/slapjack_bad_content.xml");
        contentConfig = parser.getRules(contentFile).getConfiguration();
    }

    @Test
    public void testBadTags() {
        try {
            initTags();
        }
        catch(XMLException e) {
            assertEquals(errorResources.getString("TagNotFound"),e.getMessage());
        }
    }

    @Test
    public void testBadContent() {
        initContent();
        try {
            contentConfig.getNumUserCards();
        } catch(XMLException e) {
            assertEquals(errorResources.getString("BadContent"),e.getMessage());
        }
    }

}
