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
package uk.co.silentsoftware.codec;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface for extracting raw 2bpp image(s) data from different source mediums, e.g
 * Gameboy save file or Gameboy printer dump, and returns the decoded images in the method
 * specified format.
 */
public interface Extractor {

    /**
     * Extract images from a local file system file
     *
     * @param file the file to read from
     * @return the BufferedImage representation(s)
     * @throws IOException if the file fails
     */
    List<BufferedImage> extract(File file) throws IOException;

    /**
     * Extract images from a raw byte data stream
     *
     * @param rawData the raw byte data to read from
     * @return the BufferedImage representation(s)
     */
    List<BufferedImage> extract(byte[] rawData);

    /**
     * Extract images from a local file system file and return as PNG byte data
     *
     * @param file the file to read from
     * @return the PNG representation(s)
     * @throws IOException if the file fails
     */
    List<byte[]> extractAsPng(File file) throws IOException;

    /**
     * Extract images from a raw byte data stream and return as PNG byte data
     *
     * @param rawData the raw byte data to read from
     * @return the PNG representation(s)
     */
    List<byte[]> extractAsPng(byte[] rawData);
}
