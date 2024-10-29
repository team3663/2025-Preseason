package frc.robot.subsystems.Elevator;

public class Elevator extends SubsystemBase {
    private static final double VELOCITY_THRESHOLD = Units.rotationsPerMinuteToRadiansPerSecond(100.0);
    private static final double ROTATION_THRESHOLD = Units.degreesToRadians(1);

    private final ElevatorIO io;
    private final ElevatorInputs inputs;

    private double targetVelocity;
    private double targetAngle;
    private double targetPosition;
    
    public Elevator(ElevatorIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        // Logger.processInputs("Elevator", inputs);
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
}