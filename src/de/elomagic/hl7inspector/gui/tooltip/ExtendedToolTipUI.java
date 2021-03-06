/*
 * Copyright 2006 Carsten Rambow
 * 
 * Licensed under the GNU Public License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.gnu.org/licenses/gpl.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.elomagic.hl7inspector.gui.tooltip;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalToolTipUI;
import org.apache.log4j.Logger;

/**
 *
 * @author rambow
 */
public class ExtendedToolTipUI extends MetalToolTipUI {        
    private String[] strs;
    private int maxWidth = 0;
    
    public void paint(Graphics g, JComponent c) {                
        FontMetrics metrics = g.getFontMetrics(); //Toolkit.getDefaultToolkit().getFontMetrics(g.getFont());
        Dimension size = c.getSize();
        g.setColor(SystemColor.info);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(SystemColor.infoText);
        if (strs != null) {
            for (int i=0;i<strs.length;i++) {
                g.drawString(strs[i], 3, (metrics.getHeight()) * (i+1));
            }
        }
    }
    
    public Dimension getPreferredSize(JComponent c) {
        FontMetrics metrics = c.getFontMetrics(c.getFont());// Toolkit.getDefaultToolkit().getFontMetrics(c.getFont());
        String tipText = ((JToolTip)c).getTipText();
        if (tipText == null) {
            tipText = "";
        }
        BufferedReader br = new BufferedReader(new StringReader(tipText));
        String line;
        int maxWidth = 0;
        Vector<String> v = new Vector<String>();
        try {
            while ((line = br.readLine()) != null) {
                int width = SwingUtilities.computeStringWidth(metrics, line);
                maxWidth = (maxWidth < width) ? width : maxWidth;
                v.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(getClass()).error(ex.getMessage(), ex);
        }
        
        int lines = v.size();
        if (lines < 1) {
            strs = null;
            lines = 1;
        } else {
            strs = new String[lines];
            int i=0;
            
            for (Enumeration e = v.elements(); e.hasMoreElements() ;i++) {
                strs[i] = (String)e.nextElement();
            }
        }
        int height = metrics.getHeight() * lines;
        this.maxWidth = maxWidth;
        return new Dimension(maxWidth + 6, height + 4);
    }
}