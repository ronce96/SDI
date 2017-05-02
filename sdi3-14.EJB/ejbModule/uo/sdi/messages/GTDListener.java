package uo.sdi.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;

import uo.sdi.business.TaskService;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import uo.sdi.infrastructure.Factories;
import uo.sdi.messages.util.AbstractMessageListener;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/GTDQueue") })
public class GTDListener extends AbstractMessageListener {

	private TaskService taskService = Factories.services.getTaskService();
	private UserService userService = Factories.services.getUserService();
	private User user;

	@Override
	protected void createResponse(MapMessage message, ObjectMessage response)
			throws JMSException, BusinessException {
		String cmd = message.getString("command");

		authenticateUser(message, response);
		if (cmd.equals("login")) {
			response.setObject(user != null);
		} else if (cmd.equals("getTareas")) {
			getTasks(message, response);
		} else if (cmd.equals("finishTask")) {
			finishTask(message, response);
		} else if (cmd.equals("newTask")) {
			createTask(message, response);
		} else
			throw new BusinessException("Comando incorrecto");
		
		if(user == null)
			throw new BusinessException("Login o contraseña incorrectos");

	}

	private void finishTask(MapMessage message, ObjectMessage response)
			throws JMSException, BusinessException {
		if (user != null) {
			Long taskId = message.getLong("taskId");
			for (Task t : user.getTasks())
				if (t.getId().equals(taskId)) {
					taskService.markTaskAsFinished(taskId);
					response.setObject("La tarea " + taskId
							+ " ha sido finalizada");
					return;
				}
			response.setObject("El usuario no tiene acceso a la tarea");
		} else
			response.setObject("Debes iniciar sesion");
	}

	private void getTasks(MapMessage message, ObjectMessage response)
			throws BusinessException, JMSException {
		if (user != null) {
			List<String> tareas = new ArrayList<>();
			for (Task t : taskService.findTodayTasksByUserId(user.getId()))
				tareas.add(t.toString());
			response.setObject((Serializable) tareas);
		}
	}

	private void createTask(MapMessage message, ObjectMessage response)
			throws JMSException, BusinessException {
		if (user != null) {
			Task t = new Task();
			t.setTitle(message.getString("title"));
			t.setPlanned(new Date(message.getLong("planned")));
			t.setUser(user);
			taskService.createTask(t);
			response.setObject("La tarea se ha creado correctamente");
		} else
			response.setObject("Debes iniciar sesion");
	}

	private void authenticateUser(MapMessage message, ObjectMessage response)
			throws JMSException, BusinessException {
		String login = message.getString("login");
		String password = message.getString("password");

		user = userService.findLoggableUser(login, password);
	}

}