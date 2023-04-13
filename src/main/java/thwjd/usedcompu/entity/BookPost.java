package thwjd.usedcompu.entity;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class BookPost {

    private Long id;

    //@NotBlank
    private String writerEmail;

    @NotBlank
    private String bookName;

    @NotNull //NotBlank는 String과 Collection타입에만 적용된다
    private BookCategory bookCategory;

    @NotNull //NotBlank는 String과 Collection타입에만 적용된다
    @Min(1000)
    private Integer bookPrice;

    @NotBlank
    private String bookDescription;

    private List<MultipartFile> fileList;

    private String createTime;

    private Integer viewCount=0;

    private List<String> removeFileList;

    private Integer commentCount;

    private String thumbFileName;
}
