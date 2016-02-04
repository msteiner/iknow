package org.ms.iknow.persistence.repo.memory;

import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;

import java.util.ArrayList;
import java.util.List;

public class GrammarRepositoryDE implements GrammarRepository {

    private static GrammarRepositoryDE instance;

    List<String>                       substantives = new ArrayList<String>();
    List<String>                       adjectives   = new ArrayList<String>();
    List<String>                       verbs        = new ArrayList<String>();
    List<String>                       genus        = new ArrayList<String>();
    List<String>                       numerus      = new ArrayList<String>();

    private GrammarRepositoryDE() {
        // Exists only to defeat instantiation.
    }

    public static GrammarRepositoryDE getInstance() {
        if (instance == null) {
            instance = new GrammarRepositoryDE();
        }
        return instance;
    }

    @Override
    public WordType assignGrammar(String expression) throws GrammarException {
        return assignKnownWordType(expression);
    }

    @Override
    public void addExpression(String expression, WordType wordType) throws GrammarException {
        if (expression == null) {
            throw new GrammarException("Cannot add expression [null] to a repository.");
        } else if (wordType == null) {
            throw new GrammarException("Cannot add wordType [null] to a repository.");
        }

        switch (wordType) {
            case SUBSTANTIVE:
                substantives.add(expression);
                break;
            case ADJECTIVE:
                adjectives.add(expression);
                break;
            case VERB:
                verbs.add(expression);
                break;
            case GENUS:
                genus.add(expression);
                break;
            case NUMERUS:
                numerus.add(expression);
                break;
            default:
                throw new GrammarException("Cannot add WordType.UNKNOWN to a repository.");
        }
    }

    protected WordType assignKnownWordType(String expression) {
        if (substantives.contains(expression)) {
            return WordType.SUBSTANTIVE;
        } else if (adjectives.contains(expression)) {
            return WordType.ADJECTIVE;
        } else if (verbs.contains(expression)) {
            return WordType.VERB;
        } else if (genus.contains(expression)) {
            return WordType.GENUS;
        } else if (numerus.contains(expression)) {
            return WordType.NUMERUS;
        } else {
            return WordType.UNKNOWN;
        }
    }

    @Override
    public void deleteAll() {
        substantives.clear();
        adjectives.clear();
        verbs.clear();
        genus.clear();
        numerus.clear();
    }
}
