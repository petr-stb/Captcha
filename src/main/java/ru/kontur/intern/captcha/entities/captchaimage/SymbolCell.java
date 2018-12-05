package ru.kontur.intern.captcha.entities.captchaimage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class SymbolCell{

    private int sizeX;
    private int sizeY;
    private Color backgroung;

    private BufferedImage image;
    private String symbol;
    private Color symbolColor;
    private String fontName;
    private int fontSize;
    private boolean isBold;
    private boolean isItalic;
    private int indentX;
    private int indentY;

    private int offsetY;

    private double rotate;
    private double scaleX;
    private double scaleY;

    public SymbolCell() {
        this.sizeX = ImageInit.SIZE_X;
        this.sizeY = ImageInit.SIZE_Y;
        this.backgroung = ImageInit.BACKGROUNG;

        this.image = new BufferedImage(sizeX, sizeY, TYPE_INT_RGB);

        this.symbol = getRandomSymbol();
        this.symbolColor = getRandomColorForSymbol(ImageInit.BACKGROUNG);
        this.fontName = ImageInit.FONT_NAME;
        this.fontSize = ImageInit.FONT_SIZE;
        this.isBold = getRandomIsBold();
        this.isItalic = getRandomIsItalic();
        this.indentX = ImageInit.INDENT_X;
        this.indentY = ImageInit.INDENT_Y;

        this.rotate = getRandomRotate(ImageInit.MAX_ROTATE, ImageInit.MIN_ROTATE);
        this.scaleX = getRandomScale(ImageInit.MIN_SCALE_X);
        this.scaleY = getRandomScale(ImageInit.MIN_SCALE_Y);
        this.offsetY = getRandomOffset(ImageInit.WORD_SIZE_Y);
    }

    public void createSymbol() {
        Graphics2D graphic = image.createGraphics();
        graphic.setColor(backgroung);
        graphic.fillRect(0, 0, sizeX, sizeY);
        graphic.setColor(symbolColor);
        int style = Font.PLAIN;
        if(isBold && !isItalic){
            style = Font.BOLD;
        } else if(!isBold && isItalic){
            style = Font.ITALIC;
        } else if(isBold && isItalic){
            style = Font.BOLD | Font.ITALIC;
        }
        Font font = new Font(fontName, style, fontSize);
        graphic.setFont(font);
        graphic.drawString(symbol, indentX, indentY);
    }

    public void distort() {
        this.transform(AffineTransform.getRotateInstance(rotate, sizeX / 2, sizeY / 2));
        this.transform(AffineTransform.getScaleInstance(scaleX, scaleY));
    }

    private void transform(AffineTransform transform) {
        Graphics2D graphic = image.createGraphics();
        BufferedImageOp newImageOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage newImage = new BufferedImage(sizeX, sizeY, TYPE_INT_RGB);

        Graphics2D graphicForBackground = newImage.createGraphics();
        graphicForBackground.setColor(backgroung);
        graphicForBackground.fillRect(0, 0, sizeX, sizeY);

        newImageOp.filter(image, newImage);
        graphic.drawImage(newImage, newImageOp, sizeX / 2, sizeY / 2);
        image = newImage;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    private double getRandomScale(double minScale) {
        return Math.random() * (1.0 - minScale) + minScale;
    }

    private double getRandomRotate(double maxRotate, double minRotate) {
        return Math.random() * (maxRotate - minRotate) + minRotate;
    }

    private boolean getRandomIsItalic() {
        return Math.random() > 0.5;
    }

    private boolean getRandomIsBold() {
        return Math.random() > 0.5;
    }

    private Color getRandomColorForSymbol(Color background) {
        Color colorForSymbol = new Color(0, 0, 0);
        //Обеспечиваем взаимную контрастность символа и фона
        boolean isContrast = false;
        while (!isContrast) {
            colorForSymbol = getRandomColor();
            int currentContrast = (background.getRed() + background.getGreen() + background.getBlue()) -
                    (colorForSymbol.getRed() + colorForSymbol.getGreen() + colorForSymbol.getBlue());
            if (Math.abs(currentContrast) > ImageInit.CONTRAST) {
                isContrast = true;
            }
        }
        return colorForSymbol;
    }

    private Color getRandomColor() {
        int maxValue = 255;
        int colorR = (int) (Math.random() * (maxValue + 1));
        int colorG = (int) (Math.random() * (maxValue + 1));
        int colorB = (int) (Math.random() * (maxValue + 1));
        return new Color(colorR, colorG, colorB);
    }

    private String getRandomSymbol() {
        int code = 0;
        boolean isSelect = false;
        while (!isSelect){
            //Цифры и большие латинские буквы в Unicode
            code = (int) (Math.random() * (90 - 48) + 48);
            if (code >= 58 && code <= 64){
                isSelect = false;
            } else {
                isSelect = true;
            }
        }
        return Character.toString((char) code);
    }

    private int getRandomOffset(int wordSizeY) {
        return (int) (Math.random() * (wordSizeY - sizeY));
    }
}
