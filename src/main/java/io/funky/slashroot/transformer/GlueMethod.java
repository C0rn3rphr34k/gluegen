package io.funky.slashroot.transformer;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import cucumber.api.java.en.*;

/**
 * Created by slashroot on 13.11.16.
 */
public class GlueMethod {

    public GlueMethod() {}

    public GlueMethod(GlueType type, String methodName, String annotationName) {
        System.out.println(MethodSpec.methodBuilder(methodName)
                .addAnnotation(this.buildAnnotation(type, annotationName))
                .addStatement("//Add your Code here")
                .addStatement("throw new PendingException()")
                .build()
                .toString());
        System.out.println("===============================================");
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
}
