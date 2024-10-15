package rootbe.rgb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public String getAllCompanies(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        return "company/index";
    }

    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable Integer id, Model model) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            return "company/view";
        }
        return "redirect:/companies";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/create";
    }

    @PostMapping("/create")
    public String createCompany(@ModelAttribute("company") Company company) {
        companyService.createCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            return "company/edit";
        }
        return "redirect:/companies";
    }

    @PostMapping("/edit/{id}")
    public String updateCompany(@PathVariable Integer id, @ModelAttribute("company") Company companyDetails) {
        companyService.updateCompany(id, companyDetails);
        return "redirect:/companies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }
}
