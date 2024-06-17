package sjspring.shop.pregAndBirthDeveloper.service;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageConvertingService {
    private static final String RELATIVE_PATH = "src/main/resources/tempFiles/";

    public MultipartFile convertHEICAndAVIFtoPNG(MultipartFile originalImage) throws IOException {

        //1 전달받은 HEIC 파일을 저장
        //#1 저장할 폴더 생성
        if(!new File(RELATIVE_PATH).exists()){
            try {
                File tempFile = new File(RELATIVE_PATH);
                tempFile.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        //#2 전달 받은 파일 저장
        String filename = originalImage.getOriginalFilename();
        String orgFilePath = RELATIVE_PATH + filename;
        originalImage.transferTo(new File(orgFilePath));

        //2 저장된 파일을 불러와서 변환
        String magick_path = "C:\\Program Files\\ImageMagick-7.1.1-Q16-HDRI\\magick.exe";
        //원본이미지 전체경로

        String relativePath = "src/main/resources/files/";
        //저장될 이미지 전체경로
        assert filename != null;
        String newFilename = filename.substring(0,filename.lastIndexOf("."))+".png";
        String new_file = relativePath + newFilename;

        //3 변환된 파일을 MULTIPART FILE로 반환
        Process process = new ProcessBuilder(
                "cmd", "/c",
                magick_path, "convert",
                orgFilePath, new_file)
                .start();

        File file = ResourceUtils.getFile(new_file);
        InputStream inputStream = new FileInputStream(file);

        return new MockMultipartFile("file", newFilename, "image/png", inputStream);
    }



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
