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

import java.util.BitSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PrintStatus {
    CHECKSUM_ERROR(0),
    PRINTER_BUSY(1),
    IMAGE_DATA_FULL(2),
    UNPROCESSED_DATA(3),
    PACKET_ERROR(4),
    PAPER_JAM(5),
    OTHER_ERROR(6),
    BATTERY_TOO_LOW(7);

    private final int bitNumber;
    private static final Map<Integer, PrintStatus> BIT_STATUS_MAP =
        Stream.of(PrintStatus.values()).collect(Collectors.toMap(status -> status.bitNumber, Function.identity()));

    PrintStatus(int bitNumber) {
        this.bitNumber = bitNumber;
    }

    public static PrintStatus statusOf(byte... b) {
        return BIT_STATUS_MAP.get(BitSet.valueOf(b).stream().findFirst().getAsInt());
    }
}
