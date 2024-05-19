package hanghackaton.horanedu.domain.board.controller;

import hanghackaton.horanedu.common.dto.ResponseDto;
import hanghackaton.horanedu.common.security.UserDetailsImpl;
import hanghackaton.horanedu.domain.board.dto.ClassPostResponseDto;
import hanghackaton.horanedu.domain.board.dto.PatchPostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostRequestDto;
import hanghackaton.horanedu.domain.board.dto.PostResponseDto;
import hanghackaton.horanedu.domain.board.postEnum.PostCategoryEnum;
import hanghackaton.horanedu.domain.board.service.ClassPostService;
import hanghackaton.horanedu.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final ClassPostService classPostService;

    @PostMapping()
    public ResponseDto<String> createPost(@RequestBody PostRequestDto postRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        if (StringUtils.pathEquals(postRequestDto.getCategory(), "")) {
            return classPostService.createClassPost(postRequestDto, userDetails.getUser());
        } else {
            return postService.createPost(postRequestDto, userDetails.getUser());
        }
    }

    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/class/{id}")
    public ResponseDto<ClassPostResponseDto> getClassPost(@PathVariable Long id) {
        return classPostService.getClassPost(id);
    }

    @PatchMapping("/{id}")
    public ResponseDto<String> updatePost(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestBody PatchPostRequestDto patchPostRequestDto) throws IOException {
        return postService.updatePost(id, userDetails.getUser(), patchPostRequestDto);
    }

    @PatchMapping("/class/{id}")
    public ResponseDto<String> updateClassPost(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody PatchPostRequestDto patchPostRequestDto) throws IOException {
        return classPostService.updateClassPost(id, userDetails.getUser(), patchPostRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<String> deletePost(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }

    @DeleteMapping("/class/{id}")
    public ResponseDto<String> deleteClassPost(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return classPostService.deleteClassPost(id, userDetails.getUser());
    }

    @GetMapping("/list")
    public ResponseDto<Page<PostResponseDto>> getPostList(@RequestParam int page,
                                                          @RequestParam int size,
                                                          @RequestParam String category){
        return postService.getPostList(page, size, category);
    }

    @GetMapping("/class/list")
    public ResponseDto<Page<ClassPostResponseDto>> getClassPostList(@RequestParam int page,
                                                                    @RequestParam int size,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        return classPostService.getClassPostList(page, size, userDetails.getUser());
    }

}
