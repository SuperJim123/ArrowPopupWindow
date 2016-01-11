package cn.wecoder.arrowpopupwindow.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class Util {
    public static int DpToPx(Context context, float x) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (x * scale + 0.5f);
    }

    public static int PxToDp(Context context, int x) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (x / scale + 0.5f);
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap scaleBitmap(Bitmap source, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }

    public static Bitmap getEdgeLineBitmap(Context context, Bitmap bitmap,int edgeColor, int edgeWidth) {
        Matrix matrix = new Matrix();
        float xScale = (bitmap.getWidth() + 2 * edgeWidth) / (float)bitmap.getWidth();
        float yScale = (bitmap.getHeight() + 2 * edgeWidth) / (float)bitmap.getHeight();
        matrix.postScale(xScale, yScale);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        int newBmpWidth = newBmp.getWidth();
        int newBmpHeight = newBmp.getHeight();
        int[] pixels =new int[newBmpWidth * newBmpHeight];

        for(int y = 0; y < newBmpHeight; y++) {
            for(int x = 0; x < newBmpWidth; x++) {
                int index = y * newBmpWidth + x;
                pixels[index] = 0;
                if(newBmp.getPixel(x, y) != 0) {
                    pixels[index] = edgeColor;
                    int newX = x - edgeWidth;
                    int newY = y - edgeWidth;
                    if(newX > 0 && newY > 0 && newX < bmpWidth && newY < bmpHeight) {
                        if(bitmap.getPixel(newX, newY) != 0) {
                            pixels[index] = 0;
                        }
                    }
                }
            }
        }
        newBmp.setPixels(pixels, 0, newBmpWidth, 0, 0, newBmpWidth, newBmpHeight);
        return newBmp;
    }
}
