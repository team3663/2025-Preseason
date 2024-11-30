package frc.robot.subsystems.shooter;

import java.util.function.DoubleSupplier;

public interface ShooterIO {
    default void updateInputs(ShooterInputs inputs) {
    }

    default void stop() {
        setTargetVoltage(0.0);
    }

    default void setTargetVelocity(double velocity) {
    }

    default void setTargetVoltage(double voltage) {
    }

    default void setTargetPosition(double position) {}

    default void resetPosition() {}
}