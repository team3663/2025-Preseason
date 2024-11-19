package frc.robot.subsystems.elevator;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorIO;
import frc.robot.subsystems.elevator.ElevatorInputs;

public class Elevator extends SubsystemBase {

    private static final double POSITION_THRESHOLD = Units.inchesToMeters(0.25);
    private static final double VELOCITY_THRESHOLD = Units.rotationsPerMinuteToRadiansPerSecond(100.0);

    private final ElevatorIO io;
    private final ElevatorInputs inputs = new ElevatorInputs();

    private double targetVelocity = 0.0;

    public Elevator(ElevatorIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        // Logger.processInputs("Shooter", inputs);
    }

    public double getVelocity() {
        return inputs.currentVelocity;
    }

    public boolean atTargetVelocity() {
        return Math.abs(inputs.currentVelocity - targetVelocity) < VELOCITY_THRESHOLD;
    }

    public Command withVelocity(double velocity) {
        return runEnd(
                () -> {
                    io.setTargetVelocity(velocity);
                    targetVelocity = velocity;
                },
                io::stop
        );
    }





}
