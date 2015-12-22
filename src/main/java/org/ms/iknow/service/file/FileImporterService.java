package org.ms.iknow.service.file;

import org.ms.iknow.service.type.StatementEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileImporterService {

    public List<StatementEntry> readNeurons(String fileName) {
        List<StatementEntry> entries = new ArrayList<StatementEntry>();
        Path pathToFile = Paths.get(fileName);
        // create an instance of BufferedReader 
        // using try with resource, Java 7 feature to close resources 
        try {
            BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);
            // read the first line from the text file 
            String line = br.readLine();
            // loop until all lines are read 
            while (line != null) {
                // use string.split to load a string array with the values from 
                // each line of 
                // the file, using a comma as the delimiter 
                String[] attributes = line.split(";");
                StatementEntry entry = new StatementEntry();
                entry.setParentName(attributes[0]);
                entry.setRelationId(attributes[1]);
                entry.setChildName(attributes[2]);
                entries.add(entry);

                //Book book = createBook(attributes); 
                // adding book into ArrayList 
                //      entries
                // read next line before looping // if end of file reached, line would be null 
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return entries;
    }
}
