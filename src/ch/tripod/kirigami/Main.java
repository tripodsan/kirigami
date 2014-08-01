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