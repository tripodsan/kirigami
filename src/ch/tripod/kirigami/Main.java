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
                JFrame f = new JFrame("Kirigami");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                CutArea cutArea = new CutArea();
                Toolbar tb = new Toolbar(cutArea);
                cutArea.setSize(800, 800);
                f.add(tb, BorderLayout.NORTH);
                f.add(new JScrollPane(cutArea), BorderLayout.CENTER);
                //f.getRootPane().setDefaultButton(gp.control.defaultButton);
                f.pack();
                f.setSize(800, 800);
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }

}