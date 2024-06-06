package com.ebook.app.util;

import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

public class CheckInputUtil {
    /**
     * 校验邮箱
     * @param email 邮箱
     * @param textInputLayout 输入框
     * @return 校验结果
     */
    public static boolean checkEmail(String email, TextInputLayout textInputLayout) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            textInputLayout.setError("邮箱格式不正确");
            return false;
        }
        textInputLayout.setError(null);
        return true;
    }

    /**
     * 校验空置
     * @param input 输入
     * @param textInputLayout 输入框
     * @param errorMsg 错误信息
     * @return 校验结果
     */
    public static boolean checkEmpty(String input,TextInputLayout textInputLayout,String errorMsg) {
        if (input == null || input.isEmpty()) {
            textInputLayout.setError(errorMsg);
            return false;
        }
        textInputLayout.setError(null);
        return true;
    }

    /**
     * 校验是否一致
     * @param input1 输入1
     * @param input2 输入2
     * @param textInputLayout 输入框
     * @param errorMsg 错误信息
     * @return 校验结果
     */
    public static boolean checkSame(String input1,String input2,TextInputLayout textInputLayout,String errorMsg) {
        if (!input1.equals(input2)) {
            textInputLayout.setError(errorMsg);
            return false;
        }
        textInputLayout.setError(null);
        return true;
    }
}
