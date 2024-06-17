package sjspring.shop.pregAndBirthDeveloper.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sjspring.shop.pregAndBirthDeveloper.dto.AttachedFileDto;
import sjspring.shop.pregAndBirthDeveloper.util.CustomMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AttachedFileUploadService {

    private final static String RELATIVE_PATH = "src/main/resources/files/";
    private final ImageConvertingService imageConvertingService;

    public AttachedFileDto fileUploadToLocalDir(MultipartFile multipartFile) throws IOException,InterruptedException {
        String originalFilename = multipartFile.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + originalFilename;
        String filePath = RELATIVE_PATH + fileName;

        createDirectoryIfNotExists(RELATIVE_PATH);
        String fileFormat = extractFileFormat(multipartFile);

        if (isImageFile(multipartFile) && !isUnsupportedImageFormat(Objects.requireNonNull(originalFilename))) {
            MultipartFile resizedImage = resizer(fileName, fileFormat, multipartFile, 400);
            saveFile(resizedImage, filePath);
        } else {
            assert originalFilename != null;
            if (isHeicOrAvifFormat(originalFilename)) {

                MultipartFile convertedImage = convertAndResizeHeicOrAvif(multipartFile, uuid);
                saveFile(convertedImage, filePath);

            } else if (isGifFormat(originalFilename)) {
                resizeGif(multipartFile, fileName, filePath);
            } else {
                saveFile(multipartFile, filePath);
            }
        }

        return new AttachedFileDto(originalFilename, fileName, filePath);
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    private boolean isImageFile(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).contains("image");
    }

    private boolean isUnsupportedImageFormat(String filename) {
        return  filename.endsWith("gif")  || filename.endsWith("GIF")  ||
                filename.endsWith("avif") || filename.endsWith("AVIF") ||
                filename.endsWith("heic") || filename.endsWith("HEIC");
    }

    private boolean isHeicOrAvifFormat(String filename) {
        return filename.endsWith("heic") || filename.endsWith("HEIC") || filename.endsWith("avif") || filename.endsWith("AVIF");
    }

    private boolean isGifFormat(String filename) {
        return filename.endsWith("gif") || filename.endsWith("GIF");
    }

    private MultipartFile convertAndResizeHeicOrAvif(MultipartFile file, UUID uuid) throws IOException {
        MultipartFile convertedImage = imageConvertingService.convertHEICAndAVIFtoPNG(file);
        String fileFormat = extractFileFormat(convertedImage);
        String fileName = uuid + "_" + convertedImage.getOriginalFilename();
        return resizer(fileName, fileFormat, convertedImage, 400);
    }

    private void resizeGif(MultipartFile file, String fileName, String filePath) throws IOException, InterruptedException {
        imageConvertingService.resizeGif(file, fileName, 400, 400, filePath);
    }

    private void saveFile(MultipartFile file, String filePath) throws IOException {
        file.transferTo(new File(filePath));
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


            if (image.getHeight() < width) {
                return originalImage;
            }

            MarvinImage imageMarvin = new MarvinImage(image);
            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", width);
            scale.setAttribute("newHeight", width * image.getHeight() / image.getWidth());//비율유지를 위해 높이 유지
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
        return Objects.requireNonNull(multipartFile.getContentType()).substring(multipartFile.getContentType().lastIndexOf("/") + 1);
    }
    private Metadata getMetadata(InputStream inputStream) {
        try {
            return ImageMetadataReader.readMetadata(inputStream);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    //orginal image의 metadata를 받아서 이미지의 방향을 알아낸다.
    private Integer getOrientation(Metadata metadata) throws MetadataException {
        int orientation = 1; //기본이 1

        //Metadata 객체에서 ExifIFD0Directory를 찾음.
        Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

        // directory는 있는데 그 안에 orientation값이 없을 수 있어 두개 다 체크
        if(directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION))  {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        }

        return orientation;
    }
    private BufferedImage rotateImage (BufferedImage bufferedImage, int orientation) {

        return switch (orientation) {
            case 6 -> Scalr.rotate(bufferedImage, Scalr.Rotation.CW_90);
            case 3 -> Scalr.rotate(bufferedImage, Scalr.Rotation.CW_180);
            case 8 -> Scalr.rotate(bufferedImage, Scalr.Rotation.CW_270);
            default -> bufferedImage;
        };

    }

}
