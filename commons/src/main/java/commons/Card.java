package commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import java.util.ArrayList;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;

    @OrderColumn
    private int position;

    @ElementCollection(fetch=FetchType.EAGER)
    private java.util.List<String> tasks = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    private commons.List list;
    @SuppressWarnings("unused")
    protected Card(){
        //for object mapper
    }

    public Card(String title, List list, int pos) {
        this.title = title;
        this.list = list;
        this.position = pos;
        this.description = "";
    }

    public Card(String title){
        this.title = title;
        this.description = "";
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List getList() {
        return list;
    }

    public String getDescription() {
        return description;
    }

    public java.util.List<String> getTasks() {
        return tasks;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTasks(java.util.List<String> tasks) {
        this.tasks.clear();
        for (String e : tasks) {
            this.tasks.add(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }


}
