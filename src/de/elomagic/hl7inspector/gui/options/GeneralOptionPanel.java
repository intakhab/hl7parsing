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

package de.elomagic.hl7inspector.gui.options;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.elomagic.hl7inspector.StartupProperties;
import de.elomagic.hl7inspector.gui.AbstractPanel;
import de.elomagic.hl7inspector.gui.GradientLabel;
import de.elomagic.hl7inspector.gui.PanelDialog;
import de.elomagic.hl7inspector.images.ResourceLoader;
import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
//import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * 
 * @author rambow
 */
public class GeneralOptionPanel extends AbstractPanel {
    
    /** Creates a new instance of GeneralOptionPane */
    public GeneralOptionPanel(PanelDialog d) { super(d); }
    
    public void init() { 
        cbOneInstance       = new JCheckBox();
        cbDesktopImage      = new JCheckBox();
        
        Vector<String> lf = new Vector<String>();
        for (int i=0;i<UIManager.getInstalledLookAndFeels().length;i++) {
            lf.add(UIManager.getInstalledLookAndFeels()[i].getName());
        }
        cbLookFeel = new JComboBox(lf) ;
        cbLookFeel.setEditable(false);
        cbLookFeel.setSelectedItem(UIManager.getLookAndFeel().getName());
        
        rbNoProxy           = new JRadioButton(new ProxyModeAction("No Proxy", "NO_PROXY"));
        rbSystemProxy       = new JRadioButton(new ProxyModeAction("Use System Proxy Settings", "SYS_PROXY"));
        rbHttpProxy         = new JRadioButton(new ProxyModeAction("HTTP Proxy", "USE_PROXY"));
        ButtonGroup btnGrp  = new ButtonGroup();
        btnGrp.add(rbNoProxy);
        btnGrp.add(rbSystemProxy);
        btnGrp.add(rbHttpProxy);
        
        cbCheckPeriod       = new JComboBox(new String[]{ "Never", "Every Startup", "Every Day", "Every Week", "Every Two Week", "Every Month"} ) ;
        cbCheckPeriod.setEditable(false);
        editProxyHost       = new JTextField();
        editProxyPort       = new JTextField();
        
        cbAskBeforeCheck    = new JCheckBox();
        
       FormLayout layout = new FormLayout(
        "8dlu, p, 4dlu, p:grow",
//            "8dlu, left:max(40dlu;p), 75dlu, 75dlu, 7dlu, right:p, 4dlu, 75dlu",
                "p, 3dlu, p, 3dlu, p, 3dlu, " +
                "p, 7dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, " + 
                "p, 7dlu, p, 3dlu, p, 3dlu, p");   // rows
        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();
        
        // 1st row
        builder.add(new GradientLabel("Application"),   cc.xyw(1,  1, 4));

        builder.addLabel("One Instance:",               cc.xy(2,   3));      // Ok
        builder.add(cbOneInstance,                      cc.xy(4,   3));

        builder.addLabel("Desktop Image:",              cc.xy(2,   5));      // Ok
        builder.add(cbDesktopImage,                     cc.xy(4,   5));
        
        builder.addLabel("Look & Feel:",                cc.xy(2,   7));      // Ok
        builder.add(cbLookFeel,                         cc.xy(4,   7));        
        
        builder.add(new GradientLabel("Proxy"),         cc.xyw(1,  9, 4));        // Ok

        builder.addLabel("Web Proxy",                   cc.xy(2,   11));        // Ok
        builder.add(rbNoProxy,                          cc.xy(4,   11));        // Ok

        builder.add(rbSystemProxy,                      cc.xy(4,   13));        // Ok
        
        builder.add(rbHttpProxy,                        cc.xy(4,   15));        // Ok
        
        builder.addLabel("Proxy Host:",                 cc.xy(2,   17));        // Ok
        builder.add(editProxyHost,                      cc.xy(4,   17));        // Ok
        
        builder.addLabel("Proxy Port:",                 cc.xy(2,   19));        // Ok
        builder.add(editProxyPort,                      cc.xy(4,   19));        // Ok
       
        builder.add(new GradientLabel("Auto Update"),   cc.xyw(1,  21, 4));
        
        builder.addLabel("Check Period:",               cc.xy(2,   23));
        builder.add(cbCheckPeriod,                      cc.xy(4,   23));

        builder.addLabel("Ask Before Check:",           cc.xy(2,   25));
        builder.add(cbAskBeforeCheck,                   cc.xy(4,   25));

        add(builder.getPanel(), BorderLayout.CENTER);
    }
    
