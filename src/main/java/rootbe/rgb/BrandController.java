package rootbe.rgb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/company/{companyId}")
    public String getBrandsByCompany(@PathVariable Integer companyId, Model model) {
        List<Brand> brands = brandService.getBrandsByCompanyId(companyId);
        model.addAttribute("brands", brands);
        model.addAttribute("companyId", companyId);
        return "brand/index";
    }

    @PostMapping("/create")
    public String createBrand(@RequestParam Integer companyId,
                              @RequestParam String brandCode,
                              @RequestParam String brandName,
                              @RequestParam(required = false) String productName,
                              @RequestParam(required = false) String productCode) {
        Brand newBrand = new Brand();
        newBrand.setCompanyId(companyId);
        newBrand.setBrandCode(brandCode);
        newBrand.setBrandName(brandName);
        newBrand.setProductName(productName);
        newBrand.setProductCode(productCode);
        brandService.updateBrand(newBrand); // 저장
        return "redirect:/brands/company/" + companyId;
    }

    @PostMapping("/update")
    public String updateBrand(@RequestParam("companyId") Integer companyId,
                              @RequestParam Map<String, String> brandData) {
        brandData.forEach((key, value) -> {
            if (key.startsWith("name_")) {
                Integer id = Integer.parseInt(key.split("_")[1]);
                Brand brand = brandService.getBrandById(id); // 수정된 부분
                brand.setBrandName(value);
                brandService.updateBrand(brand);
            } else if (key.startsWith("code_")) {
                Integer id = Integer.parseInt(key.split("_")[1]);
                Brand brand = brandService.getBrandById(id); // 수정된 부분
                brand.setBrandCode(value);
                brandService.updateBrand(brand);
            }
            // productName, productCode 업데이트
            else if (key.startsWith("productName_")) {
                Integer id = Integer.parseInt(key.split("_")[1]);
                Brand brand = brandService.getBrandById(id);
                brand.setProductName(value);
                brandService.updateBrand(brand);
            } else if (key.startsWith("productCode_")) {
                Integer id = Integer.parseInt(key.split("_")[1]);
                Brand brand = brandService.getBrandById(id);
                brand.setProductCode(value);
                brandService.updateBrand(brand);
            }
        });
        return "redirect:/brands/company/" + companyId;
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Integer id, @RequestParam Integer companyId) {
        brandService.deleteBrand(id);
        return "redirect:/brands/company/" + companyId;
    }
}