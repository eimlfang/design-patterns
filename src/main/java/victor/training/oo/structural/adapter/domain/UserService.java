package victor.training.oo.structural.adapter.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.oo.structural.adapter.infra.LdapUser;
import victor.training.oo.structural.adapter.infra.LdapUserWebserviceClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
	@Autowired
	private LdapUserWebserviceClient wsClient;

	public void importUserFromLdap(String username) {
		List<User> list = searchByUsername(username);
		if (list.size() != 1) {
			throw new IllegalArgumentException("There is no single user matching username " + username);
		}
		User user = list.get(0);

		if (user.getWorkEmail() != null) {
			log.debug("Send welcome email to " + user.getWorkEmail());
		}
		log.debug("Insert user in my database");
	}

	//200 linii mai jos
	public List<User> searchUserInLdap(String username) {
		return searchByUsername(username);
	}

	// codu meu. My preciousssss..... ZEN PACE ARMONIE
	// -- o linie ----------------------------------------------------------------
	// PURGATORIUL.
	// tu cel ce intra, abandoneaza orice speranta

	private User convertUser(LdapUser ldapUser) {
		String fullName = getFullName(ldapUser);
		return new User(ldapUser.getuId(), fullName, ldapUser.getWorkEmail());
	}

	private String getFullName(LdapUser ldapUser) {
		return ldapUser.getfName() + " " + ldapUser.getlName().toUpperCase();
	}

	private List<User> searchByUsername(String username) {
		return wsClient.search(username.toUpperCase(), null, null).stream()
			.map(this::convertUser)
			.collect(Collectors.toList());
	}

}
