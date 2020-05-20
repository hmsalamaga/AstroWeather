package astroweather.com.astro;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class Calculator {
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;
    private AstroDateTime astroDateTime;

    public Calculator(Double latitude, Double longitude) {
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        astroDateTime = new AstroDateTime(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND), getOffset(), timeZone.inDaylightTime(new Date()));
        location = new AstroCalculator.Location(latitude, longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);
    }

    public ArrayList<String> setSunData() {
        ArrayList<String> data = new ArrayList<>();
        AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();
        data.add("Długość: " + astroCalculator.getLocation().getLongitude());
        data.add("Szerokość: " + astroCalculator.getLocation().getLatitude());
        data.add("Wschód: " + sunInfo.getSunrise().toString());
        data.add("Azymut: " + sunInfo.getAzimuthRise());
        data.add("Zachód: " + sunInfo.getSunset().toString());
        data.add("Azymut: " + sunInfo.getAzimuthSet());
        AstroDateTime twilightEvening = sunInfo.getTwilightEvening();
        String twilightEveningTime = twilightEvening.getHour() + ":" + twilightEvening.getMinute();
        AstroDateTime twilightMorning = sunInfo.getTwilightMorning();
        String twilightMorningTime = twilightMorning.getHour() + ":" + twilightMorning.getMinute();
        data.add("Zmierzch: " + twilightEveningTime);
        data.add("Świt: " + twilightMorningTime);
        return data;
    }


    public ArrayList<String> setMoonData() {
        ArrayList<String> data = new ArrayList<>();
        AstroCalculator.MoonInfo moonInfo = astroCalculator.getMoonInfo();
        data.add("Długość: " + astroCalculator.getLocation().getLongitude());
        data.add("Szerokość: " + astroCalculator.getLocation().getLatitude());
        data.add("Wschód: " + moonInfo.getMoonrise().toString());
        data.add("Zachód: " + moonInfo.getMoonset().toString());
        data.add("Nów: " + moonInfo.getNextNewMoon().toString());
        data.add("Pełnia: " + moonInfo.getNextFullMoon().toString());
        data.add("Faza: " + moonInfo.getIllumination() * 100 + "%");
        data.add(""+moonInfo.getAge());
        return data;
    }

    public static int getOffset(){
        TimeZone timezone = TimeZone.getDefault();
        int seconds = timezone.getOffset(Calendar.ZONE_OFFSET)/1000;
        double minutes = seconds/60;
        double hours = minutes/60;
        return (int) hours;
    }


}
