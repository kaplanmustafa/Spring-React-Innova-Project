package com.innova.ws;

public class TestUtil {

    public static String createValidUserCreds() {
        String username = "user1";
        String password = "P4ssword";
        String role = "user";

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"role\": \"" + role + "\" }";
    }

    public static String createUserForSignup(String username, String fullName, String password) {

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"fullName\": \"" + fullName + "\" }";
    }

    public static String createUserWithNullUsernameForSignup() {
        String fullName = "test-name";
        String password = "P4ssword";

        return "{ \"password\": \"" + password + "\", \"fullName\": \"" + fullName + "\" }";
    }

    public static String createUserWithNullFullNameForSignup() {
        String username = "test-name-1";
        String password = "P4ssword";

        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";
    }

    public static String createUserWithNullPasswordForSignup() {
        String username = "test-name-1";
        String fullName = "test-name";

        return "{ \"username\": \"" + username + "\", \"fullName\": \"" + fullName + "\" }";
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

    public static String createPasswordUpdateVM(String currentPassword, String newPassword) {

        return "{ \"currentPassword\": \"" + currentPassword + "\", \"newPassword\": \"" + newPassword + "\"  }";
    }

    public static String createPasswordUpdateVMWithoutCurrentPassword(String newPassword) {

        return "{ \"newPassword\": \"" + newPassword + "\"  }";
    }

    public static String createPasswordUpdateVMWithoutNewPassword(String currentPassword) {

        return "{ \"currentPassword\": \"" + currentPassword + "\"  }";
    }

    public static String createUserUpdateVM(String username, String fullName) {

        return "{ \"username\": \"" + username + "\", \"fullName\": \"" + fullName + "\"  }";
    }

    public static String createUserUpdateVMWithoutUsername(String fullName) {

        return "{ \"fullName\": \"" + fullName + "\"  }";
    }

    public static String createUserUpdateVMWithoutFullName(String username) {

        return "{ \"username\": \"" + username + "\"  }";
    }
}
