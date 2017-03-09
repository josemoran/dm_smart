package com.navego360.credito.utils;

import android.content.Context;

import com.navego360.credito.R;
import com.navego360.credito.models.OfferType;

public class OfferTypeUtils {

    public static String getOfferTypeText(Context context, OfferType offerTypeObJ){
        String text = "";
        String offerType = offerTypeObJ.getOfferType();
        switch (offerType){
            case "DM_CREDITOS":
                text = context.getString(R.string.creditos_label);
                break;
            case "DM_PLAZA":
                text = context.getString(R.string.plaza_label);
                break;
            case "DM_HOTELES":
                text = context.getString(R.string.hoteles_label);
                break;
            case "DM_VIVIENDA":
                text = context.getString(R.string.vivienda_label);
                break;
        }
        return text;
    }

    public static String getOfferTypeColor(Context context, OfferType offerTypeObJ){
        String color = "";
        String offerType = offerTypeObJ.getOfferType();
        switch (offerType){
            case "DM_CREDITOS":
                color = context.getString(R.color.creditoColor);
                break;
            case "DM_PLAZA":
                color = context.getString(R.color.plazaColor);
                break;
            case "DM_HOTELES":
                color = context.getString(R.color.hotelesColor);
                break;
            case "DM_VIVIENDA":
                color = context.getString(R.color.viviendaColor);
                break;
        }
        return color;
    }
}
