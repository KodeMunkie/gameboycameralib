package uk.co.silentsoftware.codec.tile;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.silentsoftware.codec.Codec;
import uk.co.silentsoftware.codec.constants.IndexedPalette;

import java.awt.image.BufferedImage;

public class TileCodecTest {

    private static String TEST_TILE_DATA_1 = "FF007EFF858189839385A58BC9977EFF";
    private static String TEST_TILE_DATA_2 = "7C7C00C6C60000FEC6C600C6C6000000";

    @Test
    public void testSimpleTile1() throws Exception {
        Codec codec = new TileCodec(new IndexedPalette());
        BufferedImage image = codec.decode(Hex.decodeHex(TEST_TILE_DATA_1));
        Assertions.assertEquals(TEST_TILE_DATA_1, Hex.encodeHexString(codec.encode(image),false));
    }

    @Test
    public void testSimpleTile2() throws Exception {
        Codec codec = new TileCodec(new IndexedPalette());
        BufferedImage image = codec.decode(Hex.decodeHex(TEST_TILE_DATA_2));
        Assertions.assertEquals(TEST_TILE_DATA_2, Hex.encodeHexString(codec.encode(image),false));
    }
}
