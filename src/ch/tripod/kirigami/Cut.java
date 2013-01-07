/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2013 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package ch.tripod.kirigami;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
* <code>Cut</code>...
*/
public class Cut {

    private final int id;

    private Point p[];

    private java.util.List<Point2D> intersections = new ArrayList<Point2D>();

    private int selectedPoint = -1;

    Cut(int id, Point p0) {
        this.id = id;
        p = new Point[]{p0, p0};
    }

    public int getId() {
        return id;
    }

    public Line2D getLine2D() {
        return new Line2D.Double(p[0].x, p[0].y, p[1].x, p[1].y);
    }

    public void setSelectedPoint(int selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    public List<Point2D> getIntersections() {
        return intersections;
    }

    public void setPoint(int idx, Point p) {
        this.p[idx] = p;
    }

    public int getHitPointIdx(Point2D pt) {
        if (pt.distanceSq(p[0]) < 25) {
            return 0;
        } else if (pt.distanceSq(p[1]) < 25) {
            return 1;
        } else {
            return -1;
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i=0; i<p.length; i++) {
            if (selectedPoint == i) {
                g2d.setColor(Color.red);
            } else {
                g2d.setColor(Color.black);
            }
            g2d.drawOval(p[i].x-5, p[i].y-5, 10, 10);
        }
        float[] dashPattern = { 30, 10, 10, 10 };
        Stroke s = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dashPattern, 0));
        g2d.setColor(Color.gray);
        g2d.drawLine(p[0].x,p[0].y,p[1].x,p[1].y);
        g2d.setStroke(s);
        for (Point2D p: intersections) {
            g2d.setColor(Color.gray);
            g2d.drawOval((int) p.getX()-4, (int) p.getY()-4, 8, 8);
        }
    }

}