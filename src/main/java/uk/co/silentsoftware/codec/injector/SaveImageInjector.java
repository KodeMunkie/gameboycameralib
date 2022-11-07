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
package uk.co.silentsoftware.codec.injector;

import uk.co.silentsoftware.codec.Injector;
import uk.co.silentsoftware.codec.constants.IndexedPalette;
import uk.co.silentsoftware.codec.image.ImageCodec;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.silentsoftware.codec.constants.SaveImageConstants.*;


/**
 * Injects images into an existing Gameboy Camera save file
 */
public class SaveImageInjector implements Injector {

    private final ImageCodec imageCodec;
    private final ImageCodec smallImageCodec;

    public SaveImageInjector() {
        IndexedPalette indexedPalette = new IndexedPalette(IndexedPalette.EVEN_DIST_PALETTE);
        this.imageCodec = new ImageCodec(indexedPalette, IMAGE_WIDTH, IMAGE_HEIGHT);
        this.smallImageCodec = new ImageCodec(indexedPalette, SMALL_IMAGE_WIDTH, SMALL_IMAGE_HEIGHT);
    }

    @Override
    public byte[] inject(byte[] sourceSave, List<BufferedImage> payload) {
        byte[] result = sourceSave.clone();
        for (int i=0; i<MAX_SUPPORTED_IMAGES && i<payload.size(); ++i) {
            BufferedImage payloadImage = payload.get(i);
            int imageOffset = IMAGE_START_LOCATION+(i*NEXT_IMAGE_START_OFFSET);
            int smallImageOffset = imageOffset+SMALL_IMAGE_START_OFFSET;
            int activeFlagOffset = imageOffset+SMALL_IMAGE_START_OFFSET+SMALL_IMAGE_LENGTH;
            byte[] thumbImage = smallImageCodec.encode(payloadImage);
            byte[] image = imageCodec.encode(payloadImage);
            System.arraycopy(image,0,result, imageOffset ,image.length);
            System.arraycopy(thumbImage, 0, result, smallImageOffset, SMALL_IMAGE_LENGTH);
            result[activeFlagOffset] = (byte)0x10010101;
        }
        return result;
    }

    @Override
    public byte[] inject(File sourceSave, List<File> imageFiles) throws IOException {
        byte[] source = Files.readAllBytes(sourceSave.toPath());
        List<BufferedImage> images = imageFiles.stream().map(x-> {
            try {
                return ImageIO.read(new FileInputStream(x));
            } catch (IOException e) {
                // Just print the error and continue to inject what images we have
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return inject(source, images);
    }
}
