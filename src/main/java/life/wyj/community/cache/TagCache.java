package life.wyj.community.cache;

import life.wyj.community.dto.TagDTO;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("");
        program.setTags(Arrays.asList("js","php","css","html","node","python","c++","c","golang","objective-c","typescript"));
        tagDTOS.add(program);
        TagDTO framework =new TagDTO();
        framework.setTags(Arrays.asList());
        tagDTOS.add(framework);
        return tagDTOS;
    }
}
