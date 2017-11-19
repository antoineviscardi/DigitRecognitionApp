package Application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.CompoundBorder;

/**
 * Canvas is a JComponent implemented to give the user the ability to draw in
 * grayscale on a BufferedImage and easily access its content in the form of a
 * float array for compatibility with the MNIST dataset.
 * 
 * @author Antoine Viscardi
 */
@SuppressWarnings("serial")
public class Canvas extends JComponent {

	/*
	 * Constants relative to the size of the component and image. 380 by 380
	 * pixels is a convenient size since we can easily reduce the size to 38 by
	 * 38 to create MNIST format images.
	 */
	private static final int WINDOW_SIZE = 280;
	private static final int BRUSH_SIZE = 25 ;

	// What we will draw our image on.
	private BufferedImage bi;
	private Graphics2D g2;

	// Keep track of the current and past mouse location to draw lines.
	private Point lastPoint;
	private Point currentPoint;

	/**
	 * Constructor Set the preferred size and add MouseAdapter and
	 * MouseMotionAdapter in order to track mouse movements.
	 */
	public Canvas() {
		setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lastPoint = e.getPoint(); // Set last point when clicking.
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				// Set current point upon mouse dragged event.
				currentPoint = e.getPoint();
				if (g2 != null) {
					g2.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
					repaint();
					lastPoint = currentPoint;
				}
			}
		});
	}

	/**
	 * This method is called every time the component is repainted. It
	 * initialize the BufferedImage and the Graphics2D on first call.
	 * Ultimately, it only draws the BufferedImage on the Components's Graphics.
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (bi == null) {
			bi = new BufferedImage(WINDOW_SIZE, WINDOW_SIZE, BufferedImage.TYPE_INT_ARGB);
			g2 = (Graphics2D) bi.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(BRUSH_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			clear();
		}
		g.drawImage(bi, 0, 0, null);
	}

	/**
	 * Clear the image with white color
	 */
	public void clear() {
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setPaint(Color.BLACK);
		repaint();
	}

	/**
	 * Method used to get the image data.
	 * 
	 * @return A float array of every pixel of the image in the form of a float
	 *         between 0 and 1 representing the grayscale value, 0 being pure
	 *         white and 1 pure black.
	 */
	public float[] getImageData() {
		int width = bi.getWidth();
		int height = bi.getHeight();
		float[] data = new float[width * height];

		// Iterate through each pixel to apply transformation
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				/*
				 * Get RGB value of pixel, extract only one component (any could
				 * do the trick), invert it so that high values are dark and
				 * scale it between 0 and 1.
				 */
				Color pixel = new Color(bi.getRGB(i, j));
				data[j * width + i] = (float) ((255 - pixel.getRed()) / 255.0);
			}
		}
		return data;
	}
}
