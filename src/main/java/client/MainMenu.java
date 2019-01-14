package client;

import database.DatabaseEditor;
import database.FileDatabase;
import game.LearningGame;
import model.Word;
import state.ForeignPolishState;
import state.LanguageState;
import state.PolishForeignState;
import strategy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainMenu {
    private DatabaseEditor editorDB;
    private static final int LEARN = 0;
    private static final int TEST = 1;
    private static final int POLENG = 2;
    private static final int ENGPOL = 3;
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
                        if (polEngRadioButton.isSelected())
                            createGameWindow(LEARN, POLENG, difficultyComboBox.getSelectedItem().toString());
                        else
                            createGameWindow(LEARN, ENGPOL, difficultyComboBox.getSelectedItem().toString());
                    } else {
                        if (polEngRadioButton.isSelected())
                            createGameWindow(TEST, POLENG, difficultyComboBox.getSelectedItem().toString());
                        else
                            createGameWindow(TEST, ENGPOL, difficultyComboBox.getSelectedItem().toString());
                    }
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"File", "Repoistory", "Cancel"};
                int n = JOptionPane.showOptionDialog(mainframe, "Which database would you like to use", "Edit your words", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n == 0) {
                    createDataBaseWindow();
                } else if (n == 1) {
                    //TODO Repo
                } else {

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

    private void createDataBaseWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(10, 10);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setLayout(new GridLayout(1, 2));


        editorDB = new DatabaseEditor();
        editorDB.setDatabase(new FileDatabase());
        List<Word> words = editorDB.getAllWords();
        //todo zrobienie wyboru jezyka combox do editbase
        
        if (words == null) {
            JOptionPane.showMessageDialog(mainframe, "Problem with database file", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CustomListModel model = new CustomListModel(words);
        JList list = new JList(model);
        frame.add(list);

        JPanel wordDetail = new JPanel();
        wordDetail.setLayout(new GridLayout(3, 2));
        JLabel wordLabel = new JLabel("Word:");
        wordDetail.add(wordLabel);
        JTextField wordField = new JTextField();
        wordField.setEditable(false);
        wordDetail.add(wordField);
        JLabel translationLabel = new JLabel("Translation:");
        wordDetail.add(translationLabel);
        JTextField translationField = new JTextField();
        wordDetail.add(translationField);
        translationField.setEditable(false);
        JLabel languageLabel = new JLabel("Language:");
        wordDetail.add(languageLabel);
        JTextField languageField = new JTextField();
        wordDetail.add(languageField);
        languageField.setEditable(false);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String temp = (String) list.getSelectedValue();
                Word current = null;
                for (Word w : words) {
                    if (w.getWord().equals(temp)) {
                        current = w;
                        break;
                    }
                }
                wordField.setText(current.getWord());
                translationField.setText(current.getTranslation().getWord());
                languageField.setText(current.getLanguage());
                wordField.setEditable(true);
                translationField.setEditable(true);
            }
        });

        frame.add(wordDetail);
        frame.pack();
        frame.setVisible(true);


    }

    private void createGameWindow(int gameType, int langState, String diff) {
        // todo wybór skąd ma brac słowa z pliku czy z bazy
        editorDB = new DatabaseEditor();
        editorDB.setDatabase(new FileDatabase());
        LearningGame game = new LearningGame();
//        LanguageState languageState;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(10, 10);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setLayout(new BorderLayout());
        JPanel gamePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        ArrayList<JComponent> componentList = new ArrayList<>();
        switch (diff) {
            case "2 words":
                game.setGameDifficulty(new TwoWordDifficulty());
                componentList.add(new JButton("1"));
                componentList.add(new JButton("2"));
                break;
            case "3 words":
                game.setGameDifficulty(new ThreeWordDifficulty());
                componentList.add(new JButton("1"));
                componentList.add(new JButton("2"));
                componentList.add(new JButton("3"));
                break;
            case "4 words":
                game.setGameDifficulty(new FourWordDifficulty());
                componentList.add(new JButton("1"));
                componentList.add(new JButton("2"));
                componentList.add(new JButton("3"));
                componentList.add(new JButton("4"));
                break;
            case "5 words":
                game.setGameDifficulty(new FiveWordDifficulty());
                componentList.add(new JButton("1"));
                componentList.add(new JButton("2"));
                componentList.add(new JButton("3"));
                componentList.add(new JButton("4"));
                componentList.add(new JButton("5"));
                break;
            case "Write words":
                game.setGameDifficulty(new WrittenWordDifficulty());
                componentList.add(new JTextField());
                break;
            default:
                game.setGameDifficulty(new TwoWordDifficulty());
        }
        LanguageState languageState;
        if (langState == 2) {
            languageState = new PolishForeignState();
        } else {
            languageState = new ForeignPolishState();
        }
        game.setLanguageState(languageState);
        game.setQuestions(editorDB.findByLanguage(languageState.getLanguage()));
        // todo dopisac wybor iteraatora alfabetyczny / losowy -> tymczasowo alphabet
        game.createIterator(false, 1);
        Iterator<Word> iterator = game.getIterator();
        JLabel wordToTranslate = new JLabel("Word to translate");
        gamePanel.setLayout(new FlowLayout());
        gamePanel.add(wordToTranslate);

        while (iterator.hasNext()) {
            componentList.clear();
            Word word = iterator.next();
            wordToTranslate.setText("Word to translate:  " + word.getWord());
            List<Word> answerWords = game.getGameDifficulty().getAnswerWords(word, editorDB, game.getLanguageState());
            for (Word answerWord : answerWords) {
                componentList.add(new JButton(answerWord.getWord()));
            }
            //todo musi sie zatrzymac

        }
        bottomPanel.setLayout(new GridLayout());
        for (JComponent component : componentList) {
            bottomPanel.add(component);
        }

        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);

    }

}
