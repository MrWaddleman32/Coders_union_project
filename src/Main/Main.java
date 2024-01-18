package Main;

import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Snowboy Adventures");

            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);
            window.pack();
            gamePanel.setUpGame();

            gamePanel.startGameThread();

            window.setLocationRelativeTo(null);
            window.setVisible(true);
    }

}
