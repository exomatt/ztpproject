package builder;

import model.Word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Txt builder.
 */
public class TxtBuilder implements RaportBuilder {

    private List<Word> wordsToTranslateList;
    private List<List<Word>> answersList;
    private List<Word> correctAnswers;
    private List<String> userAnswersList;

    @Override
    public void addWordToTranslate(Word word) {
        if (wordsToTranslateList == null) {
            wordsToTranslateList = new LinkedList<>();
        }
        wordsToTranslateList.add(word);
    }

    @Override
    public void addAnswersList(List<Word> answers) {
        if (answersList == null) {
            answersList = new LinkedList<>();
        }
        answersList.add(answers);
    }

    @Override
    public void addCorrectAnswer(Word correct) {
        if (correctAnswers == null) {
            correctAnswers = new LinkedList<>();
        }
        correctAnswers.add(correct);
    }

    @Override
    public void addUserAnswer(String userAnswer) {
        if (userAnswersList == null) {
            userAnswersList = new LinkedList<>();
        }
        userAnswersList.add(userAnswer);
    }

    /**
     * Build txt string.
     *
     * @return the string
     * @throws IOException the io exception
     */
    public String buildTxt() throws IOException {
        BufferedWriter bufferedWriter;
        int points = 0;
        FileWriter fileWriter;
        int[] sizes;
        if (userAnswersList == null) {
            sizes = new int[]{wordsToTranslateList.size(), answersList.size(), correctAnswers.size()};
            points = -1;
        } else {
            sizes = new int[]{wordsToTranslateList.size(), answersList.size(), correctAnswers.size(), userAnswersList.size()};
        }
        IntSummaryStatistics stat = Arrays.stream(sizes).summaryStatistics();
        int maxSize = stat.getMax();
        File resultPath = new File("results/results.txt");
        if (!resultPath.getParentFile().exists())
            resultPath.getParentFile().mkdirs();
        fileWriter = new FileWriter(resultPath);
        bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < maxSize; i++) {
            bufferedWriter.write("\nWord to translate: " + wordsToTranslateList.get(i).getWord() + "\n\n");
            for (int j = 0; j < answersList.get(i).size(); j++) {
                bufferedWriter.write(String.valueOf(j + 1) + ". " + answersList.get(i).get(j).getWord() + "\n\n");
                if (userAnswersList != null) {
                    if (answersList.get(i).get(j).getWord().equals(correctAnswers.get(i).getWord()) &&
                            answersList.get(i).get(j).getWord().equals(userAnswersList.get(i))
                    ) {
                        points++;
                    }
                }
            }
            bufferedWriter.write("Correct answer: " + correctAnswers.get(i).getWord() + "\n");
            if (userAnswersList != null) {
                bufferedWriter.write("Users answer: " + userAnswersList.get(i) + "\n");
            }
        }
        if (points > -1) {
            bufferedWriter.write("\nYou've got " + String.valueOf(points) + "/" + String.valueOf(maxSize) + " points");
        }
        bufferedWriter.close();
        fileWriter.close();
        return "Path has been saved to " + resultPath.getAbsolutePath();
    }
}
