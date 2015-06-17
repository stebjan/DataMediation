package mediation;

import mediation.tree.Tree;

/**
 * Created by stebjan on 17.6.2015.
 */
public class Main {

    public static void main(String[] args) {

        Tree tree = new Tree("Root");
        tree.getRoot().addChild("Child");
        System.out.println(tree.getRoot().getName());
        System.out.println(tree.getRoot().getChildren().size());
        System.out.println(tree.getRoot().getChildren().get(0).getName());

    }
}
