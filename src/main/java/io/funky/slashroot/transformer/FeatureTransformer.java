package io.funky.slashroot.transformer;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by slashroot on 24.10.16.
 */
public class FeatureTransformer {

    private ArrayList<String> importedLines = new ArrayList<String>();
    private ArrayList<String> classLines = new ArrayList<String>();
    private ArrayList<String> methodLines = new ArrayList<String>();
    private Map<String, ArrayList> importedFile = new HashMap<String, ArrayList>();
    private JavaFile sourceFile;

    public FeatureTransformer() {}

    public Map splitLines(ArrayList<String> importedLines) {

        for (String line : importedLines) {
            if (line.startsWith("Feature")) {
                classLines.add(line);
            }
            else if (line.startsWith("Given") || line.startsWith("When") || line.startsWith("Then") || line.startsWith("And") || line.startsWith("But")) {
                methodLines.add(line);
            }
        }
        System.out.println("=================== Class Lines ===================");
        System.out.println(classLines.toString());
        System.out.println("=================== Method Lines ===================");
        System.out.println(methodLines.toString());

        importedFile.put("clazz", classLines);
        importedFile.put("methods", methodLines);

        return importedFile;
    }

    public void transformSplitedLines(Map<String, ArrayList> importedFile) {

        ArrayList<String> methodz = importedFile.get("methods");
        ArrayList<String> clazzLines = importedFile.get("clazz");
        ArrayList<MethodSpec> gcMethods = new ArrayList<MethodSpec>();

        for (String method : methodz) {

            String gherkinKeyword = method.substring(0,method.indexOf(" "));
            String methodName = method.substring(method.indexOf(" "))
                    .trim()
                    .toLowerCase()
                    .replaceAll("(\\s*)\"(.*)\"(\\s*)", "_")
                    .replaceAll(" ","_");
            String annotationName = method.substring(method.indexOf(" "))
                    .trim()
                    .replaceAll("\"(.*)\"","\"(.*)\"");
            switch (gherkinKeyword) {
                case "Given":
                    System.out.println(this.buildGivenMethod(methodName, annotationName));
                    break;
                case "When":
                    System.out.println(this.buildWhenMethod(methodName, annotationName));
                    break;
                case "Then":
                    System.out.println(this.buildThenMethod(methodName, annotationName));
                    break;
            }

            System.out.println("Keyword: " + gherkinKeyword + "\n" + "methodName: " + methodName + "\n" + "annotationName: " + annotationName);
        }


        if (clazzLines.size() > 1 || clazzLines.isEmpty()) {
            System.out.println("Something went wrong parsing the clazz String!");
        }
        else {
            System.out.println(clazzLines.get(0).replace("Feature:","").toLowerCase().trim().replace(" ","_"));
        }

    }

    private MethodSpec buildGivenMethod(String methodName, String annotationName) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class)
                .addAnnotation(this.buildGivenAnnotation(annotationName))
                .build();
    }

    private MethodSpec buildWhenMethod(String methodName, String annotationName) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class)
                .addAnnotation(this.buildWhenAnnotation(annotationName))
                .build();
    }

    private MethodSpec buildThenMethod(String methodName, String annotationName) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class)
                .addAnnotation(this.buildThenAnnotation(annotationName))
                .build();
    }

    private AnnotationSpec buildGivenAnnotation(String annotationName) {
        String value = annotationName.trim();
        System.out.println(value);

        return AnnotationSpec.builder(Given.class)
                .addMember("value","$S",value)
                .build();
    }

    private AnnotationSpec buildWhenAnnotation(String annotationName) {
        String value = annotationName.trim();

        return AnnotationSpec.builder(When.class)
                .addMember("value","$S",value)
                .build();
    }

    private AnnotationSpec buildThenAnnotation(String annotationName) {
        String value = annotationName.trim();

        return AnnotationSpec.builder(Then.class)
                .addMember("value","$S",value)
                .build();
    }
}
