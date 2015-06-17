package mediation;

import mediation.logic.WsdlParser;
import mediation.tree.Tree;

import java.io.File;

/**
 * Created by stebjan on 17.6.2015.
 */
public class Main {

    public static void main(String[] args) throws Exception{

//        Tree tree = new Tree("Root");
//        tree.getRoot().addChild("Child");
//        System.out.println(tree.getRoot().getName());
//        System.out.println(tree.getRoot().getChildren().size());
//        System.out.println(tree.getRoot().getChildren().get(0).getName());
        new WsdlParser().parseXmlFile(new File("C:\\java\\eegdataprocessor\\trunk\\method_output_def\\averaging.wsdl"));

    }
}
