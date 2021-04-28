package hr.fer.oprpp1.hw02;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

/**
 * Razred koji se koristi za provjeru rada razreda <code>SmartScriptParser</code>.
 * Šalje mu se put do ulazne datoteke kao argument preko komandne linije.
 *
 */
public class SmartScriptTester {
	public static void main(String[] args) throws IOException {
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		boolean same = document.equals(document2);
		if (same) {
			System.out.println("Success!");
		} else {
			System.out.println("Fail!");
		}
	}
}

