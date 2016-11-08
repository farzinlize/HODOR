package com.iotcup.hodor.doorian.fmhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Client {
	private Server server;
	private Scanner scanner;

	Client(String serverDir) {
		this.scanner = new Scanner(System.in);
		try {
			this.server = Server.deserialize(serverDir);
			if (server == null)
				this.setServer();
		} catch (Exception e) {
			System.out.println("loading failed");
			this.setServer();
		}
	}

	public static String sendRequest(String command){
		return null;
	}

	public void setServer() {
		this.server = new Server();
		System.out.println("Enter server IP : ");
		this.server.setIP(this.scanner.next());
		System.out.println("Enter port num : ");
		this.server.setPort(this.scanner.next());
		try {
			this.server.Serialize("database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getIP() {
		String IP;
/*		URL whatismyip;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			ip = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}*/
		try {
			IP = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		return IP;
	}

	public void launch() throws Exception {
		HttpServer cServer = HttpServer.create(new InetSocketAddress(725) , 0);
		cServer.createContext("/get", new ReqHandler(this));
		cServer.setExecutor(null);
		cServer.start();
		String IP = null;
		while (true)
			if (Client.getIP() == null) {
				System.out.println("network failed");
				continue;
			} else if (!Client.getIP().equals(IP)) {
				try {
					IP = Client.getIP();
				} catch (Exception e1) {
					e1.printStackTrace();
					return;
				}
				Map<String, String> m = new HashMap<String, String>();
				m.put("IP", IP);
				try {
					this.server.requestPost("rasp/test", m);
				} catch (Exception e) {
					e.printStackTrace();
					this.setServer();
					IP = null;
				}
			}
	}
	
	static class ReqHandler implements HttpHandler {
		private static Client client;
		public ReqHandler(Client client) {
			ReqHandler.client = client;
		}
		public void handle(HttpExchange httpExchange) throws IOException {
			if (!httpExchange.getRemoteAddress().getAddress().getHostAddress().equals(ReqHandler.client.server.getIP ())){
				System.out.println(httpExchange.getRemoteAddress().getAddress().getHostAddress() + " ignored.");
				return ;
			}
			System.out.println(httpExchange.getRemoteAddress().getAddress().getHostAddress());
			String method = httpExchange.getRequestMethod();
			if (method.equals("POST")){
				InputStream in = httpExchange.getRequestBody();
				Map<String, String> parms = new HashMap<String, String>();
				Scanner scan = new Scanner(in);
				while (scan.hasNext()){
					@SuppressWarnings("deprecation")
					String s = URLDecoder.decode(scan.next());
					String [] pars = s.split("&");
					for (String p : pars){
						int a = 0;
						while (p.charAt(a) != '=')
							a++;
						parms.put(p.substring(0, a), p.substring(a + 1, p.length()));
					}
				}
				scan.close();
				String log = doReq (parms);
				writeResponse(httpExchange, log);
			}
		}
		
		public static String doReq (Map <String, String> parms){
			String log = "OK";
			for (String key : parms.keySet())
				System.out.println(key + " = " + parms.get(key));
			return log;
		}
		
		public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
			System.out.println(response);
			httpExchange.sendResponseHeaders(200, response.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

}
