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
package uk.co.silentsoftware.codec.printer;

import org.junit.Assert;
import org.junit.Test;

public class PrintStatusTest {

    @Test
    public void testChecksumError() {
        byte b = 1; // 00000001
        Assert.assertEquals(PrintStatus.CHECKSUM_ERROR, PrintStatus.statusOf(b));
    }

    @Test
    public void testPrinterBusyError() {
        byte b = 2; // 00000010
        Assert.assertEquals(PrintStatus.PRINTER_BUSY, PrintStatus.statusOf(b));
    }

    @Test
    public void testImageDataFullError() {
        byte b = 4; // 00000100
        Assert.assertEquals(PrintStatus.IMAGE_DATA_FULL, PrintStatus.statusOf(b));
    }

    @Test
    public void testUnprocessedDataError() {
        byte b = 8; // 00001000
        Assert.assertEquals(PrintStatus.UNPROCESSED_DATA, PrintStatus.statusOf(b));
    }

    @Test
    public void testPacketError() {
        byte b = 16; // 00001000
        Assert.assertEquals(PrintStatus.PACKET_ERROR, PrintStatus.statusOf(b));
    }

    @Test
    public void testPaperJamError() {
        byte b = 32; // 00100000
        Assert.assertEquals(PrintStatus.PAPER_JAM, PrintStatus.statusOf(b));
    }

    @Test
    public void testOtherError() {
        byte b = 64; // 01000000
        Assert.assertEquals(PrintStatus.OTHER_ERROR, PrintStatus.statusOf(b));
    }

    @Test
    public void testBatteryTooLowError() {
        byte b = -128; // 10000000
        Assert.assertEquals(PrintStatus.BATTERY_TOO_LOW, PrintStatus.statusOf(b));
    }
}
