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

import java.math.BigInteger;

/**
 * Printer request data packet for use on a serial stream to a real GB printer
 */
public class PrintRequestPacket {
    private final byte[] syncWords = new byte[]{(byte) 0x88, (byte)0x33};
    private Command command;
    private final byte compression = 0x00;
    private byte[] payload;
    private byte[] checksumBytes;
    private final byte ack = 0x00;
    private final byte status = 0x00;

    private byte[] dataLength;

    public PrintRequestPacket(Command command, byte... payload) {
        this.command = command;
        this.payload = payload;
        dataLength = new byte[]{
                (byte)(0x000000FF&payload.length),
                (byte)((0x0000FF00&payload.length)>>8),
        };
        checksumBytes = createChecksumBytes(dataLength);
    }

    public byte[] toBytes() {
        byte[] syncAndCommand = new byte[]{syncWords[0], syncWords[1], command.toByte(), compression};
        byte[] ackAndStatus = new byte[]{ack,status};
        return createPacketBytes(syncAndCommand, dataLength, payload, checksumBytes, ackAndStatus);
    }

    private byte[] createChecksumBytes(byte[] dataLength) {
        int payloadSum = 0;
        for (byte b : payload) {
            payloadSum += Byte.toUnsignedInt(b);
        }
        int checksumTotal = Byte.toUnsignedInt(command.toByte())
                +Byte.toUnsignedInt(dataLength[0])
                +Byte.toUnsignedInt(dataLength[1])
                +payloadSum;
        byte[] checksum = new byte[] {
            (byte)(0x000000FF&checksumTotal),
            (byte)((0x0000FF00&checksumTotal)>>8),
        };
        //System.out.println("Checksum:"+new BigInteger(1, checksum).toString(16));
        return checksum;
    }

    private byte[] createPacketBytes(byte[] syncAndCommand, byte[] dataLength, byte[] payload, byte[] checksum, byte[] ackAndStatus) {
        byte[] packet = new byte[syncAndCommand.length+dataLength.length+payload.length+checksum.length+ackAndStatus.length];
        int position = 0;
        System.arraycopy(syncAndCommand, 0, packet, position, syncAndCommand.length);
        position += syncAndCommand.length;
        System.arraycopy(dataLength, 0, packet, position, dataLength.length);
        position += dataLength.length;
        System.arraycopy(payload, 0, packet, position, payload.length);
        position += payload.length;
        System.arraycopy(checksum, 0, packet, position, checksum.length);
        position += checksum.length;
        System.arraycopy(ackAndStatus, 0, packet, position, ackAndStatus.length);
        position += ackAndStatus.length;
        assert position == packet.length;
        return packet;
    }

    public String toHexString() {
        return new BigInteger(1, toBytes()).toString(16);
    }
}
