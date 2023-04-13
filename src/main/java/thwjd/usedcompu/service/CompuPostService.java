package thwjd.usedcompu.service;

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
import org.springframework.web.multipart.MultipartFile;
import thwjd.usedcompu.entity.CompuPost;
import thwjd.usedcompu.entity.CompuPostFile;
import thwjd.usedcompu.entity.Pagination;
import thwjd.usedcompu.entity.ValidCheckResponse;
import thwjd.usedcompu.repository.CompuPostFileRepositoryMapper;
import thwjd.usedcompu.repository.CompuPostRepositoryMapper;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CompuPostService {

    @Autowired CompuPostRepositoryMapper compuPostMapper;
    @Autowired CompuPostFileRepositoryMapper compuPostFileMapper;

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


    public void fileSave(CompuPost compuPost) throws IOException {
        //String uploadPath = Paths.get("D:", "projectEn", "usedcompu", "src", "main", "resources", "userUploadImg").toString();
        String uploadPath = Paths.get("D:", "projectEn", "usedcompu", "userUploadImg").toString();

        int order = 10;
        for (MultipartFile multipartFile : compuPost.getFileList()) {
            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + order + "_" + multipartFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath + File.separator + filename).toAbsolutePath();

            multipartFile.transferTo(savePath.toFile());
            //MultipartFile.transferTo()는 요청 시점의 임시 파일을 로컬 파일 시스템에 영구적으로 복사하는 역할을 수행한다.
            // 단 한번만 실행되며 두번째 실행부터는 성공을 보장할 수 없다.
            //Embedded Tomcat을 컨테이너로 사용할 경우 DiskFileItem.write()가 실제 역할을 수행한다.
            // I/O 사용을 최소화하기 위해 파일 이동을 시도하며, 이동이 불가능할 경우 파일 복사를 진행한다.

            CompuPostFile compuPostFile = new CompuPostFile(
                    compuPost.getId(),
                    compuPost.getWriterEmail(),
                    uploadPath,
                    filename
            );
            compuPostFileMapper.save(compuPostFile);
            order--;
        }
    }



    public ResponseEntity<Resource> fileFoundTest(String imgName) throws IOException {
        String uploadPath = Paths.get("D:", "projectEn", "usedcompu", "userUploadImg").toString();
        Resource resource = new FileSystemResource(uploadPath + File.separator + imgName);

        if(!resource.exists()){
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        Path savePath = Paths.get(uploadPath + File.separator + imgName).toAbsolutePath();
        headers.add("Content-Type", Files.probeContentType(savePath));

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }


    public void fileUpdate(CompuPost compuPost) throws IOException {
        //String uploadPath = Paths.get("D:", "projectEn", "usedcompu", "src", "main", "resources", "userUploadImg").toString();
        String uploadPath = Paths.get("D:", "projectEn", "usedcompu", "userUploadImg").toString();

        List<CompuPostFile> byId = compuPostFileMapper.findById(compuPost.getId());
        int order = 10 - byId.size() -1;

        List<String> removeFileList = compuPost.getRemoveFileList();
        if(removeFileList != null){
            for (String removeFile : removeFileList) {
                String temp = removeFile.replace("/compuPost/getImage/", "");
                temp = URLDecoder.decode(temp, StandardCharsets.UTF_8);
                compuPostFileMapper.removeFile(compuPost.getId(), temp);
            }
        }

        if(compuPost.getFileList() != null){
            for (MultipartFile multipartFile : compuPost.getFileList()) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + order + "_" + multipartFile.getOriginalFilename();
                Path savePath = Paths.get(uploadPath + File.separator + filename).toAbsolutePath();

                multipartFile.transferTo(savePath.toFile());
                //MultipartFile.transferTo()는 요청 시점의 임시 파일을 로컬 파일 시스템에 영구적으로 복사하는 역할을 수행한다.
                // 단 한번만 실행되며 두번째 실행부터는 성공을 보장할 수 없다.
                //Embedded Tomcat을 컨테이너로 사용할 경우 DiskFileItem.write()가 실제 역할을 수행한다.
                // I/O 사용을 최소화하기 위해 파일 이동을 시도하며, 이동이 불가능할 경우 파일 복사를 진행한다.

                CompuPostFile compuPostFile = new CompuPostFile(
                        compuPost.getId(),
                        compuPost.getWriterEmail(),
                        uploadPath,
                        filename
                );
                compuPostFileMapper.save(compuPostFile);
                order--;
            }
        }

    }

    public void fileDelete(Long compuPostId) {
        List<CompuPostFile> byId = compuPostFileMapper.findById(compuPostId);

        for (CompuPostFile compuPostFile : byId) {
            File file = new File(compuPostFile.getFilePath() + File.separator + compuPostFile.getFileName());

            if(file.exists()){
                file.delete();
            }
        }
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