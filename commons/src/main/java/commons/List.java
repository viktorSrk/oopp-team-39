// this is only meant as a skeleton Class so the Board Class will work

package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    public List() {

    }
}
