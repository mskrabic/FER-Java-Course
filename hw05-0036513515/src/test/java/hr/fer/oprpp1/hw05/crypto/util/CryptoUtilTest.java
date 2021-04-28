package hr.fer.oprpp1.hw05.crypto.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CryptoUtilTest {
	
	@Test
	public void hexToByteTest1() {
		
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hexToByte("01aE22"));
		
	}
	
	@Test
	public void hexToByteTest2() {
		
		assertArrayEquals(new byte[] {-85, -51, -17}, Util.hexToByte("ABCDEF"));
		
	}
	
	@Test
	public void hexToByteTest3() {
		
		assertArrayEquals(new byte[] {49, 46, 32, 79, 98, 106, 101, 99}, Util.hexToByte("312E204F626a6563"));
	}
	
	@Test
	public void hexToByteTest4() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hexToByte("000");
		});
	}
	
	@Test
	public void hexToByteTest5() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hexToByte("Z3aE22");
		});
		
	}
	
	@Test
	public void hexToByteTest6() {
		assertArrayEquals(new byte[0], Util.hexToByte(""));
	}
	
	@Test
	public void byteToHexTest1() {
		
		assertEquals("01ae22", Util.byteToHex(new byte[] {1, -82, 34}));
	}
	
	@Test
	public void byteToHexTest2() {
		
		assertEquals("abcdef", Util.byteToHex(new byte[] {-85, -51, -17}));
	}
	
	@Test
	public void byteToHexTest3() {
		
		assertEquals("312e204f626a6563", Util.byteToHex(new byte[] {49, 46, 32, 79, 98, 106, 101, 99}));
		
	}
	
	@Test
	public void byteToHexTest4() {
		
		assertEquals("", Util.byteToHex(new byte[0]));
		
	}

}
