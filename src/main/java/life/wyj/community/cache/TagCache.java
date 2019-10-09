package life.wyj.community.cache;

import life.wyj.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static  List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","node","python","c++","c","golang","objective-c","typescript"));
        tagDTOS.add(program);
        TagDTO framework =new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","django","flask","yli"));
        tagDTOS.add(framework);

        TagDTO server =new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","缓存","tomcat","负载均衡"));
        tagDTOS.add(server);


        TagDTO db =new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","sql","oracle","nosql"));
        tagDTOS.add(db);


        TagDTO tool =new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","vim","svn","ide","maven"));
        tagDTOS.add(tool);



        return tagDTOS;
    }
    public static  String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = (List<String>) tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

            return invalid;
    }
}
