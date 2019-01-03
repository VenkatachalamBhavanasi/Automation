package com.ssp.tsl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenAndClientIdGeneration {

	public static void main(String[] args) throws IOException {

		String token = "";
		String clientId = "";
		String userName = "";
		JsonObject obj = authenticate();
		// token = json.get("token").getAsString();
		token = obj.get("token").getAsString();
		clientId = obj.get("clientId").getAsString();
		userName = obj.get("username").getAsString();

		System.out.println("Token" + " = " + token);
		System.out.println("Client ID" + " = " + clientId);
		System.out.println("UserName" + " = " + userName);
	}

	private static JsonObject authenticate() throws IOException {

		String url = "http://vm-s4b-blddpy19:5001/api/auth/login";
		URL obj = new URL(url);
		// URL obj = new URL(URLEncoder.encode(url,
		// StandardCharsets.UTF_8.name()));

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");

		// con.setRequestProperty("bearer", "");

		String requestBody = "{\"username\":\"" + "sirius" + "\",\"password\":\"" + "XctqMUbg" + "\"}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();
			String res = response.toString();

			System.out.println(res);

			JsonObject json = (JsonObject) new JsonParser().parse(res);

			return json;

		}
		return null;

	}
	
}
