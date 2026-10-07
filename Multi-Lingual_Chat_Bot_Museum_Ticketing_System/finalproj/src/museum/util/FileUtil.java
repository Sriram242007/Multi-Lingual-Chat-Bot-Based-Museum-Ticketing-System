package museum.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * FileUtil.java
 *
 * @author Sriram S
 * @version 1.0
 */
public class FileUtil {

    /**
     * Read all lines from file
     * Creates file if it doesn't exist
     */
    public static List<String> readLines(String filePath) throws IOException {
        try {
            // Try to read existing file
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                // Create file if doesn't exist
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return new ArrayList<>();
            }

            return Files.readAllLines(path, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            throw e;
        }
    }

    /**
     * Write lines to file (overwrites existing)
     *
     * @return
     */
    public static boolean writeLines(String filePath, List<String> lines) throws IOException {
        try {
            Path path = Paths.get(filePath);

            // Create parent directories if needed
            Files.createDirectories(path.getParent());

            // Write all lines
            Files.write(path, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Error writing file: " + filePath);
            throw e;
        }
        return true;
    }

    /**
     * Append lines to file (adds to existing)
     */
    public static void appendLines(String filePath, List<String> lines) throws IOException {
        try {
            Path path = Paths.get(filePath);

            // Create file if doesn't exist
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            // Append lines
            Files.write(path, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("Error appending to file: " + filePath);
            throw e;
        }
    }

    /**
     * Append single line to file
     */
    public static void appendLine(String filePath, String line) throws IOException {
        appendLines(filePath, List.of(line));
    }

    /**
     * Check if file exists
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Delete file
     */
    public static boolean deleteFile(String filePath) throws IOException {
        return Files.deleteIfExists(Paths.get(filePath));
    }

    /**
     * Create directory if not exists
     */
    public static void createDirectoryIfNotExists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}