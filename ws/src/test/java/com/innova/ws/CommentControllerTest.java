package com.innova.ws;

import com.innova.ws.configuration.CustomUserDetailsService;
import com.innova.ws.jwt.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentControllerTest {

    private static final String API_1_0_COMMENTS = "/api/1.0/comments/";
    private static final String API_1_0_USERS_COMMENTS = "/api/1.0/users/comments/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void postComment_whenUserIsValid_receiveOk() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_COMMENTS + "389")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createCommment("test-comment"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void postComment_whenCommentHasNullContent_receiveBadRequest() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_COMMENTS + "389")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postComment_whenCommentHasContentWithLessThanRequired_receiveBadRequest() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_COMMENTS + "389")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createCommment(""))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postComment_whenCommentHasContentExceedsTheLengthLimit_receiveBadRequest() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        String valueOf256Chars = IntStream.rangeClosed(1,256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_COMMENTS + "389")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createCommment(valueOf256Chars))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postComment_whenUnauthorizedUserSendsTheRequest_receiveUnauthorized() throws Exception{

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_COMMENTS + "389")
                    .content(TestUtil.createCommment("test-comment"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void getCommentsOfNote_whenThereAreNoComments_receivePageWithZeroItems() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user10");

        mvc
            .perform(MockMvcRequestBuilders.get(API_1_0_USERS_COMMENTS + "390")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.empty").value("true"));
    }

    @Test
    public void getCommentsOfNote_whenThereAreComments_receivePageWithItems() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.get(API_1_0_USERS_COMMENTS + "389")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.empty").value("false"));
    }

    @Test
    public void getCommentsOfNote_whenAuthorizedUserSendsRequestForAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user2");

        mvc
            .perform(MockMvcRequestBuilders.get(API_1_0_USERS_COMMENTS + "389")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void getCommentsOfNote_whenUnauthorizedUserSendsRequest_receiveUnauthorized() throws Exception {

        mvc
            .perform(MockMvcRequestBuilders.get(API_1_0_USERS_COMMENTS + "389"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void putComment_whenValidRequestBodyFromAuthorizedUser_receiveOk() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.put(API_1_0_COMMENTS + "user1/419")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createCommment("updated-comment"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void putComment_whenIncorrectCommentIdFromAuthorizedUser_receiveNotFound() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.put(API_1_0_COMMENTS + "user1/1")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createCommment("updated-comment"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void putComment_whenAuthorizedUserSendsUpdateForAnotherUser_receiveForbidden() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user2");

        mvc
            .perform(MockMvcRequestBuilders.put(API_1_0_COMMENTS + "user1/419")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createCommment("updated-comment"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteComment_whenCommentIsOwnedByAnotherUser_receiveForbidden() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user20");

        mvc
            .perform(MockMvcRequestBuilders.delete(API_1_0_COMMENTS + "419")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteComment_whenCommentNotExist_receiveForbidden() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user20");

        mvc
            .perform(MockMvcRequestBuilders.delete(API_1_0_COMMENTS + "1")
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteComment_whenUserIsAuthorized_receiveOk() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_COMMENTS + "419")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isOk());
    }
}
