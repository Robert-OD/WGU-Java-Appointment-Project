package Utils;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeDateFormat {


    public static String convertToDateTimeFormat(ZonedDateTime ldt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = ldt.format(formatter);
        return formatDateTime;
    }

    public static ZonedDateTime stringToZonedDateTime(String date){
        String[] dateString = date.split(" ")[0].split("-");
        String[] timeString = date.split(" ")[1].split(":");
        int year = Integer.parseInt(dateString[0]);
        int month = Integer.parseInt(dateString[1]);
        int day = Integer.parseInt(dateString[2]);
        int hour = Integer.parseInt(timeString[0]);
        int minute = Integer.parseInt(timeString[1]);
        int second = 0;
        LocalDate tempLocalDate = LocalDate.of(year, month, day);
        LocalTime tempLocalTime = LocalTime.of(hour, minute, second);
        ZoneId zoneId = ZoneId.of(ZoneId.systemDefault().toString());
        return ZonedDateTime.of(tempLocalDate, tempLocalTime, zoneId);
    }
    public static ArrayList<ZonedDateTime> getBusinessHours(LocalDate inputDate) throws SQLException {
        ArrayList<ZonedDateTime> returnList = new ArrayList<ZonedDateTime>();

        ZoneId zoneId = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");
        LocalTime open = LocalTime.of(12,0);
        LocalTime close = LocalTime.of(21,0);

        ZonedDateTime startTime = ZonedDateTime.of(inputDate, open, utcZone).withZoneSameInstant(zoneId);
        ZonedDateTime endTime = ZonedDateTime.of(inputDate, close, utcZone).withZoneSameInstant(zoneId);


        String sql = "SELECT * FROM appointment";
        Query.makeQuery(sql);
        ResultSet rs = Query.getResult();

        while(startTime.isBefore(endTime)){
            returnList.add(ZonedDateTime.parse(startTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
            startTime = startTime.plusMinutes(30);
        }
        while (rs.next()){
            ZonedDateTime tempDateTime = convertToLocalTime(rs.getString("start"));
            returnList.remove(tempDateTime);
        }
        return returnList;
    }

    public static ZonedDateTime convertToUTC(ZonedDateTime zdt){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");

        ZonedDateTime utcTime = ZonedDateTime.parse(zdt.withZoneSameInstant(defaultZoneId).withZoneSameInstant(utcZoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return utcTime;
    }

    public static ZonedDateTime convertToLocalTime(ZonedDateTime zdt){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime localTime = ZonedDateTime.parse(ZonedDateTime.of(LocalDateTime.from(zdt), utcZoneId).withZoneSameInstant(defaultZoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        return localTime;

    }

    public static ZonedDateTime convertToLocalTime(String zdt){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsed = LocalDateTime.parse(zdt, formatter);
        ZonedDateTime localTime = ZonedDateTime.parse(ZonedDateTime.of(parsed, utcZoneId).withZoneSameInstant(defaultZoneId).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        return localTime;

    }
}
