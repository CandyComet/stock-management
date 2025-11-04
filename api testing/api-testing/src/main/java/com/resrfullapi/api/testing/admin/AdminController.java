package com.resrfullapi.api.testing.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepo adminRepo;

    // Get all admins
    @GetMapping("/all")
    public List<AdminModel> getAllAdmins() {
        return adminRepo.findAll();
    }

    // Get admin by id
    @GetMapping("/{id}")
    public AdminModel getAdminById(@PathVariable int id) {
        return adminRepo.findById(id).orElse(null);
    }

    // Add admin
    @PostMapping("/add")
    public AdminModel addAdmin(@RequestBody AdminModel admin) {
        return adminRepo.save(admin);
    }

    // Update admin
    @PutMapping("/update/{id}")
    public AdminModel updateAdmin(@PathVariable int id, @RequestBody AdminModel admin) {
        AdminModel existing = adminRepo.findById(id).orElse(null);
        if(existing != null){
            existing.setName(admin.getName());
            existing.setEmail(admin.getEmail());
            existing.setPassword(admin.getPassword());
            existing.setPhone(admin.getPhone());
            return adminRepo.save(existing);
        }
        return null;
    }

    // Delete admin
    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable int id) {
        adminRepo.deleteById(id);
        return "Admin deleted with id: " + id;
    }
}
