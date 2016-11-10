package com.techweb.onlinetest.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.onlinetest.model.OnlinetestUser;
import com.onlinetest.model.User;
import com.techweb.onlinetest.dao.UserDao;

@Service("userDetailServiceImpl")
public class UserDetalServiceImpl implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);

		if (user == null) {
			logger.info(String.format("No user found with user name : %s", username));
			throw new UsernameNotFoundException(String.format("No user found with user name : %s",
					username));
		}

		return new OnlinetestUser(user.getId(), user.getName(), user.getPassword(), user.getEmail(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities()));

	}
}
