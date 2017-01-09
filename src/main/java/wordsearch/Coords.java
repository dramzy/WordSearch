package wordsearch;

/**
 * Represents coordinates in a puzzle
 */
public final class Coords {
   private final int x, y;

   public Coords(final int x, final int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

}
