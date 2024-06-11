package sjspring.shop.pregAndBirthDeveloper.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageProcessingUtil {

    public MultipartFile convertHEICtoPNG(MultipartFile originalHEICImage) throws IOException {

        //1 전달받은 HEIC 파일을 저장
        //#1 저장할 폴더 생성
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
        String filename = originalHEICImage.getOriginalFilename();
        String orgFilePath = tempRelativePath + filename;
        originalHEICImage.transferTo(new File(orgFilePath));



        //2 저장된 파일을 불러와서 변환
        String magick_path = "C:\\Program Files\\ImageMagick-7.1.1-Q16-HDRI\\magick.exe";
        //원본이미지 전체경로
        String org_file = orgFilePath;

        String relativePath = "src/main/resources/files/";
        //저장될 이미지 전체경로
        String newFilename = filename.substring(0,filename.lastIndexOf("."))+".png";
        String new_file = relativePath + newFilename;

        //3 변환된 파일을 MULTIPART FILE로 반환
        Process process = new ProcessBuilder(
                "cmd", "/c",
                magick_path, "convert",
                org_file, new_file)
                .start();

        File file = ResourceUtils.getFile(new_file);
        InputStream inputStream = new FileInputStream(file);


        return new MockMultipartFile("file", newFilename, "image/png", inputStream);
    }
}



