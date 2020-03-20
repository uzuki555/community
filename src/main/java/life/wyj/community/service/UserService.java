package life.wyj.community.service;


import life.wyj.community.exception.CustomizeErrorCode;
import life.wyj.community.exception.CustomizeException;
import life.wyj.community.mapper.UserMapper;
import life.wyj.community.model.User;
import life.wyj.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUserList = userMapper.selectByExample(userExample);
        User dbUser =null;
        if(dbUserList.size()!=0){
             dbUser=dbUserList.get(0);
        }
        if(dbUser ==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(dbUser,userExample1);
        }
    }
    public  User  login(User user){
        UserExample userExample  = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName());
        List<User> dbUserList = userMapper.selectByExample(userExample);
        User dbUser = null;
        if(dbUserList.size()!=0){
            dbUser=dbUserList.get(0);
        }
        if(dbUser==null){
            return null;
        }
        if(StringUtils.equals(dbUser.getPassword(),user.getPassword())){
            return  dbUser;
        }else{
            return null;
        }
    }
    @Transactional
    public String register(@Valid User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName());
        List<User> dbUserList = userMapper.selectByExample(userExample);
        if(dbUserList.size()!=0){
            return null;
        }
        user.setAccountId(Integer.toHexString((int)new Date().getTime()));
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setToken(UUID.randomUUID().toString());
        user.setAvatarUrl("https://avatars0.githubusercontent.com/u/41314621?v=4");

       userMapper.insert(user);
       return user.getToken();
    }
}
