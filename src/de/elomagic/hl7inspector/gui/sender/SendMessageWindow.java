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

package de.elomagic.hl7inspector.gui.sender;

import de.elomagic.hl7inspector.gui.monitor.TraceMonitorDialog;
import java.awt.BorderLayout;

/**
 *
 * @author rambow
 */
public class SendMessageWindow extends TraceMonitorDialog {
    
    /** Creates a new instance of ToolBar */
    private SendMessageWindow() {
        super("Send Message Dialog");
    }
    
    protected void init() {        
        add(new SendPanel(), BorderLayout.CENTER);
    }
    
    public final static SendMessageWindow getInstance() { return instance; } 
    private final static SendMessageWindow instance = new SendMessageWindow();  

}
