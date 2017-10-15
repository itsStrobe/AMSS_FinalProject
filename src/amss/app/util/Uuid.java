package amss.app.util;

import java.lang.StringBuilder;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by Jose Zavala on 14/10/17.
 */

public final class Uuid {

  public static final Uuid NULL = new Uuid(0);

  public interface Generator {
    Uuid make();
  }

  private final Uuid root;
  private final int id;

  public Uuid(Uuid root, int id) {
    this.root = root;
    this.id = id;
  }

  public Uuid(int id) {
    this.root = null;
    this.id = id;
  }

  public Uuid root() {
    return root;
  }

  public int id() {
    return id;
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof Uuid && equals(this, (Uuid) other);
  }

  @Override
  public int hashCode() { return hash(this); }

  @Override
  public String toString() {
    return toString(this);
  }

  // Check if two Uuids share the same root. This check is only one level deep.
  public static boolean related(Uuid a, Uuid b) {
    return equals(a.root(), b.root());
  }

  // Check if two Uuids represent the same value even if they are different refereces. This
  // means that all ids from the tail to the root have the same ids.
  public static boolean equals(Uuid a, Uuid b) {

    // First check if 'a' and 'b' refer to the same instance. This also
    // checks if both 'a' and 'b' are null which saves us from having to
    // check 'a' == null && 'b' == null.

    if (a == b) {
      return true;
    }

    if (a == null && b != null) {
      return false;
    }

    if (a != null && b == null) {
      return false;
    }

    // Check id before checking the root as the ids are more likely to differ
    // and will short-circuit the logic preventing us from wasting time checking
    // the full chain.
    return a.id() == b.id() && equals(a.root(), b.root());

  }

  // Compute a hash code for the Uuids by walking up the chain.
  private static int hash(Uuid id) {

    int hash = 0;

    for (Uuid current = id; current != null; current = current.root()) {
      hash ^= Objects.hash(current.id());
    }

    return hash;
  }

  // Compute human-readable representation for Uuids
  // Use long internally to avoid negative integers.
  private static String toString(Uuid id) {
    final StringBuilder build = new StringBuilder();
    buildString(id, build);
    return String.format("[UUID:%s]", build.substring(1));  // index of 1 to skip initial '.'
  }

  private static void buildString(Uuid current, StringBuilder build) {
    final long mask = (1L << 32) - 1;  // removes sign extension
    if (current != null) {
      buildString(current.root(), build);
      build.append(".").append(current.id() & mask);
    }
  }

  // Parse
  //
  // Create a uuid from a sting.
  public static Uuid parse(String string) throws IOException {
    return parse(null, string.split("\\."), 0);
  }

  private static Uuid parse(final Uuid root, String[] tokens, int index) throws IOException {
    final long id = Long.parseLong(tokens[index]);

    if ((id >> 32) != 0) {
      throw new IOException(String.format(
          "ID value '%s' is too large to be an unsigned 32 bit integer",
          tokens[index]));
    }

    final Uuid link = new Uuid(root, (int)(id & 0xFFFFFFFF));

    final int nextIndex = index + 1;

    return nextIndex < tokens.length ?
        parse(link, tokens, nextIndex) :
        link;
  }
}
