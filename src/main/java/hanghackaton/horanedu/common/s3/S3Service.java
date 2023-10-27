package hanghackaton.horanedu.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    //파일 한개 업로드
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    //파일 여러개 업로드
    public String uploadFiles(List<MultipartFile> multipartFiles) throws IOException {

        StringBuilder images = new StringBuilder();

        for (int i = 0; i < multipartFiles.size(); i++) {

            String fileName = UUID.randomUUID() + "_" + multipartFiles.get(i).getOriginalFilename();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFiles.get(i).getSize());

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, multipartFiles.get(i).getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            if (i != 0) {
                images.append(", ");
            }
            images.append(amazonS3.getUrl(bucketName, fileName).toString());
        }

        return images.toString();
    }

}
