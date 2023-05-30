package jeonb.usedcompu.model;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class CompuPost {

    private Long id;

    //@NotBlank
    private String writerEmail;

    @NotBlank
    private String compuName;

    @NotNull //NotBlank는 String과 Collection타입에만 적용된다
    private CompuCategory compuCategory;

    @NotNull //NotBlank는 String과 Collection타입에만 적용된다
    @Min(1000)
    private Integer compuPrice;

    @NotBlank
    private String compuDescription;

    private List<MultipartFile> fileList;

    private String createTime;

    private Integer viewCount=0;

    private List<String> removeFileList;

    private Integer commentCount;

    private String thumbFileName;
}
