package mediation.logic;

import mediation.tree.Node;
import mediation.tree.Tree;
import org.apache.commons.io.FileUtils;
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
    private Tree outputTree;

    @Override
    public Tree getOutputTree() {
        return outputTree;
    }

    @Override
    public boolean compatibleParameters(File fileOfPreviousMethod, File fileOfNextMethod, OntologyParser parser)
            throws IOException, SAXException, ParserConfigurationException {
        System.out.println("Output class: " + new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE)[1]);
        outputTree = new WsdlParser().parseXmlFile(fileOfPreviousMethod, ParameterType.RESPONSE);
        List<List<String>> outputTreePaths = new ArrayList<List<String>>();
        List<List<String>> inputTreePaths = new ArrayList<List<String>>();
        System.out.println("leaves: " + outputTree.getLeaves().size());
        for (Node leaf: outputTree.getLeaves()) {
            List<String> outputPath = getNodePathFromLeaf(leaf, ParameterType.RESPONSE, null);
            outputTreePaths.add(outputPath);

        }
        System.out.println("Input class: " + new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST)[1]);
        Tree inputTree = new WsdlParser().parseXmlFile(fileOfNextMethod, ParameterType.REQUEST);
        System.out.println("leaves: " + inputTree.getLeaves().size());
        for (Node leaf: inputTree.getLeaves()) {
            List<String> inputPath = getNodePathFromLeaf(leaf, ParameterType.REQUEST, null);
            inputTreePaths.add(inputPath);

        }
        String[] requestElementAndResponseAnnotation = new String[2];
        requestElementAndResponseAnnotation[0] = new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST)[0];
        String responseClass = new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE)[1];
        int index = responseClass.lastIndexOf('#');
        responseClass = responseClass.substring(index + 1);
        requestElementAndResponseAnnotation[1] = responseClass.substring(0, 1).toLowerCase() + responseClass.substring(1);
        System.out.println(requestElementAndResponseAnnotation[0] + " " + requestElementAndResponseAnnotation[1]);
        System.out.println("Ontology: " + parser.compatibleClasses(new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE)[1],
                new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST)[1]));

        //Semantic compatibility test of ontology annotations
        if (!parser.compatibleClasses(new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE)[1],
                new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST)[1])) {
            return false;
        }

        //If semantic is ok syntax is checked
        if (comparePaths(outputTreePaths, inputTreePaths)) {
        return true;
        }

        /*
         * If the syntax does not match, the element in request (next method)
         * is changed to element from response (previous method) according to the ontology.
         * Then it is checked again
         */
        inputTreePaths = new ArrayList<List<String>>();
        for (Node leaf: inputTree.getLeaves()) {
            List<String> inputPath = getNodePathFromLeaf(leaf, ParameterType.REQUEST, requestElementAndResponseAnnotation);
            inputTreePaths.add(inputPath);
        }

        return comparePaths(outputTreePaths, inputTreePaths);
    }

    @Override
    public boolean compatibleParameters(String fileOfPreviousMethod, String fileOfNextMethod, OntologyParser parser)
            throws ParserConfigurationException, SAXException, IOException {
        return compatibleParameters(new File(fileOfPreviousMethod),
                new File(fileOfNextMethod), parser);
    }

    private List<String> getNodePathFromLeaf(Node leaf, ParameterType type, String[] requestElementAndResponseAnnotation) {
        Node current = leaf;
        List<String> path = new ArrayList<String>();
        do {
            if (requestElementAndResponseAnnotation != null && current.getName().equals(requestElementAndResponseAnnotation[0])) {
                path.add(requestElementAndResponseAnnotation[1]);
            } else {
                path.add(current.getName());
            }

            current = current.getParent();
        } while (!current.getName().equals(type.getType()));
        return path;
    }

    public void mediateData(String filename, String fileOfPreviousMethod, String fileOfNextMethod) throws IOException, ParserConfigurationException, SAXException {
        mediateData(new File(filename), new File(fileOfPreviousMethod), new File(fileOfNextMethod));


    }

    public void mediateData(File file, File fileOfPreviousMethod, File fileOfNextMethod) throws IOException, ParserConfigurationException, SAXException {
        String string = FileUtils.readFileToString(file);
        string = string.replaceAll(new WsdlParser().getSAWSDLAnnotation(fileOfPreviousMethod, ParameterType.RESPONSE)[0],
                new WsdlParser().getSAWSDLAnnotation(fileOfNextMethod, ParameterType.REQUEST)[0]);
        FileUtils.writeStringToFile(file, string);

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
