package uo.sdi.business;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.ejb.EjbClientUser;
import uo.sdi.model.User;

public interface AdminService {

	public void deepDeleteUser(Long id) throws BusinessException;

	public void disableUser(Long id) throws BusinessException;

	public void enableUser(Long id) throws BusinessException;

	public List<User> findAllUsers() throws BusinessException;

	public User findUserById(Long id) throws BusinessException;

	public void reseteaDB() throws BusinessException;

	// Servicios para el cliente ejb
	public List<EjbClientUser> findAllUsersEjbClient() throws BusinessException;

	public EjbClientUser findUserByIdEjbClient(Long id)
			throws BusinessException;

}
