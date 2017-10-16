package com.weapon.joker.lib.middleware.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * 基于Zxing的条形码封装类
 * author : yueyang
 * email : hi.yangyue1993@gmail.com
 * date : 2017/10/16
 */
public class ZxingUtils {

    /**
     *
     *  @param str 需要被转换的文字
     *
     * @param width            生成的Bitmap的宽度
     * @param height           生成的Bitmap的高度
     * @param isKeepWhiteSpace true:保留二维码白边 false:移除二维码白边
     *                         author: yueyang
     **/
    public static Bitmap createBarcode(String str, int width, int height, boolean isKeepWhiteSpace) {
        try {
            BitMatrix matrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height);
            if (!isKeepWhiteSpace) matrix = deleteWhite(matrix);
            width = matrix.getWidth();
            height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = Color.BLACK;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     *  删除二维码的白边
     * author: yueyang
     **/
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}
