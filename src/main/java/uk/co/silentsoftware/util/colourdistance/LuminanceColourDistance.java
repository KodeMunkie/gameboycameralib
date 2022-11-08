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
package uk.co.silentsoftware.util.colourdistance;

/**
 * Luminance colour distance adapted from http://appleoldies.ca/a2b/DHGRColors2017.htm
 */
public class LuminanceColourDistance implements ColourDistanceStrategy {

    public static final double LUMA_RED = 0.298839;
    public static final double LUMA_GREEN = 0.586811;
    public static final double LUMA_BLUE = 0.114350;

    @Override
    public double getColourDistance(int red, int green, int blue, int[] paletteComps) {
        double luma1 = (red*LUMA_RED + green*LUMA_GREEN + blue*LUMA_BLUE) / (255.0*1000);
        double luma2 = (paletteComps[0]*LUMA_RED + paletteComps[1]*LUMA_GREEN + paletteComps[2]*LUMA_BLUE) / (255.0*1000);
        double lumaDiff = luma1-luma2;
        double diffR = (paletteComps[0]-red)/255.0;
        double diffG = (paletteComps[1]-green)/255.0;
        double diffB = (paletteComps[2]-blue)/255.0;
        return (diffR*diffR*LUMA_RED + diffG*diffG*LUMA_GREEN+ diffB*diffB*LUMA_BLUE)*0.75+ lumaDiff*lumaDiff;
    }
}
