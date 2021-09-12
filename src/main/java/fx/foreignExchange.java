package main.java.fx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class foreignExchange {
	
	static String fx = "";

	public static void main(String[] args) {
		System.out.println("Welcome to foreignExchange!!");
		System.out.println("Subscribe to FX to get the currency exchange rates:");
		System.out.println("1. INR_USD");
		System.out.println("2. INR_EUR");
		System.out.println("3. EUR_USD");
		System.out.println("4. EUR_INR");
		System.out.println("5. USD_INR");
		System.out.println("6. USD_EUR");
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();

		if (input == 1) {
			fx = "INR_USD";
		}
		if (input == 2) {
			fx = "INR_EUR";
		}
		if (input == 3) {
			fx = "EUR_USD";
		}
		if (input == 4) {
			fx = "EUR_INR";
		}
		if (input == 5) {
			fx = "USD_INR";
		}
		if (input == 6) {
			fx = "USD_EUR";
		}

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(foreignExchange::run, 0, 5, TimeUnit.SECONDS);
	}

	static void run() {
		currency_api(fx);
	}

	private static void currency_api(String fx) {
		try {

			String apiKey = "9a0d757a2880cae55ed8";
			String url = "https://free.currconv.com/api/v7/convert?q=" + fx + "&compact=ultra&apiKey=" + apiKey;
			URL urlForGetRequest = new URL(url);
			String readLine = null;
			HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer response = new StringBuffer();
				while ((readLine = in.readLine()) != null) {
					response.append(readLine);
				}
				in.close();
				parse(fx, response.toString());

			}
		} catch (IOException e) {
			System.out.println("Something went wrong!");

		}
	}

	public static void parse(String fx, String response) {

		try {
			JSONParser jp = (JSONParser) new JSONParser();
			Object obj = jp.parse(response);
			JSONObject jobj = (JSONObject) obj;
			String result = jobj.get(fx).toString();
			System.out.println(result);
		} catch (ParseException e) {

			e.printStackTrace();
		}

	}
}
