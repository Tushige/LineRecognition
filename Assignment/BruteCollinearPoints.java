/*************************************************************************
 *  Compilation:  javac-algs4 BruteCollinearPoints.java
 *  Execution:    java-algs4 BruteCollinearPoints <inputFile.txt>
 *  Dependencies: Point.java, LineSegment.java
 *
 *  Bruteforce solution to find maximal lines given an array of points
 *
 *  Examines 4 points at a time and checks whether they all lie on the same
 *  line segment. The program returns all such line segments
 *
 *************************************************************************/
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.awt.Font;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int segmentCount;
    private LineSegment[] segmentList;
    private Point[] pointsCpy;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if(points == null) throw new NullPointerException();
        this.pointsCpy = Arrays.copyOf(points, points.length);
        this.segmentCount = 0;
        this.segmentList = new LineSegment[points.length];
        // slope of p1 and p2
        double slopeA;
        // slope of p2 and p3
        double slopeB;
        // slope of p3 and p4
        double slopeC;
        Arrays.sort(this.pointsCpy);
        if(this.pointsCpy.length < 4) {
            for(int i = 0; i < this.pointsCpy.length-1; i++) {
                if(this.pointsCpy[i].compareTo(this.pointsCpy[i+1]) == 0) throw new IllegalArgumentException();
            }
        }
        for(int i = 0; i < this.pointsCpy.length-3; i++) {
            if(this.pointsCpy[i] == null) throw new NullPointerException();
            if(this.pointsCpy[i].compareTo(this.pointsCpy[i+1]) == 0) throw new IllegalArgumentException();
            for(int j = i+1; j < this.pointsCpy.length-2; j++) {
                if(this.pointsCpy[j] == null) throw new NullPointerException();
                if(this.pointsCpy[j].compareTo(this.pointsCpy[j+1]) == 0) throw new IllegalArgumentException();
                slopeA = this.pointsCpy[i].slopeTo(this.pointsCpy[j]);
                for(int m = j+1; m < this.pointsCpy.length-1; m++) {
                    if(this.pointsCpy[m] == null) throw new NullPointerException();
                    if(this.pointsCpy[m].compareTo(this.pointsCpy[m+1]) == 0) throw new IllegalArgumentException();
                    slopeB = this.pointsCpy[i].slopeTo(this.pointsCpy[m]);
                    for(int n = m+1; n < this.pointsCpy.length; n++) {
                        if(this.pointsCpy[n] == null) throw new NullPointerException();
                        slopeC = this.pointsCpy[i].slopeTo(this.pointsCpy[n]);
                        if(slopeA == slopeB && slopeB == slopeC) {
                            LineSegment ac = new LineSegment(this.pointsCpy[i], this.pointsCpy[n]);
                            this.segmentList[segmentCount++] = ac;
                        }
                    }
                }
            }
        }
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
        LineSegment[] segs = new LineSegment[this.segmentCount];
        for(int i = 0; i < this.segmentCount; i++) {
            segs[i] = this.segmentList[i];
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        System.out.printf("num: %d\n", collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
