package com.iotcup.hodor.doorian.fmhttp;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpRequest {

    public static String sendRequest(String command, String ip, int port){
        HttpClient httpClient = HttpClients.createDefault();
        String req = "http://" + ip + ":" + (new Integer(port)).toString() + "/client_device";
        //HttpGet
        return null;
    }

}
