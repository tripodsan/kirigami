/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright ${today.year} Adobe Systems Incorporated
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

import javax.swing.*;

/**
 * <code>Ruler</code>...
 */
public class Ruler extends JPanel {

    public static final int SIZE = 30;

    private boolean horizontal;

    public Ruler setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        if (horizontal) {
            setPreferredSize(new Dimension(0, SIZE));
        } else {
            setPreferredSize(new Dimension(SIZE, 0));
        }
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        g.setColor(Color.BLACK);
        g.drawString(horizontal ? "horizontal" : "vertical", 0, 0);
        if (horizontal) {
            g.drawLine(SIZE-20, SIZE-1, getWidth(), SIZE-1);
            for (int i=0,x; (x = i*10 + SIZE-1) < getWidth(); i++) {
                int h = i % 5 == 0 ? 20 : 10;
                g.drawLine(x, SIZE-h, x, SIZE-1);
            }
        } else {
            g.drawLine(SIZE-1, 0, SIZE-1, getHeight());
            for (int i=0,y; (y = i*10 - 1) < getHeight(); i++) {
                int w = i % 5 == 0 ? 20 : 10;
                g.drawLine(SIZE-w, y, SIZE-1, y);
            }
        }
    }
}