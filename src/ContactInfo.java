
public class ContactInfo {
    private String name;
    private String phoneNumber;
    private String emailAddress;
    
    public ContactInfo(){
        this.name = "Unspecified";
        this.phoneNumber = "Unspecified";
        this.emailAddress = "Unspecified";
    }
    public String getName(){
        return this.name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getEmailAddress(){
        return this.emailAddress;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
}
