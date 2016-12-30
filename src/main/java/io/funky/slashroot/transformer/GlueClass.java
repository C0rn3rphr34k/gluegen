package io.funky.slashroot.transformer;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GlueClass {
    private String filename;
    private List<GlueMethod> methodz = new ArrayList<>();
    private TypeSpec typeSpec;

    public GlueClass() {}

    public GlueClass(String className){
        if (className==null || className.isEmpty()){
            System.out.println("Filename may not be empty!");
        } else {
            this.filename = className;
            typeSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .build();
        }
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public void addMethod(GlueMethod method) {
        if (method!=null) {
            methodz.add(method);
        }
    }

    public void addMethods(List<GlueMethod> methods) {
        if (!methods.isEmpty()) {
            methodz = methods;
        }
    }

    public void printClass() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (GlueMethod method : methodz) {
            methodSpecs.add(method.getMethodSpec());
        }

        typeSpec = TypeSpec.classBuilder(filename)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addMethods(methodSpecs)
                .build();

        System.out.println("======================================");

        System.out.println(typeSpec.toString());

        System.out.println("======================================");
    }

    public void writeClass() throws IOException{
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (GlueMethod method : methodz) {
            methodSpecs.add(method.getMethodSpec());
        }

        typeSpec = TypeSpec.classBuilder(filename)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addMethods(methodSpecs)
                .build();

        JavaFile classFile = JavaFile.builder("io.funky.slashroot.gcgen.generated",typeSpec)
                .build();

        classFile.writeTo(System.out);
    }

    public void writeClass(File path) throws IOException{
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (GlueMethod method : methodz) {
            methodSpecs.add(method.getMethodSpec());
        }

        typeSpec = TypeSpec.classBuilder(filename)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addMethods(methodSpecs)
                .build();

        JavaFile classFile = JavaFile.builder("io.funky.slashroot.gcgen.generated",typeSpec)
                .build();

        FileWriter fw = new FileWriter(path + "/" + filename + ".java");
        fw.write(classFile.toString());
        fw.close();
    }
}
