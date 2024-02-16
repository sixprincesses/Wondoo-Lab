package com.wondoo.storageservice.storage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wondoo.storageservice._global.utils.UUIDFactory;
import com.wondoo.storageservice.storage.data.request.FileRequest;
import com.wondoo.storageservice.storage.data.request.FilesRequest;
import com.wondoo.storageservice.storage.data.response.FileResponse;
import com.wondoo.storageservice.storage.data.response.FilesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {
    private final AmazonS3Client amazonS3Client;
    private final UUIDFactory uuidFactory;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileResponse saveFile(MultipartFile file) throws IOException {
        String filename = uuidFactory.generateUUID();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filesource = filename + "." + extension;
        amazonS3Client.putObject(bucket, filesource, file.getInputStream(), getObjectMetadata(file));
        return FileResponse.builder().filesource(filesource).build();
    }

    public FilesResponse saveFiles(List<MultipartFile> files) throws IOException {
        List<String> filesources = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = uuidFactory.generateUUID();
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String filesource = filename + "." + extension;
            amazonS3Client.putObject(bucket, filesource, file.getInputStream(), getObjectMetadata(file));
            filesources.add(filesource);
        }
        return FilesResponse.builder().filesources(filesources).build();
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        return metadata;
    }
    
    public void deleteFile(FileRequest file) {
        amazonS3Client.deleteObject(bucket, file.filesource());
    }

    public void deleteFiles(FilesRequest files) {
        for (String filesource : files.filesources()) {
            amazonS3Client.deleteObject(bucket, filesource);
        }
    }
}
