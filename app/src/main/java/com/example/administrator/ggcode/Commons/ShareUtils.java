package com.example.administrator.ggcode.Commons;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Commons
 * 作者名 ： g小志
 * 日期   ： 2017/7/25
 * 时间   ： 16:36
 */

public class ShareUtils {
    public static void share(Context context, String shareContent) {
        StringBuffer sb = new StringBuffer();
        sb.append(shareContent);
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "该手机不支持该操作", Toast.LENGTH_LONG).show();
        }
    }
}
