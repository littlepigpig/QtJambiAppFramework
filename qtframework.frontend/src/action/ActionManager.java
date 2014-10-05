package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.Command;

public class ActionManager
{
	private final static Map<String, CommandAction> actions = new HashMap<String, CommandAction>();
	private static List<IActionFactory> actionFactories = new ArrayList<IActionFactory>();

    public static void setCommandForAction(final String actionKey, final Command command) throws IllegalArgumentException
    {
		if (actionKey == null) {
			throw new NullPointerException("The parameter actionKey must not be null!");
		}

		final CommandAction action = getAction(actionKey);
        if(action != null) {
            action.setCommand(command);
        }
        else {
            throw new IllegalArgumentException("Action <"+actionKey+"> was not found.");
        }
    }

    public static CommandAction getAction(final String actionKey)
    {
    	final CommandAction actionObject = actions.get(actionKey);

        if(actionObject == null)
        {
        	for(final IActionFactory factory : actionFactories)
        	{
        		final CommandAction commandAction = factory.createAction(actionKey);
        		if(commandAction != null) {
        			actions.put(actionKey, commandAction);
        			break;
        		}
        	}
        }
        return actions.get(actionKey);
    }

    public static Command getCommandForAction(final String actionKey) throws IllegalArgumentException
    {
		if (actionKey == null) {
			throw new NullPointerException("The parameter actionKey must not be null!");
		}
        final CommandAction action = getAction(actionKey);
        if(action == null) {
            throw new IllegalArgumentException("Action '" + actionKey + "' was not found! ");
        } else {
            return action.getCommand();
        }
    }

    public static void registerActionFactory(final IActionFactory factory) {
        actionFactories.add(factory);
    }
}
