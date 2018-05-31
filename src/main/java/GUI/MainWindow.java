package GUI;

import Validation.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ксения on 28.03.2018.
 */

public class MainWindow extends JFrame {

    final int Width = 600;
    final int Height = 400;


    public MainWindow() {

        super();
        setSize(Width, Height);
        setTitle("UML Class Diagram Validation");
        JPanel panel = new JPanel();
        panel.setVisible(true);
        add(panel);
        JTextArea report = new JTextArea();
        report.setVisible(true);
        report.setEditable(false);
        panel.add(report);
        JMenuBar menu = new JMenuBar();

/*        JMenuItem load = new JMenuItem("Select diagram");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setVisible(true);
                fileChooser.setDialogType(0);

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int val = fileChooser.showOpenDialog((Component) e.getSource());
                if (val == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file.getName().toLowerCase().endsWith(".xmi")) {
                        Controller.diagram = file;
                    } else JOptionPane.showMessageDialog(MainWindow.this, "Incorrect file type",
                            "Error", 2);
                }
            }
        });*/

        JMenuItem validate = new JMenuItem("Validate");
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setVisible(true);
                fileChooser.setDialogType(0);

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int val = fileChooser.showOpenDialog((Component) e.getSource());
                if (val == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file.getName().toLowerCase().endsWith(".xmi")) {
                        Controller.diagram = file;
                    } else JOptionPane.showMessageDialog(MainWindow.this, "Incorrect file type",
                            "Error", 2);
                }
                try {
                    for (int i = 0; i < 25; i++) {
                        report.setText(Controller.validate());
                    }
                } catch (Controller.NoDiagramException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(MainWindow.this, "No diagram selected");

                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(MainWindow.this, "Something went wrong. Try again");
                }
            }
        });

        JMenuItem save = new JMenuItem("Save report");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Controller.diagram != null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setVisible(true);
                    fileChooser.setDialogType(1);

                    int val = fileChooser.showOpenDialog((Component) e.getSource());
                    if (val == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try (FileWriter fw = new FileWriter(file)) {
                            fw.write(Controller.stringReport);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(MainWindow.this, "Something went wrong. Try again");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this, "No diagram selected");
                }
            }
        });


//        load.setVisible(true);
        validate.setVisible(true);
        save.setVisible(true);
//        menu.add(load);
        menu.add(validate);
        menu.add(save);
        menu.setVisible(true);

        setJMenuBar(menu);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setVisible(true);

        setLocationRelativeTo(null);
    }
}

