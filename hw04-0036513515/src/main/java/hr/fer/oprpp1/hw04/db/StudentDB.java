package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Razred koji se koristi za pristup bazi podataka.
 * 
 * @author mskrabic
 *
 */
public class StudentDB {
	
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/db/database.txt"), StandardCharsets.UTF_8);
		StudentDatabase database = new StudentDatabase(lines);
		
		run(database);
	}
	
	/**
	 * Metoda čita upite koje korisnik zadaje preko konzole i provodi ih nad bazom podataka.
	 * 
	 * @param db baza podataka.
	 */
	private static void run(StudentDatabase db) {
		Scanner sc = new Scanner(System.in);
		String line;
			
		while (true) {
			System.out.print("> ");
			line = sc.nextLine();
			
			if (line.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			List<StudentRecord> records = query(line, db);
			print(records);
			System.out.println("Records selected: " + records.size() + "\n");
		}
		
		sc.close();
	}
	
	/**
	 * Metoda provodi zadani upit nad zadanom bazom podataka.
	 * 
	 * @param line upit.
	 * @param database baza podataka.
	 * 
	 * @return zapisi iz baze podataka koji zadovoljavaju predani upit.
	 * 
	 * @throws RuntimeException ako se preda neispravna naredba.
	 */
	private static List<StudentRecord> query(String line, StudentDatabase database) {
			if (!line.startsWith("query"))
				throw new RuntimeException("Invalid command: " + line);

			line = line.substring(6);
			QueryParser p = new QueryParser(line);
			if (p.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
			}
			return database.filter(new QueryFilter(p.getQuery()));
	}
	
	/**
	 * Pomoćna metoda za ispis zapisa baze podataka.
	 * 
	 * @param records zapisi koje treba ispisati.
	 */
	private static void print(List<StudentRecord> records) {
		if (records.size() == 0)
			return;
		int maxFirstNameLength = records.stream().mapToInt(r -> r.getFirstName().length()).max().getAsInt();
		int maxLastNameLength = records.stream().mapToInt(r -> r.getLastName().length()).max().getAsInt();
		int maxJmbagLength = records.stream().mapToInt(r -> r.getJmbag().length()).max().getAsInt();
		
		String header = RecordsFormatter.createHeader(maxJmbagLength, maxLastNameLength, maxFirstNameLength);
		List<String> output = RecordsFormatter.format(records, maxJmbagLength, maxLastNameLength, maxFirstNameLength);
		
		System.out.println(header);
		output.forEach(System.out::println);
		System.out.println(header);	
	}

	/**
	 * Pomoćni razred koji se koristi za formatiranje ispisa.
	 * 
	 * @author mskrabic
	 *
	 */
	private static class RecordsFormatter {
		
		/**
		 * Metoda prevodi ulaznu listu <code>StudentRecord</code>-a u listu stringova zadanog formata.
		 * 
		 * @param records zapisi o studentima.
		 * @param longestJMBAG duljina najduljeg JMBAGa među zapisima.
		 * @param longestLastName duljina najduljeg prezimena među zapisima.
		 * @param longestFirstName duljina najduljeg imena među zapisima.
		 * 
		 * @return lista formatiranih stringova koji predstavljaju zapise o studentima.
		 */
		private static List<String> format(List<StudentRecord> records, int longestJMBAG, int longestLastName, int longestFirstName) {
			List<String> result = new ArrayList<>();
			
			for (StudentRecord r : records) {
				StringBuilder row = new StringBuilder();
				row.append("| ");
				row.append(r.getJmbag());
				for (int i = 0; i < longestJMBAG - r.getJmbag().length(); i++) {
					row.append(" ");
				}
				row.append(" | ");
				row.append(r.getLastName());
				for (int i = 0; i < longestLastName - r.getLastName().length(); i++) {
					row.append(" ");
				}
				row.append(" | ");
				row.append(r.getFirstName());
				for (int i = 0; i < longestFirstName - r.getFirstName().length(); i++) {
					row.append(" ");
				}
				row.append(" | ");
				row.append(r.getFinalGrade());
				row.append(" |");
				result.add(row.toString());
			}
			return result;
		}
		
		/**
		 * Metoda stvara odgovarajuće zaglavlje za ispis zapisa iz baze podataka.
		 * 
		 * @param records zapisi o studentima.
		 * @param longestJMBAG duljina najduljeg JMBAGa među zapisima.
		 * @param longestLastName duljina najduljeg prezimena među zapisima.
		 * @param longestFirstName duljina najduljeg imena među zapisima.
		 * 
		 * @return zaglavlje za formatirani ispis zapisa.
		 */
		private static String createHeader(int longestJMBAG, int longestLastName, int longestFirstName) {
			StringBuilder sb = new StringBuilder();
			sb.append("+");
			for (int i = 0; i < longestJMBAG+2; i++) {
				sb.append("=");
			}
			sb.append("+");
			for (int i = 0; i < longestLastName+2; i++) {
				sb.append("=");
			}
			sb.append("+");
			for (int i = 0; i < longestFirstName+2; i++) {
				sb.append("=");
			}
			sb.append("+");
			for (int i = 0; i < 3; i++) {
				sb.append("=");
			}
			sb.append("+");
			
			return sb.toString();
		};
	}
}
