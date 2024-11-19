package frc.robot.subsystems.elevator;

import frc.robot.subsystems.elevator.ElevatorInputs;

public interface ElevatorIO {

    default void stop() {setTargetVoltage(0.0);}

    default void updateInputs(ElevatorInputs inputs) {
    }

    default void setTargetVelocity(double velocity) {
    }
    default void setTargetVoltage(double voltage) {
    }

}
