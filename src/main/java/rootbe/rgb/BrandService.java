package rootbe.rgb;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getBrandsByCompanyId(Integer companyId) {
        return brandRepository.findByCompanyId(companyId);
    }

    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("브랜드가 없습니다."));
    }

    public void updateBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void deleteBrand(Integer id) {
        brandRepository.deleteById(id);
    }
}