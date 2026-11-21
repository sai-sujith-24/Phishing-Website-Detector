# Phishing Website Detector (Java)

Console-based, educational URL-risk checker using Java URI parsing, URL length checks, domain/brand lookalike heuristics, HTTPS checks, keyword checks, and a scan-history feature.

**Important:** A zero heuristic score is not proof a site is safe. This app does not check a live threat-intelligence feed or visit the submitted websites.

## Run
```bash
javac PhishingDetector.java
java PhishingDetector
```

Run commands from the repository root; scan history is written to `data/scan_history.txt`. The application creates the folder if needed. Menu: scan, view history, search history, clear history, exit.

## Example
Enter a URL such as `https://example.com` and inspect the indicator score. The output is a risk hint, not a verified verdict.

## Limitations
Simple keywords and length checks can flag legitimate sites and miss sophisticated phishing pages. Add unit tests, robust domain verification and authoritative threat feeds for serious security uses. The historic sample `data/scan_history.txt` is retained; avoid committing personal browsing history.
