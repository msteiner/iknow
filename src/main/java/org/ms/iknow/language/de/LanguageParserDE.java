package org.ms.iknow.language.de;

import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
import org.ms.iknow.core.type.language.Adjectiv;
import org.ms.iknow.core.type.language.Genus;
import org.ms.iknow.core.type.language.Numerus;
import org.ms.iknow.core.type.language.Substantiv;
import org.ms.iknow.core.type.language.Unknown;
import org.ms.iknow.core.type.language.Verb;
import org.ms.iknow.core.type.language.Word;
import org.ms.iknow.core.type.sense.text.Text;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.LanguageParser;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;
import org.ms.iknow.persistence.repo.memory.GrammarRepositoryDE;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LanguageParserDE implements LanguageParser {

    GrammarRepository repo = GrammarRepositoryDE.getInstance();

    @Override
    public List<Synapse> parseStatement(String message) {
        List<Synapse> synapses = new ArrayList<Synapse>();
        List<String> tokens = getTokens(message, ",");
        List<String> elements;
        for (String token : tokens) {
            elements = getTokens(token, " ");
            synapses.add(toSynapse(elements));
        }

        return synapses;
    }

    @Override
    public List<Word> parseExpression(String expression) {

        expression = removeMarks(expression);

        List<Word> words = new ArrayList<Word>();
        WordType wordType = null;
        List<String> tokens = getTokens(expression, " ");
        for (String token : tokens) {
            // Define WordType
            try {
                wordType = repo.assignGrammar(token);
            } catch (GrammarException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Word word = createWord(token, wordType);
            words.add(word);
        }

        return words;
    }

    private String removeMarks(String expression) {
        expression = expression.replace(".", "");
        return expression;
    }

    private Word createWord(String expression, WordType wordType) {
        if (wordType == WordType.ADJECTIVE) {
            Adjectiv w = new Adjectiv();
            w.setExpression(expression);
            return w;
        } else if (wordType == WordType.GENUS) {
            Genus w = new Genus();
            w.setExpression(expression);
            return w;
        } else if (wordType == WordType.NUMERUS) {
            Numerus w = new Numerus();
            w.setExpression(expression);
            return w;
        } else if (wordType == WordType.SUBSTANTIVE) {
            Substantiv w = new Substantiv();
            w.setExpression(expression);
            return w;
        } else if (wordType == WordType.VERB) {
            Verb w = new Verb();
            w.setExpression(expression);
            return w;
        } else {
            Unknown w = new Unknown();
            w.setExpression(expression);
            return w;
        }
    }

    private List<String> getTokens(String message, String delimiter) {
        List<String> tokens = new ArrayList<String>();
        StringTokenizer parser = new StringTokenizer(trimStatement(message), delimiter);
        String token = null;
        while (parser.hasMoreTokens()) {
            token = parser.nextToken(delimiter);
            tokens.add(token);
        }
        return tokens;
    }

    private String trimStatement(String message) {
        String result1 = message.trim().replaceAll(" +", " ");
        String result2 = result1.replaceAll("^\\s+", "");
        return result2;
    }

    private Synapse toSynapse(List<String> elements) {

        Neuron parent = new Text(elements.get(0));
        Relation relation = Relation.getRelationByValue(elements.get(1));
        Neuron child = new Text(elements.get(2));
        return new Synapse(parent, relation, child);
    }
}
