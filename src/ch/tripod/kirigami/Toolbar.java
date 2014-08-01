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
