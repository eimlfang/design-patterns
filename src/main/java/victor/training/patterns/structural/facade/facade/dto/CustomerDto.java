package victor.training.patterns.structural.facade.facade.dto;

import victor.training.patterns.structural.facade.entity.Customer;

import java.text.SimpleDateFormat;

public class CustomerDto {
    public Long id;
	public String name;
	public String email;
    public Long countryId;
    public String creationDateStr;

    public CustomerDto(Customer customer) {
       this.name = customer.getName();
       this.email = customer.getEmail();
       this.creationDateStr = new SimpleDateFormat("yyyy-MM-dd").format(customer.getCreationDate());
       this.id = customer.getId();
    }

    public CustomerDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
