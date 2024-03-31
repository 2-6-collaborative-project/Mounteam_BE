package com.example.mountain.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.mountain.global.error.ErrorCode;
import com.example.mountain.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> upload(List<MultipartFile> multipartFiles) {
        List<String> imgUrlList = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket + "/feed", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(amazonS3.getUrl(bucket + "/feed", fileName).toString());
            } catch (IOException e) {
                throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
            }
        }
        return imgUrlList;
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
        ArrayList<String> fileExtensions = new ArrayList<>();
        fileExtensions.add(".jpg");
        fileExtensions.add(".jpeg");
        fileExtensions.add(".png");
        fileExtensions.add(".JPG");
        fileExtensions.add(".JPEG");
        fileExtensions.add(".PNG");
        String extension = fileName.substring(fileName.lastIndexOf("."));
        if (!fileExtensions.contains(extension)) {
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
        return extension;
    }
}