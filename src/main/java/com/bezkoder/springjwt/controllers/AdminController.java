package com.bezkoder.springjwt.controllers;

import javax.servlet.http.HttpServletRequest;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.security.services.IRoleService;
import com.bezkoder.springjwt.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class AdminController {

//    @Autowired
//    private DanhMucService danhMucService;

//    @Autowired
//    private HangSanXuatService hangSXService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

//    @Autowired
//    private LienHeService lienHeService;
//
//    @Autowired
//    private DonHangService donHangService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }

//    @GetMapping
//    public String adminPage(Model model) {
//        ListCongViecDTO listCongViec = new ListCongViecDTO();
//        listCongViec.setSoDonHangMoi(donHangService.countByTrangThaiDonHang("Đang chờ giao"));
//        listCongViec.setSoDonhangChoDuyet(donHangService.countByTrangThaiDonHang("Chờ duyệt"));
//        listCongViec.setSoLienHeMoi(lienHeService.countByTrangThai("Đang chờ trả lời"));
//
//        model.addAttribute("listCongViec", listCongViec);
//        return "admin/trangAdmin";
//    }

    @GetMapping("/category")
    public String manageCategoryPage() {
        return "admin/manageCategory";
    }

//    @GetMapping("/nhan-hieu")
//    public String quanLyNhanHieuPage() {
//        return "admin/quanLyNhanHieu";
//    }

    @GetMapping("/contact")
    public String manageContactPage() {
        return "admin/manageContact";
    }

    @GetMapping("/product")
    public String manageProductPage(Model model) {
//        model.addAttribute("listNhanHieu", hangSXService.getALlHangSX());
//        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());
        return "admin/manageProduct";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, HttpServletRequest request) {
        model.addAttribute("user", getSessionUser(request));
        return "admin/profile";
    }

    @PostMapping("/profile/update")
    public String updateNguoiDung(@ModelAttribute User nd, HttpServletRequest request) {
        User currentUser = getSessionUser(request);
        currentUser.setAddress(nd.getAddress());
        currentUser.setFull_name(nd.getFull_name());
        currentUser.setPhone(nd.getPhone());
        userService.updateUser(currentUser);
        return "redirect:/admin/profile";
    }

//    @GetMapping("/don-hang")
//    public String quanLyDonHangPage(Model model) {
//        Set<VaiTro> vaiTro = new HashSet<>();
//        // lấy danh sách shipper
//        vaiTro.add(roleService.findByTenVaiTro("ROLE_SHIPPER"));
//        List<NguoiDung> shippers = userService.getNguoiDungByVaiTro(vaiTro);
//        for (NguoiDung shipper : shippers) {
//            shipper.setListDonHang(donHangService.findByTrangThaiDonHangAndShipper("Đang giao", shipper));
//        }
//        model.addAttribute("allShipper", shippers);
//        return "admin/quanLyDonHang";
//    }

    @GetMapping("/account")
    public String manageAccountPage(Model model) {
        model.addAttribute("listVaiTro", roleService.findAllRole());
        return "admin/manageAccount";
    }

//    @GetMapping("/thong-ke")
//    public String thongKePage(Model model) {
//        return "admin/thongKe";
//    }

    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }



}
