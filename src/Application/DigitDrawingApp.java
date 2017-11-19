package Application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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

	private final static int CANVAS_SIZE = 280;
	private final static int IMAGE_SIZE = 28;
	private final static int COMPRESS_RATIO = CANVAS_SIZE / IMAGE_SIZE;

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

		// Create label to display result.
		String resultStr = "You drew: ";
		JLabel resultLabel = new JLabel(resultStr);
		resultLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		
		// Create and add Buttons with action listener.

		// When clicked, the canvas is cleared.
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.clear();
				resultLabel.setText(resultStr);
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
				imgToFile(compressImg(imgData));
				resultLabel.setText(resultStr + feedImage());
			}
		});
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnPanel.add(clearBtn);
		btnPanel.add(evaluateBtn);
		btnPanel.add(resultLabel);
		mainPanel.add(btnPanel);

		// Put everything in the Container of the JFrame.
		Container contentPane = frame.getContentPane();
		contentPane.add(mainPanel);

		// Pack and display.
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Compress the image by a factor of 10 so that the final image is 38x38
	 * pixels. It iterate through every block of 10x10 pixels in the original
	 * image, computes the mean and add it to a new float array which represents
	 * the new image.
	 * 
	 * @param imgData
	 *            the original 380x380 image
	 * @return the new 38x38 pixels
	 */
	private static float[] compressImg(float[] imgData) {
		float[] compressedImg = new float[IMAGE_SIZE * IMAGE_SIZE];
		float pixelMean = 0;
		int ix = 0; // Index of pixel in compressed image.

		for (int i = 0; i < CANVAS_SIZE; i += 10) {
			for (int j = 0; j < CANVAS_SIZE; j += 10) {
				for (int k = 0; k < COMPRESS_RATIO; k++) {
					for (int l = 0; l < COMPRESS_RATIO; l++) {
						pixelMean += imgData[convertCoord((j + l), (i + k), CANVAS_SIZE)];
					}
				}
				compressedImg[ix++] = pixelMean / (COMPRESS_RATIO * COMPRESS_RATIO);
				pixelMean = 0;
			}
		}
		return compressedImg;
	}

	/**
	 * Convert (x,y) coordinates to an index in an array of 380x380 elements.
	 * For example, the coordinates (10,10) in a 380x380 array would be the
	 * index 3810.
	 * 
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate .
	 * 
	 * @return The equivalent index in a linear array of 380x380
	 */
	private static int convertCoord(int x, int y, int width) {
		return ((y * CANVAS_SIZE) + x);
	}

	/**
	 * Takes floats and write them to a file. Mainly used for testing.
	 * 
	 * @param imgData
	 *            A float array representing every pixel of the image.
	 */
	private static void imgToFile(float[] imgData) {
		PrintStream file = null;
		try {
			file = new PrintStream("img.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < IMAGE_SIZE * IMAGE_SIZE - 1; i++) {
			file.printf("%f,", imgData[i]);
		}
		file.printf("%f", imgData[IMAGE_SIZE * IMAGE_SIZE - 1]);
	}

	private static int feedImage() {
		String command = "import JavaUtils;" + "print(JavaUtils.get_net_output())";
		ProcessBuilder pb = new ProcessBuilder("python", "-c", command);
		pb.redirectErrorStream(true);
		pb.directory(new File("src/Model"));

		int a = -1;
		try {
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			a = Integer.parseInt(in.readLine());
//			String line = in.readLine();
//			while (line != null) {
//				System.out.println(line);
//				line = in.readLine();
//			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return a;
	}
}
