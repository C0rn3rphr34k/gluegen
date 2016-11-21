package io.funky.slashroot.transformer;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import cucumber.api.java.en.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slashroot on 13.11.16.
 */
public class GlueMethod {

    private MethodSpec methodSpec;

    public GlueMethod() {}

    public GlueMethod(GlueType type, String methodName, String annotationName) {
        Integer parameterCount = 0;

        int index = annotationName.indexOf("(.*)");
        while(index >= 0){
            parameterCount++;
            index = annotationName.indexOf("(.*)",index+1);
        }

        if (parameterCount > 0){
            methodSpec = buildMethodWithParameters(type, methodName, annotationName, parameterCount);
        } else {
            methodSpec = buildMethodWithoutParameter(type, methodName, annotationName);
        }
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

    private AnnotationSpec buildAnnotation(GlueType annotationType, String annotationName) {

        AnnotationSpec annotationSpec = null;

        switch (annotationType){
            case GIVEN:
                annotationSpec = AnnotationSpec.builder(Given.class)
                        .addMember("value","$S",annotationName)
                        .build();
                break;
            case WHEN:
                annotationSpec = AnnotationSpec.builder(When.class)
                        .addMember("value","$S",annotationName)
                        .build();
                break;
            case THEN:
                annotationSpec = AnnotationSpec.builder(Then.class)
                        .addMember("value","$S",annotationName)
                        .build();
                break;
            case AND:
                annotationSpec = AnnotationSpec.builder(And.class)
                        .addMember("value","$S",annotationName)
                        .build();
                break;
            case BUT:
                annotationSpec = AnnotationSpec.builder(But.class)
                        .addMember("value","$S",annotationName)
                        .build();
                break;
        }
        return annotationSpec;
    }

    private MethodSpec buildMethodWithoutParameter(GlueType type, String methodName, String annotationName) {
        MethodSpec methodWithoutParameter = MethodSpec.methodBuilder(methodName)
                .addAnnotation(this.buildAnnotation(type, annotationName))
                .addStatement("//Add your Code here")
                .addStatement("throw new PendingException()")
                .build();

        return methodWithoutParameter;
    }

    private MethodSpec buildMethodWithParameters(GlueType type, String methodName, String annotationName, Integer parameterCount) {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();

        for (int i = 1; i <= parameterCount; i++) {
            parameterSpecs.add(ParameterSpec.builder(String.class, "arg" + i)
            .build());
        }

        MethodSpec methodWithParameters = MethodSpec.methodBuilder(methodName)
                .addAnnotation(this.buildAnnotation(type, annotationName))
                .addParameters(parameterSpecs)
                .addStatement("//Add your Code here")
                .addStatement("throw new PendigException()")
                .build();

        return methodWithParameters;
    }

    public void printMethod() {
        System.out.println(methodSpec.toString());
        System.out.println("================================");
    }

    public MethodSpec getMethodSpec() {
        return methodSpec;
    }
}
