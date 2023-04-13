package thwjd.usedcompu.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CompuPostFile {

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
