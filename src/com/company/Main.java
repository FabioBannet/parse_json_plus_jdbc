package com.company;

import com.company.Constants.Constants;
import com.company.DB.DAL;
import com.company.Entities.CityData;
import com.company.Parsing.Parser;
import com.company.Timer.WritingToDBTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class Main {



    public static void main(String[] args) {
        Parser parser = new Parser();

        List<CityData> cityData = new ArrayList<>();
        parser.parse(cityData);

        WritingToDBTimer writingToDBTimer = new WritingToDBTimer(cityData);

        new Timer().schedule(writingToDBTimer,0, Constants.TIMER_DELAY );

    }
}
