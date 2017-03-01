/***********************************************************************
 * An immutable data type that represents a point on the cartesian plane
 ***********************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
        // immutable coordinates
        private final int x;
        private final int y;

        // constructs the point (x, y)
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // draws this point
        public void draw() {
            StdDraw.point(this.x, this.y);
        }

        // draws the line segment from this point to that point
        public void drawTo(Point that) {
            StdDraw.line(this.x, this.y, that.x, that.y);
        }

        // string representation
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        /* natural order comparator
         * compare two points by y-coordinates, breaking ties by x-coordinates
         */
        public int compareTo(Point that) {
            int diffY = this.y - that.y;
            int diffX = this.x - that.x;
            if(diffY > 0) return 1;
            else if(diffY < 0) return -1;
            else if(diffX > 0) return 1;
            else if(diffX < 0) return -1;
            return 0;
        }

        // the slope between this point and that point
        public double slopeTo(Point that) {
            double diffY = this.y - that.y;
            double diffX = this.x - that.x;

            if(diffX  == 0) {
                //positive infinity case
                if(diffY != 0) return Double.POSITIVE_INFINITY;
                //negative infinity case
                else if(diffY == 0) return Double.NEGATIVE_INFINITY;
            }
            else if(diffY == 0) {
                return 0.0;
            }
            // normal case
            return (diffY / diffX);
        }

        /*
         * nested class that implements the Comparator interface
         */
        private class BySlopeOrder implements Comparator<Point> {
            /* orders points 'v' and 'w' by slope with respect to the
             * object point
             */
            @Override
            public int compare(Point v, Point w) {
                double slopeV = slopeTo(v);
                double slopeW = slopeTo(w);
                if(slopeV < slopeW) return -1;
                else if(slopeV > slopeW) return 1;
                return 0;
            }
        }

        // compare two points by slopes they make with this point
        public Comparator<Point> slopeOrder() {
            return new BySlopeOrder();
        }
}
