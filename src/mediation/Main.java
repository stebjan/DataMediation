package mediation;

import mediation.logic.DataMediator;
import mediation.logic.EEGDataMediator;
import mediation.logic.ParameterType;
import mediation.logic.WsdlParser;
import mediation.tree.Node;
import mediation.tree.Tree;

import java.io.File;

/**
 * Created by stebjan on 17.6.2015.
 */
public class Main {

    public static void main(String[] args) throws Exception{

        Tree tree =
        new WsdlParser().parseXmlFile(new File("C:\\java\\eegdataprocessor\\trunk\\method_output_def\\detection_of_epochs.wsdl"), ParameterType.REQUEST);

        System.out.println(tree.getRoot().getName());
        System.out.println(tree.getRoot().getChildren().size());
        System.out.println(tree.getRoot().getChildren().get(0).getName());
        System.out.println(tree.getRoot().getChildren().get(0).getChildren().get(0).getName());
        System.out.println(tree.getRoot().getChildren().get(0).getChildren().get(0).getChildren().get(0).getName());

        System.out.println(tree.getRoot().getChildren().get(1).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(0).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(1).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(2).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren().get(0).getName());
        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(2).getChildren().get(0).getName());

        DataMediator mediator = new EEGDataMediator();
        System.out.println(mediator.compatibleParameters("C:\\eegdataprocessor\\trunk\\method_output_def\\detection_of_epochs.wsdl",
                "C:\\eegdataprocessor\\trunk\\method_output_def\\averaging.wsdl"));

    }
}
