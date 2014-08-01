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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <code>Fold</code>...
 */
public class Fold {

    private List<Line2D> edges = new ArrayList<Line2D>();

    private Path2D path;

    private Color c = Color.white;

    private boolean front = true;

    private int z;

    private double ax = 0;

    private double ay = 0;

    private LinkedList<Line2D> trail = new LinkedList<Line2D>();
    private Color borderColor = Color.black;

    public Fold(int z, boolean front) {
        this.z = z;
        this.front = front;
    }

    public int getZ() {
        return z;
    }

    public Fold addEdge(Line2D edge) {
        edges.add(edge);
        ax += edge.getX1();
        ay += edge.getY1();
        path = null;
        return this;
    }

    public Point2D getCenter() {
        return new Point2D.Double(ax / edges.size(), ay / edges.size());
    }

    public void clear() {
        edges.clear();
        ax = ay = 0;
        path = null;
    }

    public List<Line2D> getTrail() {
        return trail;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public Path2D getPath() {
        if (path == null) {
            path = new GeneralPath();
            Iterator<Line2D> iter = edges.iterator();
            boolean first = true;
            while (iter.hasNext()) {
                Line2D line = iter.next();
                if (first) {
                    path.moveTo(line.getX1(), line.getY1());
                    first = false;
                }
                if (iter.hasNext()) {
                    path.lineTo(line.getX2(), line.getY2());
                } else {
                    path.closePath();
                }
            }
        }
        return path;
    }

    Collection<Fold> cut(int degree, Cut c, Line2D l) {
        int numFolds = 1 << degree;

        // quickly count if we cut through completely. otherwise ignore this cut
        int count = 0;
        for (Line2D e: edges) {
            if (l.intersectsLine(e)) {
                count++;
            }
        }

        List<Fold> ret = new ArrayList<Fold>();
        if (count > 0 && count % 2 == 0) {
            // build new folds
            Fold prev = null;
            Point2D pl = null;
            Fold fold = new Fold(z, front);
            ret.add(fold);

            for (Line2D e: edges) {
                if (l.intersectsLine(e)) {
                    Point2D pi = Utils.getLineLineIntersection(l, e);
                    Point2D p1 = e.getP1();
                    Point2D p2 = e.getP2();
                    c.getIntersections().add(pi);

                    if (prev == null) {
                        prev = fold;
                        pl = pi;
                        fold.addEdge(new Line2D.Double(p1, pi));

                        fold = new Fold(z, front);
                        fold.addEdge(new Line2D.Double(pi, p2));
                        ret.add(fold);
                    } else {
                        fold.addEdge(new Line2D.Double(p1, pi));
                        fold.addEdge(new Line2D.Double(pi, pl));
                        // restore prev
                        fold = prev;
                        fold.addEdge(new Line2D.Double(pl, pi));
                        fold.addEdge(new Line2D.Double(pi, p2));
                        prev = null;
                    }
                } else {
                    fold.addEdge(e);
                }
            }
            // now reflect every other fold
            for (Fold f: ret) {
                f.trail.addAll(trail);
                if (l.relativeCCW(f.getCenter()) > 0) {
                    f.mirror(l);
                    f.z = numFolds - 1 - f.z;
                    f.trail.addFirst(l);
                }
            }
        } else {
            if (count == 0) {
                // check if we need to flip
                if (l.relativeCCW(this.getCenter()) > 0) {
                    Fold fold = new Fold(z, front);
                    fold.edges.addAll(edges);
                    fold.mirror(l);
                    fold.z = numFolds - 1 - fold.z;
                    fold.trail.addAll(trail);
                    fold.trail.addFirst(l);
                    ret.add(fold);
                }
            } else {
                ret.add(this);
            }
        }

        return ret;
    }

    public void mirror(Line2D line) {
        Point2D[] pts = new Point2D[edges.size()];
        int i=0;
        for (Line2D e: edges) {
            pts[i++] = Utils.reflect(line, e.getP1());
        }
        clear();
        for (i=0;i<pts.length;i++) {
            addEdge(new Line2D.Double(pts[i], pts[(i + 1) % pts.length]));
        }
        front = !front;
    }

    public void draw(Graphics2D g2) {
        Path2D p = getPath();
        if (front) {
            g2.setColor(new Color(255,255,255,128));
        } else {
            g2.setColor(new Color(200,200,200,128));
        }
        g2.fill(p);
        g2.setColor(borderColor);
        g2.draw(p);

        String str = String.format("%d", z);
        Line2D e = edges.get(0);
        g2.drawString(str, (int) e.getX1(), (int) e.getY1());

    }

    public void drawDebug(Graphics2D g2) {
        double ax=0,ay=0;
        int count = 0;
        for (Line2D e: edges) {
            ax+= e.getX1();
            ay+= e.getY1();
            count++;
        }
        ax/=count;
        ay/=count;
//        Path2D p = getPath();
//        g2.setColor(c);
//        g2.fill(p);
//        g2.setColor(Color.black);
//        g2.draw(p);

        AffineTransform at = g2.getTransform();

        g2.translate(ax, ay);
        g2.scale(0.8, 0.8);
        g2.setColor(Color.black);
        count = 0;
        for (Line2D e: edges) {
//            String str = String.format("%d", count);
//            g2.drawString(str, (int) e.getX1(), (int) e.getY1());
            Point2D p1 = e.getP1();
            Point2D p2 = e.getP2();
            g2.drawLine((int) (p1.getX() - ax), (int) (p1.getY() - ay), (int) (p2.getX() - ax), (int) (p2.getY() - ay));
            count++;
        }
        g2.setTransform(at);
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}