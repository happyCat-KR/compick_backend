package kr.gg.compick.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import kr.gg.compick.domain.Media;
import kr.gg.compick.media.dao.MediaRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

    private final MediaRepository mediaRepository;

    private final String uploadDir = "uploads/board"; // 저장할 기본 경로 (프로젝트 루트 기준)

      @PostMapping("/upload/image")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        try {
            // DB에 저장할 Media 엔티티 생성
            Media media = Media.builder()
            
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileData(file.getBytes())  // <-- 이미지 byte[] 저장
                    .delCheck(false)
                    .build();

            mediaRepository.save(media);

            // 프론트에 반환할 URL → 미디어 조회용 엔드포인트로 설계
            String fileUrl = "/api/media/" + media.getMediaId();

            return ResponseEntity.ok(new UploadResponse(fileUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 저장 실패: " + e.getMessage());
        }
    }

    // 업로드된 이미지 조회 (URL 접근 시 DB에서 읽어오기)
    @GetMapping("/media/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        return mediaRepository.findById(id)
                .map(media -> ResponseEntity.ok()
                        .header("Content-Type", media.getFileType())
                        .body(media.getFileData()))
                .orElse(ResponseEntity.notFound().build());
    }

    // 응답 DTO
    static class UploadResponse {
        public String fileUrl;

        public UploadResponse(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}