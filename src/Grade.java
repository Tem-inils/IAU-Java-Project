public class Grade {

    // private - доступ только в нутри класса
    // static - общая для всех

    private static int nextId = 205;

    int id;
    int student_id;
    int section_id;
    float value;

    public Grade(
            int _student_id,
            int _section_id,
            float _value
    ) {

        this.id = nextId++;
        this.student_id = _student_id;
        this.section_id = _section_id;
        this.value = _value;

    }

}
