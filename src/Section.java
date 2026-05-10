import java.util.ArrayList;

public class Section {
    private static int nextId = 100;

    int id;
    int subject_id;
    String name;
    String day;
    String time;
    String room;
    ArrayList<Integer> studentIds;

    public Section(

        int _subject_id,
        String _name,
        String _day,
        String _time,
        String _room,
        ArrayList<Integer> _studentIds

        ) {
            this.id = nextId++;
            this.subject_id = _subject_id;
            this.name = _name;
            this.day = _day;
            this.time = _time;
            this.room = _room;
            this.studentIds = _studentIds;
    }

}
