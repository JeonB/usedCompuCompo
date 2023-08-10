package jeonb.usedcompu.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
public class CompuPost {
    @Id
    private Long id;

    @NotBlank
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

    @Transient
    private List<MultipartFile> fileList;

    private String createTime;

    private Integer viewCount=0;

    @Transient
    private List<String> removeFileList;

    private Integer commentCount;

    private String thumbFileName;
}
