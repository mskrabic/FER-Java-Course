package hr.fer.oprpp1.custom.scripting;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ParserLexerTest {

							/* ***************************
							 * Testovi za SmartScriptLexer
							 * ***************************/
	@Test
	public void LexerConstructorTest() {
		assertThrows(NullPointerException.class, () -> {
			new SmartScriptLexer(null);
		});
	}

	@Test
	public void LexerGetNullTokenTest() {
		assertThrows(NullPointerException.class, () -> {
			SmartScriptLexer l = new SmartScriptLexer("Test");
			l.getToken();
		});
	}

	@Test
	public void EmptyStringTest() {
		SmartScriptLexer l = new SmartScriptLexer("");
		assertEquals(l.nextToken().getType(), TokenType.EOF);
	}

	@Test
	public void getTokenTest() {
		SmartScriptLexer l = new SmartScriptLexer("Test");
		Token token = l.nextToken();
		assertEquals(token.getType(), l.getToken().getType());
		assertEquals(token.getValue(), l.getToken().getValue());
	}

	@Test
	public void nextTokenAfterEOFTest() {
		SmartScriptLexer l = new SmartScriptLexer("");
		l.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> {
			l.nextToken();
		});
	}
	
							/* ****************************
							 * Testovi za SmartScriptParser
							 * ****************************/
	
	@Test
	public void test1() {
		String example = readExample(1);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		assertTrue(node.numberOfChildren() == 1 && node.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void test2() {
		String example = readExample(2);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		assertTrue(node.numberOfChildren() == 1 && node.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void test3() {
		String example = readExample(3);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		assertTrue(node.numberOfChildren() == 1 && node.getChild(0) instanceof TextNode);
	}
	
	@Test
	public void test4() {
		String example = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));	
	}
	
	@Test
	public void test5() {
		String example = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void test6() {
		String example = readExample(6);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		assertTrue(node.numberOfChildren() == 2);
		assertTrue(node.getChild(0) instanceof TextNode);
		assertTrue(node.getChild(1) instanceof EchoNode);	
	}
	
	@Test
	public void test7() {
		String example = readExample(7);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		assertTrue(node.numberOfChildren() == 2);
		assertTrue(node.getChild(0) instanceof TextNode);
		assertTrue(node.getChild(1) instanceof EchoNode);
	}
	
	@Test
	public void test8() {
		String example = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void test9() {
		String example = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void forAsVariableNameTest() {
		String example = readExample(10);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		Element[] array = new Element[4];
		array[0] = new ElementVariable("for");
		array[1] = new ElementConstantInteger(1);
		array[2] = new ElementString("OK");
		array[3] = new ElementString("prolaz");
		assertTrue(node.numberOfChildren() == 2);
		assertTrue(node.getChild(1) instanceof ForLoopNode);
		assertArrayEquals(((ForLoopNode)node.getChild(1)).getElements(), array);
	}
	
	@Test
	public void emptyEchoNodeTest() {
		String example = readExample(11);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		
		assertTrue(node.numberOfChildren() == 2);
		assertTrue(node.getChild(1) instanceof EchoNode);
	}
	
	@Test
	public void invalidVariableNameTest() {
		String example = readExample(12);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void minusAsAnOperatorTest() {
		String example = readExample(13);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		
		assertTrue(((EchoNode)node.getChild(1)).getElements()[0] instanceof ElementOperator);
	}
	
	@Test
	public void minusAsPartOfNumberTest() {
		String example = readExample(14);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		
		assertTrue(((EchoNode)node.getChild(1)).getElements()[0] instanceof ElementConstantDouble);
	}
	
	@Test
	public void sampleTextFromHWAssignmentTest() {
		String example = readExample(15);
		SmartScriptParser p = new SmartScriptParser(example);
		DocumentNode node = p.getDocumentNode();
		
		assertEquals(node.numberOfChildren(), 4);
		assertTrue(node.getChild(0) instanceof TextNode && ((TextNode)node.getChild(0)).getText().equals("This is sample text.\r\n"));
		assertTrue(node.getChild(1) instanceof ForLoopNode);
			ForLoopNode fln = (ForLoopNode) node.getChild(1);
			assertTrue(fln.numberOfChildren() == 3);
			assertTrue(fln.getChild(0) instanceof TextNode && ((TextNode)fln.getChild(0)).getText().equals("\r\n This is "));
			assertTrue(fln.getChild(1) instanceof EchoNode && ((EchoNode)fln.getChild(1)).getElements()[0] instanceof ElementVariable);
			assertTrue(fln.getChild(2) instanceof TextNode 
					&& ((TextNode)fln.getChild(2)).getText().equals("-th time this message is generated.\r\n"));
		assertTrue(node.getChild(2) instanceof TextNode && ((TextNode)node.getChild(2)).getText().equals("\r\n"));
		assertTrue(node.getChild(3) instanceof ForLoopNode);
			ForLoopNode fln2 = (ForLoopNode) node.getChild(3);
			assertTrue(fln2.numberOfChildren() == 5);
			assertTrue(fln2.getChild(0) instanceof TextNode && ((TextNode)fln2.getChild(0)).getText().equals("\r\n sin("));
			assertTrue(fln2.getChild(1) instanceof EchoNode && ((EchoNode)fln2.getChild(1)).getElements()[0] instanceof ElementVariable);
			assertTrue(fln2.getChild(2) instanceof TextNode && ((TextNode)fln2.getChild(2)).getText().equals("^2) = "));
			assertTrue(fln2.getChild(3) instanceof EchoNode);
			assertTrue(((EchoNode)fln2.getChild(3)).getElements()[0] instanceof ElementVariable);
			assertTrue(((EchoNode)fln2.getChild(3)).getElements()[1] instanceof ElementVariable);
			assertTrue(((EchoNode)fln2.getChild(3)).getElements()[2] instanceof ElementOperator);
			assertTrue(((EchoNode)fln2.getChild(3)).getElements()[3] instanceof ElementFunction);
			assertTrue(((EchoNode)fln2.getChild(3)).getElements()[4] instanceof ElementString);
			assertTrue(((EchoNode)fln2.getChild(3)).getElements()[5] instanceof ElementFunction);
			assertTrue(fln2.getChild(4) instanceof TextNode && ((TextNode)fln2.getChild(4)).getText().equals("\r\n"));

	}
	
	@Test
	public void legalTagsTest() {
		String example = readExample(16);
		SmartScriptParser p = new SmartScriptParser(example);
		
		assertEquals(3, p.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void illegalTagTest1() {
		String example = readExample(17);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void illegalTagTest2() {
		String example = readExample(18);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void illegalTagTest3() {
		String example = readExample(19);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void illegalTagTest4() {
		String example = readExample(20);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void illegalTagTest5() {
		String example = readExample(21);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void illegalTagTest6() {
		String example = readExample(22);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void equalsSymbolAsVariableNameTest() {
		String example = readExample(23);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(example));
	}
	
	@Test
	public void escapingTest1() {
		String example = readExample(24);
		SmartScriptParser p = new SmartScriptParser(example);
		
		assertEquals(2, p.getDocumentNode().numberOfChildren());
		
	}
	
	@Test
	public void escapingTest2() {
		String example = readExample(25);
		SmartScriptParser p = new SmartScriptParser(example);
		
		EchoNode node = (EchoNode) p.getDocumentNode().getChild(0);
		
		assertEquals("Some \\ test X", node.getElements()[0].asText());
		assertEquals("Joe \"Long\" Smith", node.getElements()[1].asText());
	}
	
	@Test
	public void curlyBracketsTest() {
		String example = readExample(26);
		SmartScriptParser p = new SmartScriptParser(example);
		
		assertEquals(1, p.getDocumentNode().numberOfChildren());
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
	}
	
	@Test
	public void curlyBracketsTest2() {
		String example = readExample(27);
		SmartScriptParser p = new SmartScriptParser(example);
		
		assertEquals(2, p.getDocumentNode().numberOfChildren());
		assertTrue(p.getDocumentNode().getChild(0) instanceof TextNode);
		assertTrue(p.getDocumentNode().getChild(1) instanceof EchoNode);
	}

	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
}
