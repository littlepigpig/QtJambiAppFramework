package command;

import com.trolltech.qt.QSignalEmitter;

public final class Command extends QSignalEmitter
{
	private BusinessCheckResult lastBusinessCheckResult;

	private final BusinessStateCheckerGroup businessStateCheckerGroup;

	private boolean executePermitted;

	protected String commandKey;

	private final ICommandExecuter executer;

	public Signal0 stateChanged = new Signal0();

	public Command(final ICommandExecuter executer, final IBusinessStateChecker... businessStateChecker)
	{
		this.executer = executer;

		businessStateCheckerGroup = new BusinessStateCheckerGroup();
		businessStateCheckerGroup.addBusinessStateChecker(businessStateChecker);
	}

	public final BusinessCheckResult checkBusinessState() {
		return businessStateCheckerGroup.checkBusinessState();
	}

    public final boolean isExecutionPossible()
    {
    	try {
    		final BusinessCheckResult result = checkBusinessState();
    		if(result == null) {
    			throw new NullPointerException("The result of a business check must not be null.");
    		}

    		lastBusinessCheckResult = result;

    		return executePermitted && result.isPossible();
		}
    	catch (final Exception e) {
			System.err.println("Error while checking if exceution is possible." + e.getMessage());
			return false;
		}
    }

	public final void addBusinessStateChecker(final IBusinessStateChecker businessStateChecker) {
		businessStateCheckerGroup.addBusinessStateChecker(businessStateChecker);
	}

	public final void businessStateChanged()
	{
		try {
			final BusinessCheckResult result = checkBusinessState();
			if(result == null) {
				throw new NullPointerException("The result of a business check must not be null.");
			}

			lastBusinessCheckResult = result;
			executePermitted = result.isPossible();

			stateChanged.emit();
		}
		catch (final Exception e) {
			System.err.println("Error in 'businessStateChanged()' of command '"+commandKey+"'." + e.getMessage());
			executePermitted = true;
		}
	}

	public final String getLastBusinessCheckReason()
	{
		if(lastBusinessCheckResult == null) {
			isExecutionPossible();
		}
		return lastBusinessCheckResult.getResultReason();
	}

	public final void invoke()
	{
		try {
			if(isExecutionPossible()) {
				executer.execute();
			}
		} catch (final Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
