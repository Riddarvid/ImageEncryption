import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    private enum Pattern {
        FILLED,
        EMPTY,
        P1,
        P2
    }

    public static void main(String[] args) {
        try {
            BufferedImage source = ImageIO.read(new File("res\\source.bmp"));
            encode(source);
            BufferedImage img1 = ImageIO.read(new File("res\\img1.bmp"));
            BufferedImage img2 = ImageIO.read(new File("res\\img2.bmp"));
            decode(img1, img2);
        } catch (IOException e) {

        }
    }

    private static void decode(BufferedImage img1, BufferedImage img2) {
        try {
            BufferedImage decoded = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) == -1 && img2.getRGB(x, y) == -1) {
                        decoded.setRGB(x, y, -1);
                    } else {
                        decoded.setRGB(x, y, 0);
                    }
                }
            }
            ImageIO.write(decoded, "bmp", new File("res\\decoded.bmp"));
        } catch (IOException e) {

        }
    }

    private static void encode(BufferedImage sourceImg) {
        try {
            Random rand = new Random();
            int sourceHeight = sourceImg.getHeight();
            int sourceWidth = sourceImg.getWidth();
            BufferedImage sourceImgScaled = new BufferedImage(sourceWidth * 2, sourceHeight * 2, BufferedImage.TYPE_BYTE_BINARY);
            BufferedImage img1 = new BufferedImage(sourceWidth * 2, sourceHeight * 2, BufferedImage.TYPE_BYTE_BINARY);
            BufferedImage img2 = new BufferedImage(sourceWidth * 2, sourceHeight * 2, BufferedImage.TYPE_BYTE_BINARY);
            for (int x = 0; x < sourceWidth; x++) {
                for (int y = 0; y < sourceHeight; y++) {
                    boolean r = rand.nextBoolean();
                    if (sourceImg.getRGB(x, y) != -1) {
                        pattern(x, y, Pattern.FILLED, sourceImgScaled);
                        if (r) {
                            pattern(x, y, Pattern.P2, img1);
                            pattern(x, y, Pattern.P1, img2);
                        } else {
                            pattern(x, y, Pattern.P1, img1);
                            pattern(x, y, Pattern.P2, img2);
                        }
                    } else {
                        pattern(x, y, Pattern.EMPTY, sourceImgScaled);
                        if (r) {
                            pattern(x, y, Pattern.P2, img1);
                            pattern(x, y, Pattern.P2, img2);
                        } else {
                            pattern(x, y, Pattern.P1, img1);
                            pattern(x, y, Pattern.P1, img2);
                        }
                    }
                }
            }
            ImageIO.write(sourceImgScaled, "bmp", new File("res\\sourceScaled.bmp"));
            ImageIO.write(img1, "bmp", new File("res\\img1.bmp"));
            ImageIO.write(img2, "bmp", new File("res\\img2.bmp"));
            System.out.println("Yo");
        } catch (IOException e) {
            System.out.println("Möp");
        }
    }

    private static void pattern(int x, int y, Pattern pattern, BufferedImage img) {
        byte a, b, c, d;
        switch (pattern) {
            case FILLED:
                a = b = c = d = 0;
                break;
            case EMPTY:
                a = b = c = d = -1;
                break;
            case P1:
                a = d = 0;
                b = c = -1;
                break;
            case P2:
                a = d = -1;
                b = c = 0;
                break;
            default:
                throw new IllegalArgumentException();
        }
        img.setRGB(2 * x, 2 * y, a);
        img.setRGB(2 * x + 1, 2 * y, b);
        img.setRGB(2 * x, 2 * y + 1, c);
        img.setRGB(2 * x + 1, 2 * y + 1, d);
    }
}
