import java.io.*;  // Import classes for input/output operations
import java.net.*; // Import classes for networking, like ServerSocket and Socket

// Simple web server to serve HTML files for the puzzle game
public class PuzzleWebServer {

    public static void main(String[] args) throws IOException {
        int port = 8000;    // Port number where the server will listen for requests

        // Create a server socket that listens for incoming connections on the specified port
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server running on http://0.0.0.0:" + port);

        while (true) {
            // Wait for a client (browser) to connect
            Socket client = serverSocket.accept();

            // Handle the client request in a separate method
            handleClient(client);
        }
    }

    // Handle a single client request
    private static void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            // Read the request line
            String requestLine = in.readLine();

            // If the request is empty, do nothing
            if (requestLine == null || requestLine.isEmpty()) return;

            // Split the request line into parts (method, path, protocol)
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String path = tokens[1];

            // Default to index.html if root URL is requested
            if (path.equals("/")) path = "/index.html"; // default

            // Create a File object pointing to the requested file
            File file = new File("." + path);

            // If the file does not exist or is a directory, return 404 Not Found
            if (!file.exists() || file.isDirectory()) {
                out.write("HTTP/1.1 404 Not Found\r\n\r\n");
                out.write("404 Not Found");
                out.flush();
                return;
            }

            // Send HTTP response headers for a successful request
            out.write("HTTP/1.1 200 OK\r\n");
            out.write("Content-Type: text/html\r\n\r\n");

            // Send the actual content of the file to the client
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.write(line + "\r\n");
            }
            fileReader.close(); // Close the file reader
            out.flush();        // Make sure all data is sent to the client

        } catch (IOException e) {
            // Print error if reading from client or file fails
            e.printStackTrace();
        } finally {
            // Always try to close the client socket
            try { 
                client.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
        }
    }
}
