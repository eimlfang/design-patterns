package victor.training.oo.structural.adapter.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import victor.training.oo.structural.adapter.infra.LdapUser;
import victor.training.oo.structural.adapter.infra.LdapUserWebserviceClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LdapUserServiceAdapter {
   //infern: tu cel ce intri, abandoneaza orice speranta
   private final LdapUserWebserviceClient wsClient;

   public List<User> searchByUsername(String username) {
      return wsClient.search(username.toUpperCase(), null, null).stream()
          .map(this::convert)
          .collect(Collectors.toList());
   }

   private User convert(LdapUser ldapUser) {
      String fullName = ldapUser.getfName() + " " + ldapUser.getlName().toUpperCase();
      return new User(ldapUser.getuId(), fullName, ldapUser.getWorkEmail());
   }
}
