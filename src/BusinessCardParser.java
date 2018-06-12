import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BusinessCardParser {
    public static ContactInfo getContactInfo(String document) {
        ContactInfo newContact = new ContactInfo();

        List<String> possibleNames = new ArrayList<String>();
        List<String> possiblePhoneNumbers = new ArrayList<String>();

        Scanner scnr = new Scanner(document);

        String emailUsername = "Unspecified";

        while (scnr.hasNextLine()) {
            String docLine = scnr.nextLine();

            // If an email address has not been set, and the docLine contains a valid email address.
            if (newContact.getEmailAddress().equals("Unspecified")&& verifyEmail(docLine)) {
                newContact.setEmailAddress(docLine);
                // Sets emailUsername to a string of all characters before '@' in the email address.
                emailUsername = docLine.substring(0, docLine.indexOf('@'));
            }
            // if the docLine contains any numbers, it is a possible phonenumber.
            else if (docLine.matches(".*\\d+.*")) {
                possiblePhoneNumbers.add(docLine);
            }
            else {
                possibleNames.add(docLine);
            }

        }
        scnr.close();

        newContact.setName(parseName(possibleNames, emailUsername));

        newContact.setPhoneNumber(parsePhoneNumber(possiblePhoneNumbers));

        return newContact;
    }

    private static boolean verifyEmail(String possibleEmail) {
        if (possibleEmail.contains("@")) {

            List<String> emailTLDs = new ArrayList<String>(Arrays.asList(
                    ".com", ".org", ".net", ".gov", ".edu", ".mil"));

            //A valid email address must have a valid top level domain
            
            return emailTLDs.contains(possibleEmail.substring(
                    possibleEmail.length() - 4, possibleEmail.length()));
        }

        return false;
    }

    private static String parseName(List<String> possibleNames,String emailUsername) {
        String fullName = "Unspecified";
        int maxMatches = 0;

        for (String name : possibleNames) {

            //The full name will likely match with the email username
            int numMatches = getNumMatches(name.split(" "), emailUsername);

            if (numMatches > maxMatches) {
                maxMatches = numMatches;
                fullName = name;
            }
        }
        return fullName;
    }

    private static String parsePhoneNumber(List<String> possiblePhoneNumbers) {
        String correctPhoneNumber = "Unspecified";
        for (String phoneNumber : possiblePhoneNumbers) {
            // rawPhoneNumber is stripped to contain only numbers
            String rawPhoneNumber = phoneNumber.replaceAll("[^\\d.]", "");
            // A valid phone number contains at least 10 numbers.
            if (rawPhoneNumber.length() >= 10) {

                // If there is a keyword, such as "tel", then the following
                // number is most likely the phone number
                if (phoneNumber.toLowerCase().contains("tel")
                        || phoneNumber.toLowerCase().contains("phone")) {
                    correctPhoneNumber = rawPhoneNumber;
                    break;
                }
                // otherwise, the phone number is likely the first 10+ digit number
                else if (correctPhoneNumber.equals("Unspecified")) {
                    correctPhoneNumber = rawPhoneNumber;
                }
            }

        }
        return correctPhoneNumber;
    }

    private static Integer getNumMatches(String[] possibleFullNames,String emailUsername) {
        int numMatches = 0;
        for (String name : possibleFullNames) {
            if (emailUsername.toLowerCase().contains(name.toLowerCase())) {
                numMatches++;
            }
        }
        return numMatches;

    }

    public static void main(String[] args) {
        // For testing purposes only
        String docToParse = "Bob Smith" + "\nSoftware Engineer"
                + "\nDecision &amp; Security Technologies"
                + "\nABC Technologies" + "\n123 North 11th Street"
                + "\nSuite 229" + "\nArlington, VA 22209"
                + "\nTel: +1 (703) 555-1259" + "\nFax: +1 (703) 555-1200"
                + "\nbsmith@abctech.com";
        ContactInfo info = getContactInfo(docToParse);
        System.out.println(info.getName() + "       " + info.getPhoneNumber()
                + "       " + info.getEmailAddress());
    }
}
