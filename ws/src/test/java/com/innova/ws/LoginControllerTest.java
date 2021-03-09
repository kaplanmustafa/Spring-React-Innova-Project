package com.innova.ws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

    private static final String API_1_0_LOGIN = "/api/1.0/login";

    @Autowired
    private MockMvc mvc;

    @Test
    public void postLogin_withoutUserCredentials_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postLoginUser_withValidCredentials_receiveOk() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createValidUserCreds()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postLoginUser_withIncorrectCredentials_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createInValidUserCreds())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postLoginUser_withValidCredentials_receiveLoggedInUsername() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createValidUserCreds())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("user1"));
    }

    @Test
    public void postLoginUser_withValidCredentials_receiveLoggedInFullName() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createValidUserCreds())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.fullName").value("name1"));
    }

    @Test
    public void postLoginUser_withValidCredentials_receiveLoggedInRole() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createValidUserCreds())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("user"));
    }

    @Test
    public void postLoginAdmin_withValidCredentials_receiveOk() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createValidAdminCreds())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postLoginAdmin_withIncorrectCredentials_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_LOGIN)
                        .content(TestUtil.createInValidAdminCreds())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
