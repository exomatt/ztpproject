package Builder;

import model.Word;

import java.io.BufferedWriter;
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
    private List<Word> userAnswersList;

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
    public void addUserAnswer(Word userAnswer) {
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
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        int[] sizes;
        if (userAnswersList == null) {
            sizes = new int[]{wordsToTranslateList.size(), answersList.size(), correctAnswers.size()};
        } else {
            sizes = new int[]{wordsToTranslateList.size(), answersList.size(), correctAnswers.size(), userAnswersList.size()};
        }
        IntSummaryStatistics stat = Arrays.stream(sizes).summaryStatistics();
        int maxSize = stat.getMax();
        String resultPath = "results/results.txt";
        fileWriter = new FileWriter(resultPath);
        bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < maxSize; i++) {
            bufferedWriter.write("\nWord to translate: " + wordsToTranslateList.get(i).getWord() + "\n\n");
            for (int j = 0; j < answersList.get(i).size(); j++) {
                bufferedWriter.write(String.valueOf(j) + ". " + answersList.get(i).get(j).getWord() + "\n\n");
            }
            bufferedWriter.write("Correct answer: " + correctAnswers.get(i).getWord() + "\n");
            if (userAnswersList != null) {
                bufferedWriter.write("Users answer: " + userAnswersList.get(i).getWord() + "\n");
            }
        }
        bufferedWriter.close();
        fileWriter.close();
        return "Path has been saved to " + resultPath;
    }
}
