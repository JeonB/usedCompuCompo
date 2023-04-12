package thwjd.usedbook.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookPostFile {

    private Long id;

    @NotNull
    private Long bookPostId;

    @NotBlank
    private String writerEmail;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileName;

    public BookPostFile(){

    }

    public BookPostFile(Long bookPostId, String writerEmail, String filePath, String fileName){
        this.bookPostId = bookPostId;
        this.writerEmail = writerEmail;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
