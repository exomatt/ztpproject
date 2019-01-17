package client;

import builder.PdfBuilder;
import builder.RaportBuilder;
import builder.TxtBuilder;
import com.itextpdf.text.DocumentException;
import database.DataBaseEditorTarget;
import database.DatabaseEditorAdapter;
import database.DatabaseRepositoryAdaptee;
import database.FileDatabaseAdaptee;
import game.LearningGame;
import lombok.extern.java.Log;
import memento.GameMemento;
import model.Question;
import model.Word;
import observer.ObservableUser;
import state.ForeignPolishState;
import state.LanguageState;
import state.PolishForeignState;
import strategy.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

/**
 * The type Main menu.
 */
@Log
class MainMenu implements Observer {

    private static final int MIN_QUESTIONS_AMOUNT = 2;
    private static final int MAX_QUESTIONS_AMOUNT = 25;
    private static final int FILEDATABASE = 4;
    private static final int MYSQLDATABASE = 5;
    private DataBaseEditorTarget databaseEditor;
    private static final int LEARN = 0;
    private static final int TEST = 1;
    private static final int POLISH_ENGLISH = 2;
    private static final int ENGLISH_POLISH = 3;
    private final JFrame mainWindowFrame;
    private JButton loadSavedSessionButton = new JButton("Load saved session");
    private JList databaseWordsList;
    private List<Word> words;
    private JComboBox<String> languageComboBox;
    private GameMemento memento;
    private List<Question> questions;
    private boolean isTest;
    private JFrame sessionWindowFrame;

