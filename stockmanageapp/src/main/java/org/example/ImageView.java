package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageView {

    static JFrame j = new JFrame("Images");
    static File file;
    static String imagePath = "http://localhost:8080/uploads/"; // change if needed

    public static void main(String[] args) {
        JButton jb = new JButton("Select File");
        jb.setBounds(10, 130, 150, 30);

        try {
            JLabel lb = new JLabel();
            URL imageUrl = new URL(imagePath + "dog.png");
            ImageIcon imgIc = new ImageIcon(imageUrl);

            if (imgIc.getIconWidth() <= -1) {
                lb.setText("image not found here..");
            } else {
                Image img = imgIc.getImage();
                Image resImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lb.setIcon(new ImageIcon(resImg));
            }

            lb.setBounds(10, 10, 500, 100);
            j.add(lb);
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }

        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMenu();
            }
        });

        j.add(jb);
        j.setSize(800, 500);
        j.setLayout(null);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    static void showMenu() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Only Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int res = fileChooser.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            ApiHandler.SaveImageFile(file);
        }
    }
}
