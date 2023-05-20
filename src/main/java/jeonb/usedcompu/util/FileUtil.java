package jeonb.usedcompu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 파일 저장 및 읽기를 위한 유틸리티 클래스
 *
 * @since 1.0.0-SNAPSHOT
 */
@SuppressWarnings("unused")
public class FileUtil {

    /** Logger 출력을 위한 객체 */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 지정된 디렉토리 경로에 파일을 저장합니다.
     *
     * @param directoryPath 저장할 디렉토리 경로
     * @param filename 저장할 파일명
     * @param data 저장할 파일의 데이터(byte 배열)
     * @throws IOException 파일 저장 중에 I/O 오류가 발생한 경우
     */
    public static void save(String directoryPath, String filename, byte[] data) throws IOException {
        String pathname = directoryPath + File.separator + filename;
        File file = new File(pathname);
        FileCopyUtils.copy(data, file);
    }

    /**
     * 파일(절대경로 포함)을 저장합니다.
     *
     * @param filePath 저장할 파일명
     * @param data 저장할 파일의 데이터(byte 배열)
     * @throws IOException 파일 저장 중에 I/O 오류가 발생한 경우
     */
    public static void save(String filePath, byte[] data) throws IOException {
        File file = new File(filePath);
        FileCopyUtils.copy(data, file);
    }

    /**
     * 지정된 파일 경로에서 파일을 다운로드하고 InputStreamResource를 반환합니다.
     *
     * @param filePath 다운로드할 파일의 경로
     * @return 다운로드한 파일을 나타내는 InputStreamResource
     * @throws IOException 파일을 읽는 동안 I/O 오류가 발생한 경우
     */
    public static InputStreamResource download(String filePath) throws IOException {
        return new InputStreamResource(Files.newInputStream(Paths.get(filePath)));
    }

}
