package mighty.youtube.video.controller;


import mighty.youtube.video.dto.GeneralMessage;
import mighty.youtube.video.dto.VideoDetail;
import mighty.youtube.video.dto.VideoDetailsRequestBody;
import mighty.youtube.video.exception.InvalidFileType;
import mighty.youtube.video.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity uploadVideo(@RequestPart("videoFile")MultipartFile video,
                                      @RequestParam UUID channelId,
                                      @RequestPart ("videodetails") VideoDetailsRequestBody videoDetailsRequestBody){

        try{
        VideoDetail videoDetail = uploadService.uploadVideo(video , channelId , videoDetailsRequestBody);
        return new ResponseEntity(videoDetail, HttpStatus.CREATED); // 201
        }catch (
        InvalidFileType invalidFileType){
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setMessage(invalidFileType.getMessage());
        return new ResponseEntity(generalMessage, HttpStatus.BAD_REQUEST);
         }catch (Exception e){
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setMessage(e.getMessage());
        return new ResponseEntity(generalMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    }
}
