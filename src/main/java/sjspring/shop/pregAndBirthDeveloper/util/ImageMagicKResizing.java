package sjspring.shop.pregAndBirthDeveloper.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageMagicKResizing {

    public void resizeGif(MultipartFile gifImagefile, String filename, int targetWidth, int targetHeight, String resizedFilePath )
            throws IOException, InterruptedException {

        String tempRelativePath = "src/main/resources/tempFiles/";
        if(!new File(tempRelativePath).exists()){
            try {
                File tempFile = new File(tempRelativePath);
                tempFile.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        //#2 전달 받은 파일 저장
        String orgFilePath = tempRelativePath + filename;
        gifImagefile.transferTo(new File(orgFilePath));
        String command = String.format("magick convert %s -resize %dx%d %s", orgFilePath, targetWidth, targetHeight, resizedFilePath);

        Process process = new ProcessBuilder("cmd", "/c", command).start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("GIF resizing successful.");
        } else {
            System.err.println("GIF resizing failed. Exit code: " + exitCode);
        }
    }

}

