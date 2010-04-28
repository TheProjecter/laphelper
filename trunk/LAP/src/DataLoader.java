import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataLoader {
	public static ArrayList<GaussPrototype> loadGauss(String path) throws Exception {
		ArrayList<GaussPrototype> list = new ArrayList<GaussPrototype>();
		File file = new File(path);
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = null; // not declared within while loop
		int tokenCounter = 0, cls;
		double x = 0, y = 0;
		while ((line = input.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, " ");
			while(tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				tokenCounter ++;
				if(token.equals("#")) {
					tokenCounter = 0;
					break;
				}
//				if((tokenCounter % 3) == 1) {
					x = Double.valueOf(token);
//				} else if((tokenCounter % 3) == 2) {
					y = Double.valueOf(tokenizer.nextToken());
//				} else {
					cls = Integer.valueOf(tokenizer.nextToken());
					list.add(new GaussPrototype(x, y, cls));
//				}
			}
		}
		input.close();
		return list;
	}
	
	public static ArrayList<GaussWeight> loadGaussWeigths(String path) throws Exception {
		ArrayList<GaussWeight> list = new ArrayList<GaussWeight>();
		File file = new File(path);
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = null; // not declared within while loop
		int tokenCounter = 0;
		double x = 0, y = 0;
		while ((line = input.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, " ");
			while(tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				tokenCounter ++;
				if(token.equals("#")) {
					tokenCounter = 0;
					break;
				}
//				if((tokenCounter % 2) == 1) {
					x = Double.valueOf(token);
//				} else {
					// TODO: ACHTUNG exception fliegt bei cpw, weil andere weights-fileformat :(
					y = Double.valueOf(tokenizer.nextToken());
					list.add(new GaussWeight(x, y));
//				}
			}
		}
		input.close();
		return list;
	}
}
