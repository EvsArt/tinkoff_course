package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class FibByteCodeAppender implements ByteCodeAppender {

    private final String className = "FibCalculator";
    private final String methodName = "fib";
    private final String methodDescriptor = "(I)I";
    private final int stackSize = 5;

    @Override
    public Size apply(
        MethodVisitor methodVisitor,
        Implementation.Context implementationContext,
        MethodDescription instrumentedMethod
    ) {

        Label l1 = new Label();

        methodVisitor.visitCode();

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, l1);

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.IRETURN);

        methodVisitor.visitLabel(l1);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitInsn(Opcodes.ISUB);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, methodName, methodDescriptor, false);
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitInsn(Opcodes.ISUB);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, methodName, methodDescriptor, false);
        methodVisitor.visitInsn(Opcodes.IADD);
        methodVisitor.visitInsn(Opcodes.IRETURN);
        methodVisitor.visitEnd();

        return new ByteCodeAppender.Size(stackSize, instrumentedMethod.getStackSize());

    }
}
