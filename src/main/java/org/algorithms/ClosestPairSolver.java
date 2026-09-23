package org.algorithms;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    public int maxDepth = 0;

    public double findClosest(Point[] points) {
        maxDepth = 0;
        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        return closestPair(sortedByX, 0, points.length - 1, 1);
    }

    private double closestPair(Point[] points, int low, int high, int depth) {
        maxDepth = Math.max(maxDepth, depth);
        if (high - low <= 3) return bruteForce(points, low, high);

        int mid = low + (high - low) / 2;
        Point midPoint = points[mid];

        double dl = closestPair(points, low, mid, depth + 1);
        double dr = closestPair(points, mid + 1, high, depth + 1);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[high - low + 1];
        int j = 0;
        for (int i = low; i <= high; i++) {
            if (Math.abs(points[i].x - midPoint.x) < d) {
                strip[j++] = points[i];
            }
        }

        Arrays.sort(strip, 0, j, Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < j; ++i) {
            for (int k = i + 1; k < j && (strip[k].y - strip[i].y) < d; ++k) {
                double dist = strip[i].distance(strip[k]);
                if (dist < d) d = dist;
            }
        }
        return d;
    }

    public double bruteForce(Point[] points, int low, int high) {
        double min = Double.MAX_VALUE;
        for (int i = low; i <= high; ++i) {
            for (int j = i + 1; j <= high; ++j) {
                min = Math.min(min, points[i].distance(points[j]));
            }
        }
        return min;
    }
}