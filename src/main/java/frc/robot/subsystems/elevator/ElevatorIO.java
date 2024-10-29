package frc.robot.subsystems.elevator;

import edu.wpi.first.math.util.Units;

import java.util.function.DoubleSupplier;

public interface ElevatorIO {

    default void updateInputs(ElevatorInputs inputs) {}

    default void stop() {
        setTargetVoltage(0.0);
    }

    default void goTo(double position) {}

    default void follow(DoubleSupplier position) {}

    default void setTargetVelocity(double velocity) {}
    
    default void setTargetVoltage(double voltage) {}

    default void setTargetAngle(double angle) {}

    default void setTargetPosition(double position) {}
}
