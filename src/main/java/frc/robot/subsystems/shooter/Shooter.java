package frc.robot.subsystems.shooter;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private static final double VELOCITY_THRESHOLD = Units.rotationsPerMinuteToRadiansPerSecond(100.0);

    private final ShooterIO io;
    private final ShooterInputs inputs = new ShooterInputs();

    private double targetVelocity = 0.0;

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