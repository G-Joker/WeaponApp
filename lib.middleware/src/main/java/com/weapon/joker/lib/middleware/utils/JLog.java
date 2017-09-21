package com.weapon.joker.lib.middleware.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.weapon.joker.lib.middleware.BuildConfig;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.lib.middleware.utils.JLog
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/09/21
 *     desc   : joker log
 * </pre>
 */

public class JLog {
    private static final String LOG_FORMAT = "%1$s\n%2$s";
    private static final String LOG_TAG    = "WeaponApp";

    public static void d(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int                 i     = 1;
            final StackTraceElement   ste   = stack[i];
            Log.println(Log.DEBUG,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message));
        }
    }

    public static void d(Object... args) {
        if (args == null) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack   = new Throwable().getStackTrace();
            final int                 i       = 1;
            final StackTraceElement   ste     = stack[i];
            StringBuffer              message = new StringBuffer();
            if (args.length > 0) {
                for (int j = 0; j < args.length; j++) {
                    message.append(args[j].toString()).append(" ");
                }
            }
            Log.println(Log.DEBUG,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message.toString()));
        }
    }

    public static void i(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int                 i     = 1;
            final StackTraceElement   ste   = stack[i];
            Log.println(Log.INFO,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message));
        }
    }

    public static void i(Object... args) {
        if (args == null) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack   = new Throwable().getStackTrace();
            final int                 i       = 1;
            final StackTraceElement   ste     = stack[i];
            StringBuilder             message = new StringBuilder();
            if (args.length > 0) {
                for (int j = 0; j < args.length; j++) {
                    message.append(args[j].toString()).append(" ");
                }
            }
            Log.println(Log.INFO,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message.toString()));
        }
    }

    public static void w(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int                 i     = 1;
            final StackTraceElement   ste   = stack[i];
            Log.println(Log.WARN,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message));
        }
    }

    public static void w(Object... args) {
        if (args == null) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack   = new Throwable().getStackTrace();
            final int                 i       = 1;
            final StackTraceElement   ste     = stack[i];
            StringBuilder             message = new StringBuilder();
            if (args.length > 0) {
                for (int j = 0; j < args.length; j++) {
                    message.append(args[j].toString()).append(" ");
                }
            }
            Log.println(Log.WARN,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message.toString()));
        }
    }

    public static void e(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack = new Throwable().getStackTrace();
            final int                 i     = 1;
            final StackTraceElement   ste   = stack[i];
            Log.println(Log.ERROR,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message));
        }
    }

    public static void e(Object... args) {
        if (args == null) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack   = new Throwable().getStackTrace();
            final int                 i       = 1;
            final StackTraceElement   ste     = stack[i];
            StringBuilder             message = new StringBuilder();
            if (args.length > 0) {
                for (int j = 0; j < args.length; j++) {
                    message.append(args[j].toString()).append(" ");
                }
            }
            Log.println(Log.ERROR,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), message.toString()));
        }
    }

    public static void e(Throwable ex) {
        if (ex == null) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack      = new Throwable().getStackTrace();
            final int                 i          = 1;
            final StackTraceElement   ste        = stack[i];
            String                    logMessage = ex.getMessage();
            String                    logBody    = Log.getStackTraceString(ex);
            String                    log        = String.format(LOG_FORMAT, logMessage, logBody);
            Log.println(Log.ERROR,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), log));
        }
    }

    public static void e(Throwable ex, Object... args) {
        if (args == null) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            final StackTraceElement[] stack   = new Throwable().getStackTrace();
            final int                 i       = 1;
            final StackTraceElement   ste     = stack[i];
            StringBuilder             message = new StringBuilder();
            if (args.length > 0) {
                for (int j = 0; j < args.length; j++) {
                    message.append(args[j].toString()).append(" ");
                }
            }
            String log;
            if (ex == null) {
                log = message.toString();
            } else {
                String logMessage = message.toString() == null ? ex.getMessage() : message.toString();
                String logBody    = Log.getStackTraceString(ex);
                log = String.format(LOG_FORMAT, logMessage, logBody);
            }
            Log.println(Log.ERROR,
                        LOG_TAG,
                        String.format("[%s][%s][%s]%s", ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), log));
        }
    }

    /**
     * 以文本的形式打印一条数据流 并且返回原数据流
     */
    public static InputStream printAndReturnIs(InputStream is, Context ctx) {
        if (BuildConfig.LOG_DEBUG) {
            String result = convertStreamToString(is);
            JLog.i(result);
            return getIsFromStr(result);
        } else {
            return is;
        }

    }


    /**
     * 将InputStream流转化为string
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder  sb     = new StringBuilder();
        String         line   = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 将字符串转换为流
     */
    public static InputStream getIsFromStr(String str) {
        InputStream myIn = null;
        try {
            myIn = new ByteArrayInputStream(str.getBytes());
        } catch (Exception e) {
            JLog.e("Error! ");
        }
        return myIn;
    }
}
