package com.daniel.cursoudemy.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String localFilePath) {
        try {
            File file = new File(localFilePath);

            LOG.info("Upload Inicializado");
            s3Client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
            LOG.info("Upload Finalizado");

        } catch (AmazonServiceException am) {
            LOG.info("AmazonServiceException: " + am.getErrorMessage());
            LOG.info("Status code: " + am.getErrorCode());

        }catch(AmazonClientException ae){
            LOG.info("AmazonClientException: " + ae.getMessage());

        }
    }
}