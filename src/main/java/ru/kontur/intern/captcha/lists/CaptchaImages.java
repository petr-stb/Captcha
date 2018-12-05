package ru.kontur.intern.captcha.lists;

import ru.kontur.intern.captcha.entities.CaptchaImage;

import java.util.ArrayList;
import java.util.List;

public class CaptchaImages {
    private static volatile List<CaptchaImage> images;
    private static volatile int currentId = 0;

    public static List<CaptchaImage> getImages(){
        if(images == null){
            synchronized (CaptchaImages.class){
                if(images == null){
                    images = new ArrayList<CaptchaImage>();
                }
            }
        }
        return images;
    }

    public static void add(CaptchaImage image){
        synchronized (CaptchaImages.class){
            getImages().add(image);
            currentId++;
        }
    }

    public static int getCurrentId(){
        return currentId;
    }

    public static CaptchaImage getImageById(String id) {
        for (CaptchaImage image : getImages()) {
            if(image.getId().equals(id)) {
                return image;
            }
        }
        return null;
    }

    public static void deleteImageById(String id) {
        synchronized (CaptchaImages.class){
            CaptchaImage captchaImage = getImageById(id);
            if(captchaImage != null){
                captchaImage.getFile().delete();
                images.remove(captchaImage);
            }
        }
    }
}
