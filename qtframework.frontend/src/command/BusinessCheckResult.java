package command;

public class BusinessCheckResult
{
	private String resultReason;
	private final boolean possible;

	public static final BusinessCheckResult POSSIBLE = new BusinessCheckResult();

	public BusinessCheckResult()
	{
		possible = true;
	}

	public BusinessCheckResult(final String resultReason, final boolean possible)
	{
		this.resultReason = resultReason;
		this.possible = possible;
	}

	public boolean isPossible() {
		return possible;
	}

	public String getResultReason() {
		return resultReason;
	}


}
