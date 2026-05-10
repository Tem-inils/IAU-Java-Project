public class User {

    private static int nextId = 1000;

    int id;
    String name;
    String surname;
    String login;
    String password;
    String department;
    String post;
    String date_of_birth;

    public User(
            String _name,
            String _surname,
            String _login,
            String _password,
            String _department,
            String _post,
            String _date_of_birth
    ) {
        this.id = nextId++;
        this.name = _name;
        this.surname = _surname;
        this.login = _login;
        this.password = _password;
        this.department = _department;
        this.post = _post;
        this.date_of_birth = _date_of_birth;

    }
}
