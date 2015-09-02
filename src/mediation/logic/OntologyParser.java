package mediation.logic;

/**
 * Created by stebjan on 2.9.2015.
 */
import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.semanticweb.owlapi.search.Searcher.annotations;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;

public class OntologyParser {

    private final OWLOntology ontology;
    private OWLDataFactory df;

    public OntologyParser(OWLOntology ontology, OWLDataFactory df) {
        this.ontology = ontology;
        this.df = df;
    }

    public void parseOntology()
            throws OWLOntologyCreationException {

        for (OWLClass cls : ontology.getClassesInSignature()) {
            String id = cls.getIRI().toString();
            String label = get(cls, RDFS_LABEL.toString()).get(0);
            System.out.println(label + " [" + id + "]");
        }
    }

    private List<String> get(OWLClass clazz, String property) {
        List<String> ret = new ArrayList<String>();

        final OWLAnnotationProperty owlProperty = df
                .getOWLAnnotationProperty(IRI.create(property));
        for (OWLOntology o : ontology.getImportsClosure()) {
            for (OWLAnnotation annotation : annotations(
                    o.getAnnotationAssertionAxioms(clazz.getIRI()), owlProperty)) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    ret.add(val.getLiteral());
                }
            }
        }
        return ret;
    }

}