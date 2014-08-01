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
 * <code>Main</code>...
 */
public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                CutArea cutArea = new CutArea();
                Toolbar tb = new Toolbar(cutArea);
                Ruler hr = new Ruler().setHorizontal(true);
                Ruler vr = new Ruler().setHorizontal(false);
                JPanel content = new JPanel(new BorderLayout());
                content.add(hr, BorderLayout.PAGE_START);
                content.add(vr, BorderLayout.LINE_START);
                content.add(cutArea, BorderLayout.CENTER);


                JFrame f = new JFrame("Kirigami");
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.add(tb, BorderLayout.NORTH);
                f.add(content, BorderLayout.CENTER);

                f.pack();
                f.setSize(800, 800);
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }

}