package ru.vk.education.job.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    private final String nameFile;
    private final String externalPath;

    public FileService(String nameFile) {
        this.nameFile = nameFile;
        externalPath = System.getProperty("user.home") + File.separator + ".myapp" + File.separator;
        try {
            Files.createDirectories(Paths.get(externalPath));
            Path filePath = Paths.get(externalPath + nameFile);
            if (!Files.exists(filePath))
                Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCommandInFile(String[] wordsLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(externalPath + nameFile, StandardCharsets.UTF_8, true))) {
            for (int i = 0; i < wordsLine.length; i++) {
                writer.write(wordsLine[i]);
                if (i != wordsLine.length - 1) writer.write(" ");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getAllCommandsFromFile() {
        String fullPath = externalPath + nameFile;
        File file = new File(fullPath);
        if (!file.exists()) return null;
        try {
            return new BufferedReader(new FileReader(fullPath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public BufferedReader getAllCommandsFromFile() {
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nameFile)) {
//            if (inputStream == null) {
//                throw new FileNotFoundException(nameFile + " not find in resources");
//            }
//            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
