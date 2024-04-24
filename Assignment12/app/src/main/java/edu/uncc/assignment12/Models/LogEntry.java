package edu.uncc.assignment12.Models;
public class LogEntry {
String datetime, hoursExercised, hoursSlept, sleepQuality;

    public LogEntry(String datetime, String hoursExercised, String hoursSlept, String sleepQuality) {
        this.datetime = datetime;
        this.hoursExercised = hoursExercised;
        this.hoursSlept = hoursSlept;
        this.sleepQuality = sleepQuality;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHoursExercised() {
        return hoursExercised;
    }

    public void setHoursExercised(String hoursExercised) {
        this.hoursExercised = hoursExercised;
    }

    public String getHoursSlept() {
        return hoursSlept;
    }

    public void setHoursSlept(String hoursSlept) {
        this.hoursSlept = hoursSlept;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "datetime='" + datetime + '\'' +
                ", hoursExercised='" + hoursExercised + '\'' +
                ", hoursSlept='" + hoursSlept + '\'' +
                ", sleepQuality='" + sleepQuality + '\'' +
                '}';
    }
}
