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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteControllerTest {

    private static final String API_1_0_NOTES = "/api/1.0/notes";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void postNote_whenUserIsValid_receiveOk() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createNote("test-content", "test-title"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void postNote_whenNoteHasNullContent_receiveBadRequest() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createNoteWithoutContent("test-title"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postNote_whenNoteHasContentWithLessThanRequired_receiveBadRequest() throws Exception{

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
            .perform(MockMvcRequestBuilders.post(API_1_0_NOTES)
                    .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                    .content(TestUtil.createNote("", "test-title"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
