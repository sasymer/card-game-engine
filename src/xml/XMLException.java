package xml;

import java.util.ResourceBundle;

public class XMLException extends RuntimeException {
    private static final String ERROR_FILE = "resources.XMLErrors";
    private static final ResourceBundle errorResources= ResourceBundle.getBundle(ERROR_FILE);

    public XMLException (Throwable cause) {
        super(cause);
    }

    public XMLException (String property) {
        super(errorResources.getString(property));
    }
}