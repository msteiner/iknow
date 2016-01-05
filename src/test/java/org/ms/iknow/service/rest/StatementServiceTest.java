package org.ms.iknow.service.rest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ms.iknow.core.type.Neuron;
import org.ms.iknow.core.type.Relation;
import org.ms.iknow.persistence.repo.memory.MemoryRepository;
import org.ms.iknow.service.type.StatementEntry;

import java.util.List;

import javax.ws.rs.core.Response;

public class StatementServiceTest {

    StatementService statementService   = null;
    final String     NEURON_PARENT_NAME = "IKnow";
    final String     NEURON_RELATION    = Relation.IS.getId();
    final String     NEURON_CHILD_NAME  = "Wissen";

    @Before
    public void init() {
        statementService = new StatementService();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateStatement() {

        Response response = null;
        List<StatementEntry> entries = null;
        response = statementService.createStatement(NEURON_PARENT_NAME, NEURON_RELATION, NEURON_CHILD_NAME);
        entries = (List<StatementEntry>)response.getEntity();
        assertNotNull("Expected entries but was null.", entries);
        assertEquals("Expected 1 entry but found " + entries.size() + ".", 1, entries.size());
        assertResponse(entries.get(0), NEURON_PARENT_NAME, NEURON_RELATION, NEURON_CHILD_NAME);

        response = statementService.createStatement(NEURON_PARENT_NAME, NEURON_RELATION, NEURON_CHILD_NAME);
        entries = (List<StatementEntry>)response.getEntity();
        assertNotNull("Expected entries but was null.", entries);
        assertEquals("Expected 2 entry but found " + entries.size() + ".", 2, entries.size());
        assertResponse(entries.get(0), NEURON_PARENT_NAME, NEURON_RELATION, NEURON_CHILD_NAME);
        assertResponse(entries.get(1), NEURON_PARENT_NAME, NEURON_RELATION, NEURON_CHILD_NAME);

    }

    void assertResponse(StatementEntry entry, String parentName, String relation, String childName) {
        assertEquals("Expected [" + parentName + "] but found " + entry.getParentName() + ".", parentName, entry.getParentName());
        assertEquals("Expected [" + relation + "] but found " + entry.getRelationId() + ".", relation, entry.getRelationId());
        assertEquals("Expected [" + childName + "] but found " + entry.getChildName() + ".", childName, entry.getChildName());
    }
}
