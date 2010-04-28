import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Test2DVisualizer extends JFrame {
	public static final String file = "res\\gauss2DTr";
	
	public Test2DVisualizer() throws Exception {
		JPanel p = Visualizer2D.getChartPanel(DataLoader.loadGauss(file));
		this.setContentPane(p);
		this.setVisible(true);
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		this.setSize(toolkit.getScreenSize());
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new Test2DVisualizer();
	}

}
