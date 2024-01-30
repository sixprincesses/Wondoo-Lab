package com.wondoo.storageservice.storage.controller;

import com.wondoo.storageservice.storage.data.request.FileRequest;
import com.wondoo.storageservice.storage.data.request.FilesRequest;
import com.wondoo.storageservice.storage.data.response.FileResponse;
import com.wondoo.storageservice.storage.data.response.FilesResponse;
import com.wondoo.storageservice.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/storage-service")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/file")
    public ResponseEntity<FileResponse> saveFile(@RequestPart("filesource") MultipartFile file) throws IOException {
        FileResponse fileResponse = storageService.saveFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileResponse);
    }

    @PostMapping("/files")
    public ResponseEntity<FilesResponse> saveFiles(@RequestPart("filesources") List<MultipartFile> files) throws IOException {
        FilesResponse filesResponse = storageService.saveFiles(files);
        return ResponseEntity.status(HttpStatus.CREATED).body(filesResponse);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestBody FileRequest file) {
        storageService.deleteFile(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/files")
    public ResponseEntity<?> deleteFiles(@RequestBody FilesRequest files) {
        storageService.deleteFiles(files);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
