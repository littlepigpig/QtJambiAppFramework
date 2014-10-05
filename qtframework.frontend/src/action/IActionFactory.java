package action;

public interface IActionFactory {

    public CommandAction createAction(String actionCommandKey);

}