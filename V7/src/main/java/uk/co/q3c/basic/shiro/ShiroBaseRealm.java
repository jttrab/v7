package uk.co.q3c.basic.shiro;

import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.common.collect.ImmutableSet;

public class ShiroBaseRealm extends AuthorizingRealm {

	HashedCredentialsMatcher matcher;

	public ShiroBaseRealm() {
		matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
	}

	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		return matcher;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return true; // super.supports(token);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();

		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		String password = "password";
		return new SimpleAuthenticationInfo(username, password, this.getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String username = (String) principals.fromRealm(getName()).iterator().next();
		Set roleNames = ImmutableSet.of();
		if (username != null) {
			roleNames = ImmutableSet.of("foo", "goo");
		}
		return new SimpleAuthorizationInfo(roleNames);
	}

}
