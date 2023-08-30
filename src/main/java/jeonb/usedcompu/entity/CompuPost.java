package jeonb.usedcompu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import jeonb.usedcompu.model.CompuCategory;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
public class CompuPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100)
    private String writerEmail;

    @NotBlank
    @Column(length = 200)
    private String compuName;

    @NotNull //NotBlank는 String과 Collection타입에만 적용된다
    @Enumerated(EnumType.STRING)
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

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
