package amss.app.util;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Jose Zavala on 17/10/17.
 */

public final class Logger {

  public interface Log {

    void verbose(String message, Object... params);

    void info(String message, Object... params);

    void warning(String message, Object... params);

    void error(String message, Object... params);
    void error(Throwable error, String message, Object... params);

  }

  private static final java.util.logging.Logger logger =
      java.util.logging.Logger.getLogger("amss.app");

  static {
    logger.setLevel(java.util.logging.Level.INFO);

    // Stop this logger from sending its messages up to the root. This will
    // make our logger the new root logger.
    logger.setUseParentHandlers(false);
  }

  public static void enableFileOutput(String file) throws IOException {

    final java.util.logging.Handler handler =
        new java.util.logging.FileHandler(file, true /* append */);
    handler.setFormatter(new java.util.logging.SimpleFormatter());
    logger.addHandler(handler);
  }

  public static void enableConsoleOutput() {

    final java.util.logging.Handler handler =
        new java.util.logging.ConsoleHandler();
    handler.setFormatter(new java.util.logging.SimpleFormatter());
    logger.addHandler(handler);
  }

  public static Log newLog(Class<?> c) {

    final java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(c.getName());

    // Note: This Logger calls the standard java Logger, so the class/method lookup
    // needs to go one level deeper.  Otherwise it will appear that all the log
    // calls are coming from this object. Use logp() (log precise) to do this.
    return new Log() {

      @Override
      public void verbose(String message, Object... params) {
        log.logp(java.util.logging.Level.FINE,
            Thread.currentThread().getStackTrace()[2].getClassName(),
            Thread.currentThread().getStackTrace()[2].getMethodName(),
            String.format(message, params));
      }

      @Override
      public void info(String message, Object... params) {
        log.logp(java.util.logging.Level.INFO,
            Thread.currentThread().getStackTrace()[2].getClassName(),
            Thread.currentThread().getStackTrace()[2].getMethodName(),
            String.format(message, params));
      }

      @Override
      public void warning(String message, Object... params) {
        log.logp(java.util.logging.Level.WARNING,
            Thread.currentThread().getStackTrace()[2].getClassName(),
            Thread.currentThread().getStackTrace()[2].getMethodName(),
            String.format(message, params));
      }

      @Override
      public void error(String message, Object... params) {
        log.logp(java.util.logging.Level.SEVERE,
            Thread.currentThread().getStackTrace()[2].getClassName(),
            Thread.currentThread().getStackTrace()[2].getMethodName(),
            String.format(message, params));
      }

      @Override
      public void error(Throwable error, String message, Object... params) {
        log.logp(java.util.logging.Level.SEVERE,
            Thread.currentThread().getStackTrace()[2].getClassName(),
            Thread.currentThread().getStackTrace()[2].getMethodName(),
            String.format(message, params), error);
      }
    };
  }
}
