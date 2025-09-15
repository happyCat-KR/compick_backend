package kr.gg.compick.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final String uploadDir = "uploads/board"; // 저장할 기본 경로 (프로젝트 루트 기준)

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        try {
            // 저장 디렉토리 없으면 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일명: boardUpload_시간.png
            String filename = "boardUpload_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, filename);

            // 파일 저장
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // 프론트에 반환할 URL (정적 리소스로 매핑 예정)
            String fileUrl = "/images/board/" + filename;

            return ResponseEntity.ok().body(new UploadResponse(fileUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 저장 실패: " + e.getMessage());
        }
    }

    // 응답 DTO
    static class UploadResponse {
        public String fileUrl;

        public UploadResponse(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}
