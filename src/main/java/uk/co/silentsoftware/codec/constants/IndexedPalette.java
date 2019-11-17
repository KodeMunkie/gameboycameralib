/* Gameboy Camera Lib
 * Copyright (C) 2019 Silent Software (Benjamin Brown)
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
package uk.co.silentsoftware.codec.constants;

import java.awt.*;

/**
 * Indexed palette representation with default choices for Gameboy colour conversion
 */
public class IndexedPalette {

    /**
     * Colours that are an approximation of the original Gameboy LCD green
     */
    public static final int[] GAMEBOY_LCD_PALETTE = {
            new Color(155, 188, 15).getRGB(),
            new Color(139, 172, 15).getRGB(),
            new Color(48, 98, 48).getRGB(),
            new Color(15, 56, 15).getRGB()
    };

    /**
     * Colours evenly distributed across RGB range
     */
    public static final int[] EVEN_DIST_PALETTE = {
            new Color(255, 255, 255).getRGB(),
            new Color(170, 170, 170).getRGB(),
            new Color(85, 85, 85).getRGB(),
            new Color(0, 0, 0).getRGB()
    };

    /**
     * Colours (assumed) evenly distributed across "intensity" (luminance?) range, taken from the GB_CAMERA_DUMP app
     */
    public static final int[] GB_CAMERA_DUMP_PALETTE = {
            new Color(0xFFFFFF).getRGB(),
            new Color(0xC0C0C0).getRGB(),
            new Color(0x808080).getRGB(),
            new Color(0x000000).getRGB()
    };

    private int[] palette;

    public IndexedPalette() {
        this(GB_CAMERA_DUMP_PALETTE);
    }

    public IndexedPalette(int[] palette) {
        this.palette = palette;
    }

    public int[] getPalette() {
        return this.palette;
    }

    public int getRGB(int index) {
        return palette[index];
    }

    public int getIndex(int rgb) {
        for (int i=0; i<palette.length;++i) {
            if (palette[i] == rgb) {
                return i;
            }
        }
        throw new IllegalArgumentException("Specified RGB colour does not exist in the indexed palette");
    }
}
