package org.ms.iknow.language.de;

import org.ms.iknow.core.manager.type.Answer;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Question;
import org.ms.iknow.core.manager.type.Statement;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.Synapse;
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

    GrammarRepository grammarRepository = GrammarRepositoryDE.getInstance();

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
    public Synapse parseExpression(Message message) throws GrammarException {
        String expression = removeMarks(message.getSource());
        List<String> tokens = getTokens(expression, " ");

        if (message instanceof Statement) {
            return processStatement(tokens);
        } else if (message instanceof Question) {
            return processQuestion(tokens);
        } else if (message instanceof Answer) {
            return processAnswer(tokens);
        } else {
            // Should not be possible.
            throw new GrammarException("MessageType for expression [" + message.getExpressionAsString()
                                       + "] not found(Statement, Question, Answer...?).");
        }

    }

    /**
     * @param tokens
     * @return the synapse
     */
    private Synapse processAnswer(List<String> tokens) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param tokens
     * @return the synapse
     */
    private Synapse processQuestion(List<String> tokens) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param tokens
     * @return the synapse
     */
    private Synapse processStatement(List<String> tokens) throws GrammarException {
        if (tokens == null) {
            throw new GrammarException("Von nichts kommt nichts: Keine Tokens bekommen.");
        }
        if (tokens.size() < 3 || tokens.size() > 5) {
            throw new GrammarException("Der Satz muss zwischen 3 und 5 Wörter enthalten, weist jedoch " + tokens.size() + " auf.");
        } else {
            Neuron parent;
            Relation relation;
            Neuron child;
            // "Der ICN ist ein Zug" OR "Der Baum ist schön".
            if (isKnownAsGenus(tokens.get(0))) {
                grammarRepository.addExpression(tokens.get(1), WordType.SUBSTANTIVE);
                parent = new Text(tokens.get(1)); // Substantiv
                addSubstantiveToRepository(tokens.get(1));
                relation = grammarRepository.getRelation(tokens.get(2));
                if (tokens.size() == 4) {
                    child = new Text(tokens.get(3)); // Substantiv?Adjektiv?
                    addTokenToRepository(tokens.get(3));
                } else if (tokens.size() == 5) {
                    if (isKnownAsGenus(tokens.get(3))) {
                        child = new Text(tokens.get(4)); // Substantiv
                    } else {
                        child = new Text(tokens.get(3)); // Substantiv
                    }
                } else {
                    throw new GrammarException("Erwartet wurden 5 Wörter, gefunden jedoch 4.");
                }
            // "Hasen sind Tiere".
            } else if (isKnownAsSubstantive(tokens.get(0)) || isKnownAsAdjective(tokens.get(0))) {
              System.out.println("+++ Wort 1=" + tokens.get(0));
                addTokenToRepository(tokens.get(0));
                parent = new Text(tokens.get(0)); // Substantiv?Adjektiv?
                relation = grammarRepository.getRelation(tokens.get(1));
                addTokenToRepository(tokens.get(2));
                child = new Text(tokens.get(2)); // Substantiv?Adjektiv?
            // "Blau ist wunderbar".
            } else {
                parent = new Text(tokens.get(0)); // UNKNOWN
                relation = grammarRepository.getRelation(tokens.get(1));
                child = new Text(tokens.get(2)); // UNKNOWN
            }
            return new Synapse(parent, relation, child);
        }
    }
  
    private void addTokenToRepository(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression) || !grammarRepository.containsAdjective(expression)) {
            grammarRepository.addExpression(expression, WordType.UNKNOWN);
        }
    }
  
    private void addSubstantiveToRepository(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression)) {
            grammarRepository.addExpression(expression, WordType.SUBSTANTIVE);
        }
    }
  
    private boolean isKnownAsGenus(String genus) {
        return grammarRepository.containsGenus(genus);
    }

    private boolean isKnownAsSubstantive(String substantive) {
        return grammarRepository.containsSubstantive(substantive);
    }

    private boolean isKnownAsAdjective(String adjective) {
        return grammarRepository.containsAdjective(adjective);
    }

    private String removeMarks(String expression) {
        expression = expression.replace(".", "");
        return expression;
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
