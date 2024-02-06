package sjspring.shop.pregAndBirthDeveloper.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.imgscalr.Scalr;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sjspring.shop.pregAndBirthDeveloper.dto.AttachedFileDto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;



@RequiredArgsConstructor
public class LocalUploadUtil {
    public AttachedFileDto fileUploadToLocalDir(MultipartFile multipartFile) throws IOException, InterruptedException {

        String originalFilename = multipartFile.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + originalFilename;

        //프로젝트의 경로 지정
//        String savedPath = System.getProperty("user.dir") + "\\files"; //절대 경로
        String relativePath = "src/main/resources/files/";

        //directory 생성
        if(!new File(relativePath).exists()){
            try {
                File relativePatchCheck = new File(relativePath);
                relativePatchCheck.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        //

        String filePath = relativePath + fileName;
        String fileFormat = extractFileFormat(multipartFile); //파일 확장자명 추출

        //1.multipartFile을 buffered image type으로 변환
        if(Objects.requireNonNull(multipartFile.getContentType()).contains("image") && !originalFilename.endsWith("gif")) {
            //파일 확장자명 추출
            MultipartFile resizedImage = resizer(fileName, fileFormat, multipartFile, 400);
            resizedImage.transferTo(new File(filePath));

        } else if(originalFilename.endsWith("heic") || originalFilename.endsWith("HEIC")) {

            //1 변환
            ImageProcessingUtil imageProcessingUtil = new ImageProcessingUtil();
            MultipartFile convertedMultipartFile = imageProcessingUtil.convertHEICtoPNG(multipartFile);

            //2 변환된 file을 image reszier에 넣고 리사이징
            fileFormat = extractFileFormat(convertedMultipartFile);
            filePath = relativePath + "\\" + uuid + "_" + convertedMultipartFile.getOriginalFilename();
            fileName = convertedMultipartFile.getOriginalFilename();
            MultipartFile convertedImage = resizer(fileName, fileFormat, convertedMultipartFile, 400);




            convertedImage.transferTo(new File(filePath));
        }

        else if(originalFilename.endsWith("gif") || originalFilename.endsWith("GIF")){
            ImageMagicKResizing imageMagicKResizing = new ImageMagicKResizing();
            imageMagicKResizing.resizeGif(multipartFile, fileName, 400, 400, filePath);
        }

        else {
            CustomMultipartFile customMultipartFile =  new CustomMultipartFile(fileName,fileFormat,multipartFile.getContentType(), multipartFile.getBytes());
            customMultipartFile.transferTo(new File(filePath));
        }

        return new AttachedFileDto(originalFilename, fileName,filePath);

    }
    public MultipartFile resizer(String fileName, String fileFormat, MultipartFile originalImage, int width){
        try{

            //original image에서 metadata를 읽어온다.
            Metadata metadata = getMetadata(originalImage.getInputStream());

            //original image의 방향 추출
            int orientation = getOrientation(metadata);

            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(originalImage.getInputStream());

            //회전
            if (orientation != 1) {
                image = rotateImage(image, orientation);
            }

            //이미지 리사이징
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            if(originHeight < width){
                return originalImage;
            }

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", width);
            scale.setAttribute("newHeight", width * originHeight / originWidth);//비율유지를 위해 높이 유지
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormat, baos);
            baos.flush();

            return new CustomMultipartFile(fileName,fileFormat,originalImage.getContentType(), baos.toByteArray());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 줄이는데 실패했습니다.");
        } catch (MetadataException e) {
            throw new RuntimeException(e);
        }

    }
    private String extractFileFormat(MultipartFile multipartFile) {
        return multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/") + 1);
    }
    //image의 metadata를 가져온다.
    private Metadata getMetadata(InputStream inputStream) {
        Metadata metadata;

        try {
            metadata = ImageMetadataReader.readMetadata(inputStream);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }

        return metadata;
    }
    //orginal image의 metadata를 받아서 이미지의 방향을 알아낸다.
    private Integer getOrientation(Metadata metadata) throws MetadataException {
        //기본이 1
        int orientation = 1;

        //Metadata 객체에서 ExifIFD0Directory를 찾음.
        Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

        // directory는 있는데 그 안에 orientation값이 없을 수 있어 두개 다 체크
        if(directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION))  {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        }

        return orientation;
    }
    private BufferedImage rotateImage (BufferedImage bufferedImage, int orientation) {

        BufferedImage rotatedImage;

        if(orientation == 6 ) {
            rotatedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_90);
        } else if (orientation == 3) {
            rotatedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_180);
        } else if(orientation == 8) {
            rotatedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_270);
        } else {
            rotatedImage = bufferedImage;
        }

        return rotatedImage;
    }

//    private static BufferedImage resizeHECIImage(byte[] originalImageBytes, int targetWidth, int targetHeight) throws IOException {
//        ByteArrayInputStream bais = new ByteArrayInputStream(originalImageBytes);
//
//        return Thumbnails.of(bais)
//                .size(targetWidth, targetHeight)
//                .outputQuality(1.0)
//                .asBufferedImage();
//    }
}

//        try {
//            File directory = new File(relativePath);
//        }

//첨부파일 저장
//        multipartFile.transferTo(new File(filePath));




//        multipartFile.transferTo(savedFile);


//        return new AttachedFileDto(originalFilename, fileName,filePath);


//        //파일의 저장 경로 확인
//        File savedFile = new File(filePath);
//        String absolutePath = savedFile.getAbsolutePath();
//        System.out.println("File saved at: " + absolutePath);