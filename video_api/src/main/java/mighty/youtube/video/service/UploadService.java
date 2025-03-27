package mighty.youtube.video.service;


import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import mighty.youtube.video.dto.VideoDetail;
import mighty.youtube.video.dto.VideoDetailsRequestBody;
import mighty.youtube.video.exception.InvalidFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UploadService {

    @Autowired
    ImageKit imageKit;

    public boolean isVideoFile(MultipartFile file){
        boolean res = file.getContentType().startsWith("video/");
        return res;
    }

    public VideoDetail uploadVideo(MultipartFile video,
                                   UUID channelId,
                                   VideoDetailsRequestBody videoDetailsRequestBody) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {


        boolean isVideo = isVideoFile(video);
        if(!isVideo){
            throw  new InvalidFileType("File upload is not video");
        }

        byte [] videoBytes = video.getBytes();

        FileCreateRequest videoRequest = new FileCreateRequest(videoBytes, video.getOriginalFilename());
        videoRequest.setUseUniqueFileName(true);

        Result result = imageKit.upload(videoRequest);
        String videoId = result.getFileId();
        String videoUrl = result.getUrl();
        VideoDetail videoDetail = new VideoDetail();
        videoDetail.setVideoId(videoId);
        videoDetail.setVideoUrl(videoUrl);

        return videoDetail;

    }

}
