import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.gimpy.SimpleListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.awt.image.ImageFilter;

/**
 * @author spartajet
 * @description
 * @create 2019-03-09 18:38
 * @email spartajet.guo@gmail.com
 */
public class ImageCodeEngine extends SimpleListImageCaptchaEngine {
    private static final String CODE = "草百有嶙";
    // I、0去掉"一二三四五六七字符显示的个数"
    // 字符显示的个数
    private static final Integer MIN_WORD_LEN = 4;
    // 字符显示的个数
    private static final Integer MAX_WORD_LEN = 4;
    // 验证码图片的高度宽度设定
    private static final Integer IMAGE_WIDTH = 100;
    private static final Integer IMAGE_HEIGHT = 40;
    // 验证码中显示的字体大小
    private static final Integer MIN_FONT_SIZE = 24;
    private static final Integer MAX_FONT_SIZE = 25;

    @Override
    protected void buildInitialFactories() {
        WordGenerator wordGenerator = new RandomWordGenerator(CODE);
        BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(
                IMAGE_WIDTH, IMAGE_HEIGHT, Color.white, Color.white);
// 字体格式
        Font[] fontsList;
//        fontsList = new Font[]{Font.decode("STSongti-SC-Light")};
        fontsList = new Font[]{new Font("STSongti-SC-Light",Font.PLAIN,1)};
        //可以使用中文验证码，另外汉字宽度比较大，要重新调整一下字体大小,不然会出现异常
// 字体随机生成
        FontGenerator fontGenerator = new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, fontsList);
// 背景颜色随机生成
// 验证码的颜色-使用随机颜色器new Integer[]{0,100},new Integer[]{0,100}, new
// Integer[]{0,100}
        RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(
                new int[]{0, 150}, new int[]{0, 150},
                new int[]{0, 150});
        backgroundGenerator = new FunkyBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT);
        //文字干扰器--- 可以创建多个
        //气泡干扰
        BaffleTextDecorator baffleTextDecorator = new BaffleTextDecorator(2, cgen);
        //曲线干扰
        LineTextDecorator lineTextDecorator = new LineTextDecorator(1, new RandomRangeColorGenerator(new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}));
        TextDecorator[] textDecorators = new TextDecorator[0];
//textDecorators[0] = baffleTextDecorator;
//textDecorators[0] = lineTextDecorator;
        TextPaster textPaster = new DecoratedRandomTextPaster(MIN_WORD_LEN, MAX_WORD_LEN, new RandomRangeColorGenerator(new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}), true, new TextDecorator[]{baffleTextDecorator, lineTextDecorator});


//过滤器
        WaterFilter water = new WaterFilter();
        //振幅
        water.setAmplitude(4d);
        //显示字会出现锯齿状,true就是平滑的
        water.setAntialias(true);
        //月亮的盈亏
        //water.setPhase(30d);
        water.setWavelength(60d);

        ImageDeformation backDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation textDef = new ImageDeformationByFilters(
                new ImageFilter[]{});
        ImageDeformation postDef = new ImageDeformationByFilters(
                new ImageFilter[]{water});
        // 生成图片输出
        WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
                backgroundGenerator, textPaster);
        addFactory(new GimpyFactory(wordGenerator, wordToImage));
    }
}
