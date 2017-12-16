package com.example.ebookrepository.service;

import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.model.EbookDetails;
import com.example.ebookrepository.model.Status;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private static final String storagePath = "src/main/resources/files/";


    public StorageService() {

    }

    public ResponseEntity<?> store(MultipartFile multipartFile) {
        if (!"application/pdf".equals(multipartFile.getContentType())) {
            return new ResponseEntity<>(
                    new Status(false, "You can upload only pdf files!"),
                    HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = String.valueOf(System.currentTimeMillis());
            File file = new File(storagePath + fileName + ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();

            return getMetadata(file);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    new Status(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> getFile(Ebook ebook) throws IOException {
        Path path = Paths.get(storagePath + ebook.getFileName());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=" + ebook.getTitle() + ".pdf")
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private ResponseEntity<?> getMetadata(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDDocumentInformation documentInformation = document.getDocumentInformation();

        String author = documentInformation.getAuthor();
        String title = documentInformation.getTitle();
        String keywords = documentInformation.getKeywords();

        document.close();

        return new ResponseEntity<>(
                new EbookDetails(file.getName(), author, title, keywords),
                HttpStatus.OK);
    }
}
