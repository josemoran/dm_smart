package com.navego360.credito.utils;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;

import com.navego360.credito.models.navego.FormAnswer;
import com.navego360.credito.models.navego.FormData;

import java.util.ArrayList;
import java.util.List;

import static com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.FormAnswerEntry;
import static com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.FormDataEntry;
import static com.navego360.credito.data.common.local.NavegoCreditPersistenceContract.ServiceEntry;
import static com.navego360.credito.variables.ContentUri.CONTENT_URI_1;
import static com.navego360.credito.variables.ContentUri.CONTENT_URI_2;
import static com.navego360.credito.variables.ContentUri.CONTENT_URI_3;

public class ContentClientUtils {

    public static List<FormData> getFormData(Context context, int formId, int serviceId){
        List<FormData> formDataList = new ArrayList<>();
        try {
            String[] projection = {
                    FormDataEntry.COLUMN_NAME_ID_FORM_DATA,
                    FormDataEntry.COLUMN_NAME_ID_FORM,
                    FormDataEntry.COLUMN_NAME_SERVICE_ID,
                    FormDataEntry.COLUMN_NAME_LABEL,
                    FormDataEntry.COLUMN_NAME_EFS,
                    FormDataEntry.COLUMN_NAME_OPERATION,
                    FormDataEntry.COLUMN_NAME_SYNCWEB
            };

            String selection = FormDataEntry.COLUMN_NAME_ID_FORM + " = ? AND " +
                    FormDataEntry.COLUMN_NAME_SERVICE_ID  + " = ?";
            String[] selectionArgs = {String.valueOf(formId), String.valueOf(serviceId)};

            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_2);
            Cursor cur = yourCR.query(CONTENT_URI_2, projection, selection, selectionArgs, null);

            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    FormData form = new FormData();
                    form.setFormDataId(cur.getInt(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_ID_FORM_DATA)));
                    form.setFormId(cur.getInt(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_ID_FORM)));
                    form.setServiceId(cur.getInt(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_SERVICE_ID)));
                    form.setLabel(cur.getString(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_LABEL)));
                    form.setEfs(cur.getString(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_EFS)));
                    form.setOperation(cur.getInt(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_OPERATION)));
                    form.setSyncWeb(cur.getInt(cur.getColumnIndexOrThrow(FormDataEntry.COLUMN_NAME_SYNCWEB)));
                    formDataList.add(form);
                }
            }
            if (cur != null)
                cur.close();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return formDataList;
    }

    public static List<FormAnswer> getFormAnswer(Context context, int formDataId, int serviceId){
        List<FormAnswer> formAnswerList = new ArrayList<>();
        try {
            String[] projection = {
                    FormAnswerEntry.COLUMN_NAME_ID_FORM_DATA,
                    FormAnswerEntry.COLUMN_NAME_ID_COMPONENT,
                    FormAnswerEntry.COLUMN_NAME_SERVICE_ID,
                    FormAnswerEntry.COLUMN_NAME_TYPE,
                    FormAnswerEntry.COLUMN_NAME_ANSWER,
                    FormAnswerEntry.COLUMN_NAME_OPERATION,
                    FormAnswerEntry.COLUMN_NAME_SYNCWEB
            };

            String selection = FormAnswerEntry.COLUMN_NAME_ID_FORM_DATA + " = ? AND " +
                    FormAnswerEntry.COLUMN_NAME_SERVICE_ID  + " = ?";
            String[] selectionArgs = {String.valueOf(formDataId), String.valueOf(serviceId)};

            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_3);
            Cursor cur = yourCR.query(CONTENT_URI_3, projection, selection, selectionArgs, null);

            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    FormAnswer form = new FormAnswer();
                    form.setFormDataId(cur.getInt(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_ID_FORM_DATA)));
                    form.setComponentId(cur.getString(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_ID_COMPONENT)));
                    form.setServiceId(cur.getInt(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_SERVICE_ID)));
                    form.setTypeComponent(cur.getInt(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_TYPE)));
                    form.setAnswer(cur.getString(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_ANSWER)));
                    form.setOperation(cur.getInt(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_OPERATION)));
                    form.setSyncWeb(cur.getInt(cur.getColumnIndexOrThrow(FormAnswerEntry.COLUMN_NAME_SYNCWEB)));
                    formAnswerList.add(form);
                }
            }
            if (cur != null)
                cur.close();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return formAnswerList;
    }

    public static void updateService(Context context, String serviceId){
        try {
            String STATUS_SERVICE = "838";
            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_1);

            ContentValues values = new ContentValues();
            values.put(ServiceEntry.COLUMN_NAME_FLAG_SYNC_MOBILE, 0);
            values.put(ServiceEntry.COLUMN_NAME_ID_ESTADO, STATUS_SERVICE);

            String selection = ServiceEntry.COLUMN_NAME_SERVICE_ID + " LIKE ?";
            String[] selectionArgs = { serviceId };
            yourCR.update(CONTENT_URI_1, values, selection, selectionArgs);
            yourCR.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void updateFormData(Context context, String formDataId){
        try {
            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_2);
            ContentValues values = new ContentValues();
            values.put(FormDataEntry.COLUMN_NAME_OPERATION, 2);

            String selection = FormDataEntry.COLUMN_NAME_ID_FORM_DATA + " LIKE ?";
            String[] selectionArgs = { formDataId };

            yourCR.update(CONTENT_URI_2, values, selection, selectionArgs);
            yourCR.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void updateOrInsert(Context context, String serviceId, String formDataId,
                                      String componentId, String answer){
        try {
            int i = 0;
            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_3);

            String selection = FormAnswerEntry.COLUMN_NAME_ID_FORM_DATA + " = ? AND " +
                    FormAnswerEntry.COLUMN_NAME_ID_COMPONENT + " = ? AND " +
                    FormAnswerEntry.COLUMN_NAME_SERVICE_ID  + " = ?";
            String[] selectionArgs = {formDataId, componentId, serviceId};

            Cursor cur = yourCR.query(CONTENT_URI_3, null, selection, selectionArgs, null);

            if (cur != null && cur.getCount() > 0) while (cur.moveToNext()) i++;
            if (cur != null) cur.close();

            if(i != 0) updateFormAnswer(context, formDataId, componentId, answer);
            else insertFormAnswer(context, serviceId, formDataId, componentId, answer);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public static void updateFormAnswer(Context context, String formDataId, String componentId, String answer){
        try {
            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_3);

            ContentValues values = new ContentValues();
            values.put(FormAnswerEntry.COLUMN_NAME_ANSWER, answer);
            values.put(FormAnswerEntry.COLUMN_NAME_TYPE, 1);
            values.put(FormAnswerEntry.COLUMN_NAME_OPERATION, 2);
            values.put(FormAnswerEntry.COLUMN_NAME_SYNCWEB, 0);

            String selection = FormAnswerEntry.COLUMN_NAME_ID_FORM_DATA + " = ? AND " +
                    FormAnswerEntry.COLUMN_NAME_ID_COMPONENT + " = ? ";
            String[] selectionArgs = { formDataId, componentId };
            yourCR.update(CONTENT_URI_3, values, selection, selectionArgs);
            yourCR.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public static void insertFormAnswer(Context context, String serviceId, String formDataId,
                                        String componentId, String answer){
        try {
            ContentProviderClient yourCR = context.getContentResolver().acquireContentProviderClient(CONTENT_URI_3);

            ContentValues values = new ContentValues();
            values.put(FormAnswerEntry.COLUMN_NAME_SERVICE_ID, serviceId);
            values.put(FormAnswerEntry.COLUMN_NAME_ID_FORM_DATA, formDataId);
            values.put(FormAnswerEntry.COLUMN_NAME_ID_COMPONENT, componentId);
            values.put(FormAnswerEntry.COLUMN_NAME_ANSWER, answer);
            values.put(FormAnswerEntry.COLUMN_NAME_TYPE, 1);
            values.put(FormAnswerEntry.COLUMN_NAME_OPERATION, 1);
            values.put(FormAnswerEntry.COLUMN_NAME_SYNCWEB, 0);

            yourCR.insert(CONTENT_URI_3, values);
            yourCR.release();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
