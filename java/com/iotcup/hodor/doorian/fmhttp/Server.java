package com.iotcup.hodor.doorian.fmhttp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Server implements Serializable{

	private static final long serialVersionUID = 8996126126774900867L;
	private String IP, port;
	private ArrayList<String> serverResponse;
	
	public Server(){
		this.serverResponse = new ArrayList<String>();
	}
	
	public String getIP (){
		return this.IP;
	}
	public void setIP (String IP){
		this.IP = IP;
	}
	public void setPort (String port){
		this.port = port;
	}
	@SuppressWarnings("deprecation")
	public String requestPost (String dir, Map<String, String> pars) throws Exception{
		HttpClient httpClient = HttpClients.createDefault();
		String req = "http://" + this.IP;
		if (this.port != null && this.port.length() > 0)
			req += ":" + this.port;
		req += "/" + dir;
		HttpPost httpPost = new HttpPost(req);
		List<NameValuePair> params = new ArrayList<NameValuePair>(pars.size());
		for (String key : pars.keySet())
			params.add(new BasicNameValuePair(key, pars.get(key)));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		    	this.serverResponse.add(new DataInputStream(instream).readLine());
		    } finally {
		        instream.close();
		    }
		}
		return response.getStatusLine().getReasonPhrase();
	}
	public void Serialize (String dir) throws IOException {
		FileOutputStream f = new FileOutputStream(dir);
		ObjectOutputStream o = new ObjectOutputStream (f);
		o.writeObject(this);
		f.close();
		o.close();
	}
	public static Server deserialize (String dir) throws Exception{
		FileInputStream f = new FileInputStream(dir);
		ObjectInputStream o = new ObjectInputStream (f);
		Server server = (Server) o.readObject();
		f.close();
		o.close();
		return server;
	}
	
}
