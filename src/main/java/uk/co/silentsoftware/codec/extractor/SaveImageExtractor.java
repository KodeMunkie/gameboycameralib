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

import org.apache.commons.io.IOUtils;
import uk.co.silentsoftware.codec.Extractor;
import uk.co.silentsoftware.codec.IndexedPalette;
import uk.co.silentsoftware.codec.image.ImageCodec;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaveImageExtractor implements Extractor {

    private static final int IMAGE_WIDTH = 128;
    private static final int IMAGE_HEIGHT = 112;
    private static final int IMAGE_START_LOCATION = 0x2000;
    private static final int IMAGE_LENGTH = 0x1000;
    private static final String PNG_FORMAT = "png";
    private final ImageCodec imageCodec;

    public SaveImageExtractor(IndexedPalette palette) {
        imageCodec = new ImageCodec(palette, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    public List<BufferedImage> extract(File file) throws IOException {
        return extract(IOUtils.toByteArray(new FileInputStream(file)));
    }

    @Override
    public List<BufferedImage> extract(byte[] rawData) {
        List<BufferedImage> images = new ArrayList<>(30);
        for (int i=IMAGE_START_LOCATION; i<rawData.length; i+=IMAGE_LENGTH) {
            byte[] b = new byte[IMAGE_LENGTH];
            System.arraycopy(rawData, i, b, 0, IMAGE_LENGTH);
            images.add(imageCodec.decode(b));
        }
        return images;
    }

    @Override
    public List<byte[]> extractAsPng(File file) throws IOException {
        return extract(file).stream().map(this::imageToBytes).collect(Collectors.toList());
    }

    @Override
    public List<byte[]> extractAsPng(byte[] rawData) {
        return extract(rawData).stream().map(this::imageToBytes).collect(Collectors.toList());
    }

    private byte[] imageToBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, PNG_FORMAT, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
