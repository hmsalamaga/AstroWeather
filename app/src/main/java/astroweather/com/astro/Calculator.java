package astroweather.com.astro;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


class Calculator {
    private final AstroCalculator astroCalculator;

    public Calculator(Double latitude, Double longitude) {
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        AstroDateTime astroDateTime = new AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), getOffset(), timeZone.inDaylightTime(new Date()));
        AstroCalculator.Location location = new AstroCalculator.Location(latitude, longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);
    }

    public ArrayList<String> setSunData() {
        ArrayList<String> data = new ArrayList<>();
        AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();
        data.add("" + astroCalculator.getLocation().getLongitude());
        data.add("" + astroCalculator.getLocation().getLatitude());
        data.add(sunInfo.getSunrise().toString());
        data.add("" + sunInfo.getAzimuthRise());
        data.add(sunInfo.getSunset().toString());
        data.add("" + sunInfo.getAzimuthSet());
        AstroDateTime twilightEvening = sunInfo.getTwilightEvening();
        String twilightEveningTime = twilightEvening.getHour() + ":" + twilightEvening.getMinute();
        AstroDateTime twilightMorning = sunInfo.getTwilightMorning();
        String twilightMorningTime = twilightMorning.getHour() + ":" + twilightMorning.getMinute();
        data.add(twilightEveningTime);
        data.add(twilightMorningTime);
        return data;
    }


    public ArrayList<String> setMoonData() {
        ArrayList<String> data = new ArrayList<>();
        AstroCalculator.MoonInfo moonInfo = astroCalculator.getMoonInfo();
        data.add("" + astroCalculator.getLocation().getLongitude());
        data.add("" + astroCalculator.getLocation().getLatitude());
        data.add("" + moonInfo.getMoonrise().toString());
        data.add("" + moonInfo.getMoonset().toString());
        data.add("" + moonInfo.getNextNewMoon().toString());
        data.add("" + moonInfo.getNextFullMoon().toString());
        data.add("" + moonInfo.getIllumination() * 100 + "%");
        data.add("" + moonInfo.getAge());
        return data;
    }

    private static int getOffset() {
        TimeZone timezone = TimeZone.getDefault();
        int seconds = timezone.getOffset(Calendar.ZONE_OFFSET) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        return (int) hours;
    }


}