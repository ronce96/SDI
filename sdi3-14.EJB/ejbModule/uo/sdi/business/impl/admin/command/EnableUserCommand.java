package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.infrastructure.Factories;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.UserDao;

public class EnableUserCommand{

	private Long id;

	public EnableUserCommand(Long id) {
		this.id = id;
	}

	public Void execute() throws BusinessException {
		UserDao uDao = Factories.persistence.getUserDao();

		User user = uDao.findById(id);
		BusinessCheck.isNotNull(user, "User does not exist");

		user.setStatus(UserStatus.ENABLED);
		uDao.update(user);

		return null;
	}

}
