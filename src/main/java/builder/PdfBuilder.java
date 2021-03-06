package builder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Pdf builder.
 */
public class PdfBuilder implements RaportBuilder {

    private List<Word> wordsToTranslateList;
    private List<List<Word>> answersList;
    private List<Word> correctAnswersList;
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
        if (correctAnswersList == null) {
            correctAnswersList = new LinkedList<>();
        }
        correctAnswersList.add(correct);
    }

    @Override
    public void addUserAnswer(String userAnswer) {
        if (userAnswersList == null) {
            userAnswersList = new LinkedList<>();
        }
        userAnswersList.add(userAnswer);
    }

    /**
     * Build pdf string.
     *
     * @return the string
     * @throws IOException       the io exception
     * @throws DocumentException the document exception
     */
    public String buildPdf() throws IOException, DocumentException {
        int[] sizes;
        int points;
        if (userAnswersList == null) {
            sizes = new int[]{wordsToTranslateList.size(), answersList.size(), correctAnswersList.size()};
            points = -1;
        } else {
            sizes = new int[]{wordsToTranslateList.size(), answersList.size(), correctAnswersList.size(), userAnswersList.size()};
            points = 0;

        }
        IntSummaryStatistics stat = Arrays.stream(sizes).summaryStatistics();
        int maxSize = stat.getMax();
        Document document = new Document();
        Paragraph preface = new Paragraph();
        File resultPath = new File("results/results.pdf");
        if (!resultPath.getParentFile().exists()) {
            resultPath.getParentFile().mkdirs();
        }
        PdfWriter.getInstance(document, new FileOutputStream(resultPath));

        document.open();
        BaseFont baseFont = BaseFont.createFont("fonts/AbhayaLibre-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 16, Font.NORMAL);
        Font userAnswerFont = new Font(baseFont, 12, Font.BOLD);
        Font answerF = new Font(baseFont, 12, Font.NORMAL);


        for (int i = 0; i < maxSize; i++) {
            Paragraph paragraph = new Paragraph("Word to translate: " + wordsToTranslateList.get(i).getWord() + "\n", font);
            PdfPTable table = new PdfPTable(1);
            if (answersList.get(i).size() == 1) {
                PdfPCell cell = new PdfPCell();
                PdfPCell pdfPCell = new PdfPCell();
                if (answersList.get(i).get(0).getWord().equals(correctAnswersList.get(i).getWord())) {
                    cell.setBackgroundColor(BaseColor.GREEN);
                }
                if (userAnswersList != null) {
                    if (answersList.get(i).get(0).getWord().equals(userAnswersList.get(i))) {
                        cell.setPhrase(new Phrase(answersList.get(i).get(0).getWord(), userAnswerFont));
                    } else {
                        cell.setPhrase(new Phrase(answersList.get(i).get(0).getWord(), answerF));
                        pdfPCell.setPhrase(new Phrase(userAnswersList.get(i), userAnswerFont));
                        table.addCell(pdfPCell);
                    }
                    if (answersList.get(i).get(0).getWord().equals(correctAnswersList.get(i).getWord()) &&
                            answersList.get(i).get(0).getWord().equals(userAnswersList.get(i))
                    ) {
                        points++;

                    }
                } else {
                    cell.setPhrase(new Phrase(answersList.get(i).get(0).getWord(), userAnswerFont));
                }
                table.addCell(cell);
            } else {
                for (int j = 0; j < answersList.get(i).size(); j++) {
                    PdfPCell cell = new PdfPCell();
                    if (answersList.get(i).get(j).getWord().equals(correctAnswersList.get(i).getWord())) {
                        cell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if (userAnswersList != null) {
                        if (answersList.get(i).get(j).getWord().equals(userAnswersList.get(i))) {
                            cell.setPhrase(new Phrase(answersList.get(i).get(j).getWord(), userAnswerFont));
                        } else {
                            cell.setPhrase(new Phrase(answersList.get(i).get(j).getWord(), answerF));
                        }
                        if (answersList.get(i).get(j).getWord().equals(correctAnswersList.get(i).getWord()) &&
                                answersList.get(i).get(j).getWord().equals(userAnswersList.get(i))
                        ) {
                            points++;

                        }
                    } else {
                        cell.setPhrase(new Phrase(answersList.get(i).get(j).getWord(), answerF));
                    }
                    table.addCell(cell);
                }
            }
            preface.add(paragraph);
            preface.add(new Paragraph(" "));
            preface.add(table);
        }
        if (points > -1) {
            preface.add(new Paragraph(" "));
            preface.add(new Phrase(String.format("You had %s/%s points", String.valueOf(points), String.valueOf(maxSize)), font));
        }
        document.add(preface);
        document.close();
        return "File has been saved to " + resultPath.getAbsolutePath();
    }
}
