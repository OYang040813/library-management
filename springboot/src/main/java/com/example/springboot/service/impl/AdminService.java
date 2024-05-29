package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.KeynumRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminSevice;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminSevice {

    @Autowired
    AdminMapper adminMapper;

    private static final String PASS_SALT = "ouyang";

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    @Override
    public void save(Admin obj) {
        Date date = new Date();

        //卡号处理
        obj.setCardnum(DateUtil.format(date,"yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        obj.setUpdatetime(new Date());

        //mt5+加盐并加密密码
        obj.setKeynum(secureKeynum(obj.getKeynum()));

        Admin admin = adminMapper.getByName(obj.getName());
        if (admin==null){
            adminMapper.save(obj);
        }else{
            throw new ServiceException("该用户名已被占用");
        }
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin obj) {
        obj.setUpdatetime(new Date());
        adminMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {
        //输入密码加盐加密后再进入数据库进行对比
        request.setKeynum(secureKeynum(request.getKeynum()));
        Admin admin = null;
        try {
            admin = adminMapper.getByName(request.getName());
        }catch (Exception e){
            log.error("根据用户名{}查询出错",request.getName());
            throw new ServiceException("用户名错误");
        }

        if (admin == null){
            throw new ServiceException("用户名或密码错误");
        }

        //判断密码是否合法
        if (!request.getKeynum().equals(admin.getKeynum())){
            throw  new ServiceException("用户名或密码错误");
        }
        if (!admin.getStatus()){
            throw new ServiceException("当前用户已被禁用，请尝试登陆其他账号");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);

        //用用户ID与用户密码生成Token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getKeynum());
        loginDTO.setToken(token);
        return loginDTO;
    }

    @Override
    public void changeKeynum(KeynumRequest request) {
        request.setNewkeynum(secureKeynum(request.getNewkeynum()));
        request.setKeynum(secureKeynum(request.getKeynum()));
        int count = adminMapper.updateKeynum(request);
        if(count <= 0){
            throw new ServiceException("修改密码失败");
        }
    }

    private String secureKeynum(String keynum){
        return SecureUtil.md5(keynum + PASS_SALT);
    }
}
