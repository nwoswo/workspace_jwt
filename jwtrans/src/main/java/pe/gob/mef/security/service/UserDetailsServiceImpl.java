package pe.gob.mef.security.service;



import pe.gob.mef.repository.bean.Usuario;
import pe.gob.mef.repository.dao.UserDetailDaoInterface;
import pe.gob.mef.security.model.SpringSecurityUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailDaoInterface userdao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    
    	System.out.println("username1="+username);
    	Usuario appUser = this.userdao.findByUsername(username);
    	
    	System.out.println("username2="+appUser.getUsuDUsuario());
    	System.out.println("username3="+appUser.getUsuDPermisos());
        
        
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", username));
        } else {
        	
    
        	 
            return new SpringSecurityUser(
                    appUser.getUsuCUsuario(),
                    appUser.getUsuDUsuario(),
                    appUser.getUsuDPassword(),
                    null,
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(appUser.getUsuDPermisos())
            );
        }
    }

}
