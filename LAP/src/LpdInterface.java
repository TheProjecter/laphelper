import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class LpdInterface {
	private static String gauss2DpHeader = "# name: protos\n# type: matrix\n# rows: ö\n# columns: 3\n";
	private static String gauss2DwHeader = "# name: weights\n# type: matrix\n# rows: ö\n# columns: 2\n";
	public static String training(String trainingFile, double reduction, double slope, String weightMode)
			throws Exception {
		String execString = Constants.nnwCommand;
		execString += " " + trainingFile + " " + reduction + " -sigm " + slope;
		Runtime run = Runtime.getRuntime();
		Process pp = run.exec(execString);
		BufferedReader in =new BufferedReader(new InputStreamReader(pp.getErrorStream()));
		            String line;
		            while ((line = in.readLine()) != null) {
		                System.out.println(line);
		            }
		int exitVal = pp.waitFor();
		System.out.println("Process exitValue: " + exitVal + line);
		String toFileName = Constants.lpdPath + "\\generatedFiles\\cls" + System.currentTimeMillis() + "-R" + reduction + "-Slp" + slope + ".lpd";
		
		// in case of other weightMode start cpw
		if(!weightMode.equals(Constants.LPD_WEIGHTS)) {
			execString = Constants.cpwCommand;
			execString += " " + Constants.lpdWorkingDir + "\\reduced.lpd -sigm " + slope;
			run = Runtime.getRuntime();
			pp = run.exec(execString);
			in = new BufferedReader(new InputStreamReader(pp.getErrorStream()));
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			exitVal = pp.waitFor();
			System.out.println("Process exitValue: " + exitVal + line);
		}
		
		String fileContent = createClassifier(weightMode);
		PrototypeWriter.setContents(new File(toFileName), fileContent);
		return toFileName;
	}
	
	private static String createClassifier(String weightMode) throws Exception {
		String content = "";
		content += gauss2DpHeader;
		Iterator<Prototype> pIt = DataLoader.loadPrototypes(Constants.lpdWorkingDir + "\\reduced.lpd").iterator();
		int count = 0;
		while(pIt.hasNext()) {
			count ++;
			content += pIt.next().toString() + "\n";
		}
		content = content.replaceAll("ö", String.valueOf(count));
		
		content += gauss2DwHeader;
		// load weights from specific place
		String weightPath = null;
		if(weightMode.equals(Constants.LPD_WEIGHTS)) {
			weightPath = Constants.lpdWorkingDir + "\\weights.lpd";
		} else if(weightMode.equals(Constants.CPW_CLASS_WEIGHTS)) {
			weightPath = Constants.cpwWorkingDir + "\\cweights.cpw";
		} else if(weightMode.equals(Constants.CPW_PROT_WEIGHTS)) {
			weightPath = Constants.cpwWorkingDir + "\\pweights.cpw";
		}
		Iterator<PrototypeWeight> wIt = DataLoader.loadWeigths(weightPath).iterator();
		count = 0;
		while(wIt.hasNext()) {
			count ++;
			content += wIt.next().toString() + "\n";
		}
		content = content.replaceAll("ö", String.valueOf(count));
		return content;	
	}

//	public static void main(String[] args) throws Exception {
//		int k = 3;
//		ArrayList<GaussPrototype> data = DataLoader.loadGauss(Constants.gaussData);
//		ArrayList<GaussPrototype> cleaned = Cleaner.clean(data, k);
//		Visualizer2D.showData(cleaned);
//		String trainingFile = Constants.lpdWorkingDir + "\\gauss2DTrMatlab.txt";//.lpdPath + "\\generatedFiles\\trainingK" + k;
//		PrototypeWriter.writeGaussTrainingFile(cleaned, trainingFile);
//		
//		training(trainingFile, 50, 10);
//	}


	private static void copy(String fromFileName, String toFileName)
			throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
			System.out.print("Overwrite existing file " + toFile.getName()
					+ "? (Y/N): ");
			System.out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String response = in.readLine();
			if (!response.equals("Y") && !response.equals("y"))
				throw new IOException("FileCopy: "
						+ "existing file was not overwritten.");
		} else {
			String parent = toFile.getParent();
			if (parent == null)
				parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				throw new IOException("FileCopy: "
						+ "destination directory doesn't exist: " + parent);
			if (dir.isFile())
				throw new IOException("FileCopy: "
						+ "destination is not a directory: " + parent);
			if (!dir.canWrite())
				throw new IOException("FileCopy: "
						+ "destination directory is unwriteable: " + parent);
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
	}

}
