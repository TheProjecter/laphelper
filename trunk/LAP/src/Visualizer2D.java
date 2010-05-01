import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Visualizer2D {
	public static ChartPanel getChartPanel(ArrayList<Prototype> prototypes) {
		JFreeChart chart = ChartFactory.createScatterPlot(
				"Scatter Plot Demo", // title
				"X", "Y", // axis labels
				getDataset(prototypes), // dataset
				PlotOrientation.VERTICAL,
				true, // legend? yes
				true, // tooltips? yes
				false // URLs? no
				);
				ChartPanel chartPanel = new ChartPanel(chart); 
        return chartPanel;
	}
	
	private static XYSeriesCollection getDataset(ArrayList<Prototype> prototypes) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("class 1");
		XYSeries series2 = new XYSeries("class 2");
		Iterator<Prototype> it = prototypes.iterator();
		while(it.hasNext()) {
			Prototype next = it.next();
			if(next.getCls() == 1) {
				series1.add(next.getV()[0], next.getV()[1]);
			} else {
				series2.add(next.getV()[0], next.getV()[1]);
			}
		}
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		return dataset;
	}

	public static void showData(ArrayList<Prototype> prototypes) {
		JFrame frame = new JFrame();
		frame.setContentPane(getChartPanel(prototypes));
		frame.setVisible(true);
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		frame.setSize(toolkit.getScreenSize());
	}
}
