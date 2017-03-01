/*************************************************************************
 *  Compilation:  javac-algs4 FastCollinearPoints.java
 *  Execution:    java-algs4 FastCollinearPoints <inputFile.txt>
 *  Dependencies: Point.java, LineSegment.java
 *
 *  Sorting-based solution to find all maximal lines containing 4 or more
 *  points given an array of points
 *  Time Complexity: O(n^2*logN)
 *
 *************************************************************************/
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

import java.util.Comparator;
import java.util.Arrays;
import java.awt.Font;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    // list containing all maximal lines
    private LineSegment[] segmentList;
    // number of maximal lines found
    private int segmentCount;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if(points == null) throw new NullPointerException();
        //initialization
        this.segmentList = new LineSegment[16];
        this.segmentCount = 0;

        int counter;
        boolean newSeq = true;
        double slopeA, slopeB, blackSlope;
        Point[] cpy = Arrays.copyOf(points, points.length);
        int i, j;
        if(points.length <= 1) return;
        // for each origin points[i]
        for(i = 0; i < points.length; i++) {
            Arrays.sort(cpy);   // sort points by natural order first
            Arrays.sort(cpy, 0, cpy.length, points[i].slopeOrder()); // sort by slope
            // check for duplicate points
            if(points[i].compareTo(cpy[1]) == 0) throw new IllegalArgumentException();
            // number of points on the line
            counter = 0;
            // slope of possible sub-segment
            blackSlope = Double.NaN;
            slopeA = points[i].slopeTo(cpy[1]);
            if(points[i].compareTo(cpy[1])==1) {
                blackSlope = slopeA;
            }
            for(j = 2; j < cpy.length; j++) {
                if(cpy[j].compareTo(cpy[j-1]) == 0) throw new IllegalArgumentException();
                slopeB = points[i].slopeTo(cpy[j]);
                if(points[i].compareTo(cpy[j]) == 1) {
                    blackSlope = slopeB;
                }
                if(slopeA == slopeB) {
                    if(slopeB == blackSlope) {
                        continue;
                    }
                    counter++;
                }
                else {
                    // add only maximal line segment
                    if(counter >= 2) {
                        addLine(points[i], cpy[j-1]);
                    }
                    slopeA = slopeB;
                    counter = 0;
                }
            }
            if(counter >= 2) {
                addLine(points[i], cpy[j-1]);
            }
        }
    }

    /*
     * helper function to add lines x->y to the array of maximal line segments
     */
    private void addLine(Point x, Point y) {
        if(this.segmentList.length <= this.segmentCount) {
            resize(2 * this.segmentCount);
        }
        this.segmentList[this.segmentCount++] = new LineSegment(x, y);
    }

    // helper function to resize the array of line segments
    private void resize(int capacity) {
        LineSegment[] cpy = Arrays.copyOf(this.segmentList, capacity);
        this.segmentList = cpy;
    }

    /*
     * @return: number of line segments found
     */
    public int numberOfSegments() {
        return this.segmentCount;
    }

    /*
     * @return: array containing all maximal line segments
     */
    public LineSegment[] segments() {
        int i = 0;
        LineSegment[] segs = Arrays.copyOf(this.segmentList, this.segmentCount);
        return segs;
    }

    /*
     * test client that draws all maximal line segments
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            segment.draw();
        }
        StdDraw.show();
    }
}