    /**
     * Instantiates a new Main menu.
     */
    MainMenu() {
        mainWindowFrame = new JFrame("Learning languages");
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindowFrame.setResizable(false);
        mainWindowFrame.setLocation(10, 10);
        mainWindowFrame.setPreferredSize(new Dimension(500, 500));
        GridLayout gridLayout = new GridLayout(4, 1);
        mainWindowFrame.setLayout(gridLayout);
        JButton startButton = new JButton("Start");
        mainWindowFrame.add(startButton);
        mainWindowFrame.add(loadSavedSessionButton);
        JButton editButton = new JButton("Edit your words");
        mainWindowFrame.add(editButton);
        JButton exitButton = new JButton("Exit");
        mainWindowFrame.add(exitButton);
        mainWindowFrame.pack();
        mainWindowFrame.setVisible(true);
        loadSavedSessionButton.setEnabled(false);
        loadSavedSessionButton.addActionListener(e -> createGameWindowFromContinue());
        if (new File("save/memento.ser").exists()) {
            loadSavedSessionButton.setEnabled(true);
        }
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
            JSlider questionsAmountSlider = new JSlider(MIN_QUESTIONS_AMOUNT, MAX_QUESTIONS_AMOUNT);
                    JLabel amountLabel = new JLabel(String.valueOf(questionsAmountSlider.getValue()));
                    questionsAmountSlider.setMajorTickSpacing(5);
                    questionsAmountSlider.setMinorTickSpacing(1);
                    questionsAmountSlider.setPaintTicks(true);
                    questionsAmountSlider.addChangeListener(e1 -> amountLabel.setText(String.valueOf(questionsAmountSlider.getValue())));
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
                            new JLabel("How many words?"),
                            amountLabel,
                            questionsAmountSlider
                    };
                    int result = JOptionPane.showConfirmDialog(null, inputs, "New game options", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String gameDifficulty = difficultyComboBox.getSelectedItem().toString();
                        String iterationType = iteratorComboBox.getSelectedItem().toString();
                        int questionsAmount = questionsAmountSlider.getValue();
                        if (learnRadioButton.isSelected()) {
                            isTest = false;
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(LEARN, POLISH_ENGLISH, FILEDATABASE, gameDifficulty, iterationType, questionsAmount);   ///Learn, Polish-English, File Database
                                } else {
                                    createGameWindowFromStart(LEARN, POLISH_ENGLISH, MYSQLDATABASE, gameDifficulty, iterationType, questionsAmount);   ///Learn, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(LEARN, ENGLISH_POLISH, FILEDATABASE, gameDifficulty, iterationType, questionsAmount);   //Learn, English-Polish, File Database
                                } else {
                                    createGameWindowFromStart(LEARN, ENGLISH_POLISH, MYSQLDATABASE, gameDifficulty, iterationType, questionsAmount);   //Learn, English-Polish, Repo Database
                                }
                            }
                        } else {
                            isTest = true;
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(TEST, POLISH_ENGLISH, FILEDATABASE, gameDifficulty, iterationType, questionsAmount);   //Test, Polish-English, File Database
                                } else {
                                    createGameWindowFromStart(TEST, POLISH_ENGLISH, MYSQLDATABASE, gameDifficulty, iterationType, questionsAmount);   //Test, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(TEST, ENGLISH_POLISH, FILEDATABASE, gameDifficulty, iterationType, questionsAmount);   //Test, English-Polish, File Database
                                } else {
                                    createGameWindowFromStart(TEST, ENGLISH_POLISH, MYSQLDATABASE, gameDifficulty, iterationType, questionsAmount);   //Test, English-Polish, Repo Database
                                }
                            }
                        }
                    }
                }
        );
        editButton.addActionListener(e -> {
            Object[] options = {"File", "Repoistory", "Cancel"};
            int n = JOptionPane.showOptionDialog(mainWindowFrame, "Which database would you like to use", "Edit your words", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n == 0) {
                createDatabaseWindow(FILEDATABASE);
            } else if (n == 1) {
                createDatabaseWindow(MYSQLDATABASE);
            }
        });
        exitButton.addActionListener(e -> System.exit(0));
    }


    //// CREATE DATABASE WINDOW
    private void createDatabaseWindow(int source) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(10, 10);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;

        JButton editWordButton = new JButton("Edit");
        JButton removeWordButton = new JButton("Remove");
        if (source == MYSQLDATABASE) {
            databaseEditor = new DatabaseEditorAdapter(new DatabaseRepositoryAdaptee());
        } else {
            databaseEditor = new DatabaseEditorAdapter(new FileDatabaseAdaptee());
        }

        words = databaseEditor.findByLanguage("pl");

        if (words == null) {
            try {
                if (Files.exists(Paths.get("database.csv"))) {
                    words = Collections.emptyList();
                }
                Files.createFile(Paths.get("database.csv"));
            } catch (IOException e) {
                log.severe("Problem with db" + e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
            }
            words = Collections.emptyList();
        }
        CustomListModel model = new CustomListModel(words);
        databaseWordsList = new JList(model);

        languageComboBox = new JComboBox<>(new String[]{"Polish", "English"});
        languageComboBox.addItemListener(e -> setWordsByLanguage());
        gc.weightx = 0.5;
        gc.gridwidth = 3;
        gc.gridx = 0;
        gc.gridy = 0;
        frame.add(languageComboBox, gc);

        gc.gridwidth = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        JScrollPane listScroll = new JScrollPane(databaseWordsList);
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
        databaseWordsList.addListSelectionListener(e -> {
            if (databaseWordsList.isSelectionEmpty()) {
                return;
            }
            Word current = getCurrentWord(words, databaseWordsList);
            wordField.setText(current.getWord());
            translationField.setText(current.getTranslation().getWord());
            languageField.setText(current.getLanguage());
            editWordButton.setEnabled(true);
            removeWordButton.setEnabled(true);
        });
        gc.gridx = 1;

        JPanel buttonsPanel = new JPanel();
        JButton addWordButton = new JButton("Add");
        addWordButton.addActionListener(e -> createWordPopup());
        editWordButton.setEnabled(false);
        editWordButton.addActionListener(e -> {
            Word currentWord = getCurrentWord(words, databaseWordsList);
            createWordPopup(currentWord);
        });
        removeWordButton.setEnabled(false);
        removeWordButton.addActionListener(e -> {
            Word current = getCurrentWord(words, databaseWordsList);
            words.remove(current);
            databaseEditor.deleteWord(current);
            databaseWordsList.setModel(new CustomListModel(words));
            databaseWordsList.clearSelection();
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
            wordField.setEditable(false);
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
            if (wordField.getText().replace(" ", "").isEmpty() || translationField.getText().replace(" ", "").isEmpty()) {
                JOptionPane.showMessageDialog(null, "One of fields is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (current == null) {
                String language = languageWordComboBox.getSelectedItem().toString();
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
                databaseEditor.addWord(word);
                refreshDatabaseList();
            } else {
                Word translation = new Word(translationField.getText(), current, current.getTranslation().getLanguage());
                current.setTranslation(translation);
                databaseEditor.changeWorldTranslation(current);
                refreshDatabaseList();
            }


        }
    }

    private void refreshDatabaseList() {
        databaseWordsList.clearSelection();
        words.clear();
        setWordsByLanguage();
    }

    private void setWordsByLanguage() {
        if (languageComboBox.getSelectedItem().toString().equals("Polish")) {
            words = databaseEditor.findByLanguage("pl");
            databaseWordsList.setModel(new CustomListModel(words));
        } else {
            words = databaseEditor.findByLanguage("eng");
            databaseWordsList.setModel(new CustomListModel(words));
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

    private void createGameWindowFromStart(int gameType, int langState, int repo, String diff, String iterType, int value) {
        LearningGame game;
        game = new LearningGame();
        if (gameType == LEARN) {
            game.setIfTest(false);
        } else {
            game.setIfTest(true);
        }
        LanguageState languageState;
        if (repo == FILEDATABASE) {
            databaseEditor = new DatabaseEditorAdapter(new FileDatabaseAdaptee());
        } else {
            databaseEditor = new DatabaseEditorAdapter(new DatabaseRepositoryAdaptee());
        }

        if (langState == POLISH_ENGLISH) {
            languageState = new PolishForeignState();
        } else {
            languageState = new ForeignPolishState();
        }
        game.setLanguageState(languageState);
        game.setWordList(databaseEditor.findByLanguage(languageState.getLanguage()));

        if (game.getWordList() == null) {
            return;
        }
        int sizeOfDBWords = game.getWordList().size();

        if (value > sizeOfDBWords) {
            JOptionPane.showMessageDialog(mainWindowFrame, "There are not enough words in your database, you have " + databaseEditor.getAllWords().size() + " questions");
            value = sizeOfDBWords;
        }
        if (iterType.equals("Random")) {
            game.createIterator(true, value);
        } else {
            game.createIterator(false, value);
        }

        Iterator<Word> iterator = game.getIterator();
        questions = new ArrayList<>();
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
        while (iterator.hasNext()) {
            Question tempQuestion = new Question();
            Word word = iterator.next();
            List<Word> answerWordsList = game.getGameDifficulty().getAnswerWords(word, databaseEditor, game.getLanguageState());
            tempQuestion.setWordToTranslate(word);
            tempQuestion.setAnwswers(answerWordsList);
            questions.add(tempQuestion);
        }

        createGameWindow(questions, diff, value, game.isIfTest(), 0, game);
    }

    private void createGameWindowFromContinue() {
        memento = readMemento();
        LearningGame game = new LearningGame();
        game.setMemento(memento);
        createGameWindow(game.getQuestions(),
                game.getDiff(),
                game.getMaxValue(),
                game.isIfTest(),
                game.getLastQuestionIndex(),
                game
        );
        removeMemento();
    }

    /////////// CREATE GAME WINDOW
    private void createGameWindow(List<Question> questions, String diff, int maxWords, boolean game, int index, LearningGame learningGame) {
        sessionWindowFrame = new JFrame("Game");
        int[] currentQuestionIndex = {index};
        sessionWindowFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int result = JOptionPane.showConfirmDialog(null, "Would you like to save?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    learningGame.setQuestions(questions);
                    learningGame.setMaxValue(maxWords);
                    learningGame.setLastQuestionIndex(currentQuestionIndex[0]);
                    learningGame.setDiff(diff);
                    memento = learningGame.createMemento();
                    addMemento(memento);
                    loadSavedSessionButton.setEnabled(true);
                    sessionWindowFrame.dispose();
                } else {
                    removeMemento();
                    loadSavedSessionButton.setEnabled(false);
                    sessionWindowFrame.dispose();
                }
            }
        });
        JPanel gamePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        List<JButton> buttons = new ArrayList<>();
        JTextField userAnswer = new JTextField();
        JLabel wordToTranslate = new JLabel("Word to translate");
        JLabel timeLabel = new JLabel();
        JPanel spacingPanel = new JPanel();
        if (game) {
            ObservableUser listener = new ObservableUser(timeLabel, maxWords);
            listener.addObserver(this);
            Timer timer = new Timer(1000, listener);
            timer.setInitialDelay(0);
            timer.start();
        }
        switch (diff) {
            case "2 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 2);
                setupButtons(sessionWindowFrame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "3 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 3);
                setupButtons(sessionWindowFrame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "4 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 4);
                setupButtons(sessionWindowFrame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "5 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 5);
                setupButtons(sessionWindowFrame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "Write words":
                setupTextField(sessionWindowFrame, bottomPanel, userAnswer, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
        }


        sessionWindowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        sessionWindowFrame.setResizable(false);
        sessionWindowFrame.setLocation(10, 10);
        sessionWindowFrame.setLayout(new BorderLayout());
        gamePanel.setLayout(new FlowLayout());
        gamePanel.add(wordToTranslate);
        wordToTranslate.setText(String.format("Word to translate:  %s", questions.get(currentQuestionIndex[0]).getWordToTranslate().getWord()));

        bottomPanel.setLayout(new GridLayout());

        sessionWindowFrame.add(gamePanel, BorderLayout.NORTH);
        spacingPanel.setPreferredSize(new Dimension(300, 100));
        spacingPanel.setLayout(new BorderLayout());
        spacingPanel.add(timeLabel, BorderLayout.CENTER);
        sessionWindowFrame.add(new JPanel(), BorderLayout.EAST);
        sessionWindowFrame.add(new JPanel(), BorderLayout.WEST);
        sessionWindowFrame.add(spacingPanel, BorderLayout.CENTER);
        sessionWindowFrame.add(bottomPanel, BorderLayout.SOUTH);
        sessionWindowFrame.pack();
        sessionWindowFrame.setVisible(true);

    }

    private void addButtonsToList(List<JButton> buttons, Question question, int amount) {
        for (int i = 0; i < amount; i++) {
            buttons.add(new JButton(question.getAnwswers().get(i).getWord()));
        }
    }

    private void setupTextField(JFrame frame, JPanel bottomPanel, JTextField userAnswer, JLabel wordToTranslate, int maxWords, List<Question> questions, int[] currentQuestionIndex, boolean isTest) {
        bottomPanel.add(userAnswer);
        userAnswer.addActionListener(e -> {
            if (isTest) {
                questions.get(currentQuestionIndex[0]).setPickByUser(userAnswer.getText());
                currentQuestionIndex[0]++;
                userAnswer.setText("");
            } else {
                if (userAnswer.getText().equals(questions.get(currentQuestionIndex[0]).getWordToTranslate().getTranslation().getWord())) {
                    questions.get(currentQuestionIndex[0]).setPickByUser(userAnswer.getText());
                    currentQuestionIndex[0]++;
                    userAnswer.setText("");
                } else {
                    JOptionPane.showMessageDialog(mainWindowFrame, "Wrong answer!! Try again!");
                }
            }
            if (currentQuestionIndex[0] == maxWords) {
                resultPopup(questions, isTest);
                frame.dispose();
            } else {
                wordToTranslate.setText("Word to translate:  " + questions.get(currentQuestionIndex[0]).getWordToTranslate().getWord());
            }
        });
    }

    private void setupButtons(JFrame frame, JPanel bottomPanel, List<JButton> buttons, JLabel wordToTranslate, int maxWords, List<Question> questions, int[] currentQuestionIndex, boolean isTest) {
        for (JButton button :
                buttons) {
            bottomPanel.add(button);
            button.addActionListener(e -> {
                if (isTest) {
                    questions.get(currentQuestionIndex[0]).setPickByUser(button.getText());
                    currentQuestionIndex[0]++;
                } else {
                    if (button.getText().equals(questions.get(currentQuestionIndex[0]).getWordToTranslate().getTranslation().getWord())) {
                        questions.get(currentQuestionIndex[0]).setPickByUser(button.getText());
                        currentQuestionIndex[0]++;
                    } else {
                        JOptionPane.showMessageDialog(mainWindowFrame, "Wrong answer!! Try again!");
                    }
                }
                if (currentQuestionIndex[0] == maxWords) {
                    resultPopup(questions, isTest);
                    frame.dispose();
                } else {
                    updateUI(buttons, wordToTranslate, questions, currentQuestionIndex[0]);
                }
            });
        }
    }

    private void resultPopup(List<Question> questions, boolean game) {
        RaportBuilder builder = null;
        Object[] options = {"TXT", "PDF", "Don't save"};
        int n = JOptionPane.showOptionDialog(mainWindowFrame, "How would you like to save?", "Save your results", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == 0) {
            builder = new TxtBuilder();
        } else if (n == 1) {
            builder = new PdfBuilder();
        }
        if (n != 2) {
            for (Question q : questions) {
                builder.addWordToTranslate(q.getWordToTranslate());
                builder.addAnswersList(q.getAnwswers());
                builder.addCorrectAnswer(q.getWordToTranslate().getTranslation());
                if (game) {
                    builder.addUserAnswer(q.getPickByUser());
                }
            }
        }
        if (n == 0) {
            try {
                JOptionPane.showMessageDialog(mainWindowFrame, "Your file has been saved to " + ((TxtBuilder) builder).buildTxt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (n == 1) {
            try {
                JOptionPane.showMessageDialog(mainWindowFrame, "Your file has been saved to " + ((PdfBuilder) builder).buildPdf());
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
        loadSavedSessionButton.setEnabled(false);
    }

    private void updateUI(List<JButton> buttons, JLabel wordToTranslate, List<Question> questions, int currentQuestionIndex) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(questions.get(currentQuestionIndex).getAnwswers().get(i).getWord());
        }
        wordToTranslate.setText("Word to translate:  " + questions.get(currentQuestionIndex).getWordToTranslate().getWord());
    }

    private void addMemento(GameMemento gameMemento) {
        memento = gameMemento;
        File resultpath = new File("save/memento.ser");
        if (!resultpath.getParentFile().exists()) {
            resultpath.getParentFile().mkdirs();
        }
        try (
                FileOutputStream fout = new FileOutputStream("save/memento.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
        ) {
            oos.writeObject(memento);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private GameMemento readMemento() {
        GameMemento result = null;
        try (
                FileInputStream fin = new FileInputStream("save/memento.ser");
                ObjectInputStream ois = new ObjectInputStream(fin);
        ) {
            result = (GameMemento) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void removeMemento() {
        memento = null;
        Path fileToDeletePath = Paths.get("save/memento.ser");

        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        resultPopup(questions, isTest);
        sessionWindowFrame.dispose();
        o.deleteObserver(this);

    }
}