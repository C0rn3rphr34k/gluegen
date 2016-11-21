package io.funky.slashroot.importer;

import sun.misc.Regexp;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slashroot on 22.10.16.
 */
public class FeatureFileImporter {

    public Map<String,List<String>> readFile(File path) throws IOException {
        Map<String, List<String>> readFiles = new HashMap<>();
        List<File> fileList = new ArrayList<>();

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".feature")){
                    return true;
                }
                return false;
            }
        };

        if (path.isDirectory()){
            String[] files = path.list(filter);
            for (String file : files){
                File currentFile = new File(path,file);
                fileList.add(currentFile);
            }
        } else {
            fileList.add(path);
        }

        for (File file : fileList){
            if (!file.canRead()) {
                System.out.println("Can't read file " + file.toString() + ". Check permissions!");
                continue;
            }
            if (file.isDirectory()) {
                System.out.println(file.toString() + " is a directory! Skipping.");
                continue;
            }
            List<String> currentFileLines = readFileLines(file);
            readFiles.put(currentFileLines.get(0),currentFileLines);
        }

        return readFiles;

//        ArrayList<String> lines = new ArrayList<String>();
//        BufferedReader br = new BufferedReader(fileReader);
//        try {
//            String line = br.readLine();
//
//            while (line != null) {
//                lines.add(line.replace("\t",""));
//                line = br.readLine();
//            }
//
//        } catch (IOException e) {
//            System.out.println("Couldn't read from file. Make sure it's in the right place and may be read by your user.");
//            e.printStackTrace();
//        } finally {
//            br.close();
//        }
//
//        return lines;
    }

    private List<String> readFileLines(File currentFile){
        List<String> linesRead = new ArrayList<>();

        try {
            FileReader fr = new FileReader(currentFile);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();

            while (line != null){
                linesRead.add(line.trim());
                line = br.readLine();
            }
        } catch (FileNotFoundException e){
            System.out.println("File " + currentFile.toString() + " not found!");
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Could not read line from file: " + currentFile.toString());
            e.printStackTrace();
        }

        return linesRead;
    }
}
