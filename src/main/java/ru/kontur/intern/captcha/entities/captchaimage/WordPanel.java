package ru.kontur.intern.captcha.entities.captchaimage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class WordPanel {
    private int wordSizeX;
    private int wordSizeY;
    private Color backGround;
    private int countOfSymbols;
    private String[] symbolArray = new String[ImageInit.COUNT_OF_SYMBOLS];
    private BufferedImage image;

    public WordPanel() {
        this.wordSizeX = ImageInit.COUNT_OF_SYMBOLS * ImageInit.SIZE_X;
        this.wordSizeY = ImageInit.WORD_SIZE_Y;
        this.backGround = ImageInit.BACKGROUNG;
        this.countOfSymbols = ImageInit.COUNT_OF_SYMBOLS;
        this.image = new BufferedImage(wordSizeX, wordSizeY, TYPE_INT_RGB);
    }

    public void createSpace(){
        Graphics2D graphic = image.createGraphics();
        graphic.setColor(backGround);
        graphic.fillRect(0, 0, wordSizeX, wordSizeY);
    }

    public void fillBySymbols() {
        for (int i = 0; i < countOfSymbols; i++) {
            Graphics2D graphic = image.createGraphics();
            SymbolCell symbolCell = new SymbolCell();
            symbolArray[i] = symbolCell.getSymbol();
            symbolCell.createSymbol();
            symbolCell.distort();
            graphic.drawImage(symbolCell.getImage(), symbolCell.getSizeX() * i, symbolCell.getOffsetY(), null);
        }
    }

    public String[] getSymbolArray() {
        return symbolArray;
    }

    public BufferedImage getImage() {
        return image;
    }

}
