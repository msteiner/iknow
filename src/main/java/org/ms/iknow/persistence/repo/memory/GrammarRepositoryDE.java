package org.ms.iknow.persistence.repo.memory;

import org.ms.iknow.core.type.Relation;
import org.ms.iknow.core.type.RelationType;
import org.ms.iknow.exception.GrammarException;
import org.ms.iknow.language.de.type.WordType;
import org.ms.iknow.persistence.repo.GrammarRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GrammarRepositoryDE implements GrammarRepository {

    private static GrammarRepositoryDE instance;

    List<String>                       substantives = new ArrayList<String>();
    List<String>                       adjectives   = new ArrayList<String>();
    List<String>                       undefineds   = new ArrayList<String>();
    Map<String, String>                verbs        = new HashMap<String, String>();
    Map<String, RelationType>          verbIndex    = new HashMap<String, RelationType>();
    List<String>                       genus        = new ArrayList<String>();
    List<String>                       numerus      = new ArrayList<String>();

    private GrammarRepositoryDE() {
        // Initiate repository.
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
        checkNull(expression);
        checkNull(wordType);
        switch (wordType) {
            case SUBSTANTIVE:
                substantives.add(expression);
                break;
            case ADJECTIVE:
                adjectives.add(expression);
                break;
            case VERB:
                verbs.put(expression, UUID.randomUUID().toString());
                break;
            case GENUS:
                genus.add(expression);
                break;
            case NUMERUS:
                numerus.add(expression);
                break;
            case UNKNOWN:
                undefineds.add(expression);
                break;
            default:
                //throw new GrammarException("Cannot add WordType.UNKNOWN to a repository.");
        }
    }

    @Override
    public void addVerb(String expression, RelationType relationType) throws GrammarException {
        checkNull(expression);
        checkNull(relationType);

        String id = UUID.randomUUID().toString();
        verbs.put(expression, id);
        verbIndex.put(id, relationType);
    }

    @Override
    public boolean containsGenus(String term) {
        return genus.contains(term);
    }

    @Override
    public boolean containsAdjective(String adjective) {
        return adjectives.contains(adjective);
    }

    @Override
    public boolean containsSubstantive(String substantive) {
        return substantives.contains(substantive);
    }
  
    @Override
    public boolean containsVerb(String verb) {
        return verbs.containsKey(verb);
    }
  
    @Override
    public boolean containsNumerus(String num) {
        return numerus.contains(num);
    }
  
    @Override
    public boolean containsUndefined(String term) {
        return undefineds.contains(term);
    }

    protected WordType assignKnownWordType(String expression) {
        if (substantives.contains(expression)) {
            return WordType.SUBSTANTIVE;
        } else if (adjectives.contains(expression)) {
            return WordType.ADJECTIVE;
        } else if (verbs.containsKey(expression)) {
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
        verbIndex.clear();
        genus.clear();
        numerus.clear();
    }

    void checkNull(String expression) throws GrammarException {
        if (expression == null) {
            throw new GrammarException("Cannot add expression [null] to a repository.");
        }
    }

    void checkNull(WordType wordType) throws GrammarException {
        if (wordType == null) {
            throw new GrammarException("Cannot add wordType [null] to a repository.");
        }
    }

    void checkNull(RelationType relationType) throws GrammarException {
        if (relationType == null) {
            throw new GrammarException("Cannot add expression with Relation [null] to a repository.");
        }
    }

    @Override
    public Relation getRelation(String verb) {
        RelationType relationType = RelationType.UNKNOWN;
        if (verbs.containsKey(verb)) {
            String id = verbs.get(verb);
            if (verbIndex.containsKey(id)) {                
                relationType = verbIndex.get(id);
            }
        }
        return new Relation(relationType);
    }
}
