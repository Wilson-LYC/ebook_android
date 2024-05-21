package com.ebook.app.util;

import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

public class AlertUtil {

    /**
     * 显示错误提示弹窗。
     *
     * @param context 上下文环境
     * @param errorMessage 错误信息
     */
    public static void showErrorDialog(Context context, String errorMessage) {
        // 确保在主线程执行UI操作
        new AlertDialog.Builder(context)
                .setTitle("错误")
                .setMessage(errorMessage)
                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 显示简短的Toast提示。
     *
     * @param context 上下文环境
     * @param message 提示信息
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
