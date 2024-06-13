package sjspring.shop.pregAndBirthDeveloper.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageMagicKResizing {

    private static final String RELATIVE_PATH = "src/main/resources/tempFiles/";

    public void resizeGif(MultipartFile gifImagefile, String filename, int targetWidth, int targetHeight, String resizedFilePath)
            throws IOException, InterruptedException {

        // 프로젝트의 절대 경로를 얻음
        String projectPath = System.getProperty("user.dir");
        String tempRelativePath = projectPath + File.separator + RELATIVE_PATH;

        createDirectoryIfNotExists(tempRelativePath);

        // 저장할 원본 파일 경로 설정
        String orgFilePath = tempRelativePath + filename;
        gifImagefile.transferTo(new File(orgFilePath));

        // 이미지 리사이즈 명령어 설정
        String command = String.format("magick convert %s -resize %dx%d %s", orgFilePath, targetWidth, targetHeight, resizedFilePath);

        // 명령어 실행
        Process process = new ProcessBuilder("cmd", "/c", command).start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("GIF resizing successful.");
        } else {
            System.err.println("GIF resizing failed. Exit code: " + exitCode);
        }
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}



