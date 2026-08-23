import java.util.Scanner;

public class Phishingdetector {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Phishing Website Detector =====");

        System.out.print("Enter website URL: ");
        String url = sc.nextLine();

        System.out.println("\nEntered URL: " + url);

        // Check URL length
        if (url.length() > 80) {
            System.out.println("Warning: URL is unusually long.");
        } else {
            System.out.println("URL length looks normal.");
        }

        // Check @ symbol
        if (url.contains("@")) {
            System.out.println("Warning: URL contains @ symbol.");
        } else {
            System.out.println("No @ symbol found.");
        }

        // Check HTTPS
        if (url.startsWith("https://")) {
            System.out.println("Secure HTTPS connection detected.");
        } else {
            System.out.println("Warning: URL does not use HTTPS.");
        }

        sc.close();
    }
}