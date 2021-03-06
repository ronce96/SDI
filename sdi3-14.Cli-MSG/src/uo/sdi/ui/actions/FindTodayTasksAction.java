package uo.sdi.ui.actions;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;

import uo.sdi.ui.actions.util.AbstractMessageProducer;
import alb.util.console.Console;

public class FindTodayTasksAction extends AbstractMessageProducer {

	@SuppressWarnings("unchecked")
	@Override
	protected void processResponse(ObjectMessage message) throws JMSException {
		if (message.getObject() instanceof List) {
			List<String> tasks = (List<String>) message.getObject();
			for (String task : tasks) {
				System.out.print("\n" + task);
			}
		}else
			Console.println("\nInicia sesion primero");
	}

	@Override
	protected void createMessage(MapMessage msg) throws JMSException {
		msg.setString("command", "getTareas");
	}

}
