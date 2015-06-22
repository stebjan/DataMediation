package mediation.logic;

import mediation.tree.Tree;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Honza on 20.6.15.
 */
public class EEGDataMediator implements DataMediator {

    @Override
    public boolean compatibleParameters(File fileOfPreviousMethod, File fileOfNextMethod) throws IOException, SAXException, ParserConfigurationException {
        Tree outputTree = new WsdlParser().parseXmlFile(fileOfPreviousMethod, ParameterType.RESPONSE);
        Tree inputTree = new WsdlParser().parseXmlFile(fileOfNextMethod, ParameterType.REQUEST);

        return false;
    }

    @Override
    public boolean compatibleParameters(String fileOfPreviousMethod, String fileOfNextMethod) throws ParserConfigurationException, SAXException, IOException {
        return compatibleParameters(new File(fileOfPreviousMethod),
                new File(fileOfNextMethod));
    }
}
