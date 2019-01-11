package Builder;

import java.util.List;

public interface RaportBuilder {
    public void addWordToTranslate(String word);

    public void addAnswersList(List<String> answers);

    public void addCorrectAnswer(String correct);

    public void addUserAnswer(String userAnswer);
}
