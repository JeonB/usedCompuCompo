package jeonb.usedcompu.service;

import javax.servlet.http.HttpServletRequest;
import jeonb.usedcompu.model.CompuPost;
import jeonb.usedcompu.model.CompuPostFile;
import jeonb.usedcompu.repository.CompuPostFileRepositoryMapper;
import jeonb.usedcompu.repository.PostFileRepository;
import jeonb.usedcompu.repository.PostRepository;
import jeonb.usedcompu.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

/**
 * 첨부파일 서비스
 */
@Service
@Transactional
public class CompuPostFileService {

    /**
     * 첨부파일 영속성 매퍼
     */
    private final CompuPostFileRepositoryMapper compuPostFileMapper;
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;

    @Value("${upload.directory}")
    private String upload_directory;


    @Autowired
    public CompuPostFileService(CompuPostFileRepositoryMapper compuPostFileMapper,
            PostRepository postRepository, PostFileRepository postFileRepository) {
        this.compuPostFileMapper = compuPostFileMapper;
        this.postRepository = postRepository;
        this.postFileRepository = postFileRepository;
    }

    /**
     * 게시글 정보를 바탕으로 첨부파일을 쓰고 첨부파일 데이터를 저장한다.
     *
     * @param compuPost    게시글 객체
     * @throws IOException 파일 저장 중에 I/O 오류가 발생한 경우
     */
    public void save(CompuPost compuPost) throws IOException {

        int order = 10;
        for (MultipartFile multipartFile : compuPost.getFileList()) {
            String filename = UUID.randomUUID() + "_" + order + "_" + multipartFile.getOriginalFilename();
             String uploadPath = upload_directory; // 저장할 파일 경로

            FileUtil.save(uploadPath,filename, multipartFile.getBytes());
//            compuPostFileMapper.save(new CompuPostFile(compuPost.getId(), compuPost.getWriterEmail(),
//                    uploadPath, filename));
            postFileRepository.save(new CompuPostFile(compuPost.getId(), compuPost.getWriterEmail(),
                    uploadPath, filename));
            order--;
        }
    }


    /**
     * 게시글 정보를 바탕으로 파일을 지우고 새로 씀
     *
     * @param compuPost    게시글 객체
     * @throws IOException 파일 저장 중에 I/O 오류가 발생한 경우
     */
    public void update(CompuPost compuPost,HttpServletRequest request) throws IOException {
        List<CompuPostFile> byId = compuPostFileMapper.findById(compuPost.getId());
        int order = 10 - byId.size() -1;

        List<String> removeFileList = compuPost.getRemoveFileList();
        if(removeFileList != null){
            for (String removeFile : removeFileList) {
                String temp = removeFile.replace(upload_directory, "");
                temp = URLDecoder.decode(temp, "utf-8");
                compuPostFileMapper.removeFile(compuPost.getId(), temp);
            }
        }

        if(compuPost.getFileList() != null){
            for (MultipartFile multipartFile : compuPost.getFileList()) {
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + order + "_" + multipartFile.getOriginalFilename();
                String uploadPath = request.getSession().getServletContext().getRealPath(upload_directory); // 저장할 파일 경로
                String pathname = uploadPath + File.separator + filename;
                multipartFile.transferTo(new File(pathname));
                //MultipartFile.transferTo()는 요청 시점의 임시 파일을 로컬 파일 시스템에 영구적으로 복사하는 역할을 수행한다.
                // 단 한번만 실행되며 두번째 실행부터는 성공을 보장할 수 없다.
                //Embedded Tomcat을 컨테이너로 사용할 경우 DiskFileItem.write()가 실제 역할을 수행한다.
                // I/O 사용을 최소화하기 위해 파일 이동을 시도하며, 이동이 불가능할 경우 파일 복사를 진행한다.

                compuPostFileMapper.save(new CompuPostFile(compuPost.getId(), compuPost.getWriterEmail(),
                        upload_directory, filename));
                order--;
            }
        }

    }

    /**
     * 해당 게시글에 첨부된 파일을 삭제한다.
     *
     * @param compuPostId 게시글 식별자
     */
    public void delete(Long compuPostId) {
        List<CompuPostFile> byId = compuPostFileMapper.findById(compuPostId);

        for (CompuPostFile compuPostFile : byId) {
            File file = new File(compuPostFile.getFilePath() + File.separator + compuPostFile.getFileName());

            if(file.exists()){
                file.delete();
            }
        }
    }


}
