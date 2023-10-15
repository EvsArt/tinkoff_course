package edu.project1.response;

import edu.project1.constraints.StringConstraints;
import edu.project1.utils.IOUtils;

public enum AttemptResult {

    WINNING(() -> IOUtils.consoleLineOutput(StringConstraints.winMsg)),
    LOSS(() -> IOUtils.consoleLineOutput(StringConstraints.lossMsg)),
    SUCCESSFULGUESS(() -> IOUtils.consoleLineOutput(StringConstraints.successGuessMsg)),
    FAILEDGUESS(() -> IOUtils.consoleLineOutput(StringConstraints.failedGuessMsg)),
    WRONGINPUT(() -> IOUtils.consoleLineOutput(StringConstraints.wrongInputMsg)),
    USELESSINPUT(() -> IOUtils.consoleLineOutput(StringConstraints.uselessInput)),
    EXIT(() -> IOUtils.consoleLineOutput(StringConstraints.goodbye));
    private final AttemptResultActivity attemptResultActivity;

    AttemptResult(AttemptResultActivity attemptResultActivity) {
        this.attemptResultActivity = attemptResultActivity;
    }

    public void doResultActivity() {
        attemptResultActivity.doSmth();
    }

}
