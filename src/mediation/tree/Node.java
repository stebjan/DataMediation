package mediation.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by stebjan on 17.6.2015.
 * It represents a node of the syntax tree of I/O parameters of analytic method described via .wsdl file.
 */
public class Node implements Iterable<Node> {

    private Node parent;
    private List<Node> children;
    private String name;
    private int minOccurs;
    private int maxOccurs;

    public Node(String name) {
        this(name, 1, 1);
    }

    public Node(String name, int minOccurs, int maxOccurs) {
        this.name = name;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
        children = new ArrayList<Node>();
    }


    public Node addChild(String name) {
        Node child = new Node(name);
        child.setParent(this);
        this.getChildren().add(child);
        return child;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    @Override
    public Iterator<Node> iterator() {
        return null;
    }
}
