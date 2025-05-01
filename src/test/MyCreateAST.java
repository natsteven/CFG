package test;

import ast.*;
import cfg.CFG;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MyCreateAST {

    // only one statement
    @Test
    void test5() {
        MethodNode m = new MethodNode("m5");
        AST ast = new AST(m);

        m.addNode(new Node("n_1"));

        ast.toDot();

        testCFG(ast);
    }

    // nested if statement
    @Test
    void test6() {
        MethodNode m = new MethodNode("m6");
        AST ast = new AST(m);
        Node n = new Node("n_1");
        m.addNode(n);
        IfNode ifNode1 = new IfNode("if_1");
        IfNode ifNode2 = new IfNode("if_2");

        ArrayList<Node> truB1 = new ArrayList<Node>();
        ArrayList<Node> truB2 = new ArrayList<Node>();
        ArrayList<Node> falB1 = new ArrayList<Node>();
        ArrayList<Node> falB2 = new ArrayList<Node>();

        truB1.add(new Node("n_2"));
        truB1.add(ifNode2);
        truB2.add(new Node("n_3"));
        falB2.add(new Node("n_4"));
        truB1.add(new Node("n_5"));
        falB1.add(new Node("n_6"));

        ifNode1.setTrueBlock(truB1);
        ifNode1.setFalseBlock(falB1);
        ifNode2.setTrueBlock(truB2);
        ifNode2.setFalseBlock(falB2);
        m.addNode(ifNode1);
        m.addNode(new Node("n_7"));

        ast.toDot();

        testCFG(ast);
    }

    // nested while
    @Test
    void test7() {
        MethodNode m = new MethodNode("m7");
        AST ast = new AST(m);
        WhileNode whileNode1 = new WhileNode("while_1");
        WhileNode whileNode2 = new WhileNode("while_2");
        ArrayList<Node> truB1 = new ArrayList<Node>();
        ArrayList<Node> truB2 = new ArrayList<Node>();

        truB1.add(new Node("w1n_1"));
        truB1.add(whileNode2);
        truB2.add(new Node("w2n_1"));
        truB2.add(new Node("w2n_2"));
        truB1.add(new Node("w1n_2"));

        whileNode1.addBlock(truB1);
        whileNode2.addBlock(truB2);

        m.addNode(whileNode1);
        m.addNode(new Node("n_1"));

        ast.toDot();

        testCFG(ast);
    }

    // if inside while
    @Test
    void test8() {
        MethodNode m = new MethodNode("m8");
        AST ast = new AST(m);
        WhileNode whileNode = new WhileNode("while_1");
        IfNode ifNode = new IfNode("if_1");
        ArrayList<Node> whileB = new ArrayList<Node>();
        ArrayList<Node> truB = new ArrayList<Node>();
        ArrayList<Node> falB = new ArrayList<Node>();

        whileB.add(new Node("wn_1"));
        whileB.add(ifNode);
        whileB.add(new Node("wn_2"));
        truB.add(new Node("n_1"));
        truB.add(new Node("n_2"));
        falB.add(new Node("n_3"));

        whileNode.addBlock(whileB);
        ifNode.setTrueBlock(truB);
        ifNode.setFalseBlock(falB);

        m.addNode(whileNode);
        m.addNode(new Node("n_4"));

        ast.toDot();

        testCFG(ast);
    }

    // if in while with no buffer statements
    @Test
    void test9() {
        MethodNode m = new MethodNode("m9");
        AST ast = new AST(m);
        WhileNode whileNode = new WhileNode("while_1");
        IfNode ifNode = new IfNode("if_1");
        ArrayList<Node> whileB = new ArrayList<Node>();
        ArrayList<Node> truB = new ArrayList<Node>();
        ArrayList<Node> falB = new ArrayList<Node>();

        whileB.add(new Node("wn_1"));
        whileB.add(ifNode);
        truB.add(new Node("n_1"));
        falB.add(new Node("n_2"));

        whileNode.addBlock(whileB);
        ifNode.setTrueBlock(truB);
        ifNode.setFalseBlock(falB);

        m.addNode(whileNode);
        m.addNode(new Node("n_3"));

        ast.toDot();

        testCFG(ast);

    }

    // while no nodes after
    @Test
    void test10() {
        MethodNode m = new MethodNode("m10");
        AST ast = new AST(m);
        WhileNode whileNode = new WhileNode("while_1");
        ArrayList<Node> whileB = new ArrayList<Node>();
        whileB.add(new Node("wn_1"));
        whileB.add(new Node("wn_2"));
        whileNode.addBlock(whileB);
        m.addNode(whileNode);

        ast.toDot();

        testCFG(ast);


    }

    // only if
    @Test
    void test11() {
        MethodNode m = new MethodNode("m11");
        AST ast = new AST(m);
        IfNode ifNode = new IfNode("if_1");
        ArrayList<Node> truB = new ArrayList<Node>();
        truB.add(new Node("n_1"));
        ifNode.setTrueBlock(truB);
        m.addNode(ifNode);

        ast.toDot();
        testCFG(ast);


    }

    // return in true block
    @Test
    void test12() {
        MethodNode m = new MethodNode("m12");
        AST ast = new AST(m);
        IfNode ifNode = new IfNode("if_1");
        ArrayList<Node> truB = new ArrayList<Node>();
        ArrayList<Node> falB = new ArrayList<Node>();
        truB.add(new Node("n_1"));
        truB.add(new ReturnNode("r_1"));
        falB.add(new Node("n_2"));
        ifNode.setTrueBlock(truB);
        ifNode.setFalseBlock(falB);
        m.addNode(ifNode);
        m.addNode(new Node("n_3"));

        ast.toDot();

        testCFG(ast);

    }

    // return in while loop without conditional ? // e.g. if using while
    @Test
    void test13() {
        MethodNode m = new MethodNode("m13");
        AST ast = new AST(m);
        WhileNode whileNode = new WhileNode("while_1");
        ArrayList<Node> whileB = new ArrayList<Node>();
        whileB.add(new Node("wn_1"));
        whileB.add(new ReturnNode("r_1"));
        whileNode.addBlock(whileB);
        m.addNode(whileNode);
        m.addNode(new Node("n_1"));

        ast.toDot();
        testCFG(ast);
    }

    // return in while loop with conditional
    @Test
    void test14() {
        MethodNode m = new MethodNode("m14");
        AST ast = new AST(m);
        WhileNode whileNode = new WhileNode("while_1");
        IfNode ifNode = new IfNode("if_1");
        ArrayList<Node> whileB = new ArrayList<Node>();
        ArrayList<Node> truB = new ArrayList<Node>();
        whileB.add(new Node("wn_1"));
        whileB.add(ifNode);
        truB.add(new ReturnNode("r_1"));
        whileB.add(new Node("wn_2"));
        whileNode.addBlock(whileB);
        ifNode.setTrueBlock(truB);

        m.addNode(whileNode);
        m.addNode(new Node("n_1"));

        ast.toDot();
        testCFG(ast);
    }



    // small helper
    public void testCFG(AST ast) {
        // test CFG
        CFG cfg = new CFG();
        cfg.fromAST(ast);
        cfg.toDot(ast.getRoot().getName());
    }
}