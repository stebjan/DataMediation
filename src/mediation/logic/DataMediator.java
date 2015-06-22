package mediation.logic;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Honza on 20.6.15.
 */
public interface DataMediator {

    public boolean compatibleParameters(File fileOfPreviousMethod, File fileOfNextMethod) throws IOException, SAXException, ParserConfigurationException;

    public boolean compatibleParameters(String fileOfPreviousMethod, String fileOfNextMethod) throws ParserConfigurationException, SAXException, IOException;
}
