import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;


public class AppController {
	
	private MainFrame mainFrame = null;
	
	public AppController() {
		mainFrame = new MainFrame(this);
	}
	
	public ChartPanel scatterPlot(File file) {
		try {
			ArrayList<Prototype> prototypes = DataLoader.loadPrototypes(file.getAbsolutePath());
			if((prototypes == null) || (prototypes.size() == 0) || (prototypes.get(0).getV().length != 2)) {
				throw new Exception("Invalid Prototypes, must have 2 dimensions");
			}
			return Visualizer2D.getChartPanel(prototypes);		
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public static void main(String[] args) {
		new AppController();
	}

	public void clean(String path, int k) {
		try {
			File file = new File(path);
			ArrayList<Prototype> prototypes = DataLoader.loadPrototypes(file.getAbsolutePath());	
			ArrayList<Prototype> cleaned = Cleaner.clean(prototypes, k);
			String outputPath = Constants.generatedFilePath + "\\cleanedK" + k;
			PrototypeWriter.writeTrainingFile(cleaned, outputPath);
			mainFrame.showScatterPlot(Visualizer2D.getChartPanel(cleaned));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void train(String trainPath, double reduction, double slope, String weightMode) {
		try {
			String trainedFile = LpdInterface.training(trainPath, reduction, slope, weightMode);
			JOptionPane.showMessageDialog(mainFrame, "Created: " + trainedFile, "Success!", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
