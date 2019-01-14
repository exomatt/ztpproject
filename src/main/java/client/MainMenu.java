package client;

import game.LearningGame;
import state.ForeignPolishState;
import state.LanguageState;
import state.PolishForeignState;
import strategy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private final JFrame mainframe;
    private JButton exitButton = new JButton("Exit");
    private JPanel panel1;
    private JButton startButton = new JButton("Start");
    private JButton editButton = new JButton("Edit your words");
    private JButton continueButton = new JButton("Continue");

    public MainMenu() {
        mainframe = new JFrame("Learning languages");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        mainframe.setLocation(10, 10);
        mainframe.setPreferredSize(new Dimension(500, 500));
//        FormLayout formLayout = new FormLayout("left:100px, right:100px, center:100px, fill:100px", "top:100px, bottom:100px, center:100px, fill:100px");
//        CellConstraints c = new CellConstraints();
        GridLayout gridLayout = new GridLayout(4, 1);
        mainframe.setLayout(gridLayout);
        mainframe.add(startButton);
        mainframe.add(continueButton);
        mainframe.add(editButton);
        mainframe.add(exitButton);
        mainframe.pack();
        mainframe.setVisible(true);

        continueButton.setEnabled(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton polEngRadioButton = new JRadioButton("Polish-English");
                JRadioButton engPolRadioButton = new JRadioButton("English-Polish");
                ButtonGroup languageGroup = new ButtonGroup();
                languageGroup.add(polEngRadioButton);
                languageGroup.add(engPolRadioButton);
                JRadioButton testRadioButton = new JRadioButton("Test");
                JRadioButton learnRadioButton = new JRadioButton("Learn");
                ButtonGroup typeGroup = new ButtonGroup();
                typeGroup.add(testRadioButton);
                typeGroup.add(learnRadioButton);
                JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"2 words", "3 words", "4 words", "5 words", "Write words"});
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Choose your languages"),
                        polEngRadioButton,
                        engPolRadioButton,
                        new JLabel("Choose your type"),
                        testRadioButton,
                        learnRadioButton,
                        new JLabel("Choose your difficulty"),
                        difficultyComboBox
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "New game options", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if (learnRadioButton.isSelected()) {
                        LearningGame game = new LearningGame();
                        Difficulty difficulty;
                        LanguageState languageState;
                        switch (difficultyComboBox.getSelectedItem().toString()) {
                            case "2 words":
                                difficulty = new TwoWordDifficulty();
                                break;
                            case "3 words":
                                difficulty = new ThreeWordDifficulty();
                                break;
                            case "4 words":
                                difficulty = new FourWordDifficulty();
                                break;
                            case "5 words":
                                difficulty = new FiveWordDifficulty();
                                break;
                            case "Write words":
                                difficulty = new WrittenWordDifficulty();
                                break;
                            default:
                                difficulty = new TwoWordDifficulty();
                        }
                        if (engPolRadioButton.isSelected()) {
                            languageState = new ForeignPolishState();
                        } else {
                            languageState = new PolishForeignState();
                        }
                        game.setGameDifficulty(difficulty);
                        game.setLanguageState(languageState);
                        createGameWindow();
                    } else {
                        //TODO Dodac Test Game
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void createGameWindow() {

    }
}
