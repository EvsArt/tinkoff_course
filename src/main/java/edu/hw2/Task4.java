package edu.hw2;

public final class Task4 {

    private Task4() {
    }

    public static CallingInfo callingInfo() {
        try {
            throw new RuntimeException("");
        } catch (RuntimeException e) {
            StackTraceElement neededElement = e.getStackTrace()[1];
            // [0] - this method, [1] - calling method

            String fullClassName = neededElement.getClassName();
            String[] path = fullClassName.split("\\.");
            String className = path[path.length - 1];     // "edu.hw2.Task4" -> "Task4"

            CallingInfo info = new CallingInfo(
                className,
                neededElement.getMethodName()
            );

            return info;
        }

    }

    public record CallingInfo(String className, String methodName) {
    }

}
