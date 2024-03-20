package edu.uncc.weatherapp.models;

import org.json.JSONObject;

public class Forecast {
    String startTime, tempUnit, windSpeed, iconUrl, shortForecast;
    Double temp, relativeHumid;

    //TODO: create forecast class with the data that we will be pulling from the API. If we use a constructor similar to the once in City (primitives), we will also need to create a Forecast object as we did the city object on line 103 of CitiesFragment. we can make the constructor take a JSONobject to avoid that.
    public Forecast(){

    }
    public Forecast(JSONObject jsonObject) {
        this.startTime = jsonObject.optString("startTime");
        this.temp = jsonObject.optDouble("temp");
        this.tempUnit = jsonObject.optString("tempUnit");
        this.relativeHumid = jsonObject.optDouble("relativeHumid");
        this.windSpeed = jsonObject.optString("windSpeed");
        this.iconUrl = jsonObject.optString("icon");
        this.shortForecast = jsonObject.optString("shortForecast");
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getRelativeHumid() {
        return relativeHumid;
    }

    public void setRelativeHumid(Double relativeHumid) {
        this.relativeHumid = relativeHumid;
    }
}
