package mediation.tree;

import java.util.ArrayList;

/**
 * Created by stebjan on 17.6.2015.
 * It represents e syntax tree of I/O parameters of analytic method described via .wsdl file.
 */
public class Tree {

    private Node root;

    public Tree(String rootName) {
        this.root = new Node(rootName);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
