package ru.kontur.intern.captcha.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.kontur.intern.captcha.bootmenu.CaptchaApplication;
import ru.kontur.intern.captcha.entities.CaptchaImage;
import ru.kontur.intern.captcha.entities.User;
import ru.kontur.intern.captcha.lists.CaptchaImages;
import ru.kontur.intern.captcha.lists.Users;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CaptchaApplication.class)
@WebAppConfiguration
public class CaptchaControllerTest {

    private MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MediaType contentTypePng = new MediaType(MediaType.IMAGE_PNG.getType(),
            MediaType.IMAGE_PNG.getSubtype());

    private User user = new User();
    private CaptchaImage captchaImage = new CaptchaImage();

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Users.add(user);
        CaptchaImages.add(captchaImage);
    }

    @Test
    public void registerTest() throws Exception {
        mockMvc.perform(post("/client/register"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.secret", containsString("-")))
                .andExpect(jsonPath("$.public", containsString("-")))
                .andReturn();
    }

    @Test
    public void checkTest() throws Exception {
        mockMvc.perform(get("/captcha/new?public=" + user.getPublicKey()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn();
    }

    @Test
    public void checkTest_emptyParameters() throws Exception {
        mockMvc.perform(get("/captcha/new"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void checkTest_invalidKey() throws Exception {
        mockMvc.perform(get("/captcha/new?public=790e8400-e29b-41d4-a716-446655441234"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void getImageTest() throws  Exception {
        mockMvc.perform(get("/captcha/image?public=" + user.getPublicKey() +
                "&request=" + captchaImage.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypePng))
                .andReturn();
    }

    @Test
    public void getImageTest_emptyParameters() throws  Exception {
        mockMvc.perform(get("/captcha/image"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }


    @Test
    public void getImageTest_invalidKey() throws  Exception {
        mockMvc.perform(get("/captcha/image?public=790e8400-e29b-41d4-a716-446655441234" +
                "&request=" + captchaImage.getId()))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void getImageTest_emptyId() throws  Exception {
        mockMvc.perform(get("/captcha/image?public=" + user.getPublicKey()))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void getImageTest_invalidId() throws  Exception {
        mockMvc.perform(get("/captcha/image?public=" + user.getPublicKey() +
                "&request=abc"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void sendSolveTest() throws Exception {
        if (CaptchaImages.getImageById(captchaImage.getId()) == null){
            CaptchaImages.add(captchaImage);
        }
        mockMvc.perform(post("/captcha/solve?public=" + user.getPublicKey() +
                "&request=" + captchaImage.getId()+
                "&answer=" + captchaImage.getWord()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.response", containsString("-")))
                .andReturn();
    }


    @Test
    public void sendSolveTest_emptyParameters() throws Exception {
        mockMvc.perform(post("/captcha/solve"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void sendSolveTest_invalidKey() throws Exception {
        if (CaptchaImages.getImageById(captchaImage.getId()) == null){
            CaptchaImages.add(captchaImage);
        }
        mockMvc.perform(post("/captcha/solve?public=790e8400-e29b-41d4-a716-446655441234" +
                "&request=" + captchaImage.getId()+
                "&answer=" + captchaImage.getWord()))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void sendSolveTest_emptyId() throws Exception {
        if (CaptchaImages.getImageById(captchaImage.getId()) == null){
            CaptchaImages.add(captchaImage);
        }
        mockMvc.perform(post("/captcha/solve?public=" + user.getPublicKey() +
                "&answer=" + captchaImage.getWord()))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void sendSolveTest_invalidId() throws Exception {
        if (CaptchaImages.getImageById(captchaImage.getId()) == null){
            CaptchaImages.add(captchaImage);
        }
        mockMvc.perform(post("/captcha/solve?public=" + user.getPublicKey() +
                "&request=abc"+
                "&answer=" + captchaImage.getWord()))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void checkTokenTest() throws Exception {
        user.setToken();
        mockMvc.perform(get("/captcha/verify?secret=" + user.getSecretKey() +
                "&response=" + user.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.errorCode", is("null")))
                .andReturn();
    }

    @Test
    public void checkTokenTest_emptyParameters() throws Exception {
        mockMvc.perform(get("/captcha/verify"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void checkTokenTest_invalidKey() throws Exception {
        user.setToken();
        mockMvc.perform(get("/captcha/verify?secret=790e8400-e29b-41d4-a716-446655441234" +
                "&response=" + user.getToken()))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void checkTokenTest_invalidToken() throws Exception {
        user.setToken();
        mockMvc.perform(get("/captcha/verify?secret=" + user.getSecretKey() +
                "&response=" + "invalidToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.success", is("false")))
                .andExpect(jsonPath("$.errorCode", is("Token verify error")))
                .andReturn();
    }

    @Test
    public void getStartPageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("start"))
                .andReturn();
    }

    @Test
    public void getRegisterPageTest() throws Exception {
        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(view().name("client"))
                .andReturn();
    }

     @Test
    public void getCaptchaPageTest() throws Exception {
        mockMvc.perform(get("/captcha"))
                .andExpect(status().isOk())
                .andExpect(view().name("captcha"))
                .andReturn();
    }

    @Test
    public void getCaptchaResultPageTest() throws Exception {
        mockMvc.perform(get("/captcha/result"))
                .andExpect(status().isOk())
                .andExpect(view().name("captchaResult"))
                .andReturn();
    }

    @Test
    public void getCaptchaClosedPageTest() throws Exception {
        mockMvc.perform(get("/captcha/closedPage"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/"))
                .andReturn();
    }

    @After
    public void clean() {
        CaptchaImages.deleteImageById(captchaImage.getId());
    }
}