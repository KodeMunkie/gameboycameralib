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
package uk.co.silentsoftware.dither;

import uk.co.silentsoftware.codec.image.IndexedPalette;
import uk.co.silentsoftware.dither.strategy.AtkinsonDither;
import uk.co.silentsoftware.dither.strategy.ErrorDiffusionDither;
import uk.co.silentsoftware.util.ImageUtils;

import java.awt.image.BufferedImage;

import static uk.co.silentsoftware.util.ImageUtils.copyImage;
import static uk.co.silentsoftware.util.ImageUtils.intToRgbComponents;

public class ErrorDiffusionConverter {

    public static BufferedImage convert(BufferedImage original, IndexedPalette palette) {
        ErrorDiffusionDither ditherStrategy = new AtkinsonDither();
        BufferedImage output = copyImage(original);
        for (int y = 0; y < output.getHeight(); ++y) {
            for (int x = 0; x < output.getWidth(); ++x) {
                processPixel(ditherStrategy, palette, output, x, y);
            }
        }
        return output;
    }

    private static void processPixel(ErrorDiffusionDither ditherStrategy, IndexedPalette palette, BufferedImage output, int x, int y) {
        int oldPixel = output.getRGB(x, y);
        int[] comps = intToRgbComponents(oldPixel);
        int newPixel = ImageUtils.getClosestColour(comps[0], comps[1], comps[2], palette.getPalette());
        output.setRGB(x, y, newPixel);
        ditherStrategy.distributeError(output, oldPixel, newPixel, x, y);
    }
}