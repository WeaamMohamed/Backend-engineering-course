import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

public class AsyncFileReader {

    public static void main(String[] args) throws InterruptedException {

        Path path = Paths.get("src/test.txt");
        System.out.println("Starting reading from file async, not blocking main...");

        // Asynchronously read the file using CompletableFuture
        CompletableFuture<String> future = readFileAsync(path);
        future.thenAccept(fileContent -> {
            System.out.println("File content is: " + fileContent);
        });

        System.out.println("Program's work is not blocked while reading the file.");
        try {
            future.get(); // Block and wait for the result if needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Asynchronous file reading using CompletableFuture
    public static CompletableFuture<String> readFileAsync(Path path) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Print debug information about the file being read
                System.out.println("Reading file from: " + path);
                return Files.readString(path);
            } catch (IOException e) {
                e.printStackTrace(); // Print stack trace for errors
                return "Error reading file.";
            }
        });
    }
}
