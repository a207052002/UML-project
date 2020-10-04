package UML;
import UMLManager.*;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JToggleButton;
import javax.swing.border.*;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.JOptionPane;
import Mode.*;

public class App {
    public static void main(final String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
    private static void createAndShowGUI() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(d.getWidth() * 0.8);
        int height = (int)(d.getHeight() * 0.8);

        JFrame f = new JFrame("OOAD UML editor");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(width, height);
        f.setLayout(new GridBagLayout());

        Manager manager = new Manager();
        UMLMenu menu = new UMLMenu(manager, f);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.05;
        c.anchor = GridBagConstraints.NORTH;
        f.add(menu, c);
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = 1;
        c2.gridheight = 1;
        c2.gridwidth = 1;
        c2.weightx = 0.93;
        c2.weighty = 0.95;
        c2.insets = new Insets(10,10,10,10);
        c2.fill = GridBagConstraints.BOTH;
        f.add(manager, c2);
        MainGUI mains = new MainGUI(manager);
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 1;
        c3.gridheight = 1;
        c3.gridwidth = 1;
        c3.weightx = 0.07;
        c3.weighty = 0.95;
        c3.insets = new Insets(10,10,10,10);
        c3.fill = GridBagConstraints.BOTH;
        f.add(mains, c3);
        f.setVisible(true);
    } 
}



class MainGUI extends JPanel {
    public MainGUI(Manager manager) {
        _manager = manager;
        setBorder(BorderFactory.createRaisedBevelBorder());
        //setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setLayout((new GridLayout(0, 1, 5, 6)));
        _buttonList =  new HashMap<Mode, JToggleButton>();
        JToggleButton bt;
        bt = new JToggleButton("Select");
        add(bt);
        _buttonList.put(new Select(manager), bt);
        bt = new JToggleButton("UserCase");
        add(bt);
        _buttonList.put(new UserCase(manager), bt);
        bt = new JToggleButton("Class");
        add(bt);
        _buttonList.put(new UMLClass(manager), bt);
        bt = new JToggleButton("AssLine");
        add(bt);
        _buttonList.put(new AssLining(manager), bt);
        bt = new JToggleButton("CompLine");
        add(bt);
        _buttonList.put(new CompLining(manager), bt);
        bt =new JToggleButton("GeneLine");
        add(bt);
        _buttonList.put(new GeneLining(manager), bt);
        for(Map.Entry<Mode, JToggleButton> map : _buttonList.entrySet()) {
            bt = map.getValue();
            bt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    _manager.setMode(map.getKey());
                    for(Map.Entry<Mode, JToggleButton> imap : _buttonList.entrySet()) {
                        if(imap.getKey() == map.getKey()) {
                            imap.getValue().setSelected(true);
                        } else {
                            imap.getValue().setSelected(false);   
                        }
                    }
                }
            });
        }

    }

    Manager _manager;
    HashMap<Mode, JToggleButton> _buttonList;

}

class UMLMenu extends JMenuBar {
    public UMLMenu(Manager manager, JFrame parent) {
        super();
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JMenuItem jm;
        JMenu m = new JMenu("File");
        m.add(new JMenuItem("Open?"));
        m.add(new JMenuItem("Save?"));
        this.add(m);
        m = new JMenu("Edit");

        jm = new JMenuItem("Group");
        m.add(jm);
        jm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _manager.group();
            }
        });

        jm = new JMenuItem("Ungroup");
        m.add(jm);
        jm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _manager.ungroup();
            }
        });

        jm = new JMenuItem("Rename");
        m.add(jm);
        jm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String name = JOptionPane.showInputDialog(parent,
                        "Set object name.", null);
                System.out.println(name);
                if(name != null) {
                    _manager.setObjName(name);
                    _manager.reFresh();
                }
            }
        });

        jm = new JMenuItem("Delete");
        m.add(jm);
        jm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                _manager.objRemove();
                _manager.reFresh();
            }
        });

        this.add(m);
        _manager = manager;
        _parent = parent;
    }

    Manager _manager;
    JFrame _parent;
}