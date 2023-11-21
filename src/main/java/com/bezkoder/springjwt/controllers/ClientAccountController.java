package com.bezkoder.springjwt.controllers;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bezkoder.springjwt.models.ResponseObject;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.models.dto.PasswordDTO;
import com.bezkoder.springjwt.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")

public class ClientAccountController {

//    @Autowired
//    private SanPhamService sanPhamService;

    @Autowired
    private IUserService userService;

//    @Autowired
//    private DonHangService donHangService;



    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }

    @GetMapping("/account")
    public String accountPage(HttpServletRequest res, Model model) {
        User currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
//        List<DonHang> list = Lists.reverse(donHangService.getDonHangByNguoiDung(currentUser));
//        model.addAttribute("list",list);
        return "client/account";
    }

    @GetMapping("/changeInformation")
    public String clientChangeInformationPage(HttpServletRequest res,Model model) {
        User currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
        return "client/information";
    }

    @GetMapping("/changePassword")
    public String clientChangePasswordPage() {
        return "client/passwordChange";
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public ResponseObject commitChange(HttpServletRequest res, @RequestBody User ng) {
        User currentUser = getSessionUser(res);
        currentUser.setFull_name(ng.getFull_name());
        currentUser.setPhone(ng.getPhone());
        currentUser.setAddress(ng.getAddress());
        userService.updateUser(currentUser);
        return new ResponseObject();
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseObject passwordChange(HttpServletRequest res,@RequestBody PasswordDTO dto) {
        User currentUser = getSessionUser(res);
        if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
            ResponseObject re = new ResponseObject();
            re.setStatus("old");
            return re;
        }
        userService.changePass(currentUser, dto.getNewPassword());
        return new ResponseObject();
    }

}
