import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {

    public static HashMap<Integer, User> users = new HashMap<>();
    public static HashMap<Integer, Subject> subjects = new HashMap<>();
    public static HashMap<Integer, Section> sections = new HashMap<>();
    public static HashMap<Integer, Grade> grades = new HashMap<>();

    static {

        User user_admin = new User(
                "admin",
                "-----",
                "admin",
                "admin",
                "-----",
                "admin",
                "-----"
        );


        users.put(user_admin.id, user_admin);

    }

    // -------------------------------------------------------
    // ================== CRUD SYSTEM ===================
    /*
     A CRUD system (Create, Read, Update, Delete) is a
     foundational software design pattern used to manage data,
     allowing users to create new records, view them, modify
     existing data, and remove records.
    */
    // -------------------------------------------------------


    // -------------------------------------------------------
    // ================ GETTER/READ FUNCTIONS ================
    // -------------------------------------------------------

    public static User loginDB(String login, String password) {
        for (User user : users.values()) {
            if (user.login.equals(login) && user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public static ArrayList<Subject> getAllSubjects() {
        // [{id: 123, name: "Math", [123123, 123123]}]

        return new ArrayList<>(subjects.values());
    }

    public static ArrayList<Section> getAllSections() {
        return new ArrayList<>(sections.values());
    }

    public static ArrayList<Grade> getAllGrades() {
        return new ArrayList<>(grades.values());
    }

    public static Section getSectionById(int id) {
        return sections.get(id);
    }

    public static Grade getGradeById(int id) {
        return grades.get(id);
    }

    public static User getUserById(int id) {
        return users.get(id);
    }

    public static Subject getSubjectById(int id) {
        return subjects.get(id);
    }

    // -------------------------------------------------------
    // ================== CREATE FUNCTIONS ==================
    // -------------------------------------------------------

    public static User createUserDB(
            String name,
            String surname,
            String department,
            String post,
            String dateOfBirth
    ) {

        String baseLogin =
                (name + surname).toLowerCase();

        String basePassword =
                (name + surname).toLowerCase();


        String login;

        int counter = 0;

        boolean exists;

        do {

            exists = false;

            if (counter == 0) {
                login = baseLogin;
            } else {
                login = baseLogin + counter;
            }

            for (User user : users.values()) {

                if (user.login.equals(login)) {
                    exists = true;
                    break;
                }
            }

            counter++;

        } while (exists);

        User newUser = new User(
                name,
                surname,
                login,
                basePassword,
                department,
                post,
                dateOfBirth
        );

        users.put(newUser.id, newUser);

        return newUser;
    }

    public static Section createSectionDB(
            int subjectId,
            String name,
            String day,
            String time,
            String room,
            ArrayList<Integer> studentIds
    ) {
        Section newSection = new Section(
                subjectId,
                name,
                day,
                time,
                room,
                studentIds
        );

        sections.put(newSection.id, newSection);

        return newSection;
    }

    public static Subject createSubjectDB(String name, ArrayList<Integer> teacherIds) {
        for (Subject subject : subjects.values()) {

            if (subject.name.equals(name)) {
                return null;
            }

        }

        Subject newSubject = new Subject(name, teacherIds);
        subjects.put(newSubject.id, newSubject);

        return newSubject;
    }

    // -------------------------------------------------------
    // ================== UPDATES FUNCTIONS ==================
    // -------------------------------------------------------

    public static Section updateSectionDB(
            int id,
            int subjectId,
            String name,
            String day,
            String time,
            String room,
            ArrayList<Integer> studentIds
    ) {
        Section section = sections.get(id);

        if (section == null) {
            return null;
        }

        section.subject_id = subjectId;
        section.name = name;
        section.day = day;
        section.time = time;
        section.room = room;
        section.studentIds = studentIds;

        return section;
    }

    public static Grade updateGradeDB(int id, int studentId, int sectionId, float value) {
        Grade grade = grades.get(id);

        if (grade == null) {
            return null;
        }

        grade.student_id = studentId;
        grade.section_id = sectionId;
        grade.value = value;

        return grade;
    }

    public static Subject updateSubjectDB(int id, String name, ArrayList<Integer> teacherIds) {
        Subject subject = subjects.get(id);

        if (subject == null) {
            return null;
        }

        subject.name = name;
        subject.teacherIds = teacherIds;

        return subject;
    }

    public static void updateUserField(User user, String field, String value) {
        if (user == null) {
            return;
        }

        switch (field) {
            case "name":
                user.name = value;
                break;
            case "surname":
                user.surname = value;
                break;
            case "login":
                user.login = value;
                break;
            case "password":
                user.password = value;
                break;
            case "department":
                user.department = value;
                break;
            case "post":
                user.post = value;
                break;
            case "birth":
                user.date_of_birth = value;
                break;
            default:
                System.out.println("Unknown field");
        }
    }

    // -------------------------------------------------------
    // ================== DELETE FUNCTIONS ==================
    // -------------------------------------------------------

    public static boolean deleteSectionDB(int id) {
        Section removedSection = sections.remove(id);

        if (removedSection == null) {
            return false;
        }

        grades.values().removeIf(grade -> grade.section_id == id);

        return true;
    }

    public static boolean deleteGradeDB(int id) {
        Grade removedGrade = grades.remove(id);
        return removedGrade != null;
    }

    public static boolean deleteUserDB(int id) {
        User removedUser = users.remove(id);

        if (removedUser == null) {
            return false;
        }
        // valueOf для того что бы он не посчитал ID - За индекс. Указывает что это значение
        for (Subject subject : subjects.values()) {
            subject.teacherIds.remove(Integer.valueOf(id));
        }

        for (Section section : sections.values()) {
            section.studentIds.remove(Integer.valueOf(id));
        }
        // removeIf проверяте все значения у которых grade.student_id == user.id и удаляет их всех
        grades.values().removeIf(grade -> grade.student_id == id);

        return true;
    }

    public static boolean deleteSubjectDB(int id) {
        Subject removedSubject = subjects.remove(id);

        if (removedSubject == null) {
            return false;
        }

        ArrayList<Integer> sectionIdsToDelete = new ArrayList<>();

        for (Section section : sections.values()) {
            if (section.subject_id == id) {
                sectionIdsToDelete.add(section.id);
            }
        }

        for (int sectionId : sectionIdsToDelete) {
            deleteSectionDB(sectionId);
        }

        return true;
    }

    // ===========================================================
    // ================== NOT USABLE FUNC ========================
    // ===========================================================




    public static Grade addGradeToSubjectDB(int studentId, int sectionId, float value) {
        Grade newGrade = new Grade(studentId, sectionId, value);

        grades.put(newGrade.id, newGrade);

        return newGrade;
    }








}