package com.nora.website.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nora.website.constant.TourismConstant;
import com.nora.website.entity.Admin;
import com.nora.website.entity.AdminExample;
import com.nora.website.exception.EditAdminAcctFailedException;
import com.nora.website.exception.LoginAcctAlreadyInUseException;
import com.nora.website.exception.LoginFailedException;
import com.nora.website.mapper.AdminMapper;
import com.nora.website.service.api.AdminService;
import com.nora.website.util.tourismUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {

        //1.密码加密
        String userPswd = admin.getUserPswd();
        userPswd = tourismUtil.md5(userPswd);
        admin.setUserPswd(userPswd);

        //2.生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        //3.执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            // 检测当前捕获的异常对象，如果是 DuplicateKeyException 类型说明是账号重复导致的
            if(e instanceof DuplicateKeyException) {
            // 抛出自定义的 LoginAcctAlreadyInUseException 异常
                throw new LoginAcctAlreadyInUseException(TourismConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            // 为了不掩盖问题，如果当前捕获到的不是 DuplicateKeyException 类型的异常，则把当前捕获到的异常对象继续向上抛出
            throw e;
        }

    }

    @Override
    public List<Admin> getAll() {

        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        //1.根据登录账号查询Admin对象
            //(1).创建AdminExample对象
            AdminExample adminExample = new AdminExample();

            //(2)创建Criteria对象
            AdminExample.Criteria criteria = adminExample.createCriteria();

            //(3)在Criteria对象中封装查询条件
            criteria.andLoginAcctEqualTo(loginAcct);

            //(4)调用AdminMapper的方法执行查询
            List<Admin> list = adminMapper.selectByExample(adminExample);
        //2.判断Admin对象是否为null
        if(list == null || list.size() == 0){
            throw new LoginFailedException(TourismConstant.MESSAGE_LOGIN_FAILED);
        }
        if (list.size() > 1){
            throw new RuntimeException(TourismConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);
        //3.如果Admin对象为null则抛出异常
        if (admin == null){
            throw new LoginFailedException(TourismConstant.MESSAGE_LOGIN_FAILED);
        }
        
        //4.如果Admin对象不为null则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();
        //5.将表单提交的明文密码进行加密
        String userPswdForm = tourismUtil.md5(userPswd);
        //6.对密码进行比较
        if(Objects.equals(userPswdDB,userPswdForm)){
            //7.如果一致则返回Admin对象
            return admin;
        }else {
            //8.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(TourismConstant.MESSAGE_LOGIN_FAILED);
        }



    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        //1.调用PageHelper的静态方法开启分页功能
        //体现PageHelper的“非侵入式”设计：原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum,pageSize);

        //2.执行查询
        List<Admin> list = adminMapper.seleceAdminByKeyword(keyword);

        //3.封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    @Override
    public void remove(Integer adminId) {

        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {

        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {

        //简单密码验证，有Bug待修

        //取出表单密码
        String userpw = admin.getUserPswd();

        //加密
        String s = tourismUtil.md5(userpw);

        //取出数据库中对应id对象及密码
        Integer id = admin.getId();
        Admin admin1 = adminMapper.selectByPrimaryKey(id);
        String userPw1 = admin1.getUserPswd();

        //比较密码，如果密码相同则报错
        if(Objects.equals(s,userPw1)){
            throw new EditAdminAcctFailedException(TourismConstant.MESSAGE_EDITPW_FILED);
        }else {
            admin.setUserPswd(s);
            //有选择的更新，对于null值的字段不更新
            adminMapper.updateByPrimaryKeySelective(admin);
        }


    }
}
