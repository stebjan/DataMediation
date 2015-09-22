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
    public boolean compatibleParameters(File fileOfPreviousMethod, File fileOfNextMethod, OntologyParser parser)
            throws IOException, SAXException, ParserConfigurationException {
        System.out.println("Output class: " + new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE));
        Tree outputTree = new WsdlParser().parseXmlFile(fileOfPreviousMethod, ParameterType.RESPONSE);
        List<List<String>> outputTreePaths = new ArrayList<List<String>>();
        List<List<String>> inputTreePaths = new ArrayList<List<String>>();
        System.out.println("leaves: " + outputTree.getLeaves().size());
        for (Node leaf: outputTree.getLeaves()) {
            List<String> outputPath = getNodePathFromLeaf(leaf, ParameterType.RESPONSE);
            outputTreePaths.add(outputPath);
//            for (String node : outputPath) {
//                System.out.println("node in path: " + node);
//            }
        }
        System.out.println("Input class: " + new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST));
        Tree inputTree = new WsdlParser().parseXmlFile(fileOfNextMethod, ParameterType.REQUEST);
        System.out.println("leaves: " + inputTree.getLeaves().size());
        for (Node leaf: inputTree.getLeaves()) {
            List<String> inputPath = getNodePathFromLeaf(leaf, ParameterType.REQUEST);
            inputTreePaths.add(inputPath);
//            for (String node : inputPath) {
//                System.out.println("node in path: " + node);
//            }
        }
        parser.compatibleClasses(new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE),
                new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST));
        return comparePaths(outputTreePaths, inputTreePaths);
    }

    @Override
    public boolean compatibleParameters(String fileOfPreviousMethod, String fileOfNextMethod, OntologyParser parser)
            throws ParserConfigurationException, SAXException, IOException {
        return compatibleParameters(new File(fileOfPreviousMethod),
                new File(fileOfNextMethod), parser);
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
        for (List<String> inputList: inputPaths) {
            boolean inputInOutput = false;
            for (List<String> outputList: outputPaths) {
                if (outputList.containsAll(inputList)) {
                    inputInOutput = true;
                }
            }
            if (!inputInOutput) {
                return false;
            }
        }
        return true;
    }
}
