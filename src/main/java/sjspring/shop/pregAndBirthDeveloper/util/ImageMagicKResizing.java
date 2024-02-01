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
//        File file = ResourceUtils.getFile(orgFilePath);
//        InputStream inputStream = new FileInputStream(file);
//
//        MultipartFile multipartFile = new MockMultipartFile("file", filename, "image/gif", inputStream);
//        multipartFile.transferTo(new File(resizedFilePath));

    }

}

//    public static void main(String[] args) {
//        String originalFilePath = "path/to/original.gif";
//        String resizedFilePath = "path/to/resized.gif";
//        int targetWidth = 300;
//        int targetHeight = 200;
//
//        try {
//            resizeGif(originalFilePath, resizedFilePath, targetWidth, targetHeight);
//            System.out.println("GIF resizing completed.");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
// ImageMagick의 convert 명령어 경로
//        String command = String.format("%s convert %s -resize %dx%d %s", magickPath, orgFilePath, targetWidth, targetHeight, resizedPath);
//        Process process1 = new ProcessBuilder("cmd", "/c", command).start();
//
////        $ convert image_animation_1.gif -coalesce coalesce.gif
//        String command2 = String.format("%s convert %s -coalesce %s", magickPath, orgFilePath, resizedPath);
////        $ convert coalesce.gif -resize x200 image2.gif
//        String command3 = String.format("%s convert %s -resize x200 resized.gif", magickPath, resizedPath);


//        Process process2 = new ProcessBuilder("cmd", "/c", command2).start();
//        Process process3 = new ProcessBuilder("cmd", "/c", command3).start();

//        File file = ResourceUtils.getFile(resizedFilePath);
//        InputStream inputStream = new FileInputStream(file);
//        int exitCode = process2.waitFor();



//    String magickPath = "C:\\Program Files\\ImageMagick-7.1.1-Q16-HDRI\\magick.exe";
//
//
//    String inputFilePath = "C:\\Users\\sjyou\\IdeaProjects\\PandBproject\\PandB_Project\\src\\main\\resources\\tempFiles\\GOOD.gif";
//    String outputFilePath = "C:\\Users\\sjyou\\IdeaProjects\\PandBproject\\PandB_Project\\src\\main\\resources\\tempFiles\\TEST.gif";
