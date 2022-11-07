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
package uk.co.silentsoftware.codec.tile;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;
import uk.co.silentsoftware.codec.Codec;
import uk.co.silentsoftware.codec.constants.IndexedPalette;

import java.awt.image.BufferedImage;

public class TileCodecTest {

    private static String TEST_TILE_DATA_1 = "FF007EFF858189839385A58BC9977EFF";
    private static String TEST_TILE_DATA_2 = "7C7C00C6C60000FEC6C600C6C6000000";

    @Test
    public void testSimpleTile1() throws Exception {
        Codec codec = new TileCodec(new IndexedPalette());
        BufferedImage image = codec.decode(Hex.decodeHex(TEST_TILE_DATA_1));
        Assert.assertEquals(TEST_TILE_DATA_1, Hex.encodeHexString(codec.encode(image),false));
    }

    @Test
    public void testSimpleTile2() throws Exception {
        Codec codec = new TileCodec(new IndexedPalette());
        BufferedImage image = codec.decode(Hex.decodeHex(TEST_TILE_DATA_2));
        Assert.assertEquals(TEST_TILE_DATA_2, Hex.encodeHexString(codec.encode(image),false));
    }
}
