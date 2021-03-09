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
public class UserControllerTest {

    private static final String API_1_0_USERS = "/api/1.0/users";
    private static final String API_1_0_USERS_PASSWORD = "/api/1.0/users/password/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void postUser_whenUserIsValid_receiveOk() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserForSignup("test-username", "test-name", "P4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postUser_whenUserHasNullUsername_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserWithNullFullNameForSignup())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasNullFullName_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserWithNullUsernameForSignup())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasNullPassword_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserWithNullPasswordForSignup())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasUsernameWithLessThanRequired_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserForSignup("ltr", "test-name", "P4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasFullNameWithLessThanRequired_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserForSignup("test-username", "n", "P4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasPasswordWithLessThanRequired_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserForSignup("test-username", "test-name", "P4s"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasUsernameExceedsTheLengthLimit_receiveBadRequest() throws Exception {

        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS)
                        .content(TestUtil.createUserForSignup(valueOf256Chars, "test-name", "P4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasFullNameTheLengthLimit_receiveBadRequest() throws Exception {

        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS).
                        content(TestUtil.createUserForSignup("test-username", valueOf256Chars, "P4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasPasswordTheLengthLimit_receiveBadRequest() throws Exception {

        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS).
                        content(TestUtil.createUserForSignup("test-username", "test-name", valueOf256Chars))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasPasswordWithAllLowercase_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS).
                        content(TestUtil.createUserForSignup("test-username", "test-name", "alllowercase"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasPasswordWithAllUppercase_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS).
                        content(TestUtil.createUserForSignup("test-username", "test-name", "ALLUPPERCASE"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenUserHasPasswordWithAllNumber_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS).
                        content(TestUtil.createUserForSignup("test-username", "test-name", "123456789"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUser_whenAnotherUserHasSameUsername_receiveBadRequest() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.post(API_1_0_USERS).
                        content(TestUtil.createUserForSignup("user1", "test-name", "P4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsers_withRoleAdmin_receivePageWithUser() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

        mvc
                .perform(MockMvcRequestBuilders.get(API_1_0_USERS)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(jsonPath("$.empty").value("false"));
    }

    @Test
    public void getUsers_withRoleUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.get(API_1_0_USERS)
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void putPassword_whenValidRequestBodyFromAuthorizedUser_receiveOk() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("P4ssword", "P5ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putPassword_whenAuthorizedUserSendsUpdateForAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user2")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("P4ssword", "P5ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void putPassword_whenUnauthorizedUserSendsTheRequest_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .content(TestUtil.createPasswordUpdateVM("P4ssword", "P5ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putPassword_withNullCurrentPasswordFromUnauthorizedUser_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .content(TestUtil.createPasswordUpdateVMWithoutCurrentPassword("P6ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putPassword_withNullNewPasswordFromUnauthorizedUser_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .content(TestUtil.createPasswordUpdateVMWithoutNewPassword("P5ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putPassword_withNullCurrentPasswordFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVMWithoutCurrentPassword("P6ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPassword_withNullNewPasswordFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVMWithoutNewPassword("P5ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPassword_withIncorrectCurrentPasswordFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("Q5ssword", "T4ssword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPassword_withNewPasswordWithLessThanRequiredFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("P5ssword", "P4ss"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPassword_withNewPasswordTheLengthLimitFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("P5ssword", valueOf256Chars))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPassword_withNewPasswordWithAllLowercaseFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("P5ssword", "alllowercase"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPassword_withNewPasswordWithAllUppercaseFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS_PASSWORD + "user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createPasswordUpdateVM("P5ssword", "ALLUPPERCASE"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_whenAuthorizedUserSendsUpdateForAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user2")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVM("user3", "name3"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void putUser_whenUnauthorizedUserSendsTheRequest_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .content(TestUtil.createUserUpdateVM("user1", "name2"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putUser_withNullUsernameFromUnauthorizedUser_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .content(TestUtil.createUserUpdateVMWithoutUsername("name2"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putUser_withNullFullNameFromUnauthorizedUser_receiveUnauthorized() throws Exception {

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .content(TestUtil.createUserUpdateVMWithoutFullName("user1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void putUser_withNullUsernameFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVMWithoutUsername("name2"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_withNullFullNameFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVMWithoutFullName("user2"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_whenUserHasUsernameExceedsTheLengthLimitFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVM(valueOf256Chars, "name2"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_whenUserHasFullNameTheLengthLimitFromAuthorizedUser_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVM("user2", valueOf256Chars))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_whenUserHasUsernameWithLessThanRequired_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVM("ltr", "name1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_whenUserHasFullNameWithLessThanRequired_receiveBadRequest() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVM("user2", "n"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUser_whenValidRequestBodyFromAuthorizedUser_receiveOk() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.put(API_1_0_USERS + "/user1")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails))
                        .content(TestUtil.createUserUpdateVM("user1", "test-name"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_whenAuthorizedUserSendsRequestForAnotherUser_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_USERS + "/user2")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUser_whenUserNotExist_receiveForbidden() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user1");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_USERS + "/fake-username")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUser_whenUserIsAuthorized_receiveOk() throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername("user25");

        mvc
                .perform(MockMvcRequestBuilders.delete(API_1_0_USERS + "/user25")
                        .header("Authorization", "Bearer " + jwtUtil.generateToken(userDetails)))
                .andExpect(status().isOk());
    }
}
