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

//    private Action newNode = new De("New");
//    private Action clearAll = new ClearAction("Clear");
//    private Action kind = new KindComboAction("Kind");
//    private Action color = new ColorAction("Color");
//    private Action connect = new ConnectAction("Connect");
//    private Action delete = new DeleteAction("Delete");
//    private Action random = new RandomAction("Random");
    private JButton defaultButton = new JButton("New");
    private JComboBox kindCombo = new JComboBox();
//    private ColorIcon hueIcon = new ColorIcon(Color.blue);
    private JPopupMenu popup = new JPopupMenu();

    public Toolbar(CutArea main) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.lightGray);

        this.add(main.ACTION_NEW);
//        this.add(new JButton("Clear"));
//        this.add(kindCombo);
//        this.add(new JButton(color));
//        this.add(new JLabel(hueIcon));
//        JSpinner js = new JSpinner();
//        js.setModel(new SpinnerNumberModel(35, 5, 100, 5));
//        js.addChangeListener(new ChangeListener() {
//
//            public void stateChanged(ChangeEvent e) {
//                JSpinner s = (JSpinner) e.getSource();
//                radius = (Integer) s.getValue();
//                Node.updateRadius(nodes, radius);
//                GraphPanel.this.repaint();
//            }
//        });
//        this.add(new JLabel("Size:"));
//        this.add(js);
//        this.add(new JButton("random"));

//        popup.add(new JMenuItem(newNode));
//        popup.add(new JMenuItem(color));
//        popup.add(new JMenuItem(connect));
//        popup.add(new JMenuItem(delete));
//        JMenu subMenu = new JMenu("Kind");
//        for (Kind k : Kind.values()) {
//            kindCombo.addItem(k);
//            subMenu.add(new JMenuItem(new KindItemAction(k)));
//        }
//        popup.add(subMenu);
//        kindCombo.addActionListener(kind);
    }

}
