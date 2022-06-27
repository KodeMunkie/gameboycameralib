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
package uk.co.silentsoftware.dither.strategy;

import java.awt.image.BufferedImage;

/**
 * Implementation of the Atkinson error diffusion algorithm, adapted from imagetozxspec project
 */
public class AtkinsonDither extends AbstractErrorDiffusion {

	private static final float EIGHTH = 1f/8f;

	public void distributeError(BufferedImage output, int oldPixel, int newPixel, int x, int y) {
		int multiplier = 1;
		if (isInBounds(output, x+1*multiplier, y)) {output.setRGB(x+1*multiplier, y, calculateAdjustedRGB(oldPixel, newPixel, output.getRGB(x+1*multiplier, y), EIGHTH));}
		if (isInBounds(output, x+2*multiplier, y)) {output.setRGB(x+2*multiplier, y, calculateAdjustedRGB(oldPixel, newPixel, output.getRGB(x+2*multiplier, y), EIGHTH));}
		if (isInBounds(output, x-1*multiplier, y+1)) {output.setRGB(x-1*multiplier, y+1, calculateAdjustedRGB(oldPixel, newPixel, output.getRGB(x-1*multiplier, y+1), EIGHTH));}
		if (isInBounds(output, x, y+1)) {output.setRGB(x, y+1, calculateAdjustedRGB(oldPixel, newPixel, output.getRGB(x, y+1), EIGHTH));}
		if (isInBounds(output, x+1*multiplier, y+1)) {output.setRGB(x+1*multiplier, y+1, calculateAdjustedRGB(oldPixel, newPixel, output.getRGB(x+1*multiplier, y+1), EIGHTH));}
		if (isInBounds(output, x, y+2)) {output.setRGB(x, y+2, calculateAdjustedRGB(oldPixel, newPixel, output.getRGB(x, y+2), EIGHTH));}
	}
}
