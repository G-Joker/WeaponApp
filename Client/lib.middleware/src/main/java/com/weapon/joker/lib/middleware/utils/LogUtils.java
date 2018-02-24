package com.weapon.joker.lib.middleware.utils;


import com.orhanobut.logger.Logger;
import com.weapon.joker.lib.middleware.BuildConfig;


/**
 * 如果用于android平台，将信息记录到“LogCat”。如果用于java平台，将信息记录到“Console”
 * 使用logger封装
 */
public class LogUtils {
    public static boolean DEBUG_ENABLE = BuildConfig.LOG;// 是否调试模式

    /**
     * 默认的 TAG
     */
    private static final String DEFAULT_TAG = "WeaponApp";


    public static void v(String tag, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).v(message, args);
        }
    }

    public static void v(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).v(message, args);
        }
    }

    public static void d(String tag, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).d(message, args);
        }
    }

    public static void d(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).d(message, args);
        }
    }


    public static void i(String tag, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).i(message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).i(message, args);
        }
    }

    public static void w(String tag, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).w(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).w(message, args);
        }
    }

    public static void wtf(String tag, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).wtf(message, args);
        }
    }

    public static void wtf(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).wtf(message, args);
        }
    }

    public static void e(String tag, Throwable throwable, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).e(throwable, message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).e(message, args);
        }
    }


    public static void json(String tag, String message) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).json(message);
        }
    }

    public static void json(String message) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).json(message);
        }
    }

    public static void xml(String tag, String message) {
        if (DEBUG_ENABLE) {
            Logger.t(tag).xml(message);
        }
    }

    public static void xml(String message) {
        if (DEBUG_ENABLE) {
            Logger.t(DEFAULT_TAG).xml(message);
        }
    }
}
