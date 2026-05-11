import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========= Welcome to MiniBusi =========");
            loginMenu();

        }
    }

    // ============== Login/Register Student ==============

    public static void loginMenu() {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = DataBase.loginDB(login, password);

        if (user == null) {
            System.out.println("Wrong login or password");
            return;
        }

        System.out.println("\nWelcome " + user.name);

        if ("student".equals(user.post)) {
            showStudentProfile(user);
        } else if ("teacher".equals(user.post)) {
            showTeacherMenu(user);
        } else if ("admin".equals(user.post)) {
            showAdminMenu(user);
        } else {
            System.out.println("Problem with user.post -----> " + user.post);
        }
    }



    // ============== Student/Teacher/Admin Menus ==============

    public static void showTeacherMenu(User user) {
        while (true) {
            System.out.println("\n========= TEACHER MENU =========");
            System.out.println("1. User Info");
            System.out.println("2. Schedule");
            System.out.println("3. Grades Menu");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showUserInfo(user);
                    break;
                case "2":
                    showSchedule(user);
                    break;
                case "3":
                    teacherGradesMenu(user);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    public static void showStudentProfile(User user) {
        while (true) {
            System.out.println("\n========= STUDENT MENU =========");
            System.out.println("1. User Info");
            System.out.println("2. Schedule");
            System.out.println("3. Grades");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showUserInfo(user);
                    break;
                case "2":
                    showSchedule(user);
                    break;
                case "3":
                    showGrades(user);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    public static void showAdminMenu(User user) {
        while (true) {
            System.out.println("\n========= ADMIN MENU =========");
            System.out.println("1. Users Management");
            System.out.println("2. Subjects Management");
//            System.out.println("3. Grades Management");
            System.out.println("3. Sections Management");
            System.out.println("0. Back");

            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userManagementMenu();
                    break;
                case "2":
                    subjectManagementMenu();
                    break;
//                case "3":
//                    gradeManagementMenu();
//                    break;
                case "3":
                    sectionManagementMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }



    // ============== User Functions ==============

    public static void showUserInfo(User user) {
        System.out.println("\n========= USER INFO =========");
        System.out.println("ID: " + user.id);
        System.out.println("Name: " + user.name);
        System.out.println("Surname: " + user.surname);
        System.out.println("Department: " + user.department);
        System.out.println("Post: " + user.post);
        System.out.println("Birth: " + user.date_of_birth);

        System.out.println("\n1. Edit");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        String choice = scanner.nextLine();


        switch (choice) {
            case "1":
                editUserProfile(user);
                break;
            case "0":
                return;
            default:
                System.out.println("Wrong choice");
        }
    }

    public static void editUserProfile(User user) {
        System.out.println("\n========= EDIT USER INFO =========");
        System.out.println("1. Edit Name: " + user.name);
        System.out.println("2. Edit Surname: " + user.surname);
        System.out.println("3. Edit Birth: " + user.date_of_birth);
        System.out.println("4. Edit Password: **************");
        System.out.println("0. Back");

        System.out.print("Choose: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Write new name: ");
                DataBase.updateUserField(user, "name", scanner.nextLine());
                break;
            case "2":
                System.out.print("Write new surname: ");
                DataBase.updateUserField(user, "surname", scanner.nextLine());
                break;
            case "3":
                System.out.print("Write new birth: ");
                DataBase.updateUserField(user, "birth", scanner.nextLine());
                break;
            case "4":
                System.out.print("Write new password: ");
                DataBase.updateUserField(user, "password", scanner.nextLine());
                break;
            case "0":
                return;
            default:
                System.out.println("Wrong choice");
        }
    }

    public static void showSchedule(User user) {
        System.out.println("\n========= SCHEDULE =========");

        boolean found = false;

        if ("student".equals(user.post)) {
            for (Section section : DataBase.sections.values()) {
                if (section.studentIds.contains(user.id)) {
                    Subject subject = DataBase.subjects.get(section.subject_id);

                    if (subject == null) continue;

                    System.out.println(
                            subject.name + " | " +
                                    section.name + " | " +
                                    section.day + " | " +
                                    section.time + " | " +
                                    section.room
                    );

                    found = true;
                }
            }
        }
        else if ("teacher".equals(user.post)) {
            for (Subject subject : DataBase.subjects.values()) {
                if (subject.teacherIds.contains(user.id)) {
                    for (Section section : DataBase.sections.values()) {
                        if (section.subject_id == subject.id) {
                            System.out.println(
                                    "Subject : " + subject.name + "\n" +
                                            "Section : " + section.name + "\n" +
                                            "Day     : " + section.day + "\n" +
                                            "Time    : " + section.time + "\n" +
                                            "Room    : " + section.room + "\n" +
                                            "-----------------------------"
                            );

                            found = true;
                        }
                    }
                }
            }
        }

        if (!found) {
            System.out.println("Schedule is empty.");
        }
    }

    public static void showGrades(User user) {
        System.out.println("\n========= GRADES =========");

        boolean found = false;

        for (Grade grade : DataBase.grades.values()) {
            if (grade.student_id == user.id) {
                Section section = DataBase.sections.get(grade.section_id);
                if (section == null) continue;

                Subject subject = DataBase.subjects.get(section.subject_id);
                if (subject == null) continue;

                System.out.println(
                        "Subject : " + subject.name + "\n" +
                                "Section : " + section.name + "\n" +
                                "Grade   : " + grade.value + "\n" +
                                "-----------------------------"
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No grades yet.");
        }
    }

    // ============== Teacher Functions ==============

    public static void teacherGradesMenu(User teacher) {
        while (true) {
            System.out.println("\n========= TEACHER GRADES MENU =========");
            System.out.println("1. Show my students grades");
            System.out.println("2. Add grade");
            System.out.println("3. Edit grade");
            System.out.println("4. Delete grade");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showTeacherGrades(teacher);
                    break;
                case "2":
                    setGradeForStudent(teacher);
                    break;
                case "3":
                    editGrade(teacher);
                    break;
                case "4":
                    deleteGrade(teacher);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    public static void setGradeForStudent(User teacher) {
        System.out.println("\n========= ADD GRADE =========");

        ArrayList<Section> teacherSections = getTeacherSections(teacher);

        if (teacherSections.isEmpty()) {
            System.out.println("You don't have sections.");
            return;
        }

        printTeacherSections(teacher);

        System.out.print("Choose section ID: ");
        int sectionId = readInt();

        Section selectedSection = DataBase.sections.get(sectionId);

        if (selectedSection == null || !teacherSections.contains(selectedSection)) {
            System.out.println("Wrong section.");
            return;
        }

        printStudentsFromSection(selectedSection);

        System.out.print("Choose student ID: ");
        int studentId = readInt();

        if (!selectedSection.studentIds.contains(studentId)) {
            System.out.println("This student is not in this section.");
            return;
        }

        System.out.print("Write grade: ");
        float value = readFloat();

        DataBase.addGradeToSubjectDB(studentId, sectionId, value);

        System.out.println("Grade added!");
    }

    public static boolean showTeacherGrades(User teacher) {
        System.out.println("\n========= MY STUDENTS GRADES =========");

        boolean found = false;

        for (Grade grade : DataBase.grades.values()) {
            if (!canTeacherAccessGrade(teacher, grade)) continue;

            User student = DataBase.users.get(grade.student_id);
            Section section = DataBase.sections.get(grade.section_id);
            Subject subject = DataBase.subjects.get(section.subject_id);

            if (student == null || subject == null) continue;

            System.out.println(
                    "Grade ID : " + grade.id + "\n" +
                            "Student  : " + student.name + " " + student.surname + "\n" +
                            "Subject  : " + subject.name + "\n" +
                            "Section  : " + section.name + "\n" +
                            "Grade    : " + grade.value + "\n" +
                            "-----------------------------"
            );

            found = true;
        }

        if (!found) {
            System.out.println("No grades found.");
        }
        return found;
    }

    public static void editGrade(User teacher) {
        boolean checker = showTeacherGrades(teacher);


        if (checker) { System.out.print("Write grade ID to edit: ");
            int gradeId = readInt();

            Grade grade = DataBase.grades.get(gradeId);

            if (grade == null) {
                System.out.println("Grade not found.");
                return;
            }

            if (!canTeacherAccessGrade(teacher, grade)) {
                System.out.println("You can't edit this grade.");
                return;
            }

            System.out.print("Write new grade: ");
            float newValue = readFloat();

            grade.value = newValue;

            System.out.println("Grade updated!");}

    }

    public static void deleteGrade(User teacher) {
        boolean checker = showTeacherGrades(teacher);

        if (checker) {
            System.out.print("Write grade ID to delete: ");
            int gradeId = readInt();

            Grade grade = DataBase.grades.get(gradeId);

            if (grade == null) {
                System.out.println("Grade not found.");
                return;
            }

            if (!canTeacherAccessGrade(teacher, grade)) {
                System.out.println("You can't delete this grade.");
                return;
            }

            DataBase.deleteGradeDB(gradeId);

            System.out.println("Grade deleted!");
        }
    }

    public static ArrayList<Section> getTeacherSections(User teacher) {
        ArrayList<Section> teacherSections = new ArrayList<>();

        for (Subject subject : DataBase.subjects.values()) {
            if (subject.teacherIds.contains(teacher.id)) {
                for (Section section : DataBase.sections.values()) {
                    if (section.subject_id == subject.id) {
                        teacherSections.add(section);
                    }
                }
            }
        }

        return teacherSections;
    }

    public static void printTeacherSections(User teacher) {
        System.out.println("\n========= YOUR SECTIONS =========");

        for (Subject subject : DataBase.subjects.values()) {
            if (subject.teacherIds.contains(teacher.id)) {
                for (Section section : DataBase.sections.values()) {
                    if (section.subject_id == subject.id) {
                        System.out.println(
                                section.id + " | " +
                                        subject.name + " | " +
                                        section.name + " | " +
                                        section.day + " | " +
                                        section.time + " | " +
                                        section.room
                        );
                    }
                }
            }
        }
    }

    public static void printStudentsFromSection(Section section) {
        System.out.println("\n========= STUDENTS =========");

        for (Integer studentId : section.studentIds) {
            User student = DataBase.users.get(studentId);

            if (student != null && "student".equals(student.post)) {
                System.out.println(
                        student.id + " | " +
                                student.name + " " +
                                student.surname
                );
            }
        }
    }

    public static boolean canTeacherAccessGrade(User teacher, Grade grade) {
        Section section = DataBase.sections.get(grade.section_id);
        if (section == null) return false;

        Subject subject = DataBase.subjects.get(section.subject_id);
        if (subject == null) return false;

        return subject.teacherIds.contains(teacher.id);
    }

    public static int readInt() {
        try {

            return Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            // fix of return not always -1
            return -1;
        }
    }

    public static float readFloat() {
        try {
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ============== Admin Menu Functions ==============

    public static void subjectManagementMenu() {
        while (true) {
            System.out.println("\n========= SUBJECT MANAGEMENT MENU =========");
            System.out.println("1. Create a new subject");
            System.out.println("2. Show all subjects");
            System.out.println("3. Edit subject");
            System.out.println("4. Delete subject");
            System.out.println("0. Back");

            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewSubject();
                    break;
                case "2":
                    showAllSubjects();
                    break;
                case "3":
                    editSubjects();
                    break;
                case "4":
                    deleteSubject();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong choice");
            }

        }
    }

    public static void userManagementMenu() {
        while (true) {
            System.out.println("\n========= USERS MANAGEMENT MENU =========");
            System.out.println("1. Register a new user");
            System.out.println("2. Show all users");
            System.out.println("3. Edit user");
            System.out.println("4. Delete user");
            System.out.println("0. Back");

            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    showAllUsers();
                    break;
                case "3":
                    editUser();
                    break;
                case "4":
                    deleteUser();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Wrong choice");
            }

        }
    }

    public static void sectionManagementMenu() {
        while (true) {
            System.out.println("\n========= SECTION MANAGEMENT MENU =========");
            System.out.println("1. Create a new section");
            System.out.println("2. Show all sections");
            System.out.println("3. Edit section");
            System.out.println("4. Delete section");
            System.out.println("0. Back");

            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewSection();
                    break;

                case "2":
                    showAllSections();
                    break;

                case "3":
                    editSection();
                    break;

                case "4":
                    deleteSection();
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Wrong choice");
            }

        }
    }

    public static void gradeManagementMenu() {

    }

    // ============== Admin Functions ==============

    public static void registerNewSection() {
        System.out.println("\n========= REGISTER SECTION =========");

        for (Subject subject : DataBase.getAllSubjects()) {
            System.out.println(subject.id + " | " +subject.name);
        }

        System.out.print("Write ID of the Subject to add: ");
        int subjectID = readInt();

        System.out.print("Name for this section: ");
        String name = scanner.nextLine();

        System.out.print("Day of the section: ");
        String day = scanner.nextLine();

        System.out.print("Time for this section: ");
        String time = scanner.nextLine();

        System.out.print("Room for this section: ");
        String room = scanner.nextLine();

        ArrayList<Integer> studentsIDs= new ArrayList<>();

        System.out.println("\n========= STUDENTS TO ADD =========\n");

        for (User user : DataBase.getAllUsers()) {
            if (user.post.equals("student")){
                System.out.println(user.id + " | " + user.name);
            }
        }

        System.out.println(
                "\nWrite list of the ids  Example: <321321 312123 321312> "
                        + "\n" +
                "OR Just one ID Example : <321321>");

        System.out.print("Choice: ");
        String[] ids = scanner.nextLine().split(" ");


        for (String id : ids) {
            studentsIDs.add(Integer.parseInt(id));
        }

        Section newSection = DataBase.createSectionDB(
                subjectID, name, day, time, room, studentsIDs
        );

        if (newSection == null) {
            System.out.println("Section was not created");

        } else {
            System.out.println("\nSection was created successfully!");
            System.out.println(
                    "New section ID: " + newSection.id + "\n" +
                    "New section name: " + newSection.name + "\n"
            );
        }

    }

    public static void registerNewUser() {

        System.out.println("\n========= REGISTER USER =========");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Surname: ");
        String surname = scanner.nextLine();

//        System.out.print("Login: ");
//        String login = scanner.nextLine();
//
//        System.out.print("Password: ");
//        String password = scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        System.out.print("Post (student/teacher/admin): ");
        String post = scanner.nextLine();

        System.out.print("Birth date: ");
        String birth = scanner.nextLine();

        User newUser = DataBase.createUserDB(
                name,
                surname,
                department,
                post,
                birth
        );

        if (newUser == null) {
            System.out.println("User with this login already exists.");
        } else {
            System.out.println("\nUser registered successfully!");
            System.out.println(
                    "New user ID: " + newUser.id + "\n" +
                    "User login: " + newUser.login + "\n" +
                    "User password: " + newUser.password
            );
        }
    }

    public static void registerNewSubject() {
        System.out.println("\n========= CREATE NEW SUBJECT =========");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        ArrayList<User> users = DataBase.getAllUsers();


        for(User user : users) {
            if (user.post.equals("teacher")) {
                System.out.println(
                        user.id + " | " +
                        user.name + " " +
                        user.surname + " | " +
                        user.department + " | " + user.post

                );
            }
        }
        System.out.print("Write teacher id: ");
        ArrayList<Integer> _teacherIds = new ArrayList<>();
        _teacherIds.add(readInt());

        Subject subject = DataBase.createSubjectDB(
                name,
                _teacherIds
        );

        if (subject == null ) {
            System.out.println("\nSubject with this name already exists.");
        } else {
            System.out.println(
                    "\nNew subject ID: " + subject.id + "\n" +
                    "New subject name: " + subject.name + "\n"
            );
        }

    }

    public static void showAllUsers() {
        System.out.println("\n========= USERS =========");

        ArrayList<User> users = DataBase.getAllUsers();

        for (User user : users) {
            System.out.println(
                    user.id + " | " +
                            user.name + " " +
                            user.surname + " | " + user.post
            );
        }
    }

    public static void showAllSubjects() {
        System.out.println("\n========= SUBJECTS =========");

        ArrayList<Subject> subjects = DataBase.getAllSubjects();

        for (Subject subject : subjects) {
            StringBuilder writtenteacherIds = new StringBuilder();
            for (int id : subject.teacherIds) {
                writtenteacherIds.append(DataBase.getUserById(id).name + " | ");
            }
            System.out.println(
                    "Subject ID: " + subject.id +
                    "\nSubject Name: "+ subject.name +
                    "\nTeachers:  " + writtenteacherIds);
            System.out.println("===========================");
        }
    }

    public static void showAllSections() {
        System.out.println("\n========= SECTIONS =========");

        ArrayList<Section> sections = DataBase.getAllSections();

        for (Section section : sections) {
            Subject subject = DataBase.subjects.get(section.subject_id);

            if (subject == null) continue;

            System.out.println(
                    section.id + " | " +
                            subject.name + " | " +
                            section.day + " | " +
                            section.time + " | " +
                            section.room
            );
        }
    }

    public static void showAllGrades() {
        System.out.println("\n========= GRADES =========");

        ArrayList<Grade> grades = DataBase.getAllGrades();

        for (Grade grade : grades) {
            User user = DataBase.users.get(grade.student_id);
            Section section = DataBase.sections.get(grade.section_id);

            if (user == null || section == null) continue;

            Subject subject = DataBase.subjects.get(section.subject_id);

            if (subject == null) continue;

            System.out.println(
                    "Grade ID: " + grade.id + " | " +
                            user.name + " | " +
                            subject.name + " -> " +
                            grade.value
            );
        }
    }

    public static void editSection() {
        showAllSections();

        System.out.print("Write ID of the section: ");
        int id = readInt();

        Section section = DataBase.sections.get(id);

        if (section == null) {
            System.out.println("Section not found!");
            return;
        }

        System.out.println("\n1. Edit ----> Name");
        System.out.println("2. Edit ----> Day");
        System.out.println("3. Edit ----> Time");
        System.out.println("4. Edit ----> Room");
        System.out.println("5. Edit ----> Students");

        System.out.print("\nChoose option: ");
        int option = readInt();

        if (option == 1) {
            System.out.print("Write new section name: ");
            section.name = scanner.nextLine();

        } else if (option == 2) {
            System.out.print("Write new day: ");
            section.day = scanner.nextLine();

        } else if (option == 3) {
            System.out.print("Write new time: ");
            section.time = scanner.nextLine();

        } else if (option == 4) {
            System.out.print("Write new room: ");
            section.room = scanner.nextLine();

        } else if (option == 5) {
            showAllUsers();

            System.out.print("Write student IDs example: 1001 1002: ");
            String input = scanner.nextLine().trim();

            ArrayList<Integer> studentIds = new ArrayList<>();

            if (!input.isEmpty()) {
                String[] ids = input.split("\\s+");

                for (String studentId : ids) {
                    studentIds.add(Integer.parseInt(studentId));
                }
            }

            section.studentIds = studentIds;

        } else {
            System.out.println("Wrong option!");
            return;
        }

        System.out.println("Section updated successfully!");
    }

    public static void editSubjects() {
        showAllSubjects();

        System.out.print("Write ID of the subject: ");
        int id = readInt();

        Subject subject = DataBase.getSubjectById(id);

        if (subject == null) {
            System.out.println("Subject not found!");
            return;
        }

        System.out.println("\n1. Edit ----> Name");
        System.out.println("2. Edit ----> Teachers");

        System.out.print("\nChoose option: ");
        int option = readInt();

        if (option == 1) {
            System.out.print("Write new subject name: ");
            String name = scanner.nextLine();

            DataBase.updateSubjectDB(
                    id,
                    name,
                    subject.teacherIds
            );

        } else if (option == 2) {
            ArrayList<User> users = DataBase.getAllUsers();

            for (User user : users) {

                if (user.post.equals("teacher")) {
                    System.out.println(
                            user.id + " | " +
                                    user.name + " " +
                                    user.surname + " | " + user.post
                    );
                }

            }

            System.out.print("Write teacher IDs example: 10001 10002: ");
            // trim убирает пробелы в начале и в конце
            String input = scanner.nextLine().trim();

            ArrayList<Integer> teacherIds = new ArrayList<>();

            if (!input.isEmpty()) {

                String[] ids = input.split("\\s+");

                for (String teacherId : ids) {
                    teacherIds.add(Integer.parseInt(teacherId));
                }
            }

            DataBase.updateSubjectDB(
                    id,
                    subject.name,
                    teacherIds
            );

        } else {
            System.out.println("Wrong option!");
            return;
        }

        System.out.println("Subject updated successfully!");
    }

    public static void editUser() {
        showAllUsers();

        System.out.print("Write ID of the user: ");
        User userbyID = DataBase.getUserById(readInt());
        editUserProfile(userbyID);

    }

    public static void deleteSubject() {

        showAllSubjects();

        System.out.print("\nWrite ID to delete: ");

        DataBase.deleteSubjectDB(readInt());
    }

    public static void deleteUser() {

        showAllUsers();

        System.out.print("\nWrite ID to delete: ");

        DataBase.deleteUserDB(readInt());
    }

    public static void deleteSection() {

        showAllSections();

        System.out.print("\nWrite ID to delete: ");

        DataBase.deleteSectionDB(readInt());
    }
}