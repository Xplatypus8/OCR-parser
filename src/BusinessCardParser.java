import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BusinessCardParser {
    public static ContactInfo getContactInfo(String document) {
        ContactInfo updatedContact = new ContactInfo();

        List<String> possibleNames = new ArrayList<String>();
        List<String> possiblePhoneNumbers = new ArrayList<String>();

        Scanner scnr = new Scanner(document);

        while (scnr.hasNextLine()) {
            String docLine = scnr.nextLine();

            if (updatedContact.getEmailAddress().equals("Unspecified")
                    && verifyEmail(docLine)) {
                updatedContact.setEmailAddress(docLine);
            }
            else if (docLine.matches(".*\\d+.*")) {
                possiblePhoneNumbers.add(docLine);
            }
            else {
                possibleNames.add(docLine);
            }

        }
        scnr.close();
        
        String emailUsername = getEmailUsername(updatedContact
                .getEmailAddress());

        updatedContact.setName(parseName(possibleNames, emailUsername));
        
        updatedContact.setPhoneNumber(parsePhoneNumber(possiblePhoneNumbers));
        
        
        return updatedContact;
    }

    private static boolean verifyEmail(String possibleEmail) {
        if (possibleEmail.contains("@")) {

            List<String> emailTLDs = new ArrayList<String>(Arrays.asList(
                    ".com", ".org", ".net", ".gov", ".edu", ".mil"));

            // returns true if the potential address has a valid Top Level
            // Domain
            return emailTLDs.contains(possibleEmail.substring(
                    possibleEmail.length() - 4, possibleEmail.length()));
        }

        return false;
    }

    private static String getEmailUsername(String emailAddress) {
        int cutIndex = 0;

        for (int i = 0; i < emailAddress.length(); i++) {
            if (emailAddress.charAt(i) == '@') {
                cutIndex = i;
                break;
            }
        }

        return emailAddress.substring(0, cutIndex);
    }

    private static String parseName(List<String> possibleNames,
            String emailUsername) {
        String fullName = "Unspecified";
        int maxMatches = 0;

        for (String name : possibleNames) {

            int numMatches = getNumMatches(name.split(" "), emailUsername);

            if (numMatches > maxMatches) {
                maxMatches = numMatches;
                fullName = name;
            }
        }
        return fullName;
    }
    private static String parsePhoneNumber(List<String> possiblePhoneNumbers){
        String correctPhoneNumber = "Unspecified";
        for(String phoneNumber: possiblePhoneNumbers){
            String rawPhoneNumber = phoneNumber.replaceAll("[^\\d.]", "");
            if(rawPhoneNumber.length() >= 10){
                if(phoneNumber.toLowerCase().contains("tel") || phoneNumber.toLowerCase().contains("phone")){
                    correctPhoneNumber = rawPhoneNumber;
                    break;
                }
                else if(correctPhoneNumber.equals("Unspecified")){
                    correctPhoneNumber = rawPhoneNumber;
                }
            }
            
        }
        return correctPhoneNumber;
    }

    private static Integer getNumMatches(String[] possibleNames,
            String emailUsername) {
        int numMatches = 0;
        for (String nameSegment : possibleNames) {
            if (emailUsername.toLowerCase()
                    .contains(nameSegment.toLowerCase())) {
                numMatches++;
            }
        }
        return numMatches;

    }

    public static void main(String[] args) {
        // For testing purposes only
        String docToParse = "Bob Smith"
                + "\nSoftware Engineer"
                + "\nDecision &amp; Security Technologies"
                + "\nABC Technologies"
                + "\n123 North 11th Street"
                + "\nSuite 229"
                + "\nArlington, VA 22209"
                + "\nTel: +1 (703) 555-1259"
                + "\nFax: +1 (703) 555-1200"
                + "\nbsmith@abctech.com";
        ContactInfo info = getContactInfo(docToParse);
        System.out.println(info.getName() + "       "+info.getPhoneNumber()+ "       "
        + info.getEmailAddress());
    }
}
