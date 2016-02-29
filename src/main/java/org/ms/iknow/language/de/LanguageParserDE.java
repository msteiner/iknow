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
        ParserCase parserCase = ParserCaseFactory.getParserCase(tokens);
        return parserCase.parse();
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