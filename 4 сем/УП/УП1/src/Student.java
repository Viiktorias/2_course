public class Student extends Person implements Notifiable {
    private String login;
    private String email;

    public Student(String name, String login, String email) {
        super(name);
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", login: '").append(login).append('\'');
        sb.append(", email: '").append(email).append('\'');
        return sb.toString();
    }

    @Override
    public void notify(String message) {
        System.out.println(this + " : " + message);
    }
}
