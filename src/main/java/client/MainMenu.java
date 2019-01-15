package client;

import database.DatabaseEditor;
import database.DatabaseRepository;
import database.FileDatabase;
import game.LearningGame;
import model.Word;
import state.ForeignPolishState;
import state.LanguageState;
import state.PolishForeignState;
import strategy.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The type Main menu.
 */
public class MainMenu {

    private static final int FILEDB = 4;
    private static final int REPODB = 5;
    private DatabaseEditor editorDB;
    private static final int LEARN = 0;
    private static final int TEST = 1;
    private static final int POLENG = 2;
    private static final int ENGPOL = 3;
    private final JFrame mainframe;
    private JButton exitButton = new JButton("Exit");
    private JButton startButton = new JButton("Start");
    private JButton editButton = new JButton("Edit your words");
    private JButton continueButton = new JButton("Continue");

    /**
     * Instantiates a new Main menu.
     */
    public MainMenu() {
        mainframe = new JFrame("Learning languages");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        mainframe.setLocation(10, 10);
        mainframe.setPreferredSize(new Dimension(500, 500));
        GridLayout gridLayout = new GridLayout(4, 1);
        mainframe.setLayout(gridLayout);
        mainframe.add(startButton);
        mainframe.add(continueButton);
        mainframe.add(editButton);
        mainframe.add(exitButton);
        mainframe.pack();
        mainframe.setVisible(true);

        continueButton.setEnabled(false);
        startButton.addActionListener(e -> {
                    JRadioButton polEngRadioButton = new JRadioButton("Polish-English");
                    JRadioButton engPolRadioButton = new JRadioButton("English-Polish");
                    ButtonGroup languageGroup = new ButtonGroup();
                    languageGroup.add(polEngRadioButton);
                    languageGroup.add(engPolRadioButton);
                    polEngRadioButton.setSelected(true);

                    JRadioButton testRadioButton = new JRadioButton("Test");
                    JRadioButton learnRadioButton = new JRadioButton("Learn");
                    ButtonGroup typeGroup = new ButtonGroup();
                    typeGroup.add(testRadioButton);
                    typeGroup.add(learnRadioButton);
                    learnRadioButton.setSelected(true);

                    JRadioButton fileDatabaseRadioButton = new JRadioButton("File database");
                    JRadioButton repositoryDatabaseRadioButton = new JRadioButton("Repository");
                    ButtonGroup databaseGroup = new ButtonGroup();
                    databaseGroup.add(fileDatabaseRadioButton);
                    databaseGroup.add(repositoryDatabaseRadioButton);
                    fileDatabaseRadioButton.setSelected(true);

                    JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"2 words", "3 words", "4 words", "5 words", "Write words"});
                    JComboBox<String> iteratorComboBox = new JComboBox<>(new String[]{"Random", "Alphabet"});
                    final JComponent[] inputs = new JComponent[]{
                            new JLabel("Choose your languages"),
                            polEngRadioButton,
                            engPolRadioButton,
                            new JLabel("Choose your type"),
                            testRadioButton,
                            learnRadioButton,
                            new JLabel("Choose your database"),
                            fileDatabaseRadioButton,
                            repositoryDatabaseRadioButton,
                            new JLabel("Choose your difficulty"),
                            difficultyComboBox,
                            new JLabel("Questions sorted randomly or alphabetically"),
                            iteratorComboBox
                    };
                    int result = JOptionPane.showConfirmDialog(null, inputs, "New game options", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        if (learnRadioButton.isSelected()) {
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(LEARN, POLENG, FILEDB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   ///Learn, Polish-English, File Database
                                } else {
                                    createGameWindow(LEARN, POLENG, REPODB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   ///Learn, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(LEARN, ENGPOL, FILEDB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   //Learn, English-Polish, File Database
                                } else {
                                    createGameWindow(LEARN, ENGPOL, REPODB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   //Learn, English-Polish, Repo Database
                                }
                            }
                        } else {
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(TEST, POLENG, FILEDB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   //Test, Polish-English, File Database
                                } else {
                                    createGameWindow(TEST, POLENG, REPODB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   //Test, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(TEST, ENGPOL, FILEDB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   //Test, English-Polish, File Database
                                } else {
                                    createGameWindow(TEST, ENGPOL, REPODB, difficultyComboBox.getSelectedItem().toString(), iteratorComboBox.getSelectedItem().toString());   //Test, English-Polish, Repo Database
                                }
                            }
                        }
                    }
                }
        );
        editButton.addActionListener(e -> {
            Object[] options = {"File", "Repoistory", "Cancel"};
            int n = JOptionPane.showOptionDialog(mainframe, "Which database would you like to use", "Edit your words", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n == 0) {
                createDataBaseWindow(FILEDB);
            } else if (n == 1) {
                createDataBaseWindow(REPODB);
            }
        });
        exitButton.addActionListener(e -> System.exit(0));
    }


    //// CREATE DATABASE WINDOW
    private void createDataBaseWindow(int source) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(10, 10);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;

        JButton removeButton = new JButton("Remove");
        editorDB = new DatabaseEditor();
        if (source == REPODB) {
            editorDB.setDatabase(new DatabaseRepository());
        } else {
            editorDB.setDatabase(new FileDatabase());
        }

        List<Word> words = editorDB.findByLanguage("pl");

        if (words == null) {
            JOptionPane.showMessageDialog(mainframe, "Problem with database file", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CustomListModel model = new CustomListModel(words);
        JList list = new JList(model);

        JComboBox<String> languageComboBox = new JComboBox<>(new String[]{"Polish", "English"});
        languageComboBox.addItemListener(e -> {
            if (languageComboBox.getSelectedItem().toString().equals("Polish")) {
                List<Word> plWords = editorDB.findByLanguage("pl");
                list.setModel(new CustomListModel(plWords));
            } else {
                List<Word> plWords = editorDB.findByLanguage("eng");
                list.setModel(new CustomListModel(plWords));
            }
        });

        gc.gridwidth = 3;
        gc.weightx = 0.5;
        gc.gridx = 0;
        gc.gridy = 0;
        frame.add(languageComboBox,gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        JScrollPane listScroll = new JScrollPane(list);
        frame.add(listScroll, gc);


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
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
                removeButton.setEnabled(true);
            }
        });
        gc.gridx = 1;
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = (String) list.getSelectedValue();
                Word current = null;
                for (Word w : words) {
                    if (w.getWord().equals(temp)) {
                        current = w;
                        break;
                    }
                }
                editorDB.deleteWord(current);
                words.remove(current);
                list.setModel(new CustomListModel(words));
                list.setSelectedIndex(0);
                wordField.setText(current.getWord());
                translationField.setText(current.getTranslation().getWord());
                languageField.setText(current.getLanguage());
            }
        });
        buttonsPanel.setLayout(new GridLayout(3, 1));
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);
        frame.add(buttonsPanel, gc);


        gc.gridx = 2;
        frame.add(wordDetail,gc);


        frame.pack();
        frame.setVisible(true);


    }


    /////////// CREATE GAME WINDOW
    private void createGameWindow(int gameType, int langState, int repo, String diff, String iterType) {
        editorDB = new DatabaseEditor();
        editorDB.setDatabase(new FileDatabase());
        LearningGame game = new LearningGame();

        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(10, 10);
        frame.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        ArrayList<JComponent> componentList = new ArrayList<>();
        switch (diff) {
            case "2 words":
                game.setGameDifficulty(new TwoWordDifficulty());
                break;
            case "3 words":
                game.setGameDifficulty(new ThreeWordDifficulty());
                break;
            case "4 words":
                game.setGameDifficulty(new FourWordDifficulty());
                break;
            case "5 words":
                game.setGameDifficulty(new FiveWordDifficulty());
                break;
            case "Write words":
                game.setGameDifficulty(new WrittenWordDifficulty());
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

        frame.add(gamePanel, BorderLayout.NORTH);
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(300, 100));
        frame.add(spacingPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

    }

}
