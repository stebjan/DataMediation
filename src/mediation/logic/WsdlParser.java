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

/**
 * Created by stebjan on 17.6.2015.
 */
public class WsdlParser {

    public Tree parseXmlFile(File file) throws ParserConfigurationException, SAXException, IOException {

        Tree syntaxTree = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        Element root = getRoot(doc);
        if (root != null) {

            syntaxTree = new Tree(root.getAttribute("name"));
            System.out.println(syntaxTree.getRoot().getName());
            findChildElement(root);
        } else {
            //todo fuck with me
        }
        return syntaxTree;
    }

    private Element getRoot(Document doc) {
        NodeList list = doc.getElementsByTagName("xs:element");
        System.out.println(list.getLength());
        Element root;
        for (int i = 0; i < list.getLength(); i++) {
            root = (Element) list.item(i);
            System.out.println(root.getAttribute("name"));
            if (root.getAttribute("name").equals("Request")) {
                return root;
            }

        }
        return null;
    }

    private Element findChildElement(Element element) {
        NodeList list = element.getElementsByTagName("xs:element");
        System.out.println(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
        }

        return null;
    }
}
