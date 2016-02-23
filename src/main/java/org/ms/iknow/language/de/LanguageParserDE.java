package org.ms.iknow.language.de;

import org.ms.iknow.core.manager.type.Answer;
import org.ms.iknow.core.manager.type.Message;
import org.ms.iknow.core.manager.type.Question;
import org.ms.iknow.core.manager.type.Statement;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
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
        }
        Neuron parent;
        Relation relation;
        Neuron child;
        
        if (tokens.size() == 3) {
            // "Wale sind Tiere" oder "klein ist schön" oder "Lorem Ipsum dolor".
            if (isKnownAsGenus(tokens.get(0))) {
                throw new GrammarException("Unverständlicher Satzbau; sorry.");
            } else {
                registerToken(tokens.get(0));
                parent = new Text(tokens.get(0));
                relation = registerVerb(tokens.get(1));
                registerToken(tokens.get(2));
                child = new Text(tokens.get(2));
            }
        } else if (tokens.size() == 4) {
            // "Der Baum ist schön".
            if (isKnownAsGenus(tokens.get(0))) {
                registerSubstantive(tokens.get(1));
            } else {
                registerAdjective(tokens.get(1));
            }
            parent = new Text(tokens.get(1));
            relation = registerVerb(tokens.get(2));
            registerToken(tokens.get(3));
            child = new Text(tokens.get(3));
        } else {
            // "Der ICN ist ein Zug".
            if (isKnownAsGenus(tokens.get(0))) {
                registerSubstantive(tokens.get(1));
                parent = new Text(tokens.get(1));
                relation = registerVerb(tokens.get(2));
                if (isKnownAsGenus(tokens.get(3))) {
                    registerToken(tokens.get(4));
                    child = new Text(tokens.get(4));
                } else {
                    throw new GrammarException("Unverständlicher Satzbau; sorry.");
                }
            } else {
                throw new GrammarException("Unverständlicher Satzbau; sorry.");
            }
        }
        return new Synapse(parent, relation, child);
    }
  
    private void registerToken(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression) || !grammarRepository.containsAdjective(expression)) {
            grammarRepository.addExpression(expression, WordType.UNKNOWN);
        }
    }
  
    private void registerSubstantive(String expression) throws GrammarException {
        if (!grammarRepository.containsSubstantive(expression)) {
            grammarRepository.addExpression(expression, WordType.SUBSTANTIVE);
        }
    }
  
    private void registerAdjective(String expression) throws GrammarException {
        if (!grammarRepository.containsAdjective(expression)) {
            grammarRepository.addExpression(expression, WordType.ADJECTIVE);
        }
    }
  
    private Relation registerVerb(String expression) throws GrammarException {
        if (!grammarRepository.containsVerb(expression)) {
            grammarRepository.addExpression(expression, WordType.VERB);
            grammarRepository.addVerb(expression, RelationType.UNKNOWN);
            Relation relation = new Relation(RelationType.UNKNOWN);
            return relation;
        }
        else {
            return grammarRepository.getRelation(expression);
        }
    }
  
    private boolean isKnownAsGenus(String genus) {
        return grammarRepository.containsGenus(genus);
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
        RelationType relationType = RelationType.getRelationTypeByValue(elements.get(1));
        Relation relation = null;
        relation = new Relation(relationType);
        Neuron child = new Text(elements.get(2));
        return new Synapse(parent, relation, child);
    }
}
