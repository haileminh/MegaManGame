package com.hailm.megaman.view;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel implements SetUp{
    public BasePanel() {
       initComponents();
       addComponents();
       registerListener();
    }
}
