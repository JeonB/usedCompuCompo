package jeonb.usedcompu.annotation.service;

import jeonb.usedcompu.entity.CompuPost;
import jeonb.usedcompu.entity.Pagination;
import jeonb.usedcompu.entity.ValidCheckResponse;
import jeonb.usedcompu.repository.CompuPostRepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CompuPostService {

    private final CompuPostRepositoryMapper compuPostMapper;

    @Autowired
    public CompuPostService(CompuPostRepositoryMapper compuPostMapper) {
        this.compuPostMapper = compuPostMapper;
    }

    public List newCompuPostValidCheck(CompuPost compuPost, BindingResult bindingResult){
        List<ValidCheckResponse> response = new ArrayList<>();

        String[] fields = {"compuName", "compuCategory", "compuPrice", "compuDescription"};
        //defaultErrorAdd
        for (String field : fields) {
            if(bindingResult.hasFieldErrors(field)){
                StringBuilder errorMessage = new StringBuilder("");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors(field);
                for (FieldError fieldError : fieldErrors) {
                    errorMessage.append(fieldError.getDefaultMessage()+"<br>");
                }
                response.add(new ValidCheckResponse(false, field, errorMessage.toString()));
            }
        }

        //custom
        if(compuPost.getCompuCategory() == null){
            response.add(new ValidCheckResponse(false, "compuCategory", "값을 선택해주세요"));
        }

//        for (String field : fields) {
//            if(!bindingResult.hasFieldErrors(field)){
//                response.add(new ValidCheckResponse(true, field, ""));
//            }
//        }
        return response;
    }

    public ResponseEntity<Resource> fileFoundTest(String imgName) throws IOException {
        String uploadPath = Paths.get("C:", "Users", "jeon", "Desktop", "usedCompuCompo", "src", "main", "resources", "templates", "compuPost", "getImage").toString();
        Resource resource = new FileSystemResource(uploadPath + File.separator + imgName);

        if(!resource.exists()){
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        Path savePath = Paths.get(uploadPath + File.separator + imgName).toAbsolutePath();
        headers.add("Content-Type", Files.probeContentType(savePath));

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    public String pageProcess(String categoryName, Pagination pagination){
        pagination.setCategory(categoryName);
        pagination.setSearch();
        pagination.setListLastNum(compuPostMapper.findByCategoryCount(pagination));

        //log.info("pagination.getPage()={}", pagination.getPage());
        if(pagination.getPage() < 1){
            return "category/"+categoryName+"?page=1" +
                    "&searchRange="+pagination.getSearchRange()+
                    "&searchText="+pagination.getSearchText();
        }
        if(pagination.getPage() > pagination.getListLastNum()){
            return "category/"+categoryName+"?page="+pagination.getListLastNum()+
                    "&searchRange="+pagination.getSearchRange()+
                    "&searchText="+pagination.getSearchText();
        }

        pagination.setterProcess();

        return null;
    }

    public String pageProcess(Pagination pagination){
        pagination.setSearch();
        pagination.setListLastNum(compuPostMapper.findByAllCount(pagination));

        //log.info("pagination.getPage()={}", pagination.getPage());
        if(pagination.getPage() < 1){
            return "search?page=1" +
                    "&searchRange="+pagination.getSearchRange()+
                    "&searchText="+pagination.getSearchText();
        }
        if(pagination.getPage() > pagination.getListLastNum()){
            return "search?page="+pagination.getListLastNum()+
                    "&searchRange="+pagination.getSearchRange()+
                    "&searchText="+pagination.getSearchText();
        }

        pagination.setterProcess();

        return null;
    }


}
