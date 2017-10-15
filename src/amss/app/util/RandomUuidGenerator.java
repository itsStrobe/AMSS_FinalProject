package amss.app.util;

import java.util.Random;

import amss.app.util.Uuid;
/**
 * Created by strobe on 14/10/17.
 */

// Create a new random uuid. Uuids from this generator are random
// but are not guaranteed to be unique. Checking uniqueness is left
// to the caller.
public final class RandomUuidGenerator implements Uuid.Generator {

  private final Uuid commonRoot;
  private final Random random;

  public RandomUuidGenerator(Uuid root, long seed) {
    this.commonRoot = root;
    this.random = new Random(seed);
  }

  @Override
  public Uuid make() {
    return new Uuid(commonRoot, random.nextInt());
  }
}
