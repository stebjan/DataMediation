package mediation.logic;

import mediation.tree.Node;
import mediation.tree.Tree;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Honza on 20.6.15.
 */
public class EEGDataMediator implements DataMediator {

    @Override
    public boolean compatibleParameters(File fileOfPreviousMethod, File fileOfNextMethod) throws IOException, SAXException, ParserConfigurationException {
        Tree outputTree = new WsdlParser().parseXmlFile(fileOfPreviousMethod, ParameterType.RESPONSE);
        List<List<String>> outputTreePaths = new ArrayList<List<String>>();
        List<List<String>> inputTreePaths = new ArrayList<List<String>>();
        System.out.println("leaves: " + outputTree.getLeaves().size());
        for (Node leaf: outputTree.getLeaves()) {
            List<String> outputPath = getNodePathFromLeaf(leaf, ParameterType.RESPONSE);
            outputTreePaths.add(outputPath);
            for (String node : outputPath) {
                System.out.println("node in path: " + node);
            }
        }
        Tree inputTree = new WsdlParser().parseXmlFile(fileOfNextMethod, ParameterType.REQUEST);
        System.out.println("leaves: " + inputTree.getLeaves().size());
        for (Node leaf: inputTree.getLeaves()) {
            List<String> inputPath = getNodePathFromLeaf(leaf, ParameterType.REQUEST);
            inputTreePaths.add(inputPath);
            for (String node : inputPath) {
                System.out.println("node in path: " + node);
            }
        }

        return comparePaths(outputTreePaths, inputTreePaths);
    }

    @Override
    public boolean compatibleParameters(String fileOfPreviousMethod, String fileOfNextMethod) throws ParserConfigurationException, SAXException, IOException {
        return compatibleParameters(new File(fileOfPreviousMethod),
                new File(fileOfNextMethod));
    }

    private List<String> getNodePathFromLeaf(Node leaf, ParameterType type) {
        Node current = leaf;
        List<String> path = new ArrayList<String>();
        do {
            path.add(current.getName());
            current = current.getParent();
        } while (!current.getName().equals(type.getType()));
        return path;
    }

    private boolean comparePaths(List<List<String>> outputPaths, List<List<String>> inputPaths) {
        return false;
    }
}
