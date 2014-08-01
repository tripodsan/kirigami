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

/*************************************************************************
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
 **************************************************************************/

package ch.tripod.kirigami;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

/**
 * <code>CutArea</code>...
 */
public class CutArea extends JPanel {

    List<Cut> lines = new ArrayList<Cut>();
    Cut line;
    int selectedPoint;

    boolean drawMode;

    Fold paper;

    List<Fold> pieces = new ArrayList<Fold>();

    List<Point2D> points = new ArrayList<Point2D>();

    List<Point2D> holes = new ArrayList<Point2D>();

    public CutArea() {
        MouseHandler m = new MouseHandler();
        this.addMouseListener(m);
        this.addMouseMotionListener(m);

        Point2D p0 = new Point2D.Double(-300, -300);
        Point2D p1 = new Point2D.Double(-300,  300);
        Point2D p2 = new Point2D.Double( 300,  300);
        Point2D p3 = new Point2D.Double( 300, -300);
        paper = new Fold(0, true)
                .addEdge(new Line2D.Double(p0, p1))
                .addEdge(new Line2D.Double(p1, p2))
                .addEdge(new Line2D.Double(p2, p3))
                .addEdge(new Line2D.Double(p3, p0));
        paper.setBorderColor(Color.orange);
        calc();
    }

    public void clear() {
        pieces.clear();
        lines.clear();
        points.clear();
        calc();
        repaint();
    }

    private void calc() {
        pieces.clear();
        pieces.add(paper);

        int degree = 1;
        for (Cut l: lines) {
            l.getIntersections().clear();
            Line2D line2D = l.getLine2D();
            List<Fold> nextPieces = new ArrayList<Fold>();
            for (Fold f: pieces) {
                nextPieces.addAll(f.cut(degree, l, line2D));
            }
            pieces = nextPieces;
            degree++;
        }

        // sort by z index
        Collections.sort(pieces,new Comparator<Fold>() {
            public int compare(Fold fold, Fold fold2) {
                return fold.getZ() - fold2.getZ();
            }
        });

        holes.clear();
        for (Point2D p: points) {
            for (Fold f: pieces) {
                // check if point is inside fold
                if (f.getPath().contains(p)) {
                    Point2D q = p;
                    for (Line2D c: f.getTrail()) {
                        q = Utils.reflect(c, q);
                    }
                    holes.add(q);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g;


        Dimension size = getSize();
        int dx = size.width / 2;
        int dy = size.height / 2;
        g2d.translate(dx, dy);

        // draw paper
        paper.draw(g2d);

        // draw folded pieces
        g2d.setColor(Color.black);
        for (Fold f: pieces) {
            f.draw(g2d);
        }

        for (Cut l : lines) {
            l.draw(g2d, getSize());
        }

        g2d.setColor(Color.red);
        for (Point2D p: holes) {
            g2d.drawOval((int)p.getX()-2, (int) p.getY()-2, 4,4);
        }
    }

    private class MouseHandler extends MouseAdapter {

        private Point map(Point p) {
            Dimension size = getSize();
            int dx = size.width / 2;
            int dy = size.height / 2;
            p.translate(-dx, -dy);
            return p;
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            Point p = map(mouseEvent.getPoint());
            Cut newLine = null;
            int newPoint = -1;
            for (Cut l: lines) {
                int idx = l.getHitPointIdx(p);
                if (idx >= 0) {
                    newLine = l;
                    newPoint = idx;
                    l.setSelectedPoint(idx);
                } else {
                    l.setSelectedPoint(-1);
                }
            }
            if (newLine != line) {
                line = newLine;
                repaint();
            }
            if (newPoint != selectedPoint) {
                selectedPoint = newPoint;
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (drawMode) {
                points.add(map(mouseEvent.getPoint()));
                calc();
                repaint();
            } else {
                if (line == null) {
                    line = new Cut(lines.size(), map(mouseEvent.getPoint()));
                    selectedPoint = 1;
                    lines.add(line);
                    repaint();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (line != null) {
                line.setPoint(selectedPoint, map(mouseEvent.getPoint()));
                line = null;
                calc();
                repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            if (line != null) {
                line.setPoint(selectedPoint, map(mouseEvent.getPoint()));
                calc();
                repaint();
            } else if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                points.add(map(mouseEvent.getPoint()));
                calc();
                repaint();
            }
        }
    }

    public Action ACTION_NEW = new AbstractAction("New") {

        public void actionPerformed(ActionEvent actionEvent) {
            clear();
        }
    };

    public void setDrawMode(boolean mode) {
        this.drawMode = mode;
    }

}