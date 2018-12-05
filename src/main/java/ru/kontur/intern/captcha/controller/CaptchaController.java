package ru.kontur.intern.captcha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

import ru.kontur.intern.captcha.entities.User;
import ru.kontur.intern.captcha.entities.CaptchaImage;
import ru.kontur.intern.captcha.lists.CaptchaImages;
import ru.kontur.intern.captcha.lists.Users;
import ru.kontur.intern.captcha.timer.TimerThread;

import java.io.*;
import java.util.Map;

@Controller
public class CaptchaController {

    @RequestMapping(value = "/client/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> register(){
        User user = new User();
        Users.add(user);
        JSONObject json = new JSONObject();
        json.put("secret", user.getSecretKey());
        json.put("public", user.getPublicKey());
        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "/captcha/new", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> check(@RequestParam Map<String,String> allRequestParams) {
        if(allRequestParams.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String publicKey = "";
        for(Map.Entry<String,String> pair : allRequestParams.entrySet()){
            if(pair.getKey().equals("public")){
                publicKey = pair.getValue();
            }
        }
        User user = Users.getUserByPublicKey(publicKey);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        CaptchaImage captchaImage = new CaptchaImage();
        CaptchaImages.add(captchaImage);
        JSONObject json = new JSONObject();
        json.put("request", captchaImage.getId());
        if(System.getProperty("production") != null){
            if (System.getProperty("production").equals("test")){
                json.put("answer", captchaImage.getWord());
            } else {
                json.put("answer", "");
            }
        } else {
            json.put("answer", "");
        }
        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "/captcha/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<?> getImage(@RequestParam Map<String,String> allRequestParams){
        if(allRequestParams.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String publicKey = "";
        String id = "";
        for(Map.Entry<String,String> pair : allRequestParams.entrySet()){
            if(pair.getKey().equals("public")){
                publicKey = pair.getValue();
            } else if(pair.getKey().equals("request")){
                id = pair.getValue();
            }
        }
        User user = Users.getUserByPublicKey(publicKey);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if(id.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        CaptchaImage captchaImage = CaptchaImages.getImageById(id);
        if(captchaImage == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        byte[] bytes = null;
        try {
            File file = captchaImage.getFile();
            FileInputStream fis = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
            fis.close();
        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        }
        TimerThread timer = new TimerThread();
        timer.setCaptchaId(id);
        timer.start();
        return ResponseEntity.ok(bytes);
    }

    @RequestMapping(value = "/captcha/solve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> sendSolve(@RequestParam Map<String,String> allRequestParams){
        if(allRequestParams.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String publicKey = "";
        String id = "";
        String enteredAnswer = "";
        for(Map.Entry<String,String> pair : allRequestParams.entrySet()){
            if(pair.getKey().equals("public")) {
                publicKey = pair.getValue();
            } else if(pair.getKey().equals("request")) {
                id = pair.getValue();
            } else if(pair.getKey().equals("answer")) {
                enteredAnswer = pair.getValue();
            }
        }
        User user = Users.getUserByPublicKey(publicKey);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if(id.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        enteredAnswer = enteredAnswer.toUpperCase().replaceAll(" ", "");
        CaptchaImage captchaImage = CaptchaImages.getImageById(id);
        String referenceAnswer = captchaImage != null ? captchaImage.getWord() : null;
        CaptchaImages.deleteImageById(id);
        if(!enteredAnswer.equals(referenceAnswer)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        user.setToken();
        JSONObject json = new JSONObject();
        json.put("response", user.getToken());
        return ResponseEntity.ok(json);
    }

    @RequestMapping(value = "/captcha/verify", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> checkToken(@RequestParam Map<String,String> allRequestParams) {
        if(allRequestParams.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String secretKey = "";
        String token = "";
        for(Map.Entry<String,String> pair : allRequestParams.entrySet()){
            if(pair.getKey().equals("secret")) {
                secretKey = pair.getValue();
            } else if(pair.getKey().equals("response")) {
                token = pair.getValue();
            }
        }
        User user = Users.getUserBySecretKey(secretKey);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        JSONObject json = new JSONObject();
        if(token.isEmpty() || !token.equals(user.getToken())){
            json.put("success", "false");
            json.put("errorCode", "Token verify error");
        } else {
            json.put("success", "true");
            json.put("errorCode", "null");
        }
        user.deleteToken();
        return ResponseEntity.ok(json);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStartPage(){
        return "start";
    }

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "client";
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public String getCaptchaPage(){
        return "captcha";
    }

    @RequestMapping(value = "/captcha/result", method = RequestMethod.GET)
    public String getCaptchaResultPage(){
        return "captchaResult";
    }

    @RequestMapping(value = "/captcha/closedPage", method = RequestMethod.GET)
    public String getCaptchaClosedPage(@RequestParam Map<String,String> allRequestParams){
        if(allRequestParams.isEmpty()) {
            return "redirect:/";
        }
        ResponseEntity<?> responseEntity = checkToken(allRequestParams);
        JSONObject json = (JSONObject)responseEntity.getBody();
        String success = (String)(json.get("success"));
        if(success.equals("true")){
            return "captchaClosedPage";
        } else {
            this.getStartPage();
            return "redirect:/";
        }

    }


}
