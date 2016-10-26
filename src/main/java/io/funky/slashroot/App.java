package io.funky.slashroot;

import io.funky.slashroot.importer.FeatureFileImporter;
import io.funky.slashroot.transformer.FeatureTransformer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("This is gcgen v1.0! Copyright by slashroot.");
        System.out.println(System.lineSeparator());

        try {
            FeatureFileImporter importer = new FeatureFileImporter();
            FileReader fileReader = new FileReader("/home/slashroot/IdeaProjects/gcgen/src/main/java/io/funky/slashroot/test.feature");
            ArrayList<String> lines = importer.readFile(fileReader);

            for (String line : lines) {
                System.out.println(line);
            }

            FeatureTransformer transformer = new FeatureTransformer();
            transformer.transformSplitedLines(transformer.splitLines(lines));

        } catch (FileNotFoundException e) {
            System.out.println("File not found in path");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not open file for reading!");
            e.printStackTrace();
        }
    }
}
