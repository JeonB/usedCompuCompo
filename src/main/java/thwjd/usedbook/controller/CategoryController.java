package thwjd.usedbook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thwjd.usedbook.entity.BookPost;
import thwjd.usedbook.entity.Comment;
import thwjd.usedbook.entity.Pagination;
import thwjd.usedbook.repository.BookPostRepositoryMapper;
import thwjd.usedbook.repository.CommentRepositoryMapper;
import thwjd.usedbook.service.BookPostService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired BookPostRepositoryMapper bookPostMapper;
    @Autowired BookPostService bookPostService;
    @Autowired CommentRepositoryMapper commentMapper;

    @GetMapping("/{categoryName}")
    public String category(@PathVariable String categoryName, @ModelAttribute Pagination pagination, Model model){

        //log.info("bookpostid={}", bookpostid);

        String redirectUrl = bookPostService.pageProcess(categoryName, pagination);

        if(redirectUrl != null){
            return "redirect:/"+redirectUrl;
        }

        List<BookPost> lists = bookPostMapper.findByPaginationAndSearch(pagination);
        for (BookPost list : lists) {
            List<Comment> comments = commentMapper.findAll(list.getId());
            list.setCommentCount(comments.size());
        }
        model.addAttribute("lists", lists);
        model.addAttribute("baseUrl", "/category/"+categoryName);

        return "bookPost/list";
    }

    @GetMapping("/listOrder")
    //@ResponseBody
    public String listOrder(@RequestParam String categoryName, @ModelAttribute Pagination pagination, Model model){

        List<BookPost> lists;
        //log.info("order={}", pagination.getOrder());

        if(categoryName.equals("")){
            bookPostService.pageProcess(pagination);
            lists = bookPostMapper.findByAllPaginationAndSearch(pagination);
        }else{
            bookPostService.pageProcess(categoryName, pagination);
            lists = bookPostMapper.findByPaginationAndSearch(pagination);
        }

        model.addAttribute("lists", lists);
        return "bookPost/list :: #listTable";
    }




}
