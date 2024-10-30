package frc.robot.sybsystems.elevator;

import edu.wpi.first.math.util.Units;

public interface ElevatorIO {
    // Updates the inputs in the Elevator Inputs class
    default void updateInputs(ElevatorInputs Inputs) {
    }

    // Stops the motors by giving them 0 voltage
    default void stop() {
        setTargetVoltage(0.0);
    }

    default void setTargetVoltage(double voltage) {
    }

    default void setTargetPosition(double position){

    }
}
