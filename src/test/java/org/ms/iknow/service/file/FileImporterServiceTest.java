package org.ms.iknow.service.file;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ms.iknow.service.type.StatementEntry;

import java.util.List;

public class FileImporterServiceTest {
  
  FileImporterService fis = null;
  public static final String FILE_PATH = "src/test/resources/org/ms/iknow/service/file/";
  public static final String FILE_NAME = FILE_PATH + "import_smoke.csv";
  
  public static final int EXPECTED_SIZE = 10;
  
  @Before
  public void init() {
    fis = new FileImporterService();
  }
  
  @Test
  public void testReadNeurons() {
    List<StatementEntry> entries = fis.readNeurons(FILE_NAME);
    assertNotNull(entries);
    assertEquals("Expected " + EXPECTED_SIZE + " entries but found " + entries.size(), EXPECTED_SIZE, entries.size());
    for (StatementEntry entry : entries) {
      assertNotNull("Expected Neuron but was null.", entry.getParentName());
      assertNotNull("Expected Relation but was null.", entry.getRelationId());
      assertNotNull("Expected Neuron but was null.", entry.getChildName());
    }
  }
}
