package com.company.Parsing;

import com.company.Constants.Constants;
import com.company.Entities.CityData;
import com.company.Entities.Pollutant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private StringBuffer responseContent;
    private static HttpURLConnection connection;

    {
        responseContent = new StringBuffer();
    }

    private void read()
    {

        BufferedReader reader;
        String line;

        try {

            URL url = new URL(Constants.ADDRESS);

            // CONNECTION CONFIG
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(Constants.DELAY);
            connection.setReadTimeout(Constants.DELAY);
            int status = connection.getResponseCode();

            if(status > Constants.VALID_STATUS){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
    }

    private  static  JSONArray citiesData;

    public void parse(List<CityData> cityData){
        // считуємо данні
        read();

        try{
            citiesData = new JSONArray(responseContent.toString());

        }catch (JSONException e){
            e.printStackTrace();
        }

        for (int i = 0; i < citiesData.length(); i++) {
            JSONObject city = citiesData.getJSONObject(i);

            if(city.getString(Constants.CITY_NAME_PART).equals(Constants.LOOKING_CITY_NAME))
            {
                // я вважаю так читабельно і краще ніж через сетери перекидати
                // менше викликів і все таке
                List<Pollutant> pollutants = new ArrayList<Pollutant>();
                JSONArray pollutantJSONArray = city.getJSONArray(Constants.POLLUTANTS_PART);

                // заповнюємо array з відходами
                for (int j = 0; j < pollutantJSONArray.length(); j++) {
                    pollutants.add(
                            new Pollutant(
                                    pollutantJSONArray.getJSONObject(j).getString(Constants.POL_PART),
                                    pollutantJSONArray.getJSONObject(j).getString(Constants.UNIT_PART),
                                    pollutantJSONArray.getJSONObject(j).getString(Constants.TIME_PART),
                                    pollutantJSONArray.getJSONObject(j).getDouble(Constants.VALUE_PART),
                                    pollutantJSONArray.getJSONObject(j).getString(Constants.AVERAGING_PART),
                                    0
                            )
                    );
                }

                // заповнюємо основний об'єкт
                 cityData.add(new CityData(
                        city.getString(Constants.ID_PART),
                        city.getString(Constants.CITY_NAME_PART),
                        city.getString(Constants.STATION_NAME_PART),
                        city.getString(Constants.LOCAL_NAME_PART),
                        city.getDouble(Constants.LATITUDE_PART),
                        city.getDouble(Constants.LONGITUDE_PART),
                        city.getString(Constants.TIMEZONE_PART),
                        pollutants
                ));

            }
            }


    }
}
