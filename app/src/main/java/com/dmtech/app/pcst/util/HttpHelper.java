package com.dmtech.app.pcst.util;

public class HttpHelper {
    private static final String HOST_IP = "192.168.1.50";
    private static final String HOST_PORT = "8080";
    private static final String HOST_SITE =
            "http://" + HOST_IP + ":" + HOST_PORT + "/PicastoServer";


    public static String getRegisterServiceUrl() {
        return HOST_SITE + "/RegisterServlet";
    }

    public static String getLoginServiceUrl() {
        return HOST_SITE + "/LoginServlet";
    }

    public static String getGetPostListServiceUrl() {
        return HOST_SITE + "/GetPostListServlet";
    }

    public static String getImageUrl(String path) {
        return HOST_SITE + "/" + path;
    }
}
