package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class Task4 {

    public void writeString(Path filePath, String string) {
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.createFile(filePath);

            try (OutputStream os = Files.newOutputStream(filePath)) {
                CheckedOutputStream checkedOs = new CheckedOutputStream(os, new Adler32());
                BufferedOutputStream bufferedOs = new BufferedOutputStream(checkedOs);
                OutputStreamWriter osWriter = new OutputStreamWriter(bufferedOs, StandardCharsets.UTF_8);

                osWriter.write(string);
                osWriter.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
