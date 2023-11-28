package com.se.kltn.spamanagement.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.se.kltn.spamanagement.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.net.URLEncoder.encode;

@Service
@Log4j2
public class ImageServiceImpl implements ImageService {

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kltn-spamanagement.appspot.com/o/%s?alt=media&token=%s";

    private static final String CREDENTIAL_PATH="D:\\personal\\KLTN\\code\\KLTN-SpaManagement-BackEnd\\src\\main\\resources\\static\\credential.json";

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        log.debug("Upload file");
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
        File file = this.convertToFile(multipartFile, fileName);
        String url = this.uploadFile(file, fileName);
        file.delete();
        return url;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId id = BlobId.of("kltn-spamanagement.appspot.com", fileName);
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadToken", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(id).setMetadata(map).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(
                Files.newInputStream(Paths.get(CREDENTIAL_PATH)));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, encode(fileName, StandardCharsets.UTF_8), encode(fileName, StandardCharsets.UTF_8));
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) {
        File file = new File(fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
