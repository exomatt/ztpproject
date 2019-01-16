package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Word.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
public class Word implements Serializable {
    @Id
    @Column(name = "WORD", nullable = false)
    private String word;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "WORDTranslation")
    private Word translation;
    private String language;

    /**
     * Instantiates a new Word.
     */
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
