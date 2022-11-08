/* Gameboy Camera Lib
 * Copyright (C) 2022 Silent Software (Benjamin Brown)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.silentsoftware.util;

import uk.co.silentsoftware.util.colourdistance.ColourDistanceStrategy;
import uk.co.silentsoftware.util.colourdistance.LuminanceColourDistance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * Provides non-Gameboy specific image processing utility methods
 */
public class ImageUtils {

    private static final ColourDistanceStrategy colourDistanceStrategy = new LuminanceColourDistance();

    public static BufferedImage resize(BufferedImage buf, int imageWidth, int imageHeight) {
        if (buf.getWidth() == imageWidth && buf.getHeight() == imageHeight) {
            return buf;
        }
        Image img = buf.getScaledInstance(imageWidth, imageHeight, BufferedImage.SCALE_DEFAULT);
        BufferedImage scaled = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = null;
        try {
            g = scaled.getGraphics();
            g.drawImage(img,0,0,null);
        } finally {
            if (g != null) {
                g.dispose();
            }
        }
        return scaled;
    }

    public static BufferedImage copyImage(final Image source) {
        if (source instanceof BufferedImage) {
            BufferedImage bi = ((BufferedImage)source);
            if (BufferedImage.TYPE_INT_RGB == bi.getType()) {
                ColorModel cm = bi.getColorModel();
                return new BufferedImage(cm, bi.copyData(null), cm.isAlphaPremultiplied(), null);
            }
        }
        BufferedImage copy = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_RGB);
        copyImage(source, copy);
        return copy;
    }

    private static void copyImage(final Image source, BufferedImage dest) {
        Graphics g = null;
        try {
            g = dest.createGraphics();
            g.drawImage(source, 0, 0, null);
        } finally {
            if (g != null) {
                g.dispose();
            }
        }
    }

    public static int componentsToColor(int red, int green, int blue) {
        return new Color(correctRange(red), correctRange(green), correctRange(blue)).getRGB();
    }

    private static int correctRange(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return value;
    }

    public static int[] intToRgbComponents(int rgb) {
        return new int[] { rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF };
    }

    public static int getClosestColour(int red, int green, int blue, int[] palette) {
        double bestMatch = Double.MAX_VALUE;
        Integer closest = null;
        for (int colour : palette) {
            final int[] colourSetComps = intToRgbComponents(colour);
            double diff = colourDistanceStrategy.getColourDistance(red, green, blue, colourSetComps);
            if (diff < bestMatch) {
                closest = colour;
                bestMatch = diff;
            }
        }
        return closest;
    }
}
