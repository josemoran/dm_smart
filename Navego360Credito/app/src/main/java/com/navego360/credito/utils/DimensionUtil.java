package com.navego360.credito.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

public class DimensionUtil {

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void setBackgroundAndKeepPadding(View view, Drawable backgroundDrawable) {
        Rect drawablePadding = new Rect();
        backgroundDrawable.getPadding(drawablePadding);
        int top = view.getPaddingTop() + drawablePadding.top;
        int left = view.getPaddingLeft() + drawablePadding.left;
        int right = view.getPaddingRight() + drawablePadding.right;
        int bottom = view.getPaddingBottom() + drawablePadding.bottom;

        view.setBackgroundDrawable(backgroundDrawable);
        view.setPadding(left, top, right, bottom);
    }
}
