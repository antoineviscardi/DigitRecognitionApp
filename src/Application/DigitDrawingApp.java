package Application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Formatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * 
 * DigitDrawingApp is the main class of this application. It is in charge of
 * creating and displaying the JFrame. It is also in charge of calling the
 * Python function that evaluate the drawn image.
 * 
 * @author Antoine Viscardi
 */
public class DigitDrawingApp {

	public static void main(String args[]) {

		// Create and initialize JFrame.
		JFrame frame = new JFrame("Digit Recognition Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Create top level JPanel.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		// Create and add label.
		JLabel label = new JLabel("Draw a digit");
		label.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(label);
		mainPanel.add(labelPanel);
		Border defaultBorder = label.getBorder();

		// Create and add Canvas.
		Canvas canvas = new Canvas();
		JPanel canvasPanel = new JPanel();
		canvasPanel.add(canvas);
		canvasPanel.setBorder(defaultBorder);
		mainPanel.add(canvasPanel);

		// Create and add Buttons with action listener.

		// When clicked, the canvas is cleared.
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.clear();
			}
		});

		/*
		 * When clicked, the image is fetched, passed to a Python function for
		 * evaluation and the result is send back to Canvas.
		 */
		JButton evaluateBtn = new JButton("Evaluate");
		evaluateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float[] imgData = canvas.getImageData();
				imgToFile(imgData);
			}
		});
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnPanel.add(clearBtn);
		btnPanel.add(evaluateBtn);
		mainPanel.add(btnPanel);

		// Put everything in the Container of the JFrame.
		Container contentPane = frame.getContentPane();
		contentPane.add(mainPanel);

		// Pack and display.
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Takes floats and write them to a file. Mainly used for testing.
	 * 
	 * @param imgData
	 *            A float array representing every pixel of the image such as
	 *            the one returned by Canvas.getImageData
	 */
	private static void imgToFile(float[] imgData) {
		Formatter file = null;
		try {
			file = new Formatter("img.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < 380 * 380; i++) {
			file.format("%.0f ", imgData[i]);
			if (i % 380 == 0)
				file.format("\n");
		}
	}
}
