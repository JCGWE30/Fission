package org.lepigslayer.fission.Utilities;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StringTimeUtils {

    public static String getSimpleStamp(Instant time) {
        boolean isPast = false;

        Instant now = Instant.now();

        Instant startTime = now;
        Instant endTime = time;

        if(endTime.isBefore(startTime)) {
            isPast = true;
            endTime = now;
            startTime = time;
        }

        Duration duration = Duration.between(startTime, endTime);
        StringBuilder builder = new StringBuilder();
        if(!isPast)
            builder.append("in ");

        builder.append(getSimpleStamp(duration));

        if(isPast)
            builder.append(" ago");

        return builder.toString();
    }

    public static String getSimpleStamp(Duration duration) {
        StringBuilder builder = new StringBuilder();

        if(duration.toDaysPart()>7)
            builder.append(NumberUtils.trim(Math.floor(duration.toDaysPart()/7.0))).append("w");
        else if(duration.toDaysPart()>0)
            builder.append(duration.toDaysPart()).append("d");
        else if(duration.toHoursPart()>0)
            builder.append(duration.toHoursPart()).append("h");
        else if(duration.toMinutesPart()>0)
            builder.append(duration.toMinutesPart()).append("m");
        else
            builder.append(duration.toSecondsPart()).append("s");

        return builder.toString();
    }

    public static String getStamp(Duration duration) {
        List<String> strings = new ArrayList<>();

        if(duration.toDaysPart()>0)
            strings.add(duration.toDaysPart()+"d");
        if(duration.toHoursPart()>0)
            strings.add(duration.toHoursPart()+"h");
        if(duration.toMinutesPart()>0)
            strings.add(duration.toMinutesPart()+"m");
        if(duration.toSecondsPart()>0)
            strings.add(duration.toSecondsPart()+"s");

        if(strings.isEmpty())
            return "0s";

        return String.join(" ", strings);
    }

    public static String getStampOrEmpty(Duration duration, String defaultValue) {
        if(duration.toSecondsPart()<=0 || duration.isNegative())
            return defaultValue;

        return getStamp(duration);
    }

    public static String getHouredFormat(Duration duration){
        List<String> values = new ArrayList<>();

        if(duration.toHoursPart()>0)
            values.add(String.format("%02d",duration.toHours()));

        if(duration.toMinutesPart()>0||duration.toHours()>0)
            values.add(String.format("%02d",duration.toMinutesPart()));

        if(duration.toSecondsPart()>0||duration.toHours()>0)
            values.add(String.format("%02d",duration.toSecondsPart()));

        if(values.isEmpty())
            return "Now";

        return String.join(":", values);
    }
}
