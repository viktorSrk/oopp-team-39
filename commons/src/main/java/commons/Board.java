package commons;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

//IMPORTANT! TaskList uses Integer class right now because the list backend is not visible/made yet

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @OneToMany(cascade = CascadeType.PERSIST)
    private final Set<List> TaskLists;

    public Board() {
        TaskLists = new HashSet<>();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Set<List> getTaskLists() {
        return TaskLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return new EqualsBuilder().append(Id, board.Id).append(TaskLists, board.TaskLists).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(Id).append(TaskLists).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Id", Id)
                .append("TaskLists", TaskLists)
                .toString();
    }
}
