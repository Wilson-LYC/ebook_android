package com.ebook.app.util;

import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

/**
 * 提示弹窗工具类
 * 1. 显示弹窗。
 * 2. 显示Toast。
 */
public class AlertUtil {

    /**
     * 显示弹窗
     */
    public static void showDialog(Context context, String errorMessage) {
        new AlertDialog.Builder(context)
                .setTitle("错误")
                .setMessage(errorMessage)
                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 显示Toast提示
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
