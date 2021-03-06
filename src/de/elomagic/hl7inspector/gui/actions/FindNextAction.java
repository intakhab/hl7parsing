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
package de.elomagic.hl7inspector.gui.actions;

import de.elomagic.hl7inspector.gui.Desktop;
import de.elomagic.hl7inspector.gui.FindBar;
import de.elomagic.hl7inspector.gui.SimpleDialog;
import de.elomagic.hl7inspector.images.ResourceLoader;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

/**
 *
 * @author rambow
 */
public class FindNextAction extends AbstractAction {
    
    /** Creates a new instance of ExitAction */
    public FindNextAction() {
        super("Find next", ResourceLoader.loadImageIcon("go-next.png"));
        
        putValue(SHORT_DESCRIPTION, "Find next occurrences of the phrase ");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
    }
    
    public void actionPerformed(ActionEvent e) {
        Desktop d = Desktop.getInstance();
        FindBar f = FindBar.getInstance();
                
        if (d.getTree().getModel().getRoot() != null) {
            if ((f.getEscapedPhrase().length() != 0) && (d.getTree().getModel().getChildCount(d.getTree().getModel().getRoot()) != 0)) {
                
                // TODO Find phrase: Stops after the first hit.
                TreePath path = d.getTree().findNode(f.getEscapedPhrase()); 
                if (path == null) 
                    SimpleDialog.info("The end of message tree reached.");
                else {                
                    TreePath p = new TreePath(d.getTree().getModel());
                    Object[] o = path.getPath();
                    for (int i=0; i < o.length-1; i++)
                        p = p.pathByAddingChild(o[i]);

                    d.getTree().expandPath(p);
                    d.getTree().scrollPathToVisible(p);
                    d.getTree().setSelectionPath(p);
                }
            }/* else {
                //
            }*/
        }
    }
    
}
