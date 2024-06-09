package com.ebook.app.util;

import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.ebook.app.model.Notify;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * 提示弹窗工具类
 * 1. 显示弹窗。
 * 2. 显示Toast。
 */
public class AlertUtil {

    public static void alert(Context context, Notify notify) {
        switch (notify.getType()) {
            case Notify.DIALOG:
                showDialog(context, notify.getTitle(), notify.getMsg());
                break;
            default:
                showToast(context, notify.getMsg());
        }
    }
    public static void showDialog(Context context, String title, String message) {
        new MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("确定", null)
            .show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
