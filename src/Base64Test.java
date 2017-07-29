import static org.junit.Assert.*;
import org.junit.Test;

public class Base64Test {

	@Test
	public void base64Test() {
		String test1 = "AST";
		String answer1 = "QVNU";
		
		String test2 = "A";
		String answer2 = "QQ==";
		
		String test3 = "AS";
		String answer3 = "QVM=";
		
		assertEquals(answer1, Base64.encode(test1));		
		assertEquals(answer2, Base64.encode(test2));
		assertEquals(answer3, Base64.encode(test3));
		
		assertEquals(test1, Base64.decode(answer1));	
		assertEquals(test2, Base64.decode(answer2));	
		assertEquals(test3, Base64.decode(answer3));	
	}

}
