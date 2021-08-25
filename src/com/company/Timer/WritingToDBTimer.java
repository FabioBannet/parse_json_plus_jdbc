package com.company.Timer;

import com.company.DB.DAL;
import com.company.Entities.CityData;

import java.util.List;
import java.util.TimerTask;

public class WritingToDBTimer extends TimerTask {
    List<CityData> cityData;

    public WritingToDBTimer(List<CityData> cityData) {
        this.cityData = cityData;
    }

    @Override
    public void run() {
        DAL dal = new DAL();

        cityData.forEach(city ->{
            dal.put(city);

            System.out.println(city.getLocalName() + " was successfully added to DB");
        });
    }
}
