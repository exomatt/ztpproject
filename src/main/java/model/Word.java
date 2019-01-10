package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
