package com.company.Entities;

import java.util.Objects;

public class Pollutant {
    private String pol;
    private String unit;
    private String time;
    private Double value;
    private String averaging;
    private int cityData_id;


    public Pollutant() {}

    public Pollutant(String pol, String unit, String time, Double value, String averaging, int cityData_id) {
        this.pol = pol;
        this.unit = unit;
        this.time = time;
        this.value = value;
        this.averaging = averaging;
        this.cityData_id = cityData_id;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getAveraging() {
        return averaging;
    }

    public void setAveraging(String averaging) {
        this.averaging = averaging;
    }

    public int getCityData_id() {
        return cityData_id;
    }

    public void setCityData_id(int cityData_id) {
        this.cityData_id = cityData_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pollutant pollutant = (Pollutant) o;
        return cityData_id == pollutant.cityData_id && Objects.equals(pol, pollutant.pol) && Objects.equals(unit, pollutant.unit) && Objects.equals(time, pollutant.time) && Objects.equals(value, pollutant.value) && Objects.equals(averaging, pollutant.averaging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pol, unit, time, value, averaging, cityData_id);
    }

    @Override
    public String toString() {
        return "Pollutant{" +
                "pol='" + pol + '\'' +
                ", unit='" + unit + '\'' +
                ", time='" + time + '\'' +
                ", value='" + value + '\'' +
                ", averaging='" + averaging + '\'' +
                ", cityData_id=" + cityData_id +
                '}';
    }
}
