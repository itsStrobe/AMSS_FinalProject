package amss.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by strobe on 15/10/17.
 */

public final class Time implements Comparable<Time> {

  private static final SimpleDateFormat formatter =
      new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");

  private final Date date;

  private Time(long totalMs) { this.date = new Date(totalMs); }

  public long inMs() { return date.getTime(); }

  @Override
  public int compareTo(Time other) {
    return date.compareTo(other.date);
  }

  public boolean inRange(Time start, Time end) {
    return this.compareTo(start) >= 0 && this.compareTo(end) <= 0;
  }

  @Override
  public String toString() {
    return formatter.format(date);
  }

  public static Time fromMs(long ms) { return new Time(ms); }

  public static Time now() { return Time.fromMs(System.currentTimeMillis()); }

  public static long getDiferenceInDays(Time t1, Time t2) {
    long diff;

    diff = TimeUnit.DAYS.convert(t1.inMs() - t2.inMs(), TimeUnit.MILLISECONDS);

    return diff;
  }

  public static long getDateInMs(String date) {DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    Date startDate = new Date();
    try {
      startDate = df.parse(date);
      String newDateString = df.format(startDate);
      System.out.println(newDateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return startDate.getTime();
  }
}