import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class syncFileReader {

    public static void main(String[] args) {

        Path path = Paths.get("src/test.txt");
        String fileContent;
        System.out.println("Starting to read from the file, blocking the main thread....");

        try {
            // Read the file content synchronously (blocking)
            fileContent = Files.readString(path);
        } catch (IOException e) {
            // Handle file read errors
            throw new RuntimeException("Error reading the file: " + e.getMessage(), e);
        }

        System.out.println("File content is: " + fileContent);

        System.out.println("Program's work has resumed.");
    }
}
