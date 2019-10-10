package life.wyj.community.service;

import life.wyj.community.dto.NotificationDTO;
import life.wyj.community.dto.PaginationDTO;
import life.wyj.community.dto.QuestionDTO;
import life.wyj.community.enums.NotificationTypeEnum;
import life.wyj.community.mapper.NotificationMapper;
import life.wyj.community.mapper.UserMapper;
import life.wyj.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        notificationMapper.selectByExample(notificationExample);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        Integer totalPage;



        if(page<1){
            page =1;
        }
        if(totalCount % size ==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset= size*(page -1);


        NotificationExample notificationMapper1 = new NotificationExample();
        notificationMapper1.createCriteria().andReceiverEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationMapper1, new RowBounds(offset, size));
        List<NotificationDTO> notificationDTOs = new ArrayList<>();


        if(notifications.size()==0){
            return paginationDTO;
        }
//        for(Notification notification : notifications){
//            NotificationDTO notificationDTO = new NotificationDTO();
//
//            BeanUtils.copyProperties(notification,notificationDTO);
//            notificationDTO.setNotifier();
//            notificationDTO.setOuterTitle();
//
//        }

        for(Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setType(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOs.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOs);

        return paginationDTO;
    }
}
