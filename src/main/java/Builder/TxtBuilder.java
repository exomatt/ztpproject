package Builder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

public class TxtBuilder implements RaportBuilder {

    private List<String> wordsToTranslateList;
    private List<List<String>> answersList;
    private List<String> correctAnswers;
    private List<String> userAnswersList;

    @Override
    public void addWordToTranslate(String word) {
        if (wordsToTranslateList == null) {
            wordsToTranslateList = new LinkedList<>();
        }
        wordsToTranslateList.add(word);
    }

    @Override
    public void addAnswersList(List<String> answers) {
        if (answersList == null) {
            answersList = new LinkedList<>();
        }
        answersList.add(answers);
    }

    @Override
    public void addCorrectAnswer(String correct) {
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

    public String buildTxt() throws IOException {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        int[] sizes = {wordsToTranslateList.size(), answersList.size(), correctAnswers.size(), userAnswersList.size()};
        IntSummaryStatistics stat = Arrays.stream(sizes).summaryStatistics();
        int maxSize = stat.getMax();
        String resultPath = "results/results.txt";
        fileWriter = new FileWriter(resultPath);
        bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < maxSize; i++) {
            bufferedWriter.write("\nWord to translate: " + wordsToTranslateList.get(i) + "\n\n");
            for (int j = 0; j < answersList.get(i).size(); j++) {
                bufferedWriter.write(String.valueOf(j) + ". " + answersList.get(i).get(j) + "\n\n");
            }
            bufferedWriter.write("Correct answer: " + correctAnswers.get(i) + "\n");
            bufferedWriter.write("Users answer: " + userAnswersList.get(i) + "\n");
        }
        bufferedWriter.close();
        fileWriter.close();
        return "Path has been saved to " + resultPath;
    }
}
