
public class Base64 {

	// Base64 Alphabet table
	private static char base64_table[] = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
		
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	
	public static String encode(String input)
	{
		if(input == null || input.length() < 1)
		{
			return "";
		}
		
		byte [] in = input.getBytes();
		
		// Number of pairs in encode
		int partLen = (input.length() + 2) / 3;
		char [] result = new char[partLen * 4];
		
		//encode pair by pair, each pair contains three chars
		for(int i = 0; i < partLen; i++)
		{
			int index = 0;
			
			//get first encode char in the pair
			result[i * 3] = base64_table[in[i*3] >>> 2];
			
			//get second encode char in the pair
			if(i * 3 + 1 < input.length())
			{
				index = ((in[i*3] & 0x3) << 4 | (in[i*3 + 1] & 0xF0) >>> 4) & 0xFF;
				result[i*3 + 1] = base64_table[index];
			}
			else
			{
				//if last pair only has one chars, fill with '=' and exit
				index = ((in[i*3] & 0x3) << 4) & 0xFF;
				result[i*3 + 1] = base64_table[index];
				result[i*3 + 2] = '=';
				result[i*3 + 3] = '=';
				break;
			}
			
			//get third encode char in the pair
			if(i * 3 + 2 < input.length())
			{
				index = ((in[i*3 + 1] & 0xf) << 2 | (in[i*3 + 2] & 0xF0) >>> 6) & 0xFF;
				result[i*3 + 2] = base64_table[index];
			}
			else
			{
				//if last pair only has two chars, fill with '=' and exit
				index = ((in[i*3 + 1] & 0xf) << 2) & 0xFF;
				result[i*3 + 2] = base64_table[index];
				result[i*3 + 3] = '=';
				break;
			}
			
			//get last encode char in the pair
			index = ((in[i*3 + 2] & 0x3F)) & 0xFF;
			result[i * 3 + 3] = base64_table[index];
		} 
		
		
		return String.valueOf(result);
	}
	
	public static String decode(String input)
	{
		if(input == null || input.length() < 1 || input.length() % 4 != 0)
		{
			return "";
		}
		
		byte [] in = input.getBytes();
		
		// convert ASCII to base64
		for(int i = 0; i < input.length(); i++)
		{
			if(in[i] >= 'A' && in[i] <= 'Z' )
			{
				in[i] -= 'A';
			}
			
			if(in[i] >= 'a' && in[i] <= 'z' )
			{
				in[i] -= 'a' + 26;
			}
				
			if(in[i] >= '0' && in[i] <= '9')
			{
				in[i] -= '0' + 52;
			}
		}
		
		// Number of pairs in decode
		int partLen = input.length() / 4;
		char [] result = new char[partLen * 3];
		int endIndex = result.length;
		
		// Decode pair by pair, each pair contains for chars
		for(int i = 0; i < partLen; i++)
		{	
			// Get first decode character 
			result[i*4] = (char) ((in[i*4] << 2 | in[i*4 + 1] >>> 4) & 0xFF);
			
			// Get second decode character
			if(in[i*4 + 2] != '=')
			{
				result[i*4 + 1] = (char) ((in[i*4 + 1] << 4 | in[i*4 + 2] >>> 2) & 0xFF);
			}
			else
			{
				// two '='s at end, ignore last two characters in the result and exit
				endIndex -= 2;
				break;
			}
			
			// Get third decode character
			if(in[i*4 + 3] != '=')
			{
				result[i*4 + 2] = (char) ((in[i*4 + 2] << 6 | in[i*4 + 3]) & 0xFF);
			}
			else
			{
				// one '=' at end, ignore last one character in the result and exit
				endIndex -= 1;
			}
		}
		
		return String.valueOf(result).substring(0, endIndex);
	}
}
