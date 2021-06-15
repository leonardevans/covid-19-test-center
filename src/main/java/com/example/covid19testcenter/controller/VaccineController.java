package com.example.covid19testcenter.controller;

import com.example.covid19testcenter.model.Vaccine;
import com.example.covid19testcenter.repo.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class VaccineController {
    @Autowired
    VaccineRepo vaccineRepo;

    @GetMapping("/vaccine")
    public String showVaccineDetails(Model model){
        //get the vaccine
        Optional<Vaccine> optionalVaccine = vaccineRepo.findByName("COVID19VACCINE");
        Vaccine vaccine = optionalVaccine.orElse(new Vaccine("COVID19VACCINE"));

        model.addAttribute("vaccine", vaccine);
        return "vaccine";
    }

    @GetMapping("/editVaccine/{vaccineId}")
    public String showEditVaccine(Model model, @PathVariable("vaccineId") long vaccineId){
        //get the vaccine
        Optional<Vaccine> optionalVaccine = vaccineRepo.findById(vaccineId);
        Vaccine vaccine = optionalVaccine.orElse(new Vaccine("COVID19VACCINE"));

        model.addAttribute("vaccine", vaccine);

        return "editVaccine";
    }

    @PostMapping("/updateVaccine")
    public String showEditVaccine(@ModelAttribute("vaccine") Vaccine vaccine){
        vaccineRepo.save(vaccine);
        return "redirect:/vaccine?vaccineUpdated";
    }
}
