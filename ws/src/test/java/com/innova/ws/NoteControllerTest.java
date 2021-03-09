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
public class NoteControllerTest {

    private static final String API_1_0_NOTES = "/api/1.0/notes";
    private static final String API_1_0_USERS = "/api/1.0/users/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void postNote_whenUserIsValid_receiveOk() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user10");

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote("test-content", "test-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postNote_whenNoteHasNullContent_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNoteWithoutContent("test-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postNote_whenNoteHasContentWithLessThanRequired_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote("", "test-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postNote_whenNoteHasContentExceedsTheLengthLimit_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        String valueOf1001Chars = IntStream.rangeClosed(1, 1001).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote(valueOf1001Chars, "test-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postNote_whenNoteHasTitleTheLengthLimit_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote("test-content", valueOf256Chars))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postNote_whenUnauthorizedUserSendsTheRequest_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                        .content(TestUtil.createNote("test-content", "test-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getNotesOfUser_whenThereAreNoNotes_receivePageWithZeroItems() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user5");

        mvc
                .perform(MockMvcRequestBuilders.get(API_1_0_USERS + "user5/notes")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empty").value("true"));
    }

    @Test
    public void getNotesOfUser_whenThereAreNotes_receivePageWithItems() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.get(API_1_0_USERS + "user1/notes")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empty").value("false"));
    }

    @Test
    public void getNotesOfUser_whenAuthorizedUserSendsRequestForAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user2");

        mvc
                .perform(MockMvcRequestBuilders.get(API_1_0_USERS + "user1/notes")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getNotesOfUser_whenUnauthorizedUserSendsRequest_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.get(API_1_0_USERS + "user1/notes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putNote_whenValidRequestBodyFromAuthorizedUser_receiveOk() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_NOTES + "/user1/389")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote("updated-content", "updated-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putNote_whenIncorrectNoteIdFromAuthorizedUser_receiveNotFound() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_NOTES + "/user1/1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote("updated-content", "updated-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void putNote_whenAuthorizedUserSendsUpdateForAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user2");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_NOTES + "/user1/389")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createNote("updated-content", "updated-title"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteNote_whenNoteIsOwnedByAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user20");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_NOTES + "/389")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteNote_whenNoteNotExist_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user20");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_NOTES + "/1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteNote_whenUserIsAuthorized_receiveOk() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_NOTES + "/389")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isOk());
    }
}
