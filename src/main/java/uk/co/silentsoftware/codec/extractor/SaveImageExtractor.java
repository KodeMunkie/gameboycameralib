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
package uk.co.silentsoftware.codec.extractor;

import org.apache.commons.io.IOUtils;
import uk.co.silentsoftware.codec.Extractor;
import uk.co.silentsoftware.codec.constants.IndexedPalette;
import uk.co.silentsoftware.codec.image.ImageCodec;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.silentsoftware.codec.constants.SaveImageConstants.*;
public class SaveImageExtractor implements Extractor {

    private static final String PNG_FORMAT = "png";
    private static final int EMPTY_IMAGE_CHECKSUM = 0;
    private final ImageCodec imageCodec;
    private final ImageCodec smallImageCodec;
    public SaveImageExtractor(IndexedPalette palette) {
        this.imageCodec = new ImageCodec(palette, IMAGE_WIDTH, IMAGE_HEIGHT);
        this.smallImageCodec = new ImageCodec(palette, SMALL_IMAGE_WIDTH, SMALL_IMAGE_HEIGHT);
    }

    @Override
    public List<BufferedImage> extract(File file) throws IOException {
        return extract(IOUtils.toByteArray(Files.newInputStream(file.toPath())));
    }

    @Override
    public List<BufferedImage> extract(byte[] rawData) {
        List<BufferedImage> images = new ArrayList<>(30);
        try {
            for (int i = IMAGE_START_LOCATION; i < rawData.length; i += NEXT_IMAGE_START_OFFSET) {

                // The full size images
                byte[] image = new byte[IMAGE_LENGTH];
                System.arraycopy(rawData, i, image, 0, IMAGE_LENGTH);
                if (isEmptyImage(image)) {
                    continue;
                }
                images.add(imageCodec.decode(image));

                // The thumbs
                byte[] thumbImage = new byte[SMALL_IMAGE_LENGTH];
                System.arraycopy(rawData, i+SMALL_IMAGE_START_OFFSET, thumbImage, 0, SMALL_IMAGE_LENGTH);
                images.add(smallImageCodec.decode(thumbImage));
            }
        } catch (Exception e) {
            // Just print the error and continue to return what images we have
            e.printStackTrace();
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

    private boolean isEmptyImage(byte[] bytes) {
        int checksum = 0;
        for (byte  b: bytes) {
            checksum^=b;
        }
        return (byte)checksum == EMPTY_IMAGE_CHECKSUM;
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
