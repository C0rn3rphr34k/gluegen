package io.funky.slashroot.importer;

import sun.misc.Regexp;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by slashroot on 22.10.16.
 */
public class FeatureFileImporter {

    public ArrayList<String> readFile(FileReader fileReader) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader br = new BufferedReader(fileReader);
        try {
            String line = br.readLine();

            while (line != null) {
                lines.add(line.replace("\t",""));
                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Couldn't read from file. Make sure it's in the right place and may be read by your user.");
            e.printStackTrace();
        } finally {
            br.close();
        }

        return lines;
    }
}
