# Gameboy Camera Lib
A library capable of encoding and decoding Gameboy Camera 2BPP images as PNGs or BufferedImages.

## Usage
In short, there is no maven build or artifact yet as this is still pre-release - this means 
the API is still likely to change.

However, if you want to try it...

### Choose a palette
In the "uk.co.silentsoftware.codec.IndexPalette" class decide which constant palette you want to
use to decode/encode images or define your own indexed array and use it with this class.
Inject the IndexedPalette into the constructor for the decoder or encoder classes described below.

### To decode
If you have a Gameboy Camera save dump call any of the public "extract" methods in:
"uk.co.silentsoftware.codec.extractor.SaveImageExtractor" using the "Extractor" interface.

Or if you have the the 2bpp byte data as an array already call the public "decode" method in:
"uk.co.silentsoftware.codec.image.ImageCodec" using the "Codec" interface.

### To encode
With a BufferedImage call any of the public "encode" methods in
"uk.co.silentsoftware.codec.image.ImageCodec" using the "Codec" interface.

The source BufferedImage data will be dithered and converted to the correct dimensions
and palette automatically. If you want to customise this then consider using the
"encodeNoPreprocessing" method, but you will need the image to have the palette defined
and in use in IndexedPalette, with the width/height of the Gameboy image format 
(160x144 or 128x112).

### Still to do
Method comments and javadoc, tests and error handling (code has all been tested manually so YMMV). 
Facades, builder pattern construction, dither configuration options, and command line interface 
code will be added later.