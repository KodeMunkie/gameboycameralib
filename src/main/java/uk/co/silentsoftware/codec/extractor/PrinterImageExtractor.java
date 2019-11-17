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
package uk.co.silentsoftware.codec.extractor;

import uk.co.silentsoftware.codec.Codec;
import uk.co.silentsoftware.codec.Extractor;
import uk.co.silentsoftware.codec.constants.IndexedPalette;
import uk.co.silentsoftware.codec.image.ImageCodec;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

// TODO: Needs implementation based on testing
public class PrinterImageExtractor implements Extractor {

    private static final int IMAGE_WIDTH = 160;
    private static final int IMAGE_HEIGHT = 144;
    private final Codec imageCodec;

    public PrinterImageExtractor(IndexedPalette palette) {
        imageCodec = new ImageCodec(palette, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    public List<BufferedImage> extract(File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BufferedImage> extract(byte[] rawData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<byte[]> extractAsPng(File file) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<byte[]> extractAsPng(byte[] rawData) {
        throw new UnsupportedOperationException();
    }
}
