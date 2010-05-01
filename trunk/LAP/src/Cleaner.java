import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;


public class Cleaner {
	
	public static ArrayList<Prototype> clean(ArrayList<Prototype> data, int k) {
		ArrayList<Prototype> cleanedData = new ArrayList<Prototype>();
		Iterator<Prototype> it = data.iterator();
		while(it.hasNext()) {
			Prototype next = it.next();
			if(next.getCls() == classify(next, data, k)) {
				cleanedData.add(next);
			}
		}
		return cleanedData;		
	}
	
	private static int classify(Prototype next, ArrayList<Prototype> data, int k) {
		// store k nearest neighbors classes
		int[] nn = new int[k];
		// store corresponding distances
		double[] distances = new double[k];
		// initialize arrays
		for(int i = 0; i < k; i++) {
			distances[i] = Double.MAX_VALUE;
			nn[i] = -1;
		}
		// get through data
		for(int i = 0; i < data.size(); i++) {
			// do not consider actual prototype
			if(data.get(i).equals(next)) {
				continue;
			}
			// calculate distance between considered prototype
			double distance = calculateDistance(data.get(i).getV(), next.getV());
			// update distance and nn array
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
		// classify
		Hashtable<Integer, Integer> countTable = new Hashtable<Integer, Integer>();
		for(int i = 0; i < k; i++) {
			if(!countTable.containsKey(new Integer(nn[i]))) {
				countTable.put(new Integer(nn[i]), new Integer(1));
			} else {
				int count = countTable.get(nn[i]).intValue();
				countTable.remove(new Integer(nn[i]));
				count ++;
				countTable.put(new Integer(nn[i]), new Integer(count));
			}
		}
		// return class with most neighbors
		Iterator<Entry<Integer, Integer>> it = countTable.entrySet().iterator();
		Entry<Integer, Integer> bestEntry = it.next();
		while(it.hasNext()) {
			Entry<Integer, Integer> nextEntry = it.next();
			if(nextEntry.getValue() > bestEntry.getValue()) {
				bestEntry = nextEntry;
			}
		}
		return bestEntry.getKey();
	}

	// returns euclidean distance between two prototype data
	private static double calculateDistance(double[] v, double[] v2) {
		double sqDiffSum = 0;
		for(int i = 0; i < v.length; i++) {
			sqDiffSum += Math.pow(v[i] - v2[i], 2);
		}
		return Math.sqrt(sqDiffSum);
	}

//	public static void main(String[] args) throws Exception {
//		Runtime run = Runtime.getRuntime();
//		Process pp=run.exec(nnwCommand);
//		int exitVal = pp.waitFor();
//		System.out.println("Process exitValue: " + exitVal);
//		ArrayList<Prototype> data = DataLoader.loadPrototypes("res\\lin-genderTr");
//		ArrayList<Prototype> cleaned = Cleaner.clean(data, 3);
//		Visualizer2D.showData(cleaned);
//		System.out.println(new Double(cleaned.size() / data.size() * 100));
//	}
}
