package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import cfg.CFG;
import org.junit.jupiter.api.Test;

import ast.*;

class CreateAST {

	@Test
	void test1() {
		MethodNode m = new MethodNode("m1");
		AST ast = new AST(m);
		for(int i=0; i < 3; i++) {
			Node n = new Node("n_" + i);
			m.addNode(n);
		}
		ast.toDot();
		CFG cfg = new CFG();
		cfg.fromAST(ast);
		cfg.toDot("m1");
	}
	
	
	@Test
	void test2() {
		MethodNode m = new MethodNode("m2");
		AST ast = new AST(m);
		Node n = new Node("n_1");
		m.addNode(n);
		List<Node> truB = new ArrayList<Node>();
		for(int i=2; i < 5; i++) {
			n = new Node("n_" + i);
			truB.add(n);
		}
		IfNode ifN = new IfNode("if_1");
		ifN.setTrueBlock(truB);
		m.addNode(ifN);
		ast.toDot();
		CFG cfg = new CFG();
		cfg.fromAST(ast);
		cfg.toDot("m2");
	}
	
	@Test
	void test3() {
		MethodNode m = new MethodNode("m3");
		AST ast = new AST(m);
		Node n = new Node("n_1");
		m.addNode(n);
		IfNode ifN = new IfNode("if_1");
		List<Node> b = new ArrayList<Node>();
		for(int i=2; i < 5; i++) {
			n = new Node("n_" + i);
			b.add(n);
		}
		ifN.setTrueBlock(b);
		b = new ArrayList<Node>();
		for(int i=6; i < 8; i++) {
			n = new Node("n_" + i);
			b.add(n);
		}
		n = new ReturnNode("r_8");
		b.add(n);
		ifN.setFalseBlock(b);
		m.addNode(ifN);
		ast.toDot();
		CFG cfg = new CFG();
		cfg.fromAST(ast);
		cfg.toDot("m3");
	}
	
	@Test
	void test4() {
		MethodNode m = new MethodNode("m4");
		AST ast = new AST(m);
		Node n = new Node("n_1");
		m.addNode(n);
		IfNode ifN = new IfNode("if_1");
		List<Node> b = new ArrayList<Node>();
		for(int i=2; i < 5; i++) {
			if(i==3) {
			 n = new WhileNode("while_" + i);
			 List<Node> whileB = new ArrayList<Node>();
			 for(int j=1; j < 3; j++) {
				 Node wB = new Node("wn_"+j);
				 whileB.add(wB);
			 }
			 ((WhileNode)n).addBlock(whileB);
			} else {
				n = new Node("n_" + i);
			}
			b.add(n);
		}
		ifN.setTrueBlock(b);
		b = new ArrayList<Node>();
		for(int i=6; i < 8; i++) {
			n = new Node("n_" + i);
			b.add(n);
		}
		ifN.setFalseBlock(b);
		m.addNode(ifN);
		ast.toDot();
		CFG cfg = new CFG();
		cfg.fromAST(ast);
		cfg.toDot("m4");
	}


}
