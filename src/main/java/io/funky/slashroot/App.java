package io.funky.slashroot;

import io.funky.slashroot.importer.FeatureFileImporter;
import io.funky.slashroot.transformer.FeatureTransformer;
import io.funky.slashroot.transformer.GlueClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args ){
        File path = null;
        String packageName = null;
        List<GlueClass> finalClasses;

        System.out.println("This is gcgen v1.0!\nThe experimental Glue Code Generator for Cucumber for Java.\nCopyright by slashroot.");
        System.out.println("");
        for (int i = 0; i < args.length; i++){
            switch (args[i]){
                case "-f":
                    path = new File(args[i+1]);
                    System.out.println("Found command line parameter: " + path.toString());
                    i++;
                    break;
                case "-p":
                    packageName = args[i+1];
                    System.out.println("Found package name: " + packageName);
                    i++;
                    break;
            }
        }

        if (path==null){
            path = new File("/home/slashroot/IdeaProjects/gcgen/feature_Files/");
            System.out.println("Usage: java -jar gcgen.jar -f path/to/file");
            System.out.println("Didn't find an explicit file parameter, using: " + path.toString());
        }

        try {
            FeatureFileImporter importer = new FeatureFileImporter();
            Map<String, List<String>> readfiles = importer.readFile(path);

            FeatureTransformer transformer = new FeatureTransformer(readfiles);
            finalClasses = transformer.transform();

            for (GlueClass clazz : finalClasses) {
                clazz.writeClass(path);
            }

//            for (String line : readfiles.keySet()) {
//                List<String> currentFileLines = readfiles.get(line);
//                System.out.println("==============================");
//                for (String currentLine : currentFileLines) {
//                    System.out.println(currentLine);
//                }
//            }
        } catch (IOException e) {
            System.out.println("Could not open file for reading!");
            e.printStackTrace();
        }
    }
}
