package com.wondoo.storageservice.storage.controller;

import com.wondoo.storageservice._global.annotation.RestWondooController;
import com.wondoo.storageservice.storage.data.request.FileRequest;
import com.wondoo.storageservice.storage.data.request.FilesRequest;
import com.wondoo.storageservice.storage.data.response.FileResponse;
import com.wondoo.storageservice.storage.data.response.FilesResponse;
import com.wondoo.storageservice.storage.service.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestWondooController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/file")
    public ResponseEntity<FileResponse> saveFile(@RequestPart("file_source") MultipartFile file) throws IOException {
        FileResponse fileResponse = storageService.saveFile(file);
        //ApiResponse<FileResponse> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, fileResponse);
        return ResponseEntity.status(HttpStatus.OK).body(fileResponse);
    }

    @PostMapping("/files")
    public ResponseEntity<FilesResponse> saveFiles(@RequestPart("file_sources") List<MultipartFile> files) throws IOException {
        FilesResponse filesResponse = storageService.saveFiles(files);
        //ApiResponse<FilesResponse> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, filesResponse);
        return ResponseEntity.status(HttpStatus.OK).body(filesResponse);
    }

    @DeleteMapping("/file")
    public ResponseEntity<Void> deleteFile(@RequestBody FileRequest file) {
        storageService.deleteFile(file);
        //ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/files")
    public ResponseEntity<Void> deleteFiles(@RequestBody FilesRequest files) {
        storageService.deleteFiles(files);
        //ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
