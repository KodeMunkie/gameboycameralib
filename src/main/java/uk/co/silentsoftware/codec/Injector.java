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
 * Interface for injecting raw 2bpp image(s) data into different source mediums, e.g
 * Gameboy save file or Gameboy printer dump, and returns the encoded data as bytes
 * in the class specified format.
 */
public interface Injector {

    byte[] inject(byte[] sourceData, List<BufferedImage> payload);
    byte[] inject(File sourceFile, List<File> imageFiles) throws IOException;
}
