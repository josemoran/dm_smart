package com.navego360.credito.variables;

import android.net.Uri;

public class ContentUri {

    private static final String AUTHORITY = "com.targetmaps.navego360.newVersion.databases.contentprovider";

    public static final String SERVICE_PATH = "servicios";
    public static final String FORM_DATA_PATH = "form_datas";
    public static final String FORM_ANSWER_PATH = "form_answers";
    public static final String LAST_POSITION_PATH = "last_positions";
    public static final String SERVICE_STATUS_PATH = "service_status";

    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + AUTHORITY + "/" + SERVICE_PATH);
    public static final Uri CONTENT_URI_2 = Uri.parse("content://" + AUTHORITY + "/" + FORM_DATA_PATH);
    public static final Uri CONTENT_URI_3 = Uri.parse("content://" + AUTHORITY + "/" + FORM_ANSWER_PATH);
    public static final Uri CONTENT_URI_4 = Uri.parse("content://" + AUTHORITY + "/" + LAST_POSITION_PATH);
    public static final Uri CONTENT_URI_5 = Uri.parse("content://" + AUTHORITY + "/" + SERVICE_STATUS_PATH);
}
