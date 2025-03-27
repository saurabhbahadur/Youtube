package mighty.youtube.central.service;


import mighty.youtube.central.models.Tag;
import mighty.youtube.central.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepo tagRepo;

    public Tag getTagByName(String name){
        return tagRepo.findByName(name);
    }

    public List<Tag> getAllTagsFromSystem(List<String> tags) {
        List<Tag> dbTagList = new ArrayList<>();

        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);

            Tag tagObj = this.getTagByName(tag);

            if (tagObj == null) {

                Tag newTag = new Tag();
                newTag.setName(tag);
                tagRepo.save(newTag);
                dbTagList.add(newTag);
            } else {
                dbTagList.add(tagObj);
            }

        }

        return dbTagList;
    }
}
