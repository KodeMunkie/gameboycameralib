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
package uk.co.silentsoftware.util.printer;

import static uk.co.silentsoftware.codec.constants.PrinterConstants.MAX_PRINTER_BUFFER_LENGTH;

import java.util.stream.Stream;

public class PrinterBufferStream {

    private final byte[] imageData;

    public PrinterBufferStream(byte[] imageData) {
        if (imageData.length % MAX_PRINTER_BUFFER_LENGTH != 0) {
            throw new IllegalArgumentException("Provided data is not in gameboy tile format");
        }
        this.imageData = imageData;
    }

    public Stream<byte[]> stream() {
        Stream.Builder<byte[]> builder = Stream.builder();
        for (int position = 0; position<imageData.length; position+= MAX_PRINTER_BUFFER_LENGTH) {
            byte[] b = new byte[MAX_PRINTER_BUFFER_LENGTH];
            System.arraycopy(imageData, position, b, 0, MAX_PRINTER_BUFFER_LENGTH);
            builder.add(b);
        }
        return builder.build();
    }
}
