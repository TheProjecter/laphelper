import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataLoader {
	
	public static ArrayList<PrototypeWeight> loadWeigths(String path) throws Exception {
		ArrayList<PrototypeWeight> list = new ArrayList<PrototypeWeight>();
		File file = new File(path);
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = null; 
		int colCount = -1;
		while ((line = input.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, " ");

			if(tokenizer.nextToken().equals("#")) {
				continue;
			}
			colCount = tokenizer.countTokens();
			double[] w = new double[colCount];
			for(int i = 0; i < colCount; i++) {
				w[i] = Double.valueOf(tokenizer.nextToken());
			}
			list.add(new PrototypeWeight(w));
		}

		input.close();
		return list;
	}
	
	public static ArrayList<Prototype> loadPrototypes(String path) throws Exception {
		ArrayList<Prototype> list = new ArrayList<Prototype>();
		File file = new File(path);
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = null; 
		int colCount = -1;
		while ((line = input.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, " ");

			if(tokenizer.nextToken().equals("#")) {
				if(tokenizer.nextToken().equals("columns:")) {
					colCount = Integer.valueOf(tokenizer.nextToken());
				} 
				continue;
			}
			double[] v = new double[colCount];
			for(int i = 0; i < colCount-1; i++) {
				v[i] = Double.valueOf(tokenizer.nextToken());
			}
			int cls = Integer.valueOf(tokenizer.nextToken());
			list.add(new Prototype(v, cls));
		}
		input.close();
		return list;
	}
}
