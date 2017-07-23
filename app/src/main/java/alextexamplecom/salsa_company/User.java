package alextexamplecom.salsa_company;





public class User {
    //unique identifier is the email -> getting through login
    public String email;
    public String firstName;
    public String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Location of the dance school
    public String location;
}
