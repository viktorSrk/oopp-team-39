package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;

//IMPORTANT! TaskList uses Integer class right now because the list backend is not visible/made yet

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private final java.util.List<List> taskLists = new ArrayList<>();

    private String name;

    public Board() {
        this.name="empty";
    }

    public Board(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<List> getTaskLists() {
        return taskLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;

        return new EqualsBuilder().append(id, board.id)
                .append(taskLists, board.taskLists)
                .append(name, board.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(taskLists)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Id", id)
                .append("Name", name)
                .append("TaskLists", taskLists)
                .toString();
    }
}
