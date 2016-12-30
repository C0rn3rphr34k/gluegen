package io.funky.slashroot.transformer;

import com.squareup.javapoet.MethodSpec;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slashroot on 24.10.16.
 */
public class FeatureTransformer {

    private Map<String, List<String>> importedFiles;

    private FeatureTransformer() {}

    public FeatureTransformer(Map<String,List<String>> importedFiles){
        this.importedFiles = importedFiles;
    }

    public List<GlueClass> transform(){
        List<GlueClass> transformedFiles = new ArrayList<>();

        for (String clazz : importedFiles.keySet()){
            Map<String, List<String>> splittedLines = splitLines(importedFiles.get(clazz));
            GlueClass transformedClass = transformSplitedLines(splittedLines);
            transformedFiles.add(transformedClass);
        }

        return transformedFiles;
    }

    public Map<String, List<String>> splitLines(List<String> importedLines) {
        List<String> classLines = new ArrayList<String>();
        List<String> methodLines = new ArrayList<String>();

        Map<String, List<String>> importedFile = new HashMap<String, List<String>>();

        for (String line : importedLines) {
            if (line.startsWith("Feature")) {
                classLines.add(line.trim()
                        .replaceAll("Feature:","")
                        .replaceAll("Feature: ","")
                        .replaceAll(" ","")
                        .replaceAll(",","_")
                        .toLowerCase());
            }
            else if (line.startsWith("Given") || line.startsWith("When") || line.startsWith("Then") || line.startsWith("And") || line.startsWith("But")) {
                methodLines.add(line);
            }
        }
//        System.out.println("=================== Class Lines ===================");
//        System.out.println(classLines.toString());
//        System.out.println("=================== Method Lines ===================");
//        System.out.println(methodLines.toString());

        importedFile.put("clazz", classLines);
        importedFile.put("methods", methodLines);

        return importedFile;
    }

    public GlueClass transformSplitedLines(Map<String, List<String>> importedFile) {

        List<String> methodz = importedFile.get("methods");
        List<GlueMethod> gcMethods = new ArrayList<GlueMethod>();
        GlueClass transformedClass = new GlueClass();

        for (String method : methodz) {

            String gherkinKeyword = method.substring(0,method.indexOf(" "));
            String methodName = this.sanitizeMethodName(method);
            String annotationName = this.replaceTestDataByRegexp(method);
            Boolean methodExists = false;

            for (GlueMethod existingMethod : gcMethods){
                if (existingMethod.getMethodName().equals(methodName)){
                    methodExists = true;
                    break;
                }
            }

            if (methodExists){
                continue;
            }

            switch (gherkinKeyword) {
                case "Given" :
                    gcMethods.add(new GlueMethod(GlueType.GIVEN,methodName,annotationName));
                    break;
                case "When":
                    gcMethods.add(new GlueMethod(GlueType.WHEN,methodName,annotationName));
                    break;
                case "Then":
                    gcMethods.add(new GlueMethod(GlueType.THEN,methodName,annotationName));
                    break;
                case "And":
                    gcMethods.add(new GlueMethod(GlueType.AND,methodName,annotationName));
                    break;
                case "But":
                    gcMethods.add(new GlueMethod(GlueType.BUT,methodName,annotationName));
            }
        }


        if (importedFile.get("clazz").size() > 1 || importedFile.get("clazz").isEmpty()) {
            System.out.println("Something went wrong parsing the clazz String!");
        }
        else {
            transformedClass.setFilename(importedFile.get("clazz").get(0));
            transformedClass.addMethods(gcMethods);
            //            System.out.println(clazzLines.get(0).replace("Feature:","").toLowerCase().trim().replace(" ","_"));
        }
        return transformedClass;
    }

    private String sanitizeMethodName(String methodName){
        return methodName.substring(methodName.indexOf(" "))
                .trim()
                .toLowerCase()
                .replaceAll("(\\s*)\"(.*)\"(\\s*)", "_")
                .replaceAll(" ","_")
                .replaceAll("\\W","")
                .trim();
    }

    private String replaceTestDataByRegexp(String annotationValue){
        return annotationValue.substring(annotationValue.indexOf(" "))
                .trim()
                .replaceAll("\"(.*)\"","\"(.*)\"");
    }
}
