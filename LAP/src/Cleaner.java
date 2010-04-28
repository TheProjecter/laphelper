import java.util.ArrayList;
import java.util.Iterator;


public class Cleaner {
	
//	private static String gaussData = "res\\gauss2DTr";
	
	public static ArrayList<GaussPrototype> clean(ArrayList<GaussPrototype> data, int k) {
		ArrayList<GaussPrototype> cleanedData = new ArrayList<GaussPrototype>();
		Iterator<GaussPrototype> it = data.iterator();
		while(it.hasNext()) {
			GaussPrototype next = it.next();
			if(next.getCls() == classify(next, data, k)) {
				cleanedData.add(next);
			}
		}
		return cleanedData;
	}
	
	private static int classify(GaussPrototype next,
			ArrayList<GaussPrototype> data, int k) {
		int[] nn = new int[k];
		double[] distances = new double[k];
		for(int i = 0; i < k; i++) {
			distances[i] = Double.MAX_VALUE;
			nn[i] = -1;
		}
		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).equals(next)) {
				continue;
			}
			
			double distance = Math.sqrt((Math.pow(data.get(i).getX() - next.getX(), 2) +
					Math.pow(data.get(i).getY() - next.getY(), 2)));
			for(int j = distances.length - 1; j >= 0; j --) {
				if(distance > distances[j]) {
					break;
				}
				double oldDistance = distances[j];
				int oldClass = nn[j];
				distances[j] = distance;
				nn[j] = data.get(i).getCls();
				if(j != distances.length - 1) {
					distances[j + 1] = oldDistance;
					nn[j + 1] = oldClass;
				}
			}
		}
		int count1 = 0, count2 = 0;
		for(int i = 0; i < k; i++) {
			if(nn[i] == 1) {
				count1 ++;
			} else {
				count2 ++;
			}
		}
		if(count1 > count2) {
			return 1;
		} else if(count2 > count1) {
			return 2;
		}
		return nn[0];
	}

//	public static void main(String[] args) throws Exception {
//		Runtime run = Runtime.getRuntime();
//		Process pp=run.exec(nnwCommand);
//		int exitVal = pp.waitFor();
//		System.out.println("Process exitValue: " + exitVal);
//		ArrayList<GaussPrototype> data = DataLoader.loadGauss(gaussData);
//		ArrayList<GaussPrototype> cleaned = Cleaner.clean(data, 1);
//		Visualizer2D.showData(cleaned);
//		System.out.println(cleaned.size() / data.size() * 100);
//	}
}
