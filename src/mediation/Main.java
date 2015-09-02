package mediation;

import mediation.logic.DataMediator;
import mediation.logic.EEGDataMediator;
import mediation.logic.OntologyParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Created by stebjan on 17.6.2015.
 */
public class Main {

    public static void main(String[] args) throws Exception{

//        Tree tree =
//        new WsdlParser().parseXmlFile(new File("C:\\java\\eegdataprocessor\\trunk\\method_output_def\\detection_of_epochs.wsdl"), ParameterType.REQUEST);
//
//        System.out.println(tree.getRoot().getName());
//        System.out.println(tree.getRoot().getChildren().size());
//        System.out.println(tree.getRoot().getChildren().get(0).getName());
//        System.out.println(tree.getRoot().getChildren().get(0).getChildren().get(0).getName());
//        System.out.println(tree.getRoot().getChildren().get(0).getChildren().get(0).getChildren().get(0).getName());
//
//        System.out.println(tree.getRoot().getChildren().get(1).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(0).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(1).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(2).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren().get(0).getName());
//        System.out.println(tree.getRoot().getChildren().get(1).getChildren().get(0).getChildren().get(2).getChildren().get(0).getName());

        DataMediator mediator = new EEGDataMediator();
        System.out.println(mediator.compatibleParameters("C:\\java\\eegdataprocessor\\trunk\\method_output_def\\detection_of_epochs.wsdl",
                "C:\\java\\eegdataprocessor\\trunk\\method_output_def\\averaging.wsdl"));
        String x = "http://ontology.neuinfo.org/NIF/Dysfunction/NIF-Dysfunction.owl";

        IRI documentIRI = IRI.create(x);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(documentIRI);
        OntologyParser parser = new OntologyParser(ontology, manager.getOWLDataFactory());
        parser.parseOntology();

    }
}
