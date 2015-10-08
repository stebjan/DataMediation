package mediation.logic;

import mediation.tree.Node;
import mediation.tree.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stebjan on 17.6.2015.
 */
public class WsdlParser {

    private NodeList listOfComplexTypes;
    private Tree syntaxTree;

    /**
     *
     * @param file - WSDL description file
     * @param type - Enum of types (Request or Response)
     * @return Array of two strings - the first one is name of an element, the second one is the annotation
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public String[] getSAWSDLAnnotation(File file, ParameterType type) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        String[] map = new String[2];

        doc.getDocumentElement().normalize();
        Element root = getRoot(type, doc);
        NodeList list = root.getElementsByTagName("xs:element");
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (e.hasAttribute("sawsdl:modelReference")) {
                map[0] = e.getAttribute("name");
                map[1] = e.getAttribute("sawsdl:modelReference");
                return map;
            }
        }
        return null;
    }

    public Tree parseXmlFile(File file, ParameterType type) throws ParserConfigurationException, SAXException, IOException {

        syntaxTree = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();
        listOfComplexTypes = doc.getElementsByTagName("xs:complexType");

        Element root = getRoot(type, doc);
        if (root != null) {

            syntaxTree = new Tree(root.getAttribute("name"));
//            System.out.println(syntaxTree.getRoot().getName());
            findChildElements(root, syntaxTree.getRoot());
        } else {
            //todo fuck with me
        }
        return syntaxTree;
    }

    private Element getRoot(ParameterType type, Document doc) {
        NodeList listOfElements = doc.getElementsByTagName("xs:element");
//        System.out.println(listOfElements.getLength());
        Element root;
        for (int i = 0; i < listOfElements.getLength(); i++) {
            root = (Element) listOfElements.item(i);
            if (root.getAttribute("name").equals(type.getType())) {
                return root;
            }

        }
        return null;
    }

    private void findChildElements(Element element, Node node) {
        if (element.hasAttribute("type")) {
            for (int i = 0; i < listOfComplexTypes.getLength(); i++) {
                Element complexType = (Element) listOfComplexTypes.item(i);
                if (complexType.getAttribute("name").equals(element.getAttribute("type"))) {
                    addElementToTree(complexType, node);

                }
            }

        } else {

            addElementToTree(element, node);
        }

    }

    private void addElementToTree(Element element, Node node) {
        NodeList list = element.getElementsByTagName("xs:element");
//        System.out.println(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
//            System.out.println(e.getAttribute("name"));
            Node newNode = node.addChild(e.getAttribute("name"));
            if (e.hasAttribute("minOccurs")) {
                newNode.setMinOccurs(Integer.parseInt(e.getAttribute("minOccurs")));
            }
            if (e.hasAttribute("maxOccurs")) {
                try {
                    newNode.setMaxOccurs(Integer.parseInt(e.getAttribute("maxOccurs")));
                } catch (NumberFormatException ex) {
                    newNode.setMaxOccurs(Integer.MAX_VALUE);
                }
            }

            if (!e.getAttribute("type").startsWith("xs")) {
                findChildElements(e, newNode);
            } else {
                //add the leaf of the tree - primitive type
                Node leaf = newNode.addChild(e.getAttribute("type"));
                syntaxTree.getLeaves().add(leaf);
                if (e.hasAttribute("minOccurs")) {
                    newNode.setMinOccurs(Integer.parseInt(e.getAttribute("minOccurs")));
                }
                if (e.hasAttribute("maxOccurs")) {
                    try {
                        newNode.setMaxOccurs(Integer.parseInt(e.getAttribute("maxOccurs")));
                    } catch (NumberFormatException ex) {
                        newNode.setMaxOccurs(Integer.MAX_VALUE);
                    }
                }
            }
        }
    }
}
