package thwjd.usedcompu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thwjd.usedcompu.annotation.Login;
import thwjd.usedcompu.entity.compuPost;
import thwjd.usedcompu.entity.compuPostFile;
import thwjd.usedcompu.entity.Member;
import thwjd.usedcompu.repository.compuPostFileRepositoryMapper;
import thwjd.usedcompu.repository.compuPostRepositoryMapper;
import thwjd.usedcompu.repository.CommentRepositoryMapper;
import thwjd.usedcompu.service.compuPostService;

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

    @Autowired CompuPostService compuPostService;
    @Autowired CompuPostRepositoryMapper compuPostMapper;
    @Autowired CompuPostFileRepositoryMapper compuPostFileMapper;
    @Autowired CommentRepositoryMapper commentMapper;

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

        List validList = compuPostService.newcompuPostValidCheck(compuPost, bindingResult);
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
            
            compuPostMapper.save(compuPost);  //id저장됨
            compuPostService.fileSave(compuPost);

            String currentUrl = request.getRequestURL().toString();
            String redirectUrl = currentUrl.replace("/compuPost/write", "/compuPost/detail/"+compuPost.getId());
            response.put("status", "saveOk");
            response.put("response", redirectUrl);
        }

        return response;
    }



    @GetMapping("/detail/{compuPostId}")
    public String detail(@PathVariable Long compuPostId, Model model){
        compuPost byId = compuPostMapper.findById(compuPostId);

        byId.setViewCount(byId.getViewCount() +1);
        compuPostMapper.viewPlus(byId);
        model.addAttribute("compuPost", byId);

        List<compuPostFile> byIdFile = compuPostFileMapper.findById(compuPostId);
        model.addAttribute("fileList", byIdFile);

        return "compuPost/detail";
    }

    @PostMapping("/detail/commentList")
    @ResponseBody
    public List detailComment(@RequestBody Map<String, Long> map){
        return commentMapper.findAll(map.get("compuPostId"));
    }

    @GetMapping("/getImage/{imgName}")
    private ResponseEntity<Resource> getImageUrl(@PathVariable String imgName) throws IOException {
        return compuPostService.fileFoundTest(imgName);
        //http://localhost:8080/compuPost/imgtest/69221ce3-e582-4ce5-9a56-3730b5ba53ec_9_%EC%BD%94%EB%94%A9%EB%A7%88%EC%A7%80%EB%A7%89.jpg
    }

    @GetMapping("/edit/{compuPostId}")
    public String edit(@PathVariable Long compuPostId, Model model){
        compuPost byId = compuPostMapper.findById(compuPostId);
        model.addAttribute("compuPost", byId);

        List<compuPostFile> byIdFile = compuPostFileMapper.findById(compuPostId);
        model.addAttribute("fileList", byIdFile);

        return "compuPost/write";
    }
    @PostMapping("/edit")
    @ResponseBody
    public Map editSave(@Validated @ModelAttribute compuPost compuPost, BindingResult bindingResult,
                         HttpServletRequest request, @Login Member loginMember) throws IOException {
        Map<String, Object> response = new HashMap<>();

        List<compuPostFile> byId = compuPostFileMapper.findById(compuPost.getId());

        if(byId.size() > 10){
            response.put("status", "validPhoto");
            response.put("response", "사진은 10개까지만 업로드 가능합니다.");
        }

        List validList = compuPostService.newcompuPostValidCheck(compuPost, bindingResult);
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

            compuPostMapper.update(compuPost);
            compuPostService.fileUpdate(compuPost);

            String currentUrl = request.getRequestURL().toString();
            String redirectUrl = currentUrl.replace("/compuPost/edit", "/compuPost/detail/"+compuPost.getId());
            response.put("status", "saveOk");
            response.put("response", redirectUrl);
        }

        return response;
    }


    @GetMapping("/remove/{compuPostId}")
    public String remove(@PathVariable Long compuPostId){

        compuPost byId = compuPostMapper.findById(compuPostId);

        compuPostService.fileDelete(compuPostId);

        int deletePost = compuPostMapper.delete(compuPostId);

        if(deletePost == 1 ){
            return "redirect:/category/"+byId.getcompuCategory().getLowerCase();
        }
        return null;
    }


}
