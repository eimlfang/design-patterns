package victor.training.oo.structural.facade.facade;

import lombok.RequiredArgsConstructor;
import victor.training.oo.structural.facade.Facade;
import victor.training.oo.structural.facade.domain.EmailService;
import victor.training.oo.structural.facade.entity.Customer;
import victor.training.oo.structural.facade.facade.dto.CustomerDto;
import victor.training.oo.structural.facade.repo.CustomerRepository;
import victor.training.oo.structural.facade.service.RegisterCustomerService;

@Facade
@RequiredArgsConstructor
public class CustomerFacade {
   private final CustomerRepository customerRepo;
   private final EmailService emailService;
   private final RegisterCustomerService registerCustomerService;
   private final CustomerMapper mapper;

   public CustomerDto findById(long customerId) {
      Customer customer = customerRepo.findById(customerId);
      return new CustomerDto(customer);
   }

   public void registerCustomer(CustomerDto dto) {
      Customer customer = mapper.mapToEntity(dto);

      validateCustomer(customer);


      registerCustomerService.registerCustomer(customer);

      emailService.sendRegistrationEmail(customer.getEmail());
   }

   private void validateCustomer(Customer customer) {
      // extract CustomerValidator class
      if (customer.getName().trim().length() <= 5) {
         throw new IllegalArgumentException("Name too short");
      }
      if (customerRepo.customerExistsWithEmail(customer.getEmail())) {
         throw new IllegalArgumentException("Email already registered");
      }
   }


}
