import com.octo.captcha.Captcha;
import com.octo.captcha.image.ImageCaptcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author spartajet
 * @description
 * @create 2019-03-09 18:34
 * @email spartajet.guo@gmail.com
 */
public class Main {
    public static void main(String[] args) {
        Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        ImageCodeEngine imageCodeEngine = new ImageCodeEngine();
        Captcha captcha = imageCodeEngine.getNextCaptcha();
        ImageCaptcha imageCaptcha = imageCodeEngine.getNextImageCaptcha();
        BufferedImage bufferedImage = imageCaptcha.getImageChallenge();
        File outputfile = new File("1.jpg");
        try {
            ImageIO.write(bufferedImage, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
