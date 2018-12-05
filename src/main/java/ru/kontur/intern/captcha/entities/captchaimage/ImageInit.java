package ru.kontur.intern.captcha.entities.captchaimage;

import java.awt.*;

public class ImageInit {

    static final int SIZE_X = 40;
    static final int SIZE_Y = 40;

    static final String FONT_NAME = "Areal";
    static final  int FONT_SIZE = (int)(SIZE_Y * 1.15);

    static final int INDENT_X = 0;
    static final int INDENT_Y = (int) (FONT_SIZE * 0.8);

    static final double MAX_ROTATE = Math.PI/6;
    static final double MIN_ROTATE = -Math.PI/6;
    static final double MIN_SCALE_X = 0.7;
    static final  double MIN_SCALE_Y = 0.7;

    public static final String PATH = "";
    public static final String FORMAT = "png";

    static final int CONTRAST = 30;

    static final int WORD_SIZE_Y = (int) (SIZE_Y * 2.5);
    static final Color BACKGROUNG = new Color(128, 128, 128);
    static final int COUNT_OF_SYMBOLS = 8;


}
