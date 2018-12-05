package ru.kontur.intern.captcha.timer;

import ru.kontur.intern.captcha.lists.CaptchaImages;

public class TimerThread extends Thread{

    private String captchaId;

    private static int timeOut = 128; //Значение в секундах по умолчанию

    @Override
    public void run(){
        try {
            Thread.sleep(1000 * timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CaptchaImages.deleteImageById(captchaId);
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaId() {
        return captchaId;
    }
}
