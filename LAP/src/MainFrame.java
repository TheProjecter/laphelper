import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

public class MainFrame extends JFrame implements ActionListener {

	private AppController appCtrl;
	private ChartPanel chart = null;
	private JPanel cleanPanel, trainPanel;
	private JTextField cleanK, cleanPath;
	private JTextField trainSlope, trainPath;
	private JTextField trainReduction;
	private JComboBox weightList;
	
	public MainFrame(AppController appCtrl) {
		super("Oracle Center");
		this.appCtrl = appCtrl;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(5, 1, 5, 20));
		JButton scatterButton = new JButton("Create Scatterplot");
		scatterButton.setActionCommand(Constants.CMD_SCATTER);
		scatterButton.addActionListener(this);
		westPanel.add(scatterButton);

		JButton cleanButton = new JButton("Clean Dataset");
		cleanButton.setActionCommand(Constants.CMD_SHOW_CLEAN);
		cleanButton.addActionListener(this);
		westPanel.add(cleanButton);

		JButton trainButton = new JButton("Train LPD");
		trainButton.setActionCommand(Constants.CMD_SHOW_TRAIN);
		trainButton.addActionListener(this);
		westPanel.add(trainButton);

		getContentPane().add(westPanel, BorderLayout.WEST);

		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(Constants.CMD_SCATTER)) {
			JFileChooser fc = new JFileChooser(Constants.lpdPath);
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if(chart != null) {
					getContentPane().remove(chart);
					getContentPane().validate();
				}
				showScatterPlot(appCtrl.scatterPlot(file));
			}
		} else if(cmd.equals(Constants.CMD_SHOW_CLEAN)) {
			if(cleanPanel == null) {
				cleanPanel = createCleanPanel();
			}
			if((trainPanel != null) && trainPanel.isShowing()) {
				getContentPane().remove(trainPanel);
				getContentPane().validate();
			}
			getContentPane().add(cleanPanel, BorderLayout.EAST);
			pack();
			JFileChooser fc = new JFileChooser(Constants.lpdPath);
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				cleanPath.setText(file.getAbsolutePath());
			}
		} else if(cmd.equals(Constants.CMD_CLEAN)) {
			appCtrl.clean(cleanPath.getText(), Integer.parseInt(cleanK.getText()));
		} else if(cmd.equals(Constants.CMD_SHOW_TRAIN)) {
			if(trainPanel == null) {
				trainPanel = createTrainPanel();
			}
			if((cleanPanel != null) && cleanPanel.isShowing()) {
				getContentPane().remove(cleanPanel);
				getContentPane().validate();
			}
			getContentPane().add(trainPanel, BorderLayout.EAST);
			pack();
			JFileChooser fc = new JFileChooser(Constants.lpdPath);
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				trainPath.setText(file.getAbsolutePath());
			}
		} else if(cmd.equals(Constants.CMD_TRAIN)) {
			appCtrl.train(trainPath.getText(), Double.parseDouble(trainReduction.getText()), Double.parseDouble(trainSlope.getText()), weightList.getSelectedItem().toString());
		}

	}

	private JPanel createTrainPanel() {
		trainPanel = new JPanel();
		trainPanel.setLayout(new GridLayout(5, 2, 5, 20));
		trainPanel.add(new JLabel("Reduktion [%]: "));
		trainReduction = new JTextField("50");
		trainPanel.add(trainReduction);
		trainPanel.add(new JLabel("Sigmoid slope: "));
		trainSlope = new JTextField("10");
		trainPanel.add(trainSlope);
		trainPanel.add(new JLabel("Path:"));
		trainPath = new JTextField(Constants.lpdPath);
		trainPanel.add(trainPath);
		trainPanel.add(new JLabel("Weight training: "));
		weightList = new JComboBox(Constants.WEIGHT_MODES);
		weightList.setSelectedIndex(0);
		trainPanel.add(weightList);
		trainPanel.add(new JLabel());
//		petList.addActionListener(this);
		JButton cleanCmdButton = new JButton("Train");
		cleanCmdButton.setActionCommand(Constants.CMD_TRAIN);
		cleanCmdButton.addActionListener(this);
		trainPanel.add(cleanCmdButton);
		return trainPanel;
	}

	private JPanel createCleanPanel() {
		cleanPanel = new JPanel();
		cleanPanel.setLayout(new GridLayout(5, 2, 5, 20));
		cleanPanel.add(new JLabel("k: "));
		cleanK = new JTextField("3");
		cleanPanel.add(cleanK);
		cleanPanel.add(new JLabel("Path:"));
		cleanPath = new JTextField(Constants.lpdPath);
		cleanPanel.add(cleanPath);
		cleanPanel.add(new JLabel());
		JButton cleanCmdButton = new JButton("Clean");
		cleanCmdButton.setActionCommand(Constants.CMD_CLEAN);
		cleanCmdButton.addActionListener(this);
		cleanPanel.add(cleanCmdButton);
		return cleanPanel;
	}

	public void showScatterPlot(ChartPanel chartPanel) {
		chart = chartPanel;
		getContentPane().add(chart, BorderLayout.CENTER);
		pack();
	}

}
