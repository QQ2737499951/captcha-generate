import com.octo.captcha.image.ImageCaptcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author spartajet
 * @description
 * @create 2019-03-09 18:34
 * @email spartajet.guo@gmail.com
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        ImageCodeEngine imageCodeEngine = new ImageCodeEngine();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class clazz = loader.loadClass("com.octo.captcha.image.gimpy.Gimpy");
        Field responseFld = clazz.getDeclaredField("response");
        //取消java语言访问检查以访问private变量
        responseFld.setAccessible(true);
        for (int i = 0; i < 50000; i++) {
            ImageCaptcha imageCaptcha = imageCodeEngine.getNextImageCaptcha();
            String response = responseFld.get(imageCaptcha).toString();
            BufferedImage bufferedImage = imageCaptcha.getImageChallenge();
            File outputfile = new File(System.getProperty("user.dir")+"/images/"+response + ".jpg");
            try {
                ImageIO.write(bufferedImage, "jpg", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
