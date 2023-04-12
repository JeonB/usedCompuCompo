package thwjd.usedbook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import thwjd.usedbook.annotation.Login;
import thwjd.usedbook.entity.BookPost;
import thwjd.usedbook.entity.BookPostFile;
import thwjd.usedbook.entity.Comment;
import thwjd.usedbook.entity.Member;
import thwjd.usedbook.repository.BookPostFileRepositoryMapper;
import thwjd.usedbook.repository.BookPostRepositoryMapper;
import thwjd.usedbook.repository.CommentRepositoryMapper;
import thwjd.usedbook.service.BookPostService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/bookPost")
public class BookPostController {

    @Autowired BookPostService bookPostService;
    @Autowired BookPostRepositoryMapper bookPostMapper;
    @Autowired BookPostFileRepositoryMapper bookPostFileMapper;
    @Autowired CommentRepositoryMapper commentMapper;

    @GetMapping("/write")
    public String writeForm(@ModelAttribute BookPost bookPost){
        return "bookPost/write";
    }

    @PostMapping("/write")
    @ResponseBody
    public Map writeSave(@Validated @ModelAttribute BookPost bookPost, BindingResult bindingResult,
                               HttpServletRequest request, @Login Member loginMember) throws IOException {
        Map<String, Object> response = new HashMap<>();

        if(bookPost.getFileList().size() > 10){
            response.put("status", "validPhoto");
            response.put("response", "사진은 10개까지만 업로드 가능합니다.");
        }

        List validList = bookPostService.newBookPostValidCheck(bookPost, bindingResult);
        if(validList.size() > 0){
            response.put("status", "valid");
            response.put("response", validList);
        }

        if(validList.size() == 0){
            if(loginMember != null){
                bookPost.setWriterEmail(loginMember.getEmail());                
            }else{
                bookPost.setWriterEmail("익명@admin");    
            }

            Date currentTime = new Date();
            bookPost.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));
            
            bookPostMapper.save(bookPost);  //id저장됨
            bookPostService.fileSave(bookPost);

            String currentUrl = request.getRequestURL().toString();
            String redirectUrl = currentUrl.replace("/bookPost/write", "/bookPost/detail/"+bookPost.getId());
            response.put("status", "saveOk");
            response.put("response", redirectUrl);
        }

        return response;
    }



    @GetMapping("/detail/{bookPostId}")
    public String detail(@PathVariable Long bookPostId, Model model){
        BookPost byId = bookPostMapper.findById(bookPostId);

        byId.setViewCount(byId.getViewCount() +1);
        bookPostMapper.viewPlus(byId);
        model.addAttribute("bookPost", byId);

        List<BookPostFile> byIdFile = bookPostFileMapper.findById(bookPostId);
        model.addAttribute("fileList", byIdFile);

        return "bookPost/detail";
    }

    @PostMapping("/detail/commentList")
    @ResponseBody
    public List detailComment(@RequestBody Map<String, Long> map){
        return commentMapper.findAll(map.get("bookPostId"));
    }

    @GetMapping("/getImage/{imgName}")
    private ResponseEntity<Resource> getImageUrl(@PathVariable String imgName) throws IOException {
        return bookPostService.fileFoundTest(imgName);
        //http://localhost:8080/bookPost/imgtest/69221ce3-e582-4ce5-9a56-3730b5ba53ec_9_%EC%BD%94%EB%94%A9%EB%A7%88%EC%A7%80%EB%A7%89.jpg
    }

    @GetMapping("/edit/{bookPostId}")
    public String edit(@PathVariable Long bookPostId, Model model){
        BookPost byId = bookPostMapper.findById(bookPostId);
        model.addAttribute("bookPost", byId);

        List<BookPostFile> byIdFile = bookPostFileMapper.findById(bookPostId);
        model.addAttribute("fileList", byIdFile);

        return "bookPost/write";
    }
    @PostMapping("/edit")
    @ResponseBody
    public Map editSave(@Validated @ModelAttribute BookPost bookPost, BindingResult bindingResult,
                         HttpServletRequest request, @Login Member loginMember) throws IOException {
        Map<String, Object> response = new HashMap<>();

        List<BookPostFile> byId = bookPostFileMapper.findById(bookPost.getId());

        if(byId.size() > 10){
            response.put("status", "validPhoto");
            response.put("response", "사진은 10개까지만 업로드 가능합니다.");
        }

        List validList = bookPostService.newBookPostValidCheck(bookPost, bindingResult);
        if(validList.size() > 0){
            response.put("status", "valid");
            response.put("response", validList);
        }

        if(validList.size() == 0){
            if(loginMember != null){
                bookPost.setWriterEmail(loginMember.getEmail());
            }else{
                bookPost.setWriterEmail("익명@admin");
            }

            bookPostMapper.update(bookPost);
            bookPostService.fileUpdate(bookPost);

            String currentUrl = request.getRequestURL().toString();
            String redirectUrl = currentUrl.replace("/bookPost/edit", "/bookPost/detail/"+bookPost.getId());
            response.put("status", "saveOk");
            response.put("response", redirectUrl);
        }

        return response;
    }


    @GetMapping("/remove/{bookPostId}")
    public String remove(@PathVariable Long bookPostId){

        BookPost byId = bookPostMapper.findById(bookPostId);

        bookPostService.fileDelete(bookPostId);

        int deletePost = bookPostMapper.delete(bookPostId);

        if(deletePost == 1 ){
            return "redirect:/category/"+byId.getBookCategory().getLowerCase();
        }
        return null;
    }


}
