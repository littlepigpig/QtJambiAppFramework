package command;

import java.util.ArrayList;
import java.util.List;

/**
 * @version $LastChangedRevision: $
 */
public class BusinessStateCheckerGroup implements IBusinessStateChecker
{
    /** Use a list to store, because the business checks must be performed in the order the checkers are added. */
    private final List<IBusinessStateChecker> businessStateCheckerList = new ArrayList<IBusinessStateChecker>();


    public BusinessCheckResult checkBusinessState()
    {
        for(final IBusinessStateChecker checker : businessStateCheckerList) {
            final BusinessCheckResult checkerResult = checker.checkBusinessState();
            if(!checkerResult.isPossible()) {
                return checkerResult;
            }
        }

        return BusinessCheckResult.POSSIBLE;
    }

    /**
     * @param businessStateChecker
     */
    public void addBusinessStateChecker(final IBusinessStateChecker businessStateChecker)
    {
        businessStateCheckerList.add(businessStateChecker);
    }

    /**
     * @param businessStateChecker
     */
    public void removeBusinessStateChecker(final IBusinessStateChecker businessStateChecker)
    {
        businessStateCheckerList.remove(businessStateChecker);
    }

	public void addBusinessStateChecker(final IBusinessStateChecker[] businessStateChecker)
	{
		for(final IBusinessStateChecker businessChecker : businessStateChecker) {
			addBusinessStateChecker(businessChecker);
		}
	}
}
