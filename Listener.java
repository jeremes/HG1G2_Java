package AsteroidPhaseCurveAnalyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author akke
 */
public class Listener implements ActionListener {

    private File anna;
    private JTextArea kentta;
    private JTextArea kentta2;

    public Listener(JTextArea kentta, JTextArea kentta2) {
        this.kentta = kentta;
        this.kentta2 = kentta2;
    }

    public Listener(File file, int vakio) {
        this.anna = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            JFrame newFrame = new JFrame("File:");
            JFileChooser valitsija = new JFileChooser();
            newFrame.getContentPane().add(valitsija);
            
            int valinta = valitsija.showOpenDialog(newFrame);
            if (valinta == JFileChooser.APPROVE_OPTION) {
                   this.anna = valitsija.getSelectedFile();
            try {
                kentta.setText(HG1G2.HG1G2Function(anna));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                kentta2.setText(HG12.HG12Function(anna));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
                   
            } else if (valinta == JFileChooser.CANCEL_OPTION) {
            }
    }
}