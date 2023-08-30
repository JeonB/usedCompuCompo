package jeonb.usedcompu.controller;

import jeonb.usedcompu.model.Comment;
import jeonb.usedcompu.entity.CompuPost;
import jeonb.usedcompu.model.Pagination;
import jeonb.usedcompu.repository.CommentRepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jeonb.usedcompu.repository.CompuPostRepositoryMapper;
import jeonb.usedcompu.service.CompuPostService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/category")
public class CategoryController {

    CompuPostRepositoryMapper compuPostMapper;
    CompuPostService compuPostService;
    CommentRepositoryMapper commentMapper;

    @Autowired
    public CategoryController(CompuPostRepositoryMapper compuPostMapper,
            CompuPostService compuPostService, CommentRepositoryMapper commentMapper) {
        this.compuPostMapper = compuPostMapper;
        this.compuPostService = compuPostService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{categoryName}")
    public String category(@PathVariable String categoryName, @ModelAttribute Pagination pagination, Model model){

        //log.info("compupostid={}", compupostid);

        String redirectUrl = compuPostService.pageProcess(categoryName, pagination);

        if(redirectUrl != null){
            return "redirect:/"+redirectUrl;
        }

        List<CompuPost> lists = compuPostMapper.findByPaginationAndSearch(pagination);
        for (CompuPost list : lists) {
            List<Comment> comments = commentMapper.findAll(list.getId());
            list.setCommentCount(comments.size());
        }
        model.addAttribute("lists", lists);
        model.addAttribute("baseUrl", "/category/"+categoryName);

        return "compuPost/list";
    }

    @GetMapping("/listOrder")
    //@ResponseBody
    public String listOrder(@RequestParam String categoryName, @ModelAttribute Pagination pagination, Model model){

        List<CompuPost> lists;
        //log.info("order={}", pagination.getOrder());

        if(categoryName.equals("")){
            compuPostService.pageProcess(pagination);
            lists = compuPostMapper.findByAllPaginationAndSearch(pagination);
        }else{
            compuPostService.pageProcess(categoryName, pagination);
            lists = compuPostMapper.findByPaginationAndSearch(pagination);
        }

        model.addAttribute("lists", lists);
        return "compuPost/list :: #listTable";
    }




}
