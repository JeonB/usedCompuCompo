package thwjd.usedbook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thwjd.usedbook.entity.Comment;
import thwjd.usedbook.repository.CommentRepositoryMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired CommentRepositoryMapper commentMapper;

    @PostMapping("/write")
    @ResponseBody
    public Comment commentWrite(@RequestBody Comment comment){

        if(comment.getContent().trim().equals("")){
            return null;
        }

        comment.setWriter("익명댓글@admin");

        Date currentTime = new Date();
        comment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));

        commentMapper.write(comment);
        //log.info("comment={}", comment);

        Comment byId = commentMapper.findById(comment.getId());
        return byId;
    }

    @PostMapping("/delete")
    @ResponseBody
    public int commentDelete(@RequestBody Map<String, Long> map){
        //log.info("commentId={}", map.get("commentId"));
        int success = commentMapper.deleteById(map.get("commentId"));
        return success;
    }

    @PostMapping("/update")
    @ResponseBody
    public Comment commentUpdate(@RequestBody Comment comment){
        if(comment.getContent().trim().equals("")){
            return null;
        }

        int update = commentMapper.update(comment.getId(), comment.getContent());
        //log.info("comment={}", comment);

        Comment byId = commentMapper.findById(comment.getId());
        if(update >= 1){
            return byId;
        }else{
            return null;
        }
    }

}
