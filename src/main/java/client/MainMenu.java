package client;

import Utils.TimeCounter;
import builder.PdfBuilder;
import builder.RaportBuilder;
import builder.TxtBuilder;
import com.itextpdf.text.DocumentException;
import database.DatabaseEditor;
import database.DatabaseRepository;
import database.FileDatabase;
import game.LearningGame;
import memento.GameMemento;
import model.Question;
import model.Word;
import state.ForeignPolishState;
import state.LanguageState;
import state.PolishForeignState;
import strategy.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * The type Main menu.
 */
class MainMenu {

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
    private JList list;
    private List<Word> words;
    private JComboBox<String> languageComboBox;
    private GameMemento memento;

    /**
     * Instantiates a new Main menu.
     */
    MainMenu() {
        mainframe = new JFrame("Learning languages");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        mainframe.setLocation(10, 10);
        mainframe.setPreferredSize(new Dimension(500, 500));
        GridLayout gridLayout = new GridLayout(4, 1);
        mainframe.setLayout(gridLayout);
        JButton startButton = new JButton("Start");
        mainframe.add(startButton);
        JButton continueButton = new JButton("Continue");
        mainframe.add(continueButton);
        JButton editButton = new JButton("Edit your words");
        mainframe.add(editButton);
        JButton exitButton = new JButton("Exit");
        mainframe.add(exitButton);
        mainframe.pack();
        mainframe.setVisible(true);
        continueButton.setEnabled(false);
        if (new File("save/memento.ser").exists()) {
            continueButton.setEnabled(true);
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
                    JSlider questionsAmountSlider = new JSlider(MIN_QUESTIONS, MAX_QUESTIONS);
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
                            new JLabel("How many wordList?"),
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
                                    createGameWindowFromStart(LEARN, POLENG, FILEDB, gameDifficulty, iterType, questionsAmount);   ///Learn, Polish-English, File Database
                                } else {
                                    createGameWindowFromStart(LEARN, POLENG, REPODB, gameDifficulty, iterType, questionsAmount);   ///Learn, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(LEARN, ENGPOL, FILEDB, gameDifficulty, iterType, questionsAmount);   //Learn, English-Polish, File Database
                                } else {
                                    createGameWindowFromStart(LEARN, ENGPOL, REPODB, gameDifficulty, iterType, questionsAmount);   //Learn, English-Polish, Repo Database
                                }
                            }
                        } else {
                            if (polEngRadioButton.isSelected()) {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(TEST, POLENG, FILEDB, gameDifficulty, iterType, questionsAmount);   //Test, Polish-English, File Database
                                } else {
                                    createGameWindowFromStart(TEST, POLENG, REPODB, gameDifficulty, iterType, questionsAmount);   //Test, Polish-English, Repo Database
                                }
                            } else {
                                if (fileDatabaseRadioButton.isSelected()) {
                                    createGameWindowFromStart(TEST, ENGPOL, FILEDB, gameDifficulty, iterType, questionsAmount);   //Test, English-Polish, File Database
                                } else {
                                    createGameWindowFromStart(TEST, ENGPOL, REPODB, gameDifficulty, iterType, questionsAmount);   //Test, English-Polish, Repo Database
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
        CustomListModel model = new CustomListModel(words);
        list = new JList(model);

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
        list.addListSelectionListener(e -> {
            if (list.isSelectionEmpty()) {
                return;
            }
            Word current = getCurrentWord(words, list);
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

    private void createGameWindowFromStart(int gameType, int langState, int repo, String diff, String iterType, int value) {
        editorDB = new DatabaseEditor();
        LearningGame game;
        game = new LearningGame();
        if (gameType == LEARN) {
            game.setIfTest(false);
        } else {
            game.setIfTest(true);
        }
        LanguageState languageState;
        if (repo == FILEDB) {
            editorDB.setDatabase(new FileDatabase());
        } else {
            editorDB.setDatabase(new DatabaseRepository());
        }

        if (langState == POLENG) {
            languageState = new PolishForeignState();
        } else {
            languageState = new ForeignPolishState();
        }
        game.setLanguageState(languageState);
        game.setWordList(editorDB.findByLanguage(languageState.getLanguage()));

        int sizeOfDBWords = game.getWordList().size();

        if (value > sizeOfDBWords) {
            JOptionPane.showMessageDialog(mainframe, "There are not enough words in your database, you have " + editorDB.getAllWords().size() + "questions");
            value = sizeOfDBWords;
        }
        if (iterType.equals("Random")) {
            game.createIterator(true, value);
        } else {
            game.createIterator(false, value);
        }

        Iterator<Word> iterator = game.getIterator();
        List<Question> questions = new ArrayList<>();
        final int[] currentQuestionIndex = {0};
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
            List<Word> answerWords = game.getGameDifficulty().getAnswerWords(word, editorDB, game.getLanguageState());
            tempQuestion.setWordToTranslate(word);
            tempQuestion.setAnwswers(answerWords);
            questions.add(tempQuestion);
        }

        createGameWindow(questions, diff, value, game);
    }

    private void createGameWindowFromContinue() {
        memento = readMemento();
        //TODO Memento tutaj
//        createGameWindow(memento.getGameState().getQuestions(),memento.getGameState().getDifficulty(),memento.getGameState().get);

    }

    /////////// CREATE GAME WINDOW
//    private void createGameWindow(int gameType, int langState, int repo, String diff, String iterType, int value) {
    private void createGameWindow(List<Question> questions, String diff, int maxWords, LearningGame game) {
        TimeCounter tc = new TimeCounter();
        JFrame frame = new JFrame("Game");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int result = JOptionPane.showConfirmDialog(null, "Are you sure", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    //Do nothing
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
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date currentTime = new Date(tc.getElapsedTime());
                String format = getFormattedTime(currentTime);
                timeLabel.setText("Time elapsed: " + format);
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        int[] currentQuestionIndex = {0};
//        int maxWords = value;
//        editorDB = new DatabaseEditor();
//        LearningGame game;
//        game = new LearningGame();
//        if (gameType == LEARN) {
//            game.setIfTest(false);
//        } else {
//            game.setIfTest(true);
//        }
//        LanguageState languageState;
//        if (repo == FILEDB) {
//            editorDB.setDatabase(new FileDatabase());
//        } else {
//            editorDB.setDatabase(new DatabaseRepository());
//        }
//
//        if (langState == POLENG) {
//            languageState = new PolishForeignState();
//        } else {
//            languageState = new ForeignPolishState();
//        }
//        game.setLanguageState(languageState);
//        game.setQuestions(editorDB.findByLanguage(languageState.getLanguage()));
//
//        int sizeOfDBWords = game.getQuestions().size();
//
//        if (value > sizeOfDBWords) {
//            JOptionPane.showMessageDialog(mainframe, "There are not enough words in your database, you have " + editorDB.getAllWords().size() + "questions");
//            maxWords = sizeOfDBWords;
//        }
//        if (iterType.equals("Random")) {
//            game.createIterator(true, maxWords);
//        } else {
//            game.createIterator(false, maxWords);
//        }
//
//        Iterator<Word> iterator = game.getIterator();
//        List<Question> questions = new ArrayList<>();
//        final int[] currentQuestionIndex = {0};
//        switch (diff) {
//            case "2 words":
//                game.setGameDifficulty(new TwoWordDifficulty());
//                break;
//            case "3 words":
//                game.setGameDifficulty(new ThreeWordDifficulty());
//                break;
//            case "4 words":
//                game.setGameDifficulty(new FourWordDifficulty());
//                break;
//            case "5 words":
//                game.setGameDifficulty(new FiveWordDifficulty());
//                break;
//            case "Write words":
//                game.setGameDifficulty(new WrittenWordDifficulty());
//                break;
//            default:
//                game.setGameDifficulty(new TwoWordDifficulty());
//        }
//        while (iterator.hasNext()) {
//            Question tempQuestion = new Question();
//            Word word = iterator.next();
//            List<Word> answerWords = game.getGameDifficulty().getAnswerWords(word, editorDB, game.getLanguageState());
//            tempQuestion.setWordToTranslate(word);
//            tempQuestion.setAnwswers(answerWords);
//            questions.add(tempQuestion);
//        }
        switch (diff) {
            case "2 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 2);
                setupButtons(frame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "3 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 3);
                setupButtons(frame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "4 words"
                    :
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 4);
                setupButtons(frame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "5 words":
                addButtonsToList(buttons, questions.get(currentQuestionIndex[0]), 5);
                setupButtons(frame, bottomPanel, buttons, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
            case "Write words":
                setupTextField(frame, bottomPanel, userAnswer, wordToTranslate, maxWords, questions, currentQuestionIndex, game);
                break;
        }


        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(10, 10);
        frame.setLayout(new BorderLayout());
        gamePanel.setLayout(new FlowLayout());
        gamePanel.add(wordToTranslate);
        wordToTranslate.setText("Word to translate:  " + questions.get(currentQuestionIndex[0]).getWordToTranslate().getWord());

        bottomPanel.setLayout(new GridLayout());

        frame.add(gamePanel, BorderLayout.NORTH);
        spacingPanel.setPreferredSize(new Dimension(300, 100));
        spacingPanel.setLayout(new BorderLayout());
        spacingPanel.add(timeLabel, BorderLayout.CENTER);
        frame.add(new JPanel(), BorderLayout.EAST);
        frame.add(new JPanel(), BorderLayout.WEST);
        frame.add(spacingPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

    }

    private String getFormattedTime(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(currentTime);
    }

    private void addButtonsToList(List<JButton> buttons, Question question, int amount) {
        for (int i = 0; i < amount; i++) {
            buttons.add(new JButton(question.getAnwswers().get(i).getWord()));
        }
    }

    private void setupTextField(JFrame frame, JPanel bottomPanel, JTextField userAnswer, JLabel wordToTranslate, int maxWords, List<Question> questions, int[] currentQuestionIndex, LearningGame game) {
        bottomPanel.add(userAnswer);
        userAnswer.addActionListener(e -> {
            if (game.isIfTest()) {
                //TODO Pozbadz sie jakos tego game'a zeby mozna bylo z memento bez problemu wczytac
                checkIfCorrectAnswer(userAnswer.getText(), questions, currentQuestionIndex, game);
                userAnswer.setText("");

            } else {
                if (userAnswer.getText().equals(questions.get(currentQuestionIndex[0]).getWordToTranslate().getTranslation().getWord())) {
                    questions.get(currentQuestionIndex[0]).setPickByUser(userAnswer.getText());
                    currentQuestionIndex[0]++;
                    userAnswer.setText("");

                }
            }
            if (currentQuestionIndex[0] == maxWords) {
                resultPopup(questions, game);
                frame.dispose();
            } else {
                wordToTranslate.setText("Word to translate:  " + questions.get(currentQuestionIndex[0]).getWordToTranslate().getWord());
            }
        });
    }

    private void setupButtons(JFrame frame, JPanel bottomPanel, List<JButton> buttons, JLabel wordToTranslate, int maxWords, List<Question> questions, int[] currentQuestionIndex, LearningGame game) {
        for (JButton button :
                buttons) {
            bottomPanel.add(button);
            button.addActionListener(e -> {
                if (game.isIfTest()) {
                    checkIfCorrectAnswer(button.getText(), questions, currentQuestionIndex, game);

                } else {
                    if (button.getText().equals(questions.get(currentQuestionIndex[0]).getWordToTranslate().getTranslation().getWord())) {
                        //TODO Tutaj tez wywal tego game'a
                        questions.get(currentQuestionIndex[0]).setPickByUser(button.getText());
                        currentQuestionIndex[0]++;
                    }
                }
                if (currentQuestionIndex[0] == maxWords) {
                    resultPopup(questions, game);
                    frame.dispose();
                } else {
                    updateUI(buttons, wordToTranslate, questions, currentQuestionIndex[0]);
                }
            });
        }
    }

    private void checkIfCorrectAnswer(String text, List<Question> questions, int[] currentQuestionIndex, LearningGame game) {
        questions.get(currentQuestionIndex[0]).setPickByUser(text);
        if (text.equals(questions.get(currentQuestionIndex[0]).getWordToTranslate().getTranslation().getWord())) {
            game.incrementPoint();
        }
        currentQuestionIndex[0]++;
    }

    private void resultPopup(List<Question> questions, LearningGame game) {
        RaportBuilder builder = null;
        Object[] options = {"TXT", "PDF", "Don't save"};
        int n = JOptionPane.showOptionDialog(mainframe, "How would you like to save?", "Save your results", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
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
                if (game.isIfTest()) {
                    builder.addUserAnswer(q.getPickByUser());
                }
            }
        }
        if (n == 0) {
            try {
                JOptionPane.showMessageDialog(mainframe, "Your file has been saved to " + ((TxtBuilder) builder).buildTxt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (n == 1) {
            try {
                JOptionPane.showMessageDialog(mainframe, "Your file has been saved to " + ((PdfBuilder) builder).buildPdf());
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUI(List<JButton> buttons, JLabel wordToTranslate, List<Question> questions, int currentQuestionIndex) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(questions.get(currentQuestionIndex).getAnwswers().get(i).getWord());
        }
        wordToTranslate.setText("Word to translate:  " + questions.get(currentQuestionIndex).getWordToTranslate().getWord());
    }

    private void addMemento(GameMemento gameMemento) {
        memento = gameMemento;
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
}