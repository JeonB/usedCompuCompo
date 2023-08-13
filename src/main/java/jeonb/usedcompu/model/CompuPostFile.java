package jeonb.usedcompu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CompuPostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long compuPostId;

    @NotBlank
    private String writerEmail;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileName;

    public CompuPostFile(){

    }

    public CompuPostFile(Long compuPostId, String writerEmail, String filePath, String fileName){
        this.compuPostId = compuPostId;
        this.writerEmail = writerEmail;
        this.filePath = filePath;
        this.fileName = fileName;
    }


}
