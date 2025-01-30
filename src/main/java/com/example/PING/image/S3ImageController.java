package com.example.PING.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class S3ImageController {
    private final S3ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 보내기")
    public ResponseEntity<String> upload(@RequestPart(value = "image", required = false) MultipartFile image) {
        String profileImage = imageService.upload(image);
        return ResponseEntity.ok("Files uploaded successfully, profileImage: " + profileImage);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String addr){
        String key = imageService.deleteImageFromS3(addr);
        return ResponseEntity.ok(key);
    }

//    @PostMapping("/upload")
//    @Operation(summary = "이미지 여러장 보내기",
//            requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")))
//    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
//        for (MultipartFile file : files) {
//            try {
//                imageService.uploadFile(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//            }
//        }
//        return ResponseEntity.ok("Files uploaded successfully");
//    }
}
