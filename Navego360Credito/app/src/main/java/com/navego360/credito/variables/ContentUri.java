package com.navego360.credito.variables;

import android.net.Uri;

public class ContentUri {

    private static final String AUTHORITY = "com.targetmaps.navego360.newVersion.databases.contentprovider";

    public static final String SERVICE_PATH = "servicios";
    public static final String FORM_DATA_PATH = "form_datas";
    public static final String FORM_ANSWER_PATH = "form_answers";

    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + AUTHORITY + "/" + SERVICE_PATH);
    public static final Uri CONTENT_URI_2 = Uri.parse("content://" + AUTHORITY + "/" + FORM_DATA_PATH);
    public static final Uri CONTENT_URI_3 = Uri.parse("content://" + AUTHORITY + "/" + FORM_ANSWER_PATH);
}
