package io.funky.slashroot.transformer;

import com.squareup.javapoet.AnnotationSpec;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by slashroot on 13.11.16.
 */
public class GlueMethod {

    private AnnotationSpec annotationSpec;

    public GlueMethod() {}

    public GlueMethod(GlueType type, String methodName, String annotationName) {
        switch (type){
            case GIVEN:
                annotationSpec = this.buildGivenAnnotation(annotationName);
                break;
            case WHEN:
                annotationSpec = this.buildWhenAnnotation(annotationName);
                break;
            case THEN:
                annotationSpec = this.buildThenAnnotation(annotationName);
                break;
            case AND:
                annotationSpec = this.buildGivenAnnotation(annotationName);
                break;
            case BUT:
                annotationSpec = this.buildGivenAnnotation(annotationName);
                break;
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
}
