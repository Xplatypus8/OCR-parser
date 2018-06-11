import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class BusinessCardParser {
    public static ContactInfo getContactInfo(String document){
        ContactInfo updatedContact= new ContactInfo();
        
        List<String> possibleNames = new ArrayList<String>();
        List<String> possiblePhoneNumbers = new ArrayList<String>();
        
        Scanner scnr = new Scanner(document);
        
        while(scnr.hasNextLine()){
            String docLine = scnr.nextLine();
            
            if(updatedContact.getEmailAddress().equals("Unspecified") && verifyEmail(docLine)){
                updatedContact.setEmailAddress(docLine);
            }
            else if(docLine.contains("\\d+")){
                possiblePhoneNumbers.add(docLine);
            }
            else{
                possibleNames.add(docLine);
            }
            
        }
        return null;
    }
    private static boolean verifyEmail(String possibleEmail){
        if(possibleEmail.contains("@")){
            List<String> emailTLDs = new ArrayList<String>(
                    Arrays.asList(".com", ".org", ".net", ".gov", ".edu", ".mil"));
        return emailTLDs.contains(possibleEmail.substring(possibleEmail.length()-4, possibleEmail.length()));
        }
        
        return false;
    }
    private static String parseName(List<String> possibleNames){
        String fullName = "";
        for(String name: possibleNames){
            name.split(" ");
        }
        return "";
    }
    
    
    public static void main(String [] args){
        String docToParse = "";
        ContactInfo info = getContactInfo(docToParse);
    }
}
