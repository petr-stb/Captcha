package ru.kontur.intern.captcha.entities;

import ru.kontur.intern.captcha.entities.captchaimage.ImageInit;
import ru.kontur.intern.captcha.entities.captchaimage.WordPanel;
import ru.kontur.intern.captcha.lists.CaptchaImages;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CaptchaImage {
    private String word;
    private int id;
    private File file;

    public CaptchaImage() {
        WordPanel wordPanel = new WordPanel();
        wordPanel.createSpace();
        wordPanel.fillBySymbols();

        StringBuilder stringBuilder = new StringBuilder();
        String[] symbolArray = wordPanel.getSymbolArray();
        for (int i = 0; i < symbolArray.length; i++) {
            stringBuilder.append(symbolArray[i]);
        }
        word = stringBuilder.toString();
        id = CaptchaImages.getCurrentId();
        file = new File( ImageInit.PATH + id + "." + ImageInit.FORMAT);
        try {
            ImageIO.write(wordPanel.getImage(), ImageInit.FORMAT, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWord() {
        return word;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public File getFile() {
        return file;
    }
}