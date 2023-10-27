package hanghackaton.horanedu.domain.board.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.board.dto.PostCreateDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<String> createPost(@RequestPart PostCreateDto postCreateDto,
                                          @RequestPart(name = "images", required = false) List<MultipartFile> images,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(postCreateDto, images, userDetails.getUser());
    }

    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }



}
