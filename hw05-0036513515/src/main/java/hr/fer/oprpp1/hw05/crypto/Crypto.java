package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.oprpp1.hw05.crypto.util.Util;

/**
 * Razred koji se koristi za kriptiranje/dekriptiranje datoteka algoritmom AES i izračun MD-a datoteke.
 * @author mskrabic
 *
 */
public class Crypto {
	
	/**
	 * Glavna funkcija koja pokreće izvođenje programa.
	 * 
	 * @param args String koji određuje koju akciju treba izvršiti: "checksha" za provjeru MD-a, "encrypt" za kriptiranje i "decrypt" za dekriptiranje.
	 * 
	 * @throws IOException u slučaju pogreške kod pristupa datotekama za kriptiranje/dekriptiranje.
	 * @throws IllegalArgumentException ako se preda neispravan argument ili neispravan broj argumenata.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Invalid number of arguments: " + args.length);
		}
		
		String action = args[0];
		if (action.equals("checksha")) {
			checkSHA(Paths.get(args[1]));
		} else if (action.equals("encrypt") || action.equals("decrypt")) {
			try {
				crypto(action, Paths.get(args[1]), Paths.get(args[2]));
			} catch (Exception e) {
				throw new CryptoException(e.getMessage());
			}
		} else {
			throw new IllegalArgumentException("Invalid action! Supported actions are: checksha, encrypt and decrypt."
					+ "You entered: "+ args[0] + ".");
		}
	}

	/**
	 * Metoda koja ovisno o naredbi zadanoj putem komandne linije kriptira ili dekriptira datoteku.
	 * 
	 * @param action "encrypt" za kriptiranje ili "decrypt" za dekriptiranje.
	 * @param inputFile datoteka nad kojom se želi provesti akcija.
	 * @param outputFile izlazna datoteka.
	 * 
	 * @throws IOException u slučaju pogreške pri pisanju/čitanju datoteka.
	 * @throws IllegalBlockSizeException u slučaju pogreške pri provođenju akcije (neispravna veličina bloka).
	 * @throws BadPaddingException u slučaju pogreške pri provođenju akcije (neispravan padding).
	 */
	private static void crypto(String action, Path inputFile, Path outputFile) throws IOException, IllegalBlockSizeException, BadPaddingException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = sc.nextLine();
		sc.close();
		
		SecretKeySpec keySpec;
		AlgorithmParameterSpec paramSpec;
		Cipher cipher;
		InputStream is = null;
		OutputStream os = null;
		try {
			keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
			paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(action.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			is = Files.newInputStream(inputFile);
			os = Files.newOutputStream(outputFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (Exception e) {
			throw new CryptoException(e.getMessage());
		}
		
		byte[] buff = new byte[4096];
		byte[] result = new byte[4096];
		int k;
		while (true) {
			k = is.read(buff);
			if (k == -1)
				break;
			result = cipher.update(Arrays.copyOf(buff, k));
			if (result != null) {
				os.write(result);
			}
		}
		result = cipher.doFinal();
		os.write(result);
		is.close();
		os.close();
		
		String task = (action.equals("encrypt")) ? "Encryption" : "Decryption";
		System.out.println(task + " completed. Generated file: "
				+ outputFile.getFileName() + " based on file " + inputFile.getFileName() + ".");
		return;
		
	}

	/**
	 * Metoda računa message digest (MD) datoteke.
	 * 
	 * @param file datoteka čiji se MD želi izračunati.
	 * 
	 * @throws IOException u slučaju greške pri čitanju datoteke.
	 */
	private static void checkSHA(Path file) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide expected sha-256 digest for " + file.getFileName() + ":");
		System.out.print("> ");
		byte[] expected = Util.hexToByte(sc.nextLine());
		sc.close();
		
		MessageDigest md = null;
		InputStream is = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			is = Files.newInputStream(file);
		} catch (Exception e) {
			throw new CryptoException(e.getMessage());
		}
		
		byte[] buff = new byte[4096];
		int k;
		while (true) {
			k = is.read(buff);
			if (k == -1)
				break;
			md.update(Arrays.copyOf(buff, k));
		}
		is.close();
		byte[] result = md.digest();
		
		if (Arrays.compare(expected, result) == 0) {
			System.out.println("Digesting completed. Digest of " + file.getFileName()
								+ " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + file.getFileName()
				+ " does not match the expected digest. Digest was: " + Util.byteToHex(result));
		}
		
		
	}
}
