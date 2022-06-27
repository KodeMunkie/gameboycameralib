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

import uk.co.silentsoftware.codec.Codec;
import uk.co.silentsoftware.codec.constants.IndexedPalette;

import java.awt.image.BufferedImage;

/**
 * Implementation of 2BPP (Gameboy 2 bits per pixel) encoding and decoding algorithms at a tile level
 */
public class TileCodec implements Codec {

    private static final int ROW_BYTES = 2;
    public static final int TILE_WIDTH = 8;
    public static final int TILE_HEIGHT = 8;
    public static final int TILE_BYTES_LENGTH = 16;

    private final IndexedPalette palette;

    public TileCodec(IndexedPalette palette) {
        this.palette = palette;
    }

    @Override
    public BufferedImage decode(byte[] tileData) {
        BufferedImage buf = new BufferedImage(TILE_WIDTH, TILE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int y=0; y<TILE_HEIGHT; y++) {
            byte lowByte = reverseBitEndianess(tileData[y*ROW_BYTES]);
            byte highByte = reverseBitEndianess(tileData[y*ROW_BYTES+1]);
            for (int x=0; x<TILE_WIDTH; x++) {
                int paletteIndex = getPaletteIndex(getBit(lowByte, x), getBit(highByte, x));
                buf.setRGB(x, y, palette.getRGB(paletteIndex));
            }
        }
        return buf;
    }

    @Override
    public byte[] encode(BufferedImage buf) {
        byte[] result = new byte[TILE_BYTES_LENGTH];
        for (int y=0; y<TILE_HEIGHT; y++) {
            byte lowByte = (byte)0;
            byte highByte = (byte)0;
            for (int x=0; x<TILE_WIDTH; x++) {
                int rgb = buf.getRGB(x,y);
                byte index = (byte)palette.getIndex(rgb);
                byte lowBit = getBit(index,0);
                byte highBit = getBit(index,1);
                if (lowBit == 1) {
                    lowByte = setBit(lowByte, x);
                }
                if (highBit == 1) {
                    highByte = setBit(highByte, x);
                }
            }
            lowByte = reverseBitEndianess(lowByte);
            highByte = reverseBitEndianess(highByte);
            result[y*ROW_BYTES] = lowByte;
            result[y*ROW_BYTES+1] = highByte;
        }
        return result;
    }

    public byte setBit(byte b, int position) {
        return (byte) (b | (1 << position));
    }

    public byte getBit(byte b, int position) {
        return (byte)((b >> position) & 1);
    }

    public int getPaletteIndex(byte lowBit, byte highBit) {
        int index = 0;
        index |= highBit << 1;
        index |= lowBit;
        return index;
    }

    /**
     * GB streamed data (memory or printer dump) has reversed endianness bits
     * @param byteToReverse
     * @return byte with reversed bits
     */
    public static byte reverseBitEndianess(byte byteToReverse) {
        byte b = 0;
        for (int i = 0; i < 8; ++i) {
            b <<= 1;
            b |= byteToReverse & 1;
            byteToReverse >>= 1;
        }
        return b;
    }
}
