package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
public class Word {
    @Id
    @Column(name = "WORD", nullable = false)
    private String word;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "WORD")
    private Word translation;
    private String language;

    public Word() {
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", translation=" + translation.getWord()
                +
                ", language='" + language + '\'' +
                '}';
    }
}
