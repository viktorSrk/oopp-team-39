package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    @SuppressWarnings("unused")
    public Card(){
        //for object mapper
    }

    public Card(String title){
        this.title = title;
    }
}
