package com.dong.controllers;

import com.dong.pojo.Customer;
import com.dong.pojo.DetailReceipt;
import com.dong.pojo.Receipt;
import com.dong.pojo.Service;
import com.dong.pojo.UseService;
import com.dong.repository.impl.ReceiptRepositoryImpl;
import com.dong.service.AccountsService;
import com.dong.service.CustomerService;
import com.dong.service.DetailReceiptService;
import com.dong.service.ReceiptService;
import com.dong.service.RelativeParkCardService;
import com.dong.service.RoomService;
import com.dong.service.ServiceService;
import com.dong.service.UseServiceService;
import com.dong.service.impl.ReceiptServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.Map;
import org.springframework.context.annotation.PropertySource;

@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
public class ReceiptController {
//    @Autowired
//    private CustomerService cusService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private DetailReceiptService detailReceiptService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RelativeParkCardService relativeParkCardService;

    @Autowired
    private UseServiceService useServiceService;

    @GetMapping("/receipt")
    public String getReceipts(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("receipt", new Receipt());
        model.addAttribute("receipts", this.receiptService.getReceipt(params));
        return "receipt";
    }

    @GetMapping("/addReceipt")
    public String addReceipts(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("receipt", new Receipt());
        model.addAttribute("parkCards", this.relativeParkCardService.getRelativeParkCard(params));
//        model.addAttribute("useServices", this.receiptService.getReceipt(params));
        model.addAttribute("useServices", this.useServiceService.getUseServices(params));
//        model.addAttribute("services", this.serviceService.getServices());
        return "addReceipt";
    }

//? @Valid: This annotation is used validation. It instructs Spring MVC to 
    //validate the Receipt object (c) using any validation annotations present on its properties.    
//? BindingResult rs: This parameter is of type BindingResult. It's used to capture any validation errors 
    //that might occur during the validation process with @Valid.
    @PostMapping("/addReceipt")
    public String addOrUpdateReceipts(Model model, @RequestParam Map<String, String> params) {
        if (this.detailReceiptService.addOrUpdateDetailReceipt(params) == true 
                && this.relativeParkCardService.updateRelativeParkCard(params)
                && this.useServiceService.UpdateUseService(params) ) {
            return "redirect:/receipt";
        }
        return "addReceipt";
    }

    @GetMapping("/receiptDetail")
    public String getReceiptDetail(@RequestParam Map<String, String> params,
            Model model) {
        model.addAttribute("receipts", this.receiptService.getReceipt(params));
        model.addAttribute("parkCards", this.relativeParkCardService.getRelativeParkCard(params));
        

        return "receiptDetail";
    }

    @GetMapping("/receipt/{id}")
    public String getReceiptById(Model model, @PathVariable("id") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("receipts", this.receiptService.getReceiptById(id));

        return "receiptDetail";
    }
}
