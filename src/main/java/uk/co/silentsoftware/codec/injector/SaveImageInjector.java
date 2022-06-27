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

import org.apache.commons.io.IOUtils;
import uk.co.silentsoftware.codec.constants.IndexedPalette;
import uk.co.silentsoftware.codec.Injector;
import uk.co.silentsoftware.codec.image.ImageCodec;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.silentsoftware.codec.constants.SaveImageConstants.*;


/**
 * Injects images into an existing Gameboy Camera save file
 */
public class SaveImageInjector implements Injector {

    private final ImageCodec imageCodec;

    public SaveImageInjector() {
        this.imageCodec = new ImageCodec(new IndexedPalette(IndexedPalette.EVEN_DIST_PALETTE), IMAGE_WIDTH, IMAGE_HEIGHT);;
    }

    @Override
    public byte[] inject(byte[] sourceSave, List<BufferedImage> payload) {
        byte[] result = sourceSave.clone();
        for (int i=0, payloadSize=payload.size(); i<MAX_SUPPORTED_IMAGES && i<payloadSize; ++i) {
            byte[] encodedImage = imageCodec.encode(payload.get(i));
            System.arraycopy(encodedImage,0,result,IMAGE_START_LOCATION+(i*NEXT_IMAGE_START_OFFSET),encodedImage.length);
        }
        return result;
    }

    @Override
    public byte[] inject(File sourceSave, List<File> imageFiles) throws IOException {
        byte[] source = IOUtils.toByteArray(new FileInputStream(sourceSave));
        // TODO: Actual error handling!
        List<BufferedImage> images = imageFiles.stream().map(x-> {
            try {
                return ImageIO.read(new FileInputStream(x));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return inject(source, images);
    }
}
