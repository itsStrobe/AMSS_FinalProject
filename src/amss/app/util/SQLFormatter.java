package amss.app.util;

/**
 * Created by Jose Zavala on 17/10/17.
 */
public class SQLFormatter {
  public static String sqlID(Uuid userID) {
    String sqlID = "NULL";
    if (userID != null) {
      sqlID = userID.toString();
      sqlID = sqlID.replace("[UUID:", "");
      sqlID = sqlID.replace("]", "");
    }

    return sqlID;
  }

  public static String sqlID(Uuid userID1, Uuid userID2) {
    String sqlID1 = "NULL";
    if (userID1 != null) {
      sqlID1 = userID1.toString();
      sqlID1 = sqlID1.replace("[UUID:", "");
      sqlID1 = sqlID1.replace("]", "");
    }

    String sqlID2 = "NULL";
    if (userID2 != null) {
      sqlID2 = userID2.toString();
      sqlID2 = sqlID2.replace("[UUID:", "");
      sqlID2 = sqlID2.replace("]", "");
    }

    return sqlID1 + sqlID2;
  }

  public static String sqlInt(int number) {
    return Integer.toString(number);
  }

  public static String sqlChar(char character) {
    return Character.toString(character);
  }

  public static String sqlBool(boolean bool) {
    if(bool)
      return "1";
    else
      return "0";
  }

  public static String sqlTime(Time userTime) {
    Long inMs = userTime.inMs();
    String sqlCreationTime = Long.toString(inMs);
    return sqlCreationTime;
  }
}
