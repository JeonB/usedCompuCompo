package jeonb.usedcompu.controller;

import jeonb.usedcompu.model.*;
import jeonb.usedcompu.repository.CommentRepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jeonb.usedcompu.repository.CompuPostFileRepositoryMapper;
import jeonb.usedcompu.repository.CompuPostRepositoryMapper;
import jeonb.usedcompu.service.CompuPostService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired CompuPostRepositoryMapper compuPostMapper;
    @Autowired CompuPostFileRepositoryMapper compuPostFileMapper;
    @Autowired CompuPostService compuPostService;
    @Autowired
    CommentRepositoryMapper commentMapper;

   /* enum클래스 상수 리스트 확인
   public static void main(String[] args) {
        HomeController hc = new HomeController();
        hc.test();
    }
    public void test(){
        CompuCategory[] categories = CompuCategory.values();
        for(CompuCategory c : CompuCategory.values())
            System.out.println(c);
    }
    */
    @GetMapping("/")
    public String index(@ModelAttribute Pagination pagination, Model model){

        CompuCategory[] categories = CompuCategory.values(); //GRAPHIC,CPU,RAM

        for (CompuCategory category : categories) {
            List<CompuPost> compuPostList = compuPostMapper.getIndexList(category.toString());

            for (CompuPost compuPost : compuPostList) {
                CompuPostFile oneFile = compuPostFileMapper.getOneFile(compuPost.getId());
                if(oneFile == null){
                    compuPost.setThumbFileName(null);
                }else{
                    compuPost.setThumbFileName(oneFile.getFileName());
                }

            }

            model.addAttribute(category.getLowerCase()+"List", compuPostList);
            //System.out.println(result.get(category.getLowerCase()+"FileList"));
        }

        return "index";
    }
    /*
    HttpSession은 세션 생성 시 임의의 토큰 값을 생성하여 해당 토큰 값을 JSESSIONID 쿠키로 클라이언트에 전달한다.
    http://localhost:8080/;jsessionid=FF5914CE9797A5F3185B2EFF0E4E472F
     */

    @GetMapping("/search")
    public String category(@ModelAttribute Pagination pagination, Model model){

        String redirectUrl = compuPostService.pageProcess(pagination);

        if(redirectUrl != null){
            return "redirect:/"+redirectUrl;
        }
        //log.info("pagination={}", pagination.getSearch());

        List<CompuPost> lists = compuPostMapper.findByAllPaginationAndSearch(pagination);
        for (CompuPost list : lists) {
            List<Comment> comments = commentMapper.findAll(list.getId());
            list.setCommentCount(comments.size());
        }
        //log.info("pagination={}", pagination);
        model.addAttribute("lists", lists);
        model.addAttribute("baseUrl", "/search");

        return "compuPost/list";
    }

    @GetMapping("/test")
    @ResponseBody
    public String testHello(){
        return "testpageHi";
    }
}
