package com.nora.website.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.nora.website.constant.TourismConstant;
import com.nora.website.entity.Admin;
import com.nora.website.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session){

        //强制Session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session){

        //调用Service方法执行登录检查
        //这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        //将登录成功返回的admin对象存入Session域
        session.setAttribute(TourismConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        //避免表单重复提交
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(

            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){
        //调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        //将PageInfo对象存入模型
        modelMap.addAttribute(TourismConstant.ATTR_NAME_PAGE_INFO,pageInfo);

        return "admin-page";
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword
    ){

        //执行删除
        adminService.remove(adminId);

        //页面跳转:回到分页页面
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/save.html")
    public String saveAdmin(Admin admin){

        adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html";
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ){
       //根据adminId查询对象
       Admin admin = adminService.getAdminById(adminId);

       //将admin对象存入模型
       modelMap.addAttribute(admin);

        return "admin-edit";
    }

    @RequestMapping("/admin/update.html")
    public String updateAdmin(@RequestParam("pageNum") Integer pageNum,
                              @RequestParam("keyword") String keyword,
                              Admin admin
                              ){

        adminService.updateAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
