import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class URLFetcher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a URL:");
        String userInput = scanner.nextLine();

        try {
            URL url = new URL(userInput);

            // Open a connection to the URL and cast it to HttpURLConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            httpURLConnection.setRequestMethod("GET");

            // Get the input stream of the connection
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Print the response
                System.out.println(content.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
