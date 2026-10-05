import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Phishingdetector {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("       PHISHING WEBSITE DETECTOR");
        System.out.println("======================================");

        // Get URL from user
        System.out.print("Enter website URL: ");
        String url = sc.nextLine().trim();

        int score = 0;

        System.out.println("\nEntered URL: " + url);
        System.out.println("--------------------------------------");

        // 1. Validate URL format
        try {
            URI uri = new URI(url);

            if (uri.getScheme() == null ||
                uri.getHost() == null ||
                (!uri.getScheme().equalsIgnoreCase("http")
                && !uri.getScheme().equalsIgnoreCase("https"))) {

                System.out.println("Warning: Invalid website URL format.");
                score++;

            } else {
                System.out.println("Valid website URL format.");
            }

        } catch (Exception e) {
            System.out.println("Warning: Invalid website URL format.");
            score++;
        }

        // 2. Check URL length
        if (url.length() > 80) {

            System.out.println("Warning: URL is unusually long.");
            score++;

        } else {

            System.out.println("URL length looks normal.");

        }

        // 3. Check @ symbol
        if (url.contains("@")) {

            System.out.println("Warning: URL contains @ symbol.");
            score++;

        } else {

            System.out.println("No @ symbol found.");

        }

        // 4. Check HTTPS
        if (url.toLowerCase().startsWith("https://")) {

            System.out.println("Secure HTTPS connection detected.");

        } else {

            System.out.println("Warning: HTTPS connection not detected.");
            score++;

        }

        // 5. Check IP address
        String ipPattern =
                ".*\\b(\\d{1,3}\\.){3}\\d{1,3}\\b.*";

        if (url.matches(ipPattern)) {

            System.out.println("Warning: URL contains an IP address.");
            score++;

        } else {

            System.out.println("No IP address found.");

        }

        // 6. Domain analysis
        try {

            URI domainURI = new URI(url);
            String host = domainURI.getHost();

            if (host == null) {

                System.out.println("Warning: Domain could not be identified.");
                score++;

            } else {

                System.out.println("Domain: " + host);

                // Check domain length
                if (host.length() > 50) {

                    System.out.println(
                        "Warning: Domain name is unusually long."
                    );
                    score++;

                } else {

                    System.out.println(
                        "Domain length looks normal."
                    );

                }

                // Check number of subdomains
                String[] parts = host.split("\\.");

                if (parts.length > 4) {

                    System.out.println(
                        "Warning: Too many subdomains detected."
                    );
                    score++;

                } else {

                    System.out.println(
                        "Domain structure looks normal."
                    );

                }

                // Check suspicious hyphen usage
                if (host.contains("--")) {

                    System.out.println(
                        "Warning: Suspicious hyphen pattern detected."
                    );
                    score++;

                } else {

                    System.out.println(
                        "No suspicious hyphen pattern found."
                    );

                }
            }

        } catch (Exception e) {

            System.out.println(
                "Warning: Unable to analyze domain."
            );
            score++;
        }

        // 7. Check suspicious keywords
        String[] suspiciousWords = {
            "login",
            "verify",
            "account",
            "update",
            "secure",
            "bank",
            "free",
            "bonus",
            "gift"
        };

        int keywordCount = 0;

        for (String word : suspiciousWords) {

            if (url.toLowerCase().contains(word)) {

                System.out.println(
                    "Warning: Suspicious keyword found - " + word
                );

                score++;
                keywordCount++;
            }
        }

        if (keywordCount == 0) {

            System.out.println(
                "No suspicious keywords found."
            );

        }

        // 8. Display phishing score
        System.out.println("--------------------------------------");

        System.out.println("Phishing Score: " + score);

        // 9. Determine final result
        String result;

        if (score <= 1) {

            result = "SAFE WEBSITE";

        } else if (score <= 3) {

            result = "SUSPICIOUS WEBSITE";

        } else {

            result = "PHISHING WEBSITE";
        }

        System.out.println("Result: " + result);

        // 10. Get current date and time
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String scanTime = now.format(formatter);

        System.out.println("Scan Date & Time: " + scanTime);

        // 11. Save scan history
        try {

            FileWriter writer =
                new FileWriter("data/scan_history.txt", true);

            writer.write("URL: " + url + "\n");
            writer.write("Phishing Score: " + score + "\n");
            writer.write("Result: " + result + "\n");
            writer.write("Scan Date & Time: " + scanTime + "\n");
            writer.write("--------------------------------------\n");

            writer.close();

            System.out.println(
                "Scan result saved to scan_history.txt."
            );

        } catch (IOException e) {

            System.out.println(
                "Error saving scan history."
            );
        }

        System.out.println("======================================");

        sc.close();
    }
}