package Builder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

public class PdfBuilder implements RaportBuilder {

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

    public String buildPdf() throws FileNotFoundException, DocumentException {
        int[] sizes = {wordsToTranslateList.size(), answersList.size(), correctAnswers.size(), userAnswersList.size()};
        IntSummaryStatistics stat = Arrays.stream(sizes).summaryStatistics();
        int maxSize = stat.getMax();
        Document document = new Document();
        Paragraph preface = new Paragraph();
        File resultPath = new File("results/results.pdf");
        if (!resultPath.getParentFile().exists())
            resultPath.getParentFile().mkdirs();
        PdfWriter.getInstance(document, new FileOutputStream(resultPath));

        document.open();
        Font font = new Font(Font.FontFamily.COURIER, 16, Font.NORMAL);
        Font correctAnswerF = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
        Font answerF = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);


        for (int i = 0; i < maxSize; i++) {
            Paragraph paragraph = new Paragraph("Word to translate: " + wordsToTranslateList.get(i) + "\n", font);
            PdfPTable table = new PdfPTable(1);
            for (int j = 0; j < answersList.get(i).size(); j++) {
                PdfPCell cell = new PdfPCell();
                if (answersList.get(i).get(j).equals(correctAnswers.get(i))) {
                    cell.setBackgroundColor(BaseColor.GREEN);
                }
                if (answersList.get(i).get(j).equals(userAnswersList.get(i))) {
                    cell.setPhrase(new Phrase(answersList.get(i).get(j), correctAnswerF));
                } else {
                    cell.setPhrase(new Phrase(answersList.get(i).get(j), answerF));
                }
                table.addCell(cell);

            }
            preface.add(paragraph);
            preface.add(new Paragraph(" "));
            preface.add(table);

        }
        document.add(preface);
        document.close();
        return "File has been saved to " + resultPath;
    }
}
