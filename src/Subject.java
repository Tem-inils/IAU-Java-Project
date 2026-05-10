import java.util.ArrayList;

public class Subject {
     private static int nextId = 1;

    int id;
    String name;
    ArrayList<Integer> teacherIds;

    public Subject (String _name, ArrayList<Integer> _teacherIds) {
        this.id = nextId++;
        this.name = _name;
        this.teacherIds = _teacherIds;
    }

}
