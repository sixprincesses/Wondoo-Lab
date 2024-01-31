package com.wondoo.storageservice.storage.controller;

import com.wondoo.storageservice._global.data.ApiResponse;
import com.wondoo.storageservice._global.data.StatusCode;
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
@CrossOrigin("*")
@RequestMapping("/storage-service")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/file")
    public ResponseEntity<ApiResponse<FileResponse>> saveFile(@RequestPart("filesource") MultipartFile file) throws IOException {
        FileResponse fileResponse = storageService.saveFile(file);
        ApiResponse<FileResponse> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, fileResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/files")
    public ResponseEntity<ApiResponse<FilesResponse>> saveFiles(@RequestPart("filesources") List<MultipartFile> files) throws IOException {
        FilesResponse filesResponse = storageService.saveFiles(files);
        ApiResponse<FilesResponse> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, filesResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/file")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@RequestBody FileRequest file) {
        storageService.deleteFile(file);
        ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/files")
    public ResponseEntity<ApiResponse<Void>> deleteFiles(@RequestBody FilesRequest files) {
        storageService.deleteFiles(files);
        ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
