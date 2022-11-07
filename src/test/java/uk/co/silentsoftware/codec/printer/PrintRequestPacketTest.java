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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PrintRequestPacketTest {

    @Test
    public void testDataPayload() throws DecoderException {
        byte[] chunk = Hex.decodeHex(PrintRequestPacketFixture.BANNER_DATA_PAYLOAD);
        PrintRequestPacket printRequestPacket = new PrintRequestPacket(Command.DATA, chunk);
        String hexString = printRequestPacket.toHexString();
        Assert.assertEquals(PrintRequestPacketFixture.EXPECTED_BANNER_DATA_PAYLOAD_PACKET, hexString);
    }

    @Test
    public void testEmptyDataPayload() throws DecoderException, IOException {
       PrintRequestPacket printRequestPacket = new PrintRequestPacket(Command.DATA);
       String hexString = printRequestPacket.toHexString();
       Assert.assertEquals("88330400000004000000", hexString);
    }

    @Test
    public void testPrintPayload() throws DecoderException, IOException {
        PrintRequestPacket printRequestPacket = new PrintRequestPacket(Command.PRINT, new byte[]{0x01, 0x13, (byte)0xE4, 0x40});
        String hexString = printRequestPacket.toHexString();
        Assert.assertEquals("8833020004000113e4403e010000", hexString);
    }

    @Test
    public void testInquiryPayload() throws DecoderException, IOException {
        PrintRequestPacket printRequestPacket = new PrintRequestPacket(Command.INQUIRY);
        String hexString = printRequestPacket.toHexString();
        Assert.assertEquals("88330f0000000f000000", hexString);
    }
}
