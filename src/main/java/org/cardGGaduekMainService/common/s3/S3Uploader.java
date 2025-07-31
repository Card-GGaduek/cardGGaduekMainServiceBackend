package org.cardGGaduekMainService.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class S3Uploader {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("MultipartFile is empty");
        }

        System.out.println("Upload 시작: " + file.getOriginalFilename());
        System.out.println("Bucket: " + bucket);
        System.out.println("ContentType: " + file.getContentType());
        System.out.println("Size: " + file.getSize());

        String extension = getFileExtension(file.getOriginalFilename());
        if (!isImageExtension(extension)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
        }

        String uniqueFileName;
        try {
            uniqueFileName = generateFileKey(file, dirName);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("파일 해시 생성 실패", e);
        }

        System.out.println("업로드 대상 경로: " + uniqueFileName);

        // 중복 확인: S3에 존재하면 업로드 생략
        if (amazonS3.doesObjectExist(bucket, uniqueFileName)) {
            System.out.println("동일한 이미지가 이미 존재합니다. 업로드 생략.");
            return amazonS3.getUrl(bucket, uniqueFileName).toString();
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(new PutObjectRequest(bucket, uniqueFileName, file.getInputStream(), metadata));
        } catch (Exception e) {
            System.err.println("S3 업로드 중 오류 발생");
            e.printStackTrace();
            throw new IOException("S3 업로드 실패: " + e.getMessage());
        }

        String uploadedUrl = amazonS3.getUrl(bucket, uniqueFileName).toString();
        System.out.println("업로드 완료. URL: " + uploadedUrl);
        return uploadedUrl;
    }

    // 해시 기반 파일 키 생성
    private String generateFileKey(MultipartFile file, String dirName) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(file.getBytes());
        String hashHex = Hex.encodeHexString(hash); // ← 여기 바뀜
        String extension = getFileExtension(file.getOriginalFilename());

        return dirName + "/" + hashHex + "." + extension;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 확장자를 확인할 수 없습니다.");
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    private boolean isImageExtension(String ext) {
        return ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif") || ext.equals("webp");
    }
}
