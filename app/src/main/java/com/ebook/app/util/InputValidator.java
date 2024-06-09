package com.ebook.app.util;

import com.google.android.material.textfield.TextInputLayout;

public class InputValidator {
    /**
     * 校验邮箱
     * @param email 邮箱
     * @param til 输入框
     * @return 校验结果
     */
    public static boolean isValidEmail(String email, TextInputLayout til) {
        if (email == null || email.isEmpty()) {
            til .setError(null);
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            til.setError("邮箱格式不正确");
            return false;
        }
        til.setError(null);
        return true;
    }
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            return false;
        }
        return true;
    }

    /**
     * 校验空置
     * @param input 输入
     * @param til 输入框
     * @param errorMsg 错误信息
     * @return 校验结果
     */
    public static boolean isNotEmpty(String input,TextInputLayout til,String errorMsg) {
        if (input == null || input.isEmpty()) {
            til.setError(errorMsg);
            return false;
        }
        til.setError(null);
        return true;
    }

    public static boolean isNotEmpty(String input) {
        return input != null && !input.isEmpty();
    }
}
