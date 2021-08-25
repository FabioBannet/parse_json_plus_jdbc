package com.company.Entities;

import java.util.List;
import java.util.Objects;

public class CityData {
    private int id;
    private String res_id;
    private String cityName;
    private String stationName;
    private String localName;
    private double latitude;
    private double longitude;
    private String timeZone;
    private List<Pollutant> pollutants;

    public CityData() {}

    public CityData(String res_id, String cityName, String stationName, String localName, Double latitude, Double longitude, String timeZone, List<Pollutant> pollutants) {
        this.res_id = res_id;
        this.cityName = cityName;
        this.stationName = stationName;
        this.localName = localName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
        this.pollutants = pollutants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<Pollutant> getPollutants() {
        return pollutants;
    }

    public void setPollutants(List<Pollutant> pollutants) {
        this.pollutants = pollutants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityData cityData = (CityData) o;
        return Objects.equals(id, cityData.id) && Objects.equals(cityName, cityData.cityName) && Objects.equals(stationName, cityData.stationName) && Objects.equals(localName, cityData.localName) && Objects.equals(latitude, cityData.latitude) && Objects.equals(longitude, cityData.longitude) && Objects.equals(timeZone, cityData.timeZone) && Objects.equals(pollutants, cityData.pollutants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cityName, stationName, localName, latitude, longitude, timeZone, pollutants);
    }

    @Override
    public String toString() {
        return "CityData{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", localName='" + localName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timeZone='" + timeZone + '\'' +
                ", pollutants=" + pollutants +
                '}';
    }
}
