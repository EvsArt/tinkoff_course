package edu.hw11.task2;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

@Slf4j
public class Task2 {
    ArithmeticUtils utils = new ArithmeticUtils();

    public int getSum(int i1, int i2) {
        return utils.sum(i1, i2);
    }

    public void changeSumToMul() {

        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum").and(returns(int.class)).and(takesArguments(int.class, int.class)))
            .intercept(MethodDelegation.to(NewMethodHandler.class))
            .make()
            .load(
                ArithmeticUtils.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );

    }

}
