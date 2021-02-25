package com.innova.ws;

public class TestUtil {

    public static String createValidUserCreds() {
        String username = "user1";
        String password = "P4ssword";
        String role = "user";

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }";
    }

    public static String createInValidUserCreds() {
        String username = "test-user";
        String password = "P4ssword";
        String role = "user";

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }";
    }

    public static String createValidAdminCreds() {
        String username = "admin";
        String password = "P4ssword";
        String role = "admin";

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }";
    }

    public static String createInValidAdminCreds() {
        String username = "test-admin";
        String password = "P4ssword";
        String role = "admin";

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }";
    }
}
