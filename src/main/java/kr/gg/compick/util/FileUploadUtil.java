package kr.gg.compick.util;

import java.util.List;

public class FileUploadUtil {

    // URL만 저장할 때는 단순히 검증 로직 정도만 수행
    public static String saveImageUrl(String url) {
        String validUrl = "";
        List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png", ".gif", ".webp", ".jfif");

        
        String extension = url.substring(url.lastIndexOf(".")).toLowerCase();
        if (allowedExtensions.contains(extension)) {
            validUrl= url;
        } else {
            System.out.println("허용되지 않은 파일 형식: " + extension);
        }
        
        return validUrl;
    }

    // private static final Path UPLOAD_DIR = Paths.get("C:", "autofeed_image_folder", "board_images");
    // private static final String URL_PREFIX = "/images/board/";

    // public static List<String> saveImages(List<String> files, Long userIdx) throws IOException {
    //     List<String> savedUrls = new ArrayList<>();

    //     List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png", ".gif", ".webp", "jfif");

    //     File saveDir = UPLOAD_DIR.toFile();
    //     if (!saveDir.exists()) {
    //         saveDir.mkdirs();
    //     }
    //     int index = 0;

    //     for (String file : files) {
    //         if (file != null && !file.isEmpty()) {

    //             String originalFileName = file.getOriginalFilename();
    //             String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();

    //             if (!allowedExtensions.contains(extension)) {
    //                 System.out.println("허용되지 않은 파일 형식: " + extension);
    //                 continue; // 이 파일은 저장하지 않음
    //             }

    //             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    //             String timestamp = LocalDateTime.now().format(formatter);
    //             String savedFileName = userIdx + "_" + timestamp + "_" + index + extension;
    //             index++; // 같이 올라가는 사진 순서 구분

    //             Path savedFilePath = UPLOAD_DIR.resolve(savedFileName);
    //             File savedFile = savedFilePath.toFile();

    //             file.transferTo(savedFile);

    //             savedUrls.add(URL_PREFIX + savedFileName); // 접근용 URL
    //         }
    //     }

    //     return savedUrls;
    // }
    
    
}
