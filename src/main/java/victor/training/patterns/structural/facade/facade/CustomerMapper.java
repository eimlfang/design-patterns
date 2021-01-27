package victor.training.patterns.structural.facade.facade;

import lombok.RequiredArgsConstructor;
import victor.training.patterns.structural.facade.entity.Customer;
import victor.training.patterns.structural.facade.facade.dto.CustomerDto;
import victor.training.patterns.structural.facade.repo.SiteRepository;

import java.text.SimpleDateFormat;
@RequiredArgsConstructor
public class CustomerMapper {
   private final SiteRepository siteRepo;

   public Customer toEntity(CustomerDto dto) {
      Customer customer = new Customer();
      customer.setEmail(dto.email);
      customer.setName(dto.name);
      customer.setSite(siteRepo.getReference(dto.countryId));
      return customer;
   }
}