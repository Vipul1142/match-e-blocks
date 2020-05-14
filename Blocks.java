import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Blocks extends JFrame {
    private static final long serialVersionUID = 1L;
    String themeList[] = { "Default", "Ben", "Cartoons", "DC", "Marvel", "Pokemon", "Programming", "TechBrands" };
    int tileClickCount = 0, tileToMatch, currentClickedBlock, score = 0;
    Color themeColor = Color.CYAN;
    JFrame mainWindow = new JFrame("New Game");
    JPanel buttonPanel = new JPanel(new GridLayout(4, 5));
    ArrayList<JButton> buttonList = new ArrayList<JButton>();
    ArrayList<ImageIcon> imageList = new ArrayList<ImageIcon>();
    JComboBox<String> themeSelector = new JComboBox<>(themeList);
    JButton colorChooser = new JButton("Change Color");
    JButton resetButton = new JButton("New game");

    void createButtons() {
        for (int count = 0; count < 20; ++count) {
            final JButton button = new JButton();
            button.setBackground(themeColor);
            buttonList.add(button);

            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    currentClickedBlock = buttonList.indexOf(evt.getSource());
                    gamePlay(currentClickedBlock);
                }

            });
            buttonPanel.add(button);
        }
    }

    void initiateFrame() {
        final ImageIcon appIcon = new ImageIcon("./images/icon.png");
        mainWindow.setIconImage(appIcon.getImage());
        mainWindow.setLocationByPlatform(true);
        mainWindow.setSize(650, 600);
        mainWindow.setResizable(false);
        mainWindow.setLayout(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        themeSelector.setBounds(75, 10, 120, 25);
        resetButton.setBounds(250, 10, 110, 25);
        colorChooser.setBounds(425, 10, 150, 25);
        buttonPanel.setBounds(0, 45, 650, 520);
        resetButton.setBackground(themeColor);
        colorChooser.setBackground(themeColor);
        themeSelector.setBackground(themeColor);
        mainWindow.add(themeSelector);
        mainWindow.add(resetButton);
        mainWindow.add(colorChooser);
        mainWindow.add(buttonPanel);
    }

    void setTheme() {
        final String theme = (String) themeSelector.getSelectedItem();
        imageList.clear();
        for (int index = 0; index < 10; index++) {
            final String imagePath = "./images/" + theme + "/image" + Integer.toString(index) + ".png";
            final ImageIcon image = new ImageIcon(imagePath);
            imageList.add(image);
            imageList.add(image);
        }
        Collections.shuffle(imageList);
    }

    void resetGame() {
        tileClickCount = 0;
        score = 0;
        buttonList.clear();
        imageList.clear();
        mainWindow.dispose();
        new Blocks().setGame();
    }

    void setButtonImage() {
        for (JButton button : buttonList) {
            button.setIcon(null);
        }
        if (tileClickCount == 1) {
            buttonList.get(tileToMatch).setIcon(imageList.get(tileToMatch));
        }
        buttonList.get(currentClickedBlock).setIcon(imageList.get(currentClickedBlock));
    }

    void secondClickTreat() {
        Timer timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                buttonList.get(tileToMatch).setIcon(null);
                buttonList.get(currentClickedBlock).setIcon(null);
                if (isPairFound()) {
                    score++;
                    setButtonsOff();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    boolean isPairFound() {
        return imageList.get(currentClickedBlock) == imageList.get(tileToMatch);
    }

    void setColor() {
        Color selectedColor = JColorChooser.showDialog(this, "Choose Color", themeColor);
        if (selectedColor != null) {
            themeColor = selectedColor;
        }
        for (int index = 0; index < 20; ++index) {
            buttonList.get(index).setBackground(themeColor);
        }
        resetButton.setBackground(themeColor);
        colorChooser.setBackground(themeColor);
        themeSelector.setBackground(themeColor);
    }

    void setButtonsOff() {
        buttonList.get(tileToMatch).setVisible(false);
        buttonList.get(currentClickedBlock).setVisible(false);
    }

    void gamePlay(int currentClickedBlock) {
        if (tileClickCount == 0) {
            tileToMatch = currentClickedBlock;
        }
        if (tileToMatch == currentClickedBlock) {
            tileClickCount = 0;
        }
        setButtonImage();
        if (tileClickCount == 1) {
            secondClickTreat();
        }
        tileClickCount = (tileClickCount + 1) % 2;
        if (score == 10) {
            JOptionPane.showMessageDialog(mainWindow, "**VICTORY**");
            resetGame();
        }
    }

    void startGame() {

        resetButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                resetGame();
            }

        });
        colorChooser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                setColor();
            }

        });
        themeSelector.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (score == 0) {
                    setTheme();
                    buttonList.get(tileToMatch).setIcon(null);
                    tileClickCount = 0;
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Game Has Already started!", "Stop",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    void setGame() {
        createButtons();
        initiateFrame();
        setTheme();
        startGame();
    }

    /*
     * public void actionPActionEvent event) { if (event.getSource() == resetButton)
     * { resetGame(); } else if (event.getSource() == colorChooser) { setColor(); }
     * else if (event.getSource() == themeSelector) { if (score == 0) { setTheme();
     * } else { JOptionPane.showMessageDialog(mainWindow,
     * "Game Has Already started!", "Stop", JOptionPane.ERROR_MESSAGE); } } else {
     * currentClickedBlock = buttonList.indexOf(event.getSource());
     * gamePlay(currentClickedBlock); } }
     */
    public static void main(final String arg[]) {
        new Blocks().setGame();
    }
}