package mediation.logic;

import mediation.tree.Tree;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Honza on 20.6.15.
 */
public interface DataMediator {

    public boolean compatibleParameters(File fileOfPreviousMethod, File fileOfNextMethod, OntologyParser parser) throws IOException, SAXException, ParserConfigurationException;

    public boolean compatibleParameters(String fileOfPreviousMethod, String fileOfNextMethod, OntologyParser parser) throws ParserConfigurationException, SAXException, IOException;

    public Tree getOutputTree();

    public void mediateData(String filename, String fileOfPreviousMethod, String fileOfNextMethod) throws IOException, ParserConfigurationException, SAXException;

    public void mediateData(File file, File fileOfPreviousMethod, File fileOfNextMethod) throws IOException, ParserConfigurationException, SAXException;
}
