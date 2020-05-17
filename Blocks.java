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
    int tileClickCount = 0, firstTile, secondTile, score = 0;
    Color themeColor = Color.CYAN;
    JFrame gameFrame = new JFrame("Match Blocks");
    JPanel buttonPanel = new JPanel(new GridLayout(4, 5));
    ArrayList<JButton> buttonList = new ArrayList<JButton>();
    ArrayList<ImageIcon> imageList = new ArrayList<ImageIcon>();
    JComboBox<String> themeSelector = new JComboBox<>(themeList);
    JButton colorChooser = new JButton("Change Color");
    JButton resetButton = new JButton("New game");

    void createButtons() {
        for (int count = 0; count < 20; ++count) {
            final JButton tempButton = new JButton();
            tempButton.setBackground(themeColor);
            buttonList.add(tempButton);

            tempButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int currentTileIndex = buttonList.indexOf(evt.getSource());
                    blockClickedAction(currentTileIndex);
                }

            });
            buttonPanel.add(tempButton);
        }
    }

    void initiateFrame() {
        final ImageIcon appIcon = new ImageIcon("./images/icon.png");
        gameFrame.setIconImage(appIcon.getImage());
        gameFrame.setLocationByPlatform(true);
        gameFrame.setSize(650, 600);
        gameFrame.setResizable(false);
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.add(themeSelector);
        gameFrame.add(resetButton);
        gameFrame.add(colorChooser);
        gameFrame.add(buttonPanel);
        themeSelector.setBounds(75, 10, 120, 25);
        resetButton.setBounds(250, 10, 110, 25);
        colorChooser.setBounds(425, 10, 150, 25);
        buttonPanel.setBounds(0, 45, 650, 520);
        resetButton.setBackground(themeColor);
        colorChooser.setBackground(themeColor);
        themeSelector.setBackground(themeColor);
    }

    void setTheme() {
        imageList.clear();
        final String theme = (String) themeSelector.getSelectedItem();
        for (int index = 0; index < 10; index++) {
            final String currentImagePath = "./images/" + theme + "/image" + Integer.toString(index) + ".png";
            final ImageIcon currentImage = new ImageIcon(currentImagePath);
            imageList.add(currentImage);
            imageList.add(currentImage);
        }
        Collections.shuffle(imageList);
    }

    void resetGame() {
        tileClickCount = 0;
        score = 0;
        buttonList.clear();
        gameFrame.dispose();
        new Blocks().setGame();
    }

    void setButtonImageOnClick() {
        for (JButton tempButton : buttonList) {
            tempButton.setIcon(null);
        }
        if (tileClickCount == 1) {
            buttonList.get(firstTile).setIcon(imageList.get(firstTile));
        }
        buttonList.get(secondTile).setIcon(imageList.get(secondTile));
    }

    void secondClickTreat() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                buttonList.get(firstTile).setIcon(null);
                buttonList.get(secondTile).setIcon(null);
                if (isPairFound()) {
                    score++;
                    setPairedButtonsOff();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    boolean isPairFound() {
        return imageList.get(secondTile) == imageList.get(firstTile);
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

    void setPairedButtonsOff() {
        buttonList.get(firstTile).setVisible(false);
        buttonList.get(secondTile).setVisible(false);
    }

    void blockClickedAction(int currentTileIndex) {
        if (tileClickCount == 0) {
            firstTile = currentTileIndex;
        }
        if (firstTile == secondTile) {
            tileClickCount = 0;
        }
        setButtonImageOnClick();
        secondTile = currentTileIndex;
        if (tileClickCount == 1) {
            secondClickTreat();
        }
        tileClickCount = (tileClickCount + 1) % 2;
        if (score == 10) {
            JOptionPane.showMessageDialog(gameFrame, "**VICTORY**");
            resetGame();
        }
    }

    void gamePlay() {

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
                    buttonList.get(firstTile).setIcon(null);
                    tileClickCount = 0;
                } else {
                    JOptionPane.showMessageDialog(gameFrame, "Game Has Already started!", "Stop",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    void setGame() {
        createButtons();
        initiateFrame();
        setTheme();
        gamePlay();
    }

    public static void main(final String arg[]) {
        new Blocks().setGame();
    }
}