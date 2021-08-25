package com.company.DB;

import com.company.Entities.CityData;
import com.company.Entities.Pollutant;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class DAL {

    private String schema_name;
    private IMySqlDB mysqlDB;

    {
        schema_name = "city_data";
        mysqlDB = new MySqlDB();
    }
    private void checkSchema(){
        String sql = "CREATE SCHEMA IF NOT EXISTS `"+schema_name+"`";
        mysqlDB.executeUpdateSQL(sql);
        mysqlDB.setUrl(mysqlDB.getUrl()+ "/" + schema_name);
    }

    private void checkTables(){
        String firstTableSql = "CREATE TABLE IF NOT EXISTS`cityData` (\n" +
                "  `id` int NOT NULL auto_increment,\n" +
                "  `res_id` VARCHAR(255) NOT NULL,\n" +
                "  `cityName` varchar(255) DEFAULT NULL,\n" +
                "  `stationName` varchar(255) DEFAULT NULL,\n" +
                "  `localName` varchar(255) DEFAULT NULL,\n" +
                "  `latitude` DECIMAL(10,7) DEFAULT NULL,\n" +
                "  `longitude` DECIMAL(10,7) DEFAULT NULL,\n" +
                "  `timeZone` varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ")ENGINE=INNODB;";

        mysqlDB.executeUpdateSQL(firstTableSql);

        String secondTableSql = "CREATE TABLE IF NOT EXISTS `polutants` (\n" +
                "  `id` int NOT NULL auto_increment,\n" +
                "  `pol` VARCHAR(255) DEFAULT NULL,\n" +
                "  `unit` VARCHAR(255) DEFAULT NULL,\n" +
                "  `time` VARCHAR(255) DEFAULT NULL,\n" +
                "  `value` DOUBLE DEFAULT NULL,\n" +
                "  `averaging` VARCHAR(255) DEFAULT NULL,  \n" +
                "  `cityData_id` int,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `cityData_index`(`cityData_id`),\n" +
                "  foreign key(`cityData_id`) references citydata(id)\n" +
                ")ENGINE=INNODB;";

        mysqlDB.executeUpdateSQL(secondTableSql);
    }


    public DAL() {
        // Перевіряємо чи є БД та таблиці, якщо немає створюємо.
        checkSchema();
        checkTables();
    }



    public List<CityData> getAll(){
        String sql = "SELECT * FROM city_data.citydata";

        List<CityData> result = (List<CityData>) mysqlDB.executeQuery(sql, cityDataListFunction);

        result.forEach(city ->{
            String sqlPollutants = "SELECT * FROM city_data.polutants WHERE cityData_id ="+ city.getId() + "";
            List<Pollutant> pollutants = (List<Pollutant>) mysqlDB.executeQuery(sqlPollutants, pollutantFunction);

            city.setPollutants(pollutants);
        });

        return result;

    }

   public CityData getByResID(String resID){
        String sql = "SELECT * FROM `city_data`.`citydata`" +
                "WHERE res_id ='"+resID +"'";

        CityData result= (CityData) mysqlDB.executeQuery(sql, cityDataFunction);

        String sqlPollutants = "SELECT * FROM `city_data`.`polutants` WHERE cityData_id ="+result.getId() +"";

        List<Pollutant> pollutants = (List<Pollutant>) mysqlDB.executeQuery(sqlPollutants, pollutantFunction);

        result.setPollutants(pollutants);

        return  result;
    }

   public CityData getByID(int id){
        String sql = "SELECT * FROM city_data.citydata WHERE id="+ id +"";

        CityData result= (CityData) mysqlDB.executeQuery(sql, cityDataFunction);

        String sqlPollutants = "SELECT * FROM city_data.polutants WHERE cityData_id ="+ id + "";

        List<Pollutant> pollutants = (List<Pollutant>) mysqlDB.executeQuery(sqlPollutants, pollutantFunction);

        result.setPollutants(pollutants);

        return  result;
    }


   public void put(CityData cityData){
            String sqlCityData = "INSERT INTO `city_data`.`citydata`\n" +
                    "(`res_id`," +
                    "`cityName`," +
                    "`stationName`," +
                    "`localName`," +
                    "`latitude`," +
                    "`longitude`," +
                    "`timeZone`)" +
                    "VALUES(" +
                    "'"+cityData.getRes_id()+"'," +
                    "'"+cityData.getCityName()+"'," +
                    "'"+cityData.getStationName()+"'," +
                    "'"+cityData.getLocalName()+"'," +
                    ""+cityData.getLatitude()+"," +
                    ""+cityData.getLongitude()+"," +
                    "'"+cityData.getTimeZone()+"')";
            mysqlDB.executeUpdateSQL(sqlCityData);

            //отримуємо id в БД
           final CityData cityDataBD = getByResID(cityData.getRes_id());

           List<Pollutant> pollutants = cityData.getPollutants();

           if(pollutants == null){
               return;
           }
          for (int i = 0; i < pollutants.size() ; i++) {
              String polSql = "INSERT INTO `city_data`.`polutants`" +
                      "(pol," +
                      "unit," +
                      "time," +
                      "value," +
                      "averaging," +
                      "cityData_id)" +
                      "VALUES(" +
                      "'"+pollutants.get(i).getPol()+"'," +
                      "'"+pollutants.get(i).getUnit()+"'," +
                      "'"+pollutants.get(i).getTime()+"'," +
                      "'"+pollutants.get(i).getValue()+"'," +
                      "'"+pollutants.get(i).getAveraging()+"'," +
                      "'"+cityDataBD.getId()+"')";

              mysqlDB.executeUpdateSQL(polSql);
          }
      }

   public void delete(int id){
        String sqlDelCityData = "DELETE FROM `city_data`.`citydata` WHERE id="+id+"";
        String sqlDelPol = "DELETE FROM `city_data`.`polutants`WHERE cityData_id="+id+"";

        mysqlDB.executeUpdateSQL(sqlDelPol);
        mysqlDB.executeUpdateSQL(sqlDelCityData);
      }

    //Lambdas
    private static Function<ResultSet, CityData> cityDataFunction = res -> {
        try {
            CityData cityData = new CityData();
            while (res.next()) {
                cityData.setId(res.getInt("id"));
                cityData.setRes_id(res.getString("res_id"));
                cityData.setCityName(res.getString("cityName"));
                cityData.setStationName(res.getString("stationName"));
                cityData.setLocalName(res.getString("localName"));
                cityData.setLatitude(res.getDouble("latitude"));
                cityData.setLongitude(res.getDouble("longitude"));
                cityData.setTimeZone(res.getString("timeZone"));
            }
            return cityData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };

    private static Function<ResultSet, List<Pollutant>> pollutantFunction = res ->{

        List<Pollutant> polls = new ArrayList<>();
        try{
            while (res.next()){
                Pollutant pol = new Pollutant();
                pol.setPol(res.getString("pol"));
                pol.setUnit(res.getString("unit"));
                pol.setTime(res.getString("time"));
                pol.setValue(res.getDouble("value"));
                pol.setAveraging(res.getString("averaging"));
                pol.setCityData_id(res.getInt("cityData_id"));

                polls.add(pol);
            }
        }catch (Exception e){

            return polls;
        }

        return polls;
    };

    private static Function<ResultSet, List<CityData>> cityDataListFunction = res -> {
        try {
            List<CityData> list = new ArrayList<>();
            while (res.next()) {
                CityData cityData = new CityData();
                cityData.setId(res.getInt("id"));
                cityData.setRes_id(res.getString("res_id"));
                cityData.setCityName(res.getString("cityName"));
                cityData.setStationName(res.getString("stationName"));
                cityData.setLocalName(res.getString("localName"));
                cityData.setLatitude(res.getDouble("latitude"));
                cityData.setLongitude(res.getDouble("longitude"));
                cityData.setTimeZone(res.getString("timeZone"));

                list.add(cityData);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };

}

