package com.daniel.cursoudemy.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.daniel.cursoudemy.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile){
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(is, fileName, contentType);

        } catch (IOException io) {
            throw new FileException("Erro de IO" + io.getMessage());

        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);

            LOG.info("Upload Inicializado");
            s3Client.putObject(bucketName, fileName, is, objectMetadata);
            LOG.info("Upload Finalizado");

            return s3Client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
//    public void uploadFile(String localFilePath) {
//        try {
//            File file = new File(localFilePath);
//
//            LOG.info("Upload Inicializado");
//            s3Client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
//            LOG.info("Upload Finalizado");
//
//        } catch (AmazonServiceException am) {
//            LOG.info("AmazonServiceException: " + am.getErrorMessage());
//            LOG.info("Status code: " + am.getErrorCode());
//
//        }catch(AmazonClientException ae){
//            LOG.info("AmazonClientException: " + ae.getMessage());
//
//        }
//    }
}
