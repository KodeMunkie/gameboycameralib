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
