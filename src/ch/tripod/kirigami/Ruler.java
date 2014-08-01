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