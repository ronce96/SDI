package uo.sdi.business;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;

public interface UserService {

	public User registerUser(User user) throws BusinessException;
	public void updateUserDetails(User user) throws BusinessException;
	public User findLoggableUser(String login, String password) throws BusinessException;
	public User findUserByLogin(String login) throws BusinessException;

}
