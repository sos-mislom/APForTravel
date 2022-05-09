package com.example.shnyagashnyajnaya.OTMAPI;

import java.util.ArrayList;
import java.util.Arrays;

public class APIConfig {
    public static final String DEFAULT_NOTIFICATION_CHANNEL = "DEFAULT_CHANNEL";
    public static final String HOST_URL = "https://api.opentripmap.com/";
    public static String KINDS_OF_PLACES = "interesting_places";
    public static ArrayList<String> CENSORED_KINDS_OF_PLACES = new ArrayList<>();
    public static final String API_YANDEX_MAP = "26047576-121f-4108-a0be-a1c7e90cfde7";
    public static final String API_OTM = "5ae2e3f221c38a28845f05b6f1e0630a42d3fa1a1d917100fb503b38";
    public static String LANGUAGE = "ru";
    public static final ArrayList<String> ALLOWED_KINDS_OF_PLACES = new ArrayList<String>(Arrays.asList(
            "historic",
            "cultural",
            "industrial_facilities",
            "natural",
            "architecture",
            "other"
    ));
}
