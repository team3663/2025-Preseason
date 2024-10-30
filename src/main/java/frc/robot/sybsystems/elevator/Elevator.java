package frc.robot.sybsystems.elevator;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class Elevator extends SubsystemBase {
    private static final double POSITION_THRESHOLD = Units.inchesToMeters(0.25);
    // create the elevator io, inputs and threshold
    private final ElevatorIO io;
    private final ElevatorInputs inputs = new ElevatorInputs();

    private double targetPosition = 0.0;

    public Elevator(ElevatorIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        // updates the inputs every 20 ms
        io.updateInputs(inputs);
    }

    public double getPosition() {
        return inputs.currentPosition;
    }

    public boolean atTargetPosition() {
        return Math.abs(inputs.currentPosition - targetPosition) < POSITION_THRESHOLD;
    }

    /**
     * Tells the elevator to move to positions and does not end
     */
    public Command followPosition(DoubleSupplier position) {
        return run(
                () -> {
                    io.setTargetPosition(position.getAsDouble());
                    targetPosition = position.getAsDouble();
                }
        );
    }


    /**
     * Tells the elevator to move positions until it reaches the target position, then it ends
     */
    public Command toPosition(double position) {
        return followPosition(() -> targetPosition).until(this::atTargetPosition);
    }
}
