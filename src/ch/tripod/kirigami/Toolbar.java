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

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <code>Toolbar</code>...
 */
public class Toolbar extends JToolBar {

    private JToggleButton draw = new JToggleButton("Draw");

    public Toolbar(final CutArea main) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.lightGray);

        this.add(main.ACTION_NEW);
        this.add(draw);

        draw.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                main.setDrawMode(draw.isSelected());
            }
        });
    }
}
