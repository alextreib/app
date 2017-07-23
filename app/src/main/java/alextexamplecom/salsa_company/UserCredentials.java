package alextexamplecom.salsa_company;


public class UserCredentials {
    //unique identifier is the email -> getting through login
    private String email;
    private String pwd;

    public UserCredentials(String firstName, String lastName) {
        this.email = firstName;
        this.pwd = lastName;
    }

    public static String encrypt(UserCredentials userCre)
    {
        return userCre.email + userCre.pwd;
    }

    public static UserCredentials encrypt(String encrypStr)
    {
        UserCredentials userCred=new UserCredentials("No implemented yet","No implemented yet");
        return userCred;
    }
}