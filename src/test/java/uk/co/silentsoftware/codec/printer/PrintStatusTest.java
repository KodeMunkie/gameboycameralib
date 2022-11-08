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
