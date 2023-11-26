package hanghackaton.horanedu.domain.board.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.board.dto.PatchPostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.service.ClassPostService;
import hanghackaton.horanedu.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final ClassPostService classPostService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<String> createPost(@RequestPart PostRequestDto postRequestDto,
                                          @RequestPart(name = "images", required = false) List<MultipartFile> images,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        if (StringUtils.pathEquals(postRequestDto.getCategory(), "")) {
            return classPostService.createClassPost(postRequestDto,images, userDetails.getUser());
        } else {
            return postService.createPost(postRequestDto, images, userDetails.getUser());
        }
    }

    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PatchMapping("/{id}")
    public ResponseDto<String> updatePost(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestPart(name = "patchPostRequestDto") PatchPostRequestDto patchPostRequestDto,
                                          @RequestPart(name = "image", required = false) List<MultipartFile> images) {
        return postService.updatePost(id, userDetails.getUser(), patchPostRequestDto, images);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<String> deletePost(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }

}
