package puzzle;

/**
 * Represents coordinates in a puzzle
 */
public final class Coord2d {
   private int x, y;

   public Coord2d(final int x, final int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public void setY(final int y) {
      this.y = y;
   }

   public Coord2d moveX(final int xDelta) {
      x += xDelta;
      return this;
   }

   public Coord2d moveY(final int yDelta) {
      y += yDelta;
      return this;
   }

   public Coord2d move(final Direction direction, final int distance) {
      moveX(distance * direction.getxDelta());
      moveY(distance * direction.getyDelta());
      return this;
   }

   public Coord2d clone() {
      return new Coord2d(x, y);
   }

   @Override
   public String toString() {
      return "(Row " + y + ", Col " + x + ")";
   }
}
