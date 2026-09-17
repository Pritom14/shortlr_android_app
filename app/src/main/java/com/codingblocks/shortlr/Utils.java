package com.codingblocks.shortlr;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by piyush0 on 11/04/17.
 */

public class Utils {
    public static final String TAG = "Utils";

    public static String getFromClip(Context context) {
        Log.d(TAG, "getFromClip: reading text from the system clipboard");
        String retVal = null;
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData abc = myClipboard.getPrimaryClip();
        if (abc != null) {
            ClipData.Item item = abc.getItemAt(0);
            String text = item.getText().toString();
            retVal = text;
        }
        Log.i(TAG, "getFromClip: returning clipboard text = " + retVal);
        return retVal;
    }

    public static String saveToClipboard(String text, Context context) {
        Log.d(TAG, "saveToClipboard: saving text to clipboard, text = " + text);
        ClipData myClip;

        myClip = ClipData.newPlainText("text", text);
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "saveToClipboard: text copied to clipboard successfully");
        return text;
    }

    public static String getHost(String url) {
        Log.d(TAG, "getHost: extracting host from url = " + url);
        if (url == null || url.length() == 0) {
            Log.i(TAG, "getHost: url is null or empty, returning empty string");
            return "";
        }

        int doubleslash = url.indexOf("//");
        if (doubleslash == -1)
            doubleslash = 0;
        else
            doubleslash += 2;

        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;

        String host = url.substring(doubleslash, end);
        Log.i(TAG, "getHost: resolved host = " + host);
        return host;
    }

    private static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public static boolean isUrl(String str) {
        Log.d(TAG, "isUrl: checking whether string matches url pattern, str = " + str);
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(str);//replace with string to compare
        boolean matches = m.find();
        Log.i(TAG, "isUrl: match result = " + matches);
        return matches;
    }

}
