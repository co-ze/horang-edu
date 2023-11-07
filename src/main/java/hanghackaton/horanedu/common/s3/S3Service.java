package hanghackaton.horanedu.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hanghackaton.horanedu.domain.board.entity.ClassPost;
import hanghackaton.horanedu.domain.board.entity.Post;
import hanghackaton.horanedu.domain.board.entity.PostImage;
import hanghackaton.horanedu.domain.board.repository.postImage.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final PostImageRepository postImageRepository;

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
    @Transactional
    public List<PostImage> uploadPostImages(List<MultipartFile> multipartFiles, Post post) throws IOException {

        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

            String image = amazonS3.getUrl(bucketName, fileName).toString();

            PostImage postImage = new PostImage(image);
            postImage.setPost(post);
            postImageRepository.save(postImage);

            postImages.add(postImage);
        }

        return postImages;

    }

    //파일 여러개 업로드 (학급 게시판)
    @Transactional
    public List<PostImage> uploadPostImages(List<MultipartFile> multipartFiles, ClassPost classPost) throws IOException {

        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            String image = amazonS3.getUrl(bucketName, fileName).toString();

            PostImage postImage = new PostImage(image);
            postImage.setClassPost(classPost);
            postImageRepository.save(postImage);

            postImages.add(postImage);
        }

        return postImages;

    }

}
