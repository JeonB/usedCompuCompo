package jeonb.usedcompu.controller;

import java.util.Optional;
import jeonb.usedcompu.annotation.Login;
import jeonb.usedcompu.model.CompuPost;
import jeonb.usedcompu.model.CompuPostFile;
import jeonb.usedcompu.model.Member;
import jeonb.usedcompu.repository.CommentRepositoryMapper;
import jeonb.usedcompu.repository.PostFileRepository;
import jeonb.usedcompu.repository.PostRepository;
import jeonb.usedcompu.service.CompuPostFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jeonb.usedcompu.repository.CompuPostFileRepositoryMapper;
import jeonb.usedcompu.repository.CompuPostRepositoryMapper;
import jeonb.usedcompu.service.CompuPostService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/compuPost")
public class CompuPostController {

    private final CompuPostService compuPostService;
    private final CompuPostRepositoryMapper compuPostMapper;
    private final CompuPostFileRepositoryMapper compuPostFileMapper;
    private final CommentRepositoryMapper commentMapper;
    private final CompuPostFileService compuPostFileService;
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;

    @Autowired
    public CompuPostController(CompuPostService compuPostService, CompuPostRepositoryMapper compuPostMapper, CompuPostFileRepositoryMapper compuPostFileMapper, CommentRepositoryMapper commentMapper, CompuPostFileService compuPostFileService,
            PostRepository postRepository, PostFileRepository postFileRepository) {
        this.compuPostService = compuPostService;
        this.compuPostMapper = compuPostMapper;
        this.compuPostFileMapper = compuPostFileMapper;
        this.commentMapper = commentMapper;
        this.compuPostFileService = compuPostFileService;
        this.postRepository = postRepository;
        this.postFileRepository = postFileRepository;
    }

    @GetMapping("/write")
    public String writeForm(@ModelAttribute CompuPost compuPost){
        return "compuPost/write";
    }

    @PostMapping("/write")
    @ResponseBody
    public Map writeSave(@Validated @ModelAttribute CompuPost compuPost, BindingResult bindingResult,
                               HttpServletRequest request, @Login Member loginMember) throws IOException {
        Map<String, Object> response = new HashMap<>();

        if(compuPost.getFileList().size() > 10){
            response.put("status", "validPhoto");
            response.put("response", "사진은 10개까지만 업로드 가능합니다.");
        }

        List validList = compuPostService.newCompuPostValidCheck(compuPost, bindingResult);
        if(validList.size() > 0){
            response.put("status", "valid");
            response.put("response", validList);
        }

        if(validList.size() == 0){
            if(loginMember != null){
                compuPost.setWriterEmail(loginMember.getEmail());                
            }else{
                compuPost.setWriterEmail("익명@admin");    
            }

            Date currentTime = new Date();
            compuPost.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));
            
//            compuPostMapper.save(compuPost);  //id저장됨
            postRepository.save(compuPost);
            compuPostFileService.save(compuPost);

            String currentUrl = request.getRequestURL().toString();
            String redirectUrl = currentUrl.replace("/compuPost/write", "/compuPost/detail/"+compuPost.getId());
            response.put("status", "saveOk");
            response.put("response", redirectUrl);
        }

        return response;
    }



    @GetMapping("/detail/{compuPostId}")
    public String detail(@PathVariable Long compuPostId, Model model){
        CompuPost byId = compuPostMapper.findById(compuPostId);

        byId.setViewCount(byId.getViewCount() +1);// TODO: Event 를 활용해서 게시글 조회하는 이벤트가 발생했을 때 setViewCount를 호출하는 EventHandler를 활용하면 좋음
        compuPostMapper.viewPlus(byId);
        model.addAttribute("compuPost", byId);

        List<CompuPostFile> byIdFile = compuPostFileMapper.findById(compuPostId);
        model.addAttribute("fileList", byIdFile);

        return "compuPost/detail";
    }

    @PostMapping("/detail/commentList")
    @ResponseBody
    public List detailComment(@RequestBody Map<String, Long> map){
        return commentMapper.findAll(map.get("compuPostId"));
    }

    @GetMapping("compuPost/getImage/{imgName}")
    private ResponseEntity<Resource> getImageUrl(@PathVariable String imgName) throws IOException {
        return compuPostService.fileFoundTest(imgName);
        //http://localhost:8080/compuPost/imgtest/69221ce3-e582-4ce5-9a56-3730b5ba53ec_9_%EC%BD%94%EB%94%A9%EB%A7%88%EC%A7%80%EB%A7%89.jpg
    }

    @GetMapping("/edit/{compuPostId}")
    public String edit(@PathVariable Long compuPostId, Model model){
//        CompuPost byId = compuPostMapper.findById(compuPostId);  JPA대체
        Optional<CompuPost> byId = postRepository.findById(compuPostId);
        if(byId.isPresent()){
            model.addAttribute("compuPost", byId.get());

//            List<CompuPostFile> byIdFile = compuPostFileMapper.findById(compuPostId);
            List<CompuPostFile> byIdFile = postFileRepository.findAllByCompuPostId(compuPostId);
            model.addAttribute("fileList", byIdFile);
        }

        return "compuPost/write";
    }
    @PostMapping("/edit")
    @ResponseBody
    public Map editSave(@Validated @ModelAttribute CompuPost compuPost, BindingResult bindingResult,
                         HttpServletRequest request, @Login Member loginMember) throws IOException {
        Map<String, Object> response = new HashMap<>();

//        List<CompuPostFile> byId = compuPostFileMapper.findById(compuPost.getId());
        List<CompuPostFile> byId = postFileRepository.findAllByCompuPostId(compuPost.getId());
        if(byId.size() > 10){
            response.put("status", "validPhoto");
            response.put("response", "사진은 10개까지만 업로드 가능합니다.");
        }

        List validList = compuPostService.newCompuPostValidCheck(compuPost, bindingResult);
        if(validList.size() > 0){
            response.put("status", "valid");
            response.put("response", validList);
        }

        if(validList.size() == 0){
            if(loginMember != null){
                compuPost.setWriterEmail(loginMember.getEmail());
            }else{
                compuPost.setWriterEmail("익명@admin");
            }

//            compuPostMapper.update(compuPost);
            postRepository.save(compuPost);
            compuPostFileService.update(compuPost);


            String currentUrl = request.getRequestURL().toString();
            String redirectUrl = currentUrl.replace("/compuPost/edit", "/compuPost/detail/"+compuPost.getId());
            response.put("status", "saveOk");
            response.put("response", redirectUrl);
        }

        return response;
    }


    @GetMapping("/remove/{compuPostId}")
    public String remove(@PathVariable Long compuPostId){

//        CompuPost byId = compuPostMapper.findById(compuPostId);
        Optional<CompuPost> byId = postRepository.findById(compuPostId);
        compuPostFileService.delete(compuPostId);

//        int deletePost = compuPostMapper.delete(compuPostId);

        if( byId.isPresent()){
            postRepository.deleteById(compuPostId);
            return "redirect:/category/"+byId.get().getCompuCategory().getLowerCase();
        }
        return null;
    }


}
