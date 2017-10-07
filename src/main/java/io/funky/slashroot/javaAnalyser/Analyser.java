package io.funky.slashroot.javaAnalyser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Analyser {

    public void printMethodNames(CompilationUnit cu) {
        cu.accept(new MethodVisitor(), null);
    }

    public void printClickMethodNames(CompilationUnit cu) {
        cu.accept(new ClickMethodSearchVisitor(), null);
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }

    private static class ClickMethodSearchVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            this.nameContainsClick(n.getName(),"click");
            super.visit(n, arg);
        }

        public void nameContainsClick(SimpleName methodName, String pattern) {
            if (methodName.toString().toLowerCase().contains(pattern)) {
                System.out.println(methodName);
            }
        }
    }
}
