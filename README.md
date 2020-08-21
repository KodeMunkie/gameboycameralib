# Gameboy Camera Lib
A library capable of encoding and decoding Gameboy Camera 2BPP images as PNGs or BufferedImages.

## Usage
In short, there is no maven build or artifact yet as this is still pre-release - this means 
the API is still likely to change.

However, if you want to try it...

### Choose a palette
Gameboy images contain no RGB palette information, only 4 shades. The representation of these shades
on a true colour display requires you to make a choice as to what RGB values they represent, usually greens 
(screen like) or greys (printer like). 

A few example palettes are already available in the "uk.co.silentsoftware.codec.IndexPalette" class.
From the "uk.co.silentsoftware.codec.IndexPalette" class decide which palette constant you want to
use to decode/encode images or define your own indexed array pass it into the constructor on this class.
Inject the IndexedPalette into the constructor for the decoder or encoder classes described below.

### To decode
If you have a Gameboy Camera save dump call any of the public "extract" or "extractAsPng" methods in:
"uk.co.silentsoftware.codec.extractor.SaveImageExtractor" using the "Extractor" interface.

E.g.
```java
Extractor extractor = new SaveImageExtractor(new IndexedPalette(IndexedPalette.EVEN_DIST_PALETTE));
List<BufferedImage> images = extractor.extract(new File("C:/Temp/MyGameboyImageSaveFile.sav"));
```
Or
```java
Extractor extractor = new SaveImageExtractor(new IndexedPalette(IndexedPalette.EVEN_DIST_PALETTE));
List<byte[]> images = extractor.extractAsPng(new File("C:/Temp/MyGameboyImageSaveFile.sav"));
```

Or if you have a single extracted 2bpp byte data as an array already call the public "decode" method in:
"uk.co.silentsoftware.codec.image.ImageCodec" using the "Codec" interface.

E.g.
```java
Codec codec = new ImageCodec(new IndexedPalette(IndexedPalette.GAMEBOY_LCD_PALETTE), SaveImageConstants.IMAGE_WIDTH, SaveImageConstants.IMAGE_HEIGHT)
BufferedImage myBufferedImage = codec.decode(myRaw2BppImageDataArray);
```

### To encode
With a BufferedImage call any of the public "encode" methods in
"uk.co.silentsoftware.codec.image.ImageCodec" using the "Codec" interface.

E.g.
```java
Codec codec = new ImageCodec(new IndexedPalette(IndexedPalette.GAMEBOY_LCD_PALETTE), SaveImageConstants.IMAGE_WIDTH, SaveImageConstants.IMAGE_HEIGHT)
byte[] myRaw2BppImageDataArray = codec.encode(myBufferedImageData);
```

The source BufferedImage data assumed using the the Indexed palette RGB values, will be dithered and converted to the 
specified dimensions to be compatible with Gameboy hardware. If you want to customise this then consider using the
"encodeNoPreprocessing" method, but you will need the image to have the RGB values defined
and in use in IndexedPalette, with the width/height of the Gameboy image format 
(160x144 for printer saves or 128x112 for camera dumps).
Equivalent to SaveImageExtractor there is also a SaveImageInjector class which can put overwrite images in an existing
save file, however it currently lacks the ability to create a Gameboy save file from scratch so I'm not recommending
you use that yet.

### Still to do
PrinterImageExtractor (ImageCodec class' decode methods already work for single printer images, but direct extraction 
from a print stream/dump is not yet possible. 
Improve SaveImageInjector to create images too and add a PrinterImageInjector implementation. 

Also still to change - method comments and javadoc, tests and error handling (code has all been tested manually so YMMV). 
Facades, builder pattern construction, dither configuration options, and command line interface 
code.