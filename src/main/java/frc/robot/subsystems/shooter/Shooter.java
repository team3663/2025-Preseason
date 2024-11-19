package frc.robot.subsystems.shooter;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class Shooter extends SubsystemBase {
    private static final double VELOCITY_THRESHOLD = Units.rotationsPerMinuteToRadiansPerSecond(100.0);
    private static final double POSITION_THRESHOLD = Units.degreesToRadians(1);

    private final ShooterIO io;
    private final ShooterInputs inputs = new ShooterInputs();

    private double targetVelocity = 0.0;
    private double targetPosition = 0.0;

    public Shooter(ShooterIO io) {
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

    public boolean atTargetPosition() {
        return Math.abs(inputs.currentPosition - targetPosition) < POSITION_THRESHOLD;
    }

    public Command stop() {
        return runOnce(
            io::stop
        );
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

    public Command followVelocity(DoubleSupplier velocity) {
        return runEnd(
                () -> {
                    io.setTargetVelocity(velocity.getAsDouble());
                    targetVelocity = velocity.getAsDouble();

                },
                io::stop
        );
    }

    public Command togglePosition(double pos1, double pos2) {
        return runEnd(
                () -> {
                    if (atTargetPosition()) {
                        if (targetPosition == pos2) {
                            io.setTargetPosition(pos1);
                            targetPosition = pos1;
                        } else {
                            io.setTargetPosition(pos2);
                            targetPosition = pos2;
                        }
                    } else {
                        if (targetPosition == pos1) {
                            io.setTargetPosition(pos1);
                            targetPosition = pos1;
                        } else {
                            io.setTargetPosition(pos2);
                            targetPosition = pos2;
                        }
                    }
                },
                io::stop
        );
    }

    public Command resetPosition() {
        return runOnce(
                () -> io.resetPosition()
        );
    }

    public Command followPosition(DoubleSupplier position) {
        return runEnd(
                () -> {
                    io.setTargetPosition(position.getAsDouble());
                    targetPosition = position.getAsDouble();
                },
                io::stop
        );
    }
}
