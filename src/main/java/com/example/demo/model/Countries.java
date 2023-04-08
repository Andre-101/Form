package com.example.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Countries {

    private static final String ACCESS_TOKEN_URL = "https://www.universal-tutorial.com/api/getaccesstoken";
    private static final String COUNTRIES_URL = "https://www.universal-tutorial.com/api/countries/";
    private static final String STATES_URL = "https://www.universal-tutorial.com/api/states/";
    private static final String CITIES_URL = "https://www.universal-tutorial.com/api/cities/";

    private static final String API_TOKEN = "CwaPbrJx0QDA4eU_WP1N6vWKYGODk-8XKpQvRufy0nww-iejU4jlyqL-uTWwzw520uE";
    private static final String USER_EMAIL = "kabh28@hotmail.com";

    private String authToken;

    public Countries() throws IOException {
        authenticate();
    }

    private void authenticate() throws IOException, JSONException {
        URL url = new URL(ACCESS_TOKEN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("api-token", API_TOKEN);
        connection.setRequestProperty("user-email", USER_EMAIL);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            JSONObject jsonObject = new JSONObject(response);
            authToken = jsonObject.getString("auth_token");
            in.close();
        } else {
            throw new IOException("Failed to authenticate, response code: " + responseCode);
        }
    }

    private JSONArray getCountriesList() throws IOException, JSONException {
        URL url = new URL(COUNTRIES_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + authToken);
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            JSONArray jsonArray = new JSONArray(response);
            in.close();
            return jsonArray;
        } else {
            throw new IOException("Failed to get countries, response code: " + responseCode);
        }
    }

    private JSONArray getStatesList(String country) throws IOException, JSONException {
        URL url = new URL(STATES_URL + country);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + authToken);
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            JSONArray jsonArray = new JSONArray(response);
            in.close();
            return jsonArray;
        } else {
            throw new IOException("Failed to get states, response code: " + responseCode);
        }
    }

    private JSONArray getCitiesList(String state) throws IOException, JSONException {
        URL url = new URL(CITIES_URL + state);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + authToken);
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            JSONArray jsonArray = new JSONArray(response);
            in.close();
            return jsonArray;
        } else {
            throw new IOException("Failed to get cities, response code: " + responseCode);
        }
    }

    public ObservableList<String> getCountries() {
        ObservableList<String> countries;
        ArrayList<String> countriesList = new ArrayList<>();
        try {
            JSONArray list = getCountriesList();
            for (int i = 0; i < list.length(); i++) {
                countriesList.add(((JSONObject) list.get(i)).getString("country_name"));
            }
            countries = FXCollections.observableArrayList(countriesList);
        } catch (IOException e) {
            e.printStackTrace();
            countries = FXCollections.observableArrayList("No connexion");
        }
        return countries;
    }

    public ObservableList<String> getStates(String  country) {
        if (country.equalsIgnoreCase("No connexion")) return null;
        ObservableList<String> states = null;
        ArrayList<String> statesList = new ArrayList<>();
        try {
            JSONArray list = getStatesList(country);
            for (int i = 0; i < list.length(); i++) {
                statesList.add(((JSONObject) list.get(i)).getString("state_name"));
            }
            states = FXCollections.observableArrayList(statesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return states;
    }

    public ObservableList<String> getCities(String state) {
        ObservableList<String> cities = null;
        ArrayList<String> citiesList = new ArrayList<>();
        try {
            JSONArray list = getCitiesList(state);
            for (int i = 0; i < list.length(); i++) {
                citiesList.add(((JSONObject) list.get(i)).getString("city_name"));
            }
            cities = FXCollections.observableArrayList(citiesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
