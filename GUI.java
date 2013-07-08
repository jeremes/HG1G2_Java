
package AsteroidPhaseCurveAnalyzer;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author akke
 */
public class GUI implements Runnable {

    private JFrame frame;

    public GUI() {
    }

    @Override
    public void run() {
        frame = new JFrame("Asteroid Phase Curve Analyzer");
       // frame.setPreferredSize(new Dimension(800, 600));
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
 
        JLabel valinta = new JLabel("HG1G2");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        container.add(valinta, c);
        c.anchor = GridBagConstraints.NORTHWEST;
        
        JLabel valinta2 = new JLabel("HG12");
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        container.add(valinta2, c);
        c.anchor = GridBagConstraints.NORTHEAST;
 
        JTextArea area = new JTextArea(15, 20);
        JScrollPane scroller = new JScrollPane(area);
        area.setEditable(false);
 
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 4;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.SOUTHWEST;
        container.add(scroller, c);
 
        JTextArea area2 = new JTextArea(15,20);
        JScrollPane scroller2 = new JScrollPane(area2);
        area2.setEditable(false);
        
        c.gridx = 3;
        c.gridwidth = 2;
        c.gridy = 4;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.SOUTHEAST;
        container.add(scroller2, c);
        
        Listener axonman = new Listener(area, area2);
        JButton paina = new JButton("Choose a file");
        paina.addActionListener(axonman);
 
        c.gridx = 3;
        c.gridy = 0;
        c.weighty = 0;
        c.weightx = 0;
        c.ipady = 0;
        c.anchor = GridBagConstraints.EAST;
        container.add(paina, c);
    }
}