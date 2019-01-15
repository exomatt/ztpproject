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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The type Main menu.
 */
public class MainMenu {

    private static final int MIN_QUESTIONS = 2;
    private static final int MAX_QUESTIONS = 25;
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
    private CustomListModel model;
    private JList list;
    private List<Word> words;
    private JComboBox<String> languageComboBox;

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
            JSlider questionsAmountSlider = new JSlider(MIN_QUESTIONS, MAX_QUESTIONS);
            JLabel amountLabel = new JLabel(String.valueOf(questionsAmountSlider.getValue()));
            questionsAmountSlider.setMajorTickSpacing(5);
            questionsAmountSlider.setMinorTickSpacing(1);
            questionsAmountSlider.setPaintTicks(true);
            questionsAmountSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    amountLabel.setText(String.valueOf(questionsAmountSlider.getValue()));
                }
            });
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
                    iteratorComboBox,
                    new JLabel("How many questions?"),
                    amountLabel,
                    questionsAmountSlider
                    };
                    int result = JOptionPane.showConfirmDialog(null, inputs, "New game options", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String gameDifficulty = difficultyComboBox.getSelectedItem().toString();
                        String iterType = iteratorComboBox.getSelectedItem().toString();
                        int questionsAmount = questionsAmountSlider.getValue();
                        if (learnRadioButton.isSelected()) {
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(LEARN, POLENG, FILEDB, gameDifficulty, iterType, questionsAmount);   ///Learn, Polish-English, File Database
                                } else {
                                    createGameWindow(LEARN, POLENG, REPODB, gameDifficulty, iterType, questionsAmount);   ///Learn, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(LEARN, ENGPOL, FILEDB, gameDifficulty, iterType, questionsAmount);   //Learn, English-Polish, File Database
                                } else {
                                    createGameWindow(LEARN, ENGPOL, REPODB, gameDifficulty, iterType, questionsAmount);   //Learn, English-Polish, Repo Database
                                }
                            }
                        } else {
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(TEST, POLENG, FILEDB, gameDifficulty, iterType, questionsAmount);   //Test, Polish-English, File Database
                                } else {
                                    createGameWindow(TEST, POLENG, REPODB, gameDifficulty, iterType, questionsAmount);   //Test, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindow(TEST, ENGPOL, FILEDB, gameDifficulty, iterType, questionsAmount);   //Test, English-Polish, File Database
                                } else {
                                    createGameWindow(TEST, ENGPOL, REPODB, gameDifficulty, iterType, questionsAmount);   //Test, English-Polish, Repo Database
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
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;

        JButton editWordButton = new JButton("Edit");
        JButton removeWordButton = new JButton("Remove");
        editorDB = new DatabaseEditor();
        if (source == REPODB) {
            editorDB.setDatabase(new DatabaseRepository());
        } else {
            editorDB.setDatabase(new FileDatabase());
        }

        words = editorDB.findByLanguage("pl");

        if (words == null) {
            JOptionPane.showMessageDialog(mainframe, "Problem with database file", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model = new CustomListModel(words);
        list = new JList(model);

        languageComboBox = new JComboBox<>(new String[]{"Polish", "English"});
        languageComboBox.addItemListener(e -> {
            setWordsByLanguage();
        });
        gc.weightx = 0.5;
        gc.gridwidth = 3;
        gc.gridx = 0;
        gc.gridy = 0;
        frame.add(languageComboBox, gc);

        gc.gridwidth = 1;
        gc.weighty = 1;
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
                if (list.isSelectionEmpty()) {
                    return;
                }
                Word current = getCurrentWord(words, list);
                wordField.setText(current.getWord());
                translationField.setText(current.getTranslation().getWord());
                languageField.setText(current.getLanguage());
                editWordButton.setEnabled(true);
                removeWordButton.setEnabled(true);
            }
        });
        gc.gridx = 1;

        JPanel buttonsPanel = new JPanel();
        JButton addWordButton = new JButton("Add");
        addWordButton.addActionListener(e -> {
            createWordPopup();
        });
        editWordButton.setEnabled(false);
        editWordButton.addActionListener(e -> {
            Word current = getCurrentWord(words, list);
            createWordPopup(current);
        });
        removeWordButton.setEnabled(false);
        removeWordButton.addActionListener(e -> {
            Word current = getCurrentWord(words, list);
            editorDB.deleteWord(current);
            words.remove(current);
            list.setModel(new CustomListModel(words));
            list.clearSelection();
            wordField.setText("");
            translationField.setText("");
            languageField.setText("");
            removeWordButton.setEnabled(false);
        });
        buttonsPanel.setLayout(new GridLayout(3, 1));
        buttonsPanel.add(addWordButton);
        buttonsPanel.add(editWordButton);
        buttonsPanel.add(removeWordButton);
        frame.add(buttonsPanel, gc);

        gc.gridx = 2;
        frame.add(wordDetail, gc);

        frame.pack();
        frame.setVisible(true);
    }

    private void createWordPopup() {
        createWordPopup(null);
    }

    private void createWordPopup(Word current) {
        JTextField wordField = new JTextField();
        JTextField translationField = new JTextField();
        JComboBox<String> languageWordComboBox = new JComboBox<>();
        if (current == null) {
            languageWordComboBox = new JComboBox<>(new String[]{"pl", "eng"});
        }
        if (current != null) {
            wordField.setText(current.getWord());
            translationField.setText(current.getTranslation().getWord());
        }
        List<JComponent> inputs = new ArrayList<>();
        inputs.add(new JLabel("Your word"));
        inputs.add(wordField);
        inputs.add(new JLabel("Translation"));
        inputs.add(translationField);
        if (current == null) {
            inputs.add(new JLabel("Word language"));
            inputs.add(languageWordComboBox);
        }
        int result = JOptionPane.showConfirmDialog(null, inputs.toArray(), "New word", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            //TODO zmiana by słowo nie było dostepne a tylko translakcja
            if (wordField.getText().replace(" ", "").isEmpty() || translationField.getText().replace(" ", "").isEmpty()) {
                JOptionPane.showMessageDialog(null, "One of fields is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (current == null) {
                String language = languageWordComboBox.getSelectedItem().toString();
                System.out.println(language);
                Word translation;
                Word word;
                if (language.equals("pl")) {
                    word = new Word(wordField.getText(), null, "pl");
                    translation = new Word(translationField.getText(), word, "eng");
                } else {
                    word = new Word(wordField.getText(), null, "eng");
                    translation = new Word(translationField.getText(), word, "pl");
                }
                word.setTranslation(translation);
                editorDB.addWord(word);
                refreshList();
            } else {
                Word translation = new Word(translationField.getText(), current, current.getTranslation().getLanguage());
                current.setTranslation(translation);
                editorDB.changeWorldTranslation(current);
                refreshList();
            }


        }
    }

    private void refreshList() {
        list.clearSelection();
        words.clear();
        setWordsByLanguage();
    }

    private void setWordsByLanguage() {
        if (languageComboBox.getSelectedItem().toString().equals("Polish")) {
            words = editorDB.findByLanguage("pl");
            list.setModel(new CustomListModel(words));
        } else {
            words = editorDB.findByLanguage("eng");
            list.setModel(new CustomListModel(words));
        }
    }

    private Word getCurrentWord(List<Word> words, JList list) {
        String temp = (String) list.getSelectedValue();
        Word current = null;
        for (Word w : words) {
            if (w.getWord().equals(temp)) {
                current = w;
                break;
            }
        }
        return current;
    }


    /////////// CREATE GAME WINDOW
    private void createGameWindow(int gameType, int langState, int repo, String diff, String iterType, int value) {
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
        Word word = iterator.next();
        wordToTranslate.setText("Word to translate:  " + word.getWord());
        List<Word> answerWords = game.getGameDifficulty().getAnswerWords(word, editorDB, game.getLanguageState());
//        for (Word answerWord : answerWords) {
//                componentList.add(new JButton(answerWord.getWord()));
//        }
        componentList.add(new JTextField());
            //todo musi sie zatrzymac

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
