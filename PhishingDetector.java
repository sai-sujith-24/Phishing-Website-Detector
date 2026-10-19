import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Phishingdetector {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("       PHISHING WEBSITE DETECTOR");
            System.out.println("======================================");
            System.out.println("1. Scan a Website");
            System.out.println("2. View Scan History");
            System.out.println("3. Search Scan History");
            System.out.println("4. Clear Scan History");
            System.out.println("5. Exit");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            // 1. Scan Website
            if (choice.equals("1")) {

                System.out.print("\nEnter website URL: ");
                String url = sc.nextLine().trim();
                // Validate empty URL
             if (url.isEmpty()) {
            System.out.println(
                      "Error: URL cannot be empty. Please try again."
                );
    continue;
}

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

                        System.out.println(
                            "Warning: Invalid website URL format."
                        );

                        score++;

                    } else {

                        System.out.println(
                            "Valid website URL format."
                        );
                    }

                } catch (Exception e) {

                    System.out.println(
                        "Warning: Invalid website URL format."
                    );

                    score++;
                }

                // 2. Check URL length
                if (url.length() > 80) {

                    System.out.println(
                        "Warning: URL is unusually long."
                    );

                    score++;

                } else {

                    System.out.println(
                        "URL length looks normal."
                    );
                }

                // 3. Check @ symbol
                if (url.contains("@")) {

                    System.out.println(
                        "Warning: URL contains @ symbol."
                    );

                    score++;

                } else {

                    System.out.println(
                        "No @ symbol found."
                    );
                }

                // 4. Check HTTPS
                if (url.toLowerCase().startsWith("https://")) {

                    System.out.println(
                        "Secure HTTPS connection detected."
                    );

                } else {

                    System.out.println(
                        "Warning: HTTPS connection not detected."
                    );

                    score++;
                }

                // 5. Check IP address
                String ipPattern =
                        ".*\\b(\\d{1,3}\\.){3}\\d{1,3}\\b.*";

                if (url.matches(ipPattern)) {

                    System.out.println(
                        "Warning: URL contains an IP address."
                    );

                    score++;

                } else {

                    System.out.println(
                        "No IP address found."
                    );
                }

                // 6. Domain analysis
                try {

                    URI domainURI = new URI(url);
                    String host = domainURI.getHost();

                    if (host == null) {

                        System.out.println(
                            "Warning: Domain could not be identified."
                        );

                        score++;

                    } else {

                        System.out.println(
                            "Domain: " + host
                        );

                        // Domain length
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

                        // Subdomain check
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

                        // Hyphen check
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

                // 7. Suspicious keywords
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
                            "Warning: Suspicious keyword found - "
                            + word
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

                // Display score
                System.out.println("--------------------------------------");

                System.out.println(
                    "Phishing Score: " + score
                );

                // Determine result
                String result;

                if (score <= 1) {

                    result = "SAFE WEBSITE";

                } else if (score <= 3) {

                    result = "SUSPICIOUS WEBSITE";

                } else {

                    result = "PHISHING WEBSITE";
                }

                System.out.println(
                    "Result: " + result
                );

                // Scan timestamp
                LocalDateTime now = LocalDateTime.now();

                DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                    );

                String scanTime = now.format(formatter);

                System.out.println(
                    "Scan Date & Time: " + scanTime
                );

                // Save scan history
                try {

                    FileWriter writer =
                        new FileWriter(
                            "data/scan_history.txt",
                            true
                        );

                    writer.write(
                        "URL: " + url + "\n"
                    );

                    writer.write(
                        "Phishing Score: " + score + "\n"
                    );

                    writer.write(
                        "Result: " + result + "\n"
                    );

                    writer.write(
                        "Scan Date & Time: " + scanTime + "\n"
                    );

                    writer.write(
                        "--------------------------------------\n"
                    );

                    writer.close();

                    System.out.println(
                        "Scan result saved to scan_history.txt."
                    );

                } catch (IOException e) {

                    System.out.println(
                        "Error saving scan history."
                    );
                }
            }

            // 2. View Scan History
            else if (choice.equals("2")) {

                System.out.println();
                System.out.println("======================================");
                System.out.println("           SCAN HISTORY");
                System.out.println("======================================");

                try {

                    BufferedReader reader =
                        new BufferedReader(
                            new FileReader(
                                "data/scan_history.txt"
                            )
                        );

                    String line;
                    boolean hasHistory = false;

                    while ((line = reader.readLine()) != null) {

                        System.out.println(line);
                        hasHistory = true;
                    }

                    reader.close();

                    if (!hasHistory) {

                        System.out.println(
                            "No scan history found."
                        );
                    }

                } catch (IOException e) {

                    System.out.println(
                        "Unable to read scan history."
                    );
                }
            }

            // 3. Search Scan History
            else if (choice.equals("3")) {

                System.out.println();
                System.out.println("======================================");
                System.out.println("        SEARCH SCAN HISTORY");
                System.out.println("======================================");

                System.out.print(
                    "Enter URL or keyword to search: "
                );

                String search =
                    sc.nextLine().trim().toLowerCase();

                try {

                    BufferedReader reader =
                        new BufferedReader(
                            new FileReader(
                                "data/scan_history.txt"
                            )
                        );

                    String line;
                    String record = "";
                    boolean found = false;

                    while ((line = reader.readLine()) != null) {

                        record = record + line + "\n";

                        if (line.startsWith(
                                "--------------------------------------")) {

                            if (record.toLowerCase().contains(search)) {

                                System.out.println(record);
                                found = true;
                            }

                            record = "";
                        }
                    }

                    reader.close();

                    if (!found) {

                        System.out.println(
                            "No matching scan history found."
                        );
                    }

                } catch (IOException e) {

                    System.out.println(
                        "Unable to read scan history."
                    );
                }
            }

            // 4. Clear Scan History
           // 4. Clear Scan History
else if (choice.equals("4")) {

    System.out.println();
    System.out.print(
        "Are you sure you want to clear scan history? (yes/no): "
    );

    String confirmation = sc.nextLine().trim();

    if (confirmation.equalsIgnoreCase("yes")) {

        File historyFile = new File("data/scan_history.txt");

        if (historyFile.exists()) {

            try (FileWriter writer = new FileWriter(historyFile, false)) {

                System.out.println();
                System.out.println("Scan history cleared successfully!");

            } catch (IOException e) {

                System.out.println("Unable to clear scan history.");
            }

        } else {

            System.out.println("No scan history file found.");
        }

    } else {

        System.out.println("Clear history cancelled.");
    }
}
            // 5. Exit
            else if (choice.equals("5")) {

                System.out.println();
                System.out.println(
                    "Thank you for using Phishing Website Detector."
                );

                System.out.println(
                    "Exiting program..."
                );

                break;
            }

            // Invalid choice
            else {

                System.out.println();

                System.out.println(
                    "Invalid choice. Please enter 1 to 5."
                );
            }
        }

        sc.close();
    }
}