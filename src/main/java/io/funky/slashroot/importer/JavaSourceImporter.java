package io.funky.slashroot.importer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JavaSourceImporter {

    String sourceFilePath = null;

    public JavaSourceImporter(){}

    public JavaSourceImporter(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public CompilationUnit read() {
        try {
            FileReader fileReader = new FileReader(sourceFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            CompilationUnit compilationUnit = JavaParser.parse(bufferedReader);

            return compilationUnit;
        } catch(FileNotFoundException e) {
            System.out.println(String.format("File %s not found.",sourceFilePath));
        }
        return null;
    }

    public CompilationUnit read(String sourceFilePath){
        this.sourceFilePath = sourceFilePath;

        return this.read();
    }
}
