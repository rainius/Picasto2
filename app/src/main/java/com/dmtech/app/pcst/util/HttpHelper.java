package com.dmtech.app.pcst.util;
//网络访问相关工具类
public class HttpHelper {
    //服务器IP地址：在Terminal中通过ipconfig /all命令查看
    private static final String HOST_IP = "192.168.3.171";
    //Tomcat服务端口：参见Eclipse中的Server配置
    private static final String HOST_PORT = "8080";
    //Picasto服务端应用地址
    private static final String HOST_SITE =
            "http://" + HOST_IP + ":" + HOST_PORT + "/Picasto2020";

    //URL to LoginServlet
    public static String getLoginServiceUrl() {
        return HOST_SITE + "/LoginServlet";
    }
    //URL to GetPostListServlet
    public static String getGetPostListServiceUrl() {
        return HOST_SITE + "/GetPostListServlet";
    }
    //URL to images
    public static String getImageUrl(String path) {
        return HOST_SITE + "/" + path;
    }

}
