package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.Part;
import com.example.demo.service.InhousePartService;
import com.example.demo.service.InhousePartServiceImpl;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 *
 *
 *
 *
 */
@Controller
public class AddInhousePartController{
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model theModel){
        InhousePart inhousepart=new InhousePart();
        theModel.addAttribute("inhousepart",inhousepart);
        return "InhousePartForm";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model theModel) {
        theModel.addAttribute("inhousepart", part);
        if (theBindingResult.hasErrors()) {
            return "InhousePartForm";
        } else {
            InhousePartService repo = context.getBean(InhousePartServiceImpl.class);
            InhousePart ip = repo.findAll().stream().filter(existing->existing.getName().equals(part.getName()))
                    .findFirst().orElse(null);

            if (ip != null && ip.getPartId() == part.getPartId()) {
                ip.setName(part.getName());
                ip.setPrice(part.getPrice());
                ip.setInv(part.getInv());
                ip.setMinInv(part.getMinInv());
                ip.setMaxInv(part.getMaxInv());
                ip.setPartId(part.getPartId());
                repo.save(ip);
                return "confirmationaddpart";

            }
            else if (ip != null) {
                InhousePart multiPackPart = new InhousePart();
                multiPackPart.setName(ip.getName() + " multi-pack");
                multiPackPart.setPrice(ip.getPrice() * part.getInv());
                multiPackPart.setInv(part.getInv());
                multiPackPart.setMinInv(part.getMinInv());
                multiPackPart.setMaxInv(part.getMaxInv());
                multiPackPart.setPartId(ip.getPartId());
                repo.save(multiPackPart);
                return "confirmationaddpart";
            }
            else {
                repo.save(part);
                return "confirmationaddpart";
            }
        }
    }

}
