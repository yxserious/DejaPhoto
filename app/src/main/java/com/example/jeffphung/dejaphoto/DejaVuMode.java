package com.example.jeffphung.dejaphoto;

/**
 * Created by kaijiecai on 4/28/17.
 *
 * This is dejaVuMode class which contains boolean values of all modes inside the dejaVuMode
 * all modes are true by default
 *
 * call set+Modename(boolean b) to set turn on/off the mode
 *    - true is on, false is off
 * call is+ModenameOn to get the current mode status
 *m
 */

public class DejaVuMode {
    private boolean dejaVuModeOn = true;
    private boolean locationModeOn = true;
    private boolean timeModeOn = true;
    private boolean dayModeOn = true;

    private static DejaVuMode dejaVuModeInstance = new DejaVuMode();

    private DejaVuMode(){}

    public static DejaVuMode getDejaVuModeInstance(){
        return dejaVuModeInstance;
    }


    public boolean isDejaVuModeOn() {
        return dejaVuModeOn;
    }

    public void setDejaVuModeOn(boolean dejaVuModeOn) {
        this.dejaVuModeOn = dejaVuModeOn;
    }

    public boolean isLocationModeOn() {
        return locationModeOn;
    }

    public void setLocationModeOn(boolean locationModeOn) {
        this.locationModeOn = locationModeOn;
    }

    public boolean isTimeModeOn() {
        return timeModeOn;
    }

    public void setTimeModeOn(boolean timeModeOn) {
        this.timeModeOn = timeModeOn;
    }

    public boolean isDayModeOn() {
        return dayModeOn;
    }

    public void setDayModeOn(boolean dayModeOn) {
        this.dayModeOn = dayModeOn;
    }
}
