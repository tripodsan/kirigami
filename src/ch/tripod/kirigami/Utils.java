/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.tripod.kirigami;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * <code>Utils</code>...
 */
public class Utils {

    public static Point2D getLineLineIntersection(Line2D l0, Line2D l1) {
        double x1 = l0.getX1();
        double y1 = l0.getY1();
        double x2 = l0.getX2();
        double y2 = l0.getY2();
        double x3 = l1.getX1();
        double y3 = l1.getY1();
        double x4 = l1.getX2();
        double y4 = l1.getY2();
        double det1And2 = det(x1, y1, x2, y2);
        double det3And4 = det(x3, y3, x4, y4);
        double x1LessX2 = x1 - x2;
        double y1LessY2 = y1 - y2;
        double x3LessX4 = x3 - x4;
        double y3LessY4 = y3 - y4;
        double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
        if (det1Less2And3Less4 == 0){
            // the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
            return null;
        }
        double x = (det(det1And2, x1LessX2,
                det3And4, x3LessX4) /
                det1Less2And3Less4);
        double y = (det(det1And2, y1LessY2,
                det3And4, y3LessY4) /
                det1Less2And3Less4);
        return new Point2D.Double(x, y);
    }

    public static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    public static Point2D reflect(Line2D l, Point2D p) {
        if (l.getX1() == l.getX2()) {
            return new Point2D.Double(l.getX1() - (p.getX() - l.getX1()), p.getY());
        } else if (l.getY1() == l.getY2()) {
            return new Point2D.Double(p.getX(), l.getY1() - (p.getY() - l.getY1()));
        } else {
            double a = (l.getY2() - l.getY1()) / (l.getX2() - l.getX1());
            double c = l.getY1() - a*l.getX1();
            double d = (p.getX() + (p.getY() - c)*a)/(1 + a*a);
            double x = 2*d - p.getX();
            double y = 2*d*a - p.getY() + 2*c;
            return new Point2D.Double(x,y);
        }

        /*
        Given (x,y) and a line y = ax + c we want the point (x', y') reflected on the line.

Set d:= (x + (y - c)*a)/(1 + a^2)

Then x' = 2*d - x

and y' = 2*d*a - y + 2c
         */
    }

}