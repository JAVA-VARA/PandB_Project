//package sjspring.shop.pregAndBirthDeveloper.util;
//
//import org.im4java.core.ConvertCmd;
//import org.im4java.core.IM4JavaException;
//import org.im4java.core.IMOperation;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//public class ResizingGifUtil {
//    public MultipartFile GifResizer(){
//
//        String inputPath = "path/to/input.gif";
//        String outputPath = "path/to/output.gif";
//        double scale = 0.5; // 50% 크기로 리사이징
//
//        try {
//            resizeGif(inputPath, outputPath, scale);
//            System.out.println("GIF 이미지가 성공적으로 리사이징되었습니다.");
//        } catch (IM4JavaException | IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
//
//    private void resizeGif(String inputPath, String outputPath, double scale) throws IOException, InterruptedException, IM4JavaException {
//        // ConvertCmd 객체 생성
//        ConvertCmd cmd = new ConvertCmd();
//        // IMOperation 객체 생성
//        IMOperation op = new IMOperation();
//
//        op.addImage(inputPath);
//        op.resize(Integer.valueOf((int) (100 * scale) + "%"));
//        op.addImage(outputPath);
//
//        // ConvertCmd를 사용하여 이미지 변환 실행
//        cmd.run(op);
//    }
//
//}
