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
package uk.co.silentsoftware.codec.image;

import uk.co.silentsoftware.codec.Codec;
import uk.co.silentsoftware.codec.constants.IndexedPalette;
import uk.co.silentsoftware.codec.tile.TileCodec;
import uk.co.silentsoftware.dither.ErrorDiffusionConverter;
import uk.co.silentsoftware.util.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Implementation of 2BPP (Gameboy 2 bits per pixel) encoding and decoding algorithms at image level (multiple tiles)
 */
public class ImageCodec implements Codec {
    private final int imageWidth;
    private final int imageHeight;
    private final IndexedPalette palette;

    public ImageCodec(IndexedPalette palette, int imageWidth, int imageHeight) {
        this.palette = palette;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public BufferedImage decode(byte[] data) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Codec tileCodec = new TileCodec(palette);
        Graphics g = null;
        try {
            g = image.getGraphics();
            int xPos = 0;
            int yPos = 0;
            for (int i = 0; i < data.length; i += TileCodec.TILE_BYTES_LENGTH) {
                byte[] tileData = new byte[TileCodec.TILE_BYTES_LENGTH];
                System.arraycopy(data, i, tileData, 0, TileCodec.TILE_BYTES_LENGTH);
                BufferedImage tile = tileCodec.decode(tileData);
                g.drawImage(tile, xPos, yPos, null);
                xPos+=TileCodec.TILE_WIDTH;
                if (xPos >= imageWidth) {
                    xPos = 0;
                    yPos+=TileCodec.TILE_HEIGHT;
                }
                // Failsafe
                if (yPos >= imageHeight) {
                    break;
                }
            }
        } finally {
            if (g != null) {
                g.dispose();
            }
        }
        return image;
    }

    @SuppressWarnings("unused")
    public byte[] encodeNoPreprocessing(BufferedImage buf) {
        return encodeInternal(buf);
    }

    @Override
    public byte[] encode(BufferedImage buf) {
        buf = ImageUtils.resize(buf, imageWidth, imageHeight);
        buf =  ErrorDiffusionConverter.convert(buf, palette);
        return encodeInternal(buf);
    }

    private byte[] encodeInternal(BufferedImage buf) {
        Codec tileCodec = new TileCodec(palette);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int y=0; y+TileCodec.TILE_HEIGHT<=buf.getHeight(); y+=TileCodec.TILE_HEIGHT) {
            for (int x=0; x+TileCodec.TILE_WIDTH<=buf.getWidth(); x+=TileCodec.TILE_WIDTH) {
                try {
                    baos.write(tileCodec.encode(buf.getSubimage(x,y,TileCodec.TILE_WIDTH, TileCodec.TILE_HEIGHT)));
                } catch (IOException e) {
                    // Can likely be ignored for this in memory stream type
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }
}