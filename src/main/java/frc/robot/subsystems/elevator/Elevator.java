package frc.robot.subsystems.elevator;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorIO;
import frc.robot.subsystems.elevator.ElevatorInputs;

public class Elevator extends SubsystemBase {
    private static final double VELOCITY_THRESHOLD = Units.rotationsPerMinuteToRadiansPerSecond(100.0);
    private static final double ANGLE_THRESHOLD = Units.degreesToRadians(1);
    private static final double POSITION_THRESHOLD = Units.inchesToMeters(0.25);
    private static final double VOLTAGE_THRESHOLD = .5; // From 0 to 12

    private final ElevatorIO io;
    private final ElevatorInputs inputs;

    private double targetVelocity;
    private double targetVoltage;
    private double targetAngle;
    private double targetPosition;
    
    public Elevator(ElevatorIO io) {
        this.io = io;

        inputs = new ElevatorInputs();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        // Logger.processInputs("Elevator", inputs); // TODO make work
    }

    public double getVelocity() {
        return inputs.currentVelocity;
    }

    public double getPosition() {
        return inputs.currentPosition;
    }
    
    public double getAngle() {
        return inputs.currentAngle;
    }

    public boolean atTargetVelocity() {
        return Math.abs(inputs.currentVelocity - targetVelocity) < VELOCITY_THRESHOLD;
    }

    public boolean atTargetVoltage() {
        return Math.abs(inputs.currentAppliedVoltage - targetVoltage) < VOLTAGE_THRESHOLD;
    }

    public boolean atTargetPosition() {
        return Math.abs(inputs.currentPosition - targetPosition) < POSITION_THRESHOLD;
    }

    public boolean atTargetAngle() {
        return Math.abs(inputs.currentAngle - targetAngle) < ANGLE_THRESHOLD;
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

    public Command withVoltage(double voltage) {
        return runEnd(
                () -> {
                    io.setTargetVoltage(voltage);
                    targetVoltage = voltage;
                },
                io::stop
        );
    }

    public Command toPosition(double position) {
        return runEnd(
                () -> {
                    io.setTargetPosition(position);
                    targetPosition = position;
                },
                io::stop
        );
    }

    public Command toAngle(double angle) {
        return runEnd(
                () -> {
                    io.setTargetAngle(angle);
                    targetAngle = angle;
                },
                io::stop
        );
    }
}