package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    volatile static boolean isFrameReady = true;
    public static void main(String[] args) {
        final int screenWight = 1200;
        final int screenHeight = 800;

        JFrame jFrame = new JFrame();
        jFrame.setSize(screenWight, screenHeight);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = new BufferedImage(screenWight, screenHeight,
                BufferedImage.TYPE_INT_ARGB);
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);

        BorderLayout borderLayout = new BorderLayout();
        jFrame.getContentPane().setLayout(borderLayout);
        jFrame.getContentPane().add(label, BorderLayout.CENTER);

        jFrame.setVisible(true);

        Model model = new Model();
        Render render = new Render();

        var lastTime = System.currentTimeMillis();

        while (jFrame.isVisible()){
            var time = System.currentTimeMillis();
            model.update(time-lastTime);
            lastTime = time;

            if (isFrameReady){
                isFrameReady = false;
                render.draw(image, model);

                SwingUtilities.invokeLater(() -> {
                    jFrame.repaint();
                    isFrameReady = true;
                });
            }

        }
    }
}