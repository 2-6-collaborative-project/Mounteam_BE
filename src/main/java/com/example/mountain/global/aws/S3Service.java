package com.example.mountain.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> upload(List<MultipartFile> multipartFiles, String dirName) {
        List<String> imgUrlList = new ArrayList<>();
        String bucketPath = getBucketPath(dirName);

        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucketPath, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(amazonS3.getUrl(bucketPath, fileName).toString());
            } catch (IOException e) {
                throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
            }
        }
        return imgUrlList;
    }

    public String upload(MultipartFile file, String dirName) {
        String bucketPath = getBucketPath(dirName);

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketPath, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucketPath, fileName).toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
    }

    public String profileImageUpload(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket + "/profile", fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket + "/profile", fileName).toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.WRONG_INPUT_IMAGE);
        }
    }

    private String getBucketPath (String dirName) {
        String bucketPath;
        if (dirName.equals("review")) {
            bucketPath = bucket + "/review";
        } else if (dirName.equals("feed")) {
            bucketPath = bucket + "/feed";
        } else if (dirName.equals("team")) {
            bucketPath = bucket + "/team";
        } else if (dirName.equals("team-review")) {
            bucketPath = bucket + "/team-review";
        } else if (dirName.equals("badge")) {
            bucketPath = bucket + "/badge";
        }else {
            throw new CustomException(ErrorCode.INVALID_TYPE_VALUE);
        }
        return bucketPath;
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

    public void deleteImage(String fileUrl) {
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    public String updateImage(String fileUrl, MultipartFile file) {
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());
        String s3profileUrl = "";

        if (doesFileExist(bucket, fileName)) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
            s3profileUrl = profileImageUpload(file);
        } else {
            s3profileUrl = profileImageUpload(file);
        }
        return s3profileUrl;
    }

    private boolean doesFileExist(String bucketName, String key) {
        return amazonS3.doesObjectExist(bucketName, key);
    }

    public String getBadgeImgUrl(String dirName, String mountainName) {
        String bucketPath = getBucketPath(dirName);
        String fileName = mountainName + ".png";
        return amazonS3Client.getUrl(bucketPath,fileName).toString();
    }

}