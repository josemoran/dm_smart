package com.navego360.credito.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

public class ViewUtils {

    // Cambiar color de fondo
    public static void changeBackground(Context context, View view, String color, int drawable){
        boolean different = false;
        int backgroundColor = Color.parseColor(color);

        StateListDrawable sld = new StateListDrawable();
        Resources resources = context.getResources();
        Drawable backgroundDrawable = resources.getDrawable(drawable);
        backgroundDrawable.mutate();
        if (backgroundDrawable instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) backgroundDrawable;
            shapeDrawable.getPaint().setColor(backgroundColor);
            backgroundDrawable = shapeDrawable;
        } else if (backgroundDrawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) backgroundDrawable;
            gradientDrawable.setColor(backgroundColor);
            backgroundDrawable = gradientDrawable;
        } else if (backgroundDrawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) backgroundDrawable;
            colorDrawable.setColor(backgroundColor);
            backgroundDrawable = colorDrawable;
        }

        if(!different) {
            sld.addState(new int[]{}, backgroundDrawable);
            DimensionUtil.setBackgroundAndKeepPadding(view, sld);
        }
    }

}
