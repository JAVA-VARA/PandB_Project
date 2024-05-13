//package sjspring.shop.pregAndBirthDeveloper.config;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//// AWS S3 Client 생성
//
//@Configuration
//public class S3Config {
//
//    @Value("${aws.accessKeyId}")
//    private String accessKeyId;
//
//    @Value("${aws.secretKey}")
//    private String secretKey;
//
//    @Value("${aws.region}")
//    private String region;
//
//    @Value("${aws.bucket}")
//    private String bucketName;
//
//    //AWS S3 CLIENT 등록
//    @Bean
//    public S3Client s3Client() {
//        return S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
//                .build();
//    }
//
//    @Bean
//    public AmazonS3Client amazonS3Client() {
//        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
//
//        return (AmazonS3Client) AmazonS3ClientBuilder
//                .standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();
//    }
//
//
//
//}