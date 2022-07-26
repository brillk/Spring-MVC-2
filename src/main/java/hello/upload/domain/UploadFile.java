package hello.upload.domain;


import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; // 유저가 업로드한 파일 이름
    private String storeFileName; // 서버가 기억하는 파일 이름


    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
