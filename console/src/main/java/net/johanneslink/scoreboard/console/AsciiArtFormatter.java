package net.johanneslink.scoreboard.console;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class AsciiArtFormatter {

    public static String format(final String stringToFormat) {
	final BufferedImage bufferedImage = new BufferedImage(75, 15, BufferedImage.TYPE_INT_RGB);
	final Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();

	try {
	    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    graphics2D.setFont(new Font("Arial", Font.PLAIN, 18));
	    graphics2D.drawString(stringToFormat, 1, 15);

	    final StringBuilder stringBuilder = new StringBuilder();
	    for (int y = 0; y < bufferedImage.getHeight(); y++) {
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
		    final int color = bufferedImage.getRGB(x, y);
		    final String pixelChar = color == -16777216 ? " " : "\u2588";
		    stringBuilder.append(pixelChar);
		}

		stringBuilder.append("\n");
	    }
	    return stringBuilder.toString();
	} finally {
	    graphics2D.dispose();
	}
    }
}