    public Icon getIcon() { return ResourceLoader.loadImageIcon("preferences-desktop.png", ResourceLoader.LARGE_IMAGE); }    
    
    public String getTitle() { return "General"; }
    
    public String getDescription() { return "General Options"; }        
    
    public void read() {
        StartupProperties p = StartupProperties.getInstance();

        cbOneInstance.setSelected(p.isOneInstance());
        cbDesktopImage.setSelected(p.isDesktopImage());
        
        for (int i=0;i<UIManager.getInstalledLookAndFeels().length;i++) {
            if (p.getLookAndFeel().equals(UIManager.getInstalledLookAndFeels()[i].getClassName())) {
                cbLookFeel.setSelectedItem(UIManager.getInstalledLookAndFeels()[i].getName());
            }
        }
                
        switch (p.getProxyMode()) {
            case 0: { rbNoProxy.setSelected(true); break; }
            case 1: { rbSystemProxy.setSelected(true); break; }
            case 2: { rbHttpProxy.setSelected(true); break; }
            default: { rbNoProxy.setSelected(true); break; }
        }
        editProxyHost.setText(p.getProxyHost());
        editProxyPort.setText(Integer.toString(p.getProxyPort()));                
        updateProxyModeButtons(p.getProxyMode() == 2);
        
        switch (p.getAutoUpdatePeriod()) {
            case 0: { cbCheckPeriod.setSelectedIndex(1); break; }
            case 1: { cbCheckPeriod.setSelectedIndex(2); break; }
            case 7: { cbCheckPeriod.setSelectedIndex(3); break; }
            case 14: { cbCheckPeriod.setSelectedIndex(4); break; }
            case 30: { cbCheckPeriod.setSelectedIndex(5); break; }            
            default: { cbCheckPeriod.setSelectedIndex(0); break; }
        }        
        cbAskBeforeCheck.setSelected(p.isAutoUpdateAsk());
    }
    
    public void write() {
        StartupProperties p = StartupProperties.getInstance();
        
        p.setOneInstance(cbOneInstance.isSelected());
        p.setDesktopImage(cbDesktopImage.isSelected());        
        
        for (int i=0;i<UIManager.getInstalledLookAndFeels().length;i++) {
            if (cbLookFeel.getSelectedItem().equals(UIManager.getInstalledLookAndFeels()[i].getName())) {
                p.setLookAndFeel(UIManager.getInstalledLookAndFeels()[i].getClassName());
            }
        }
        
        if (rbNoProxy.isSelected())
            p.setProxyMode(0);
        else if (rbSystemProxy.isSelected())
            p.setProxyMode(1);
        else if (rbHttpProxy.isSelected())
            p.setProxyMode(2);
        else 
            p.setProxyMode(0);

        p.setProxyHost(editProxyHost.getText());
        try { p.setProxyPort(Integer.parseInt(editProxyPort.getText())); } catch (Exception e) { p.setProxyPort(0); }
        
        switch (cbCheckPeriod.getSelectedIndex()) {
            case 1: { p.setAutoUpdatePeriod(0); break; }
            case 2: { p.setAutoUpdatePeriod(1); break; }
            case 3: { p.setAutoUpdatePeriod(7); break; }
            case 4: { p.setAutoUpdatePeriod(14); break; }
            case 5: { p.setAutoUpdatePeriod(30); break; }            
            default: { p.setAutoUpdatePeriod(-1); break; }
        }             
        p.setAutoUpdateAsk(cbAskBeforeCheck.isSelected());        
    }
    
    class ProxyModeAction extends AbstractAction {
        public ProxyModeAction(String name, String cmd) {
            super(name);
            
            putValue(AbstractAction.ACTION_COMMAND_KEY, cmd);
        }
        
        public void actionPerformed(java.awt.event.ActionEvent e) {
            boolean b = e.getActionCommand().equals("USE_PROXY");
            updateProxyModeButtons(b);
            
        }
    }    
    
    private void updateProxyModeButtons(boolean enabled) {
        editProxyHost.setEnabled(enabled);
        editProxyPort.setEnabled(enabled);
    }
    
    private JCheckBox cbOneInstance;
    private JCheckBox cbDesktopImage;
    private JComboBox cbLookFeel;
    private JRadioButton rbNoProxy;
    private JRadioButton rbSystemProxy;
    private JRadioButton rbHttpProxy;
    private JTextField editProxyHost;
    private JTextField editProxyPort;
    private JComboBox cbCheckPeriod;
    private JCheckBox cbAskBeforeCheck;    
}
