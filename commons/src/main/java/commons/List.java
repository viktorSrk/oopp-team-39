// SKELETON CLASS

package commons;

import javax.persistence.*;

@Entity
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    /**
     * Creates a new List. List for cards is initialized and number_of_cards set to 0
     * @param title the title of the new List
     */
    public List(String title) {
        this.title = title;
    }

    /**
     * No argument constructor of a List. Sets title to "New List".
     */
    public List() {
        this.title = "New List";
    }

    /**
     * Gets the ID of the List
     * @return the ID of this list
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the Title of this List
     * @return the title of this list
     */
    public String getTitle() {
        return title;
    }
}
