package frc.robot.subsystems.shooter;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class Shooter extends SubsystemBase {
    private static final double VELOCITY_THRESHOLD = Units.rotationsPerMinuteToRadiansPerSecond(100.0);
    private static final double ROTATIONS_THRESHOLD = 0.1;

    private final ShooterIO io;
    private final ShooterInputs inputs = new ShooterInputs();

    private double targetVelocity = 0.0;
    private double targetRotations = 0.0;

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

    public double getRotations(){
        return inputs.currentRotations;
    }

    public boolean atTargetVelocity() {
        return Math.abs(inputs.currentVelocity - targetVelocity) < VELOCITY_THRESHOLD;
    }

    public boolean atTargetRotations(){
        return Math.abs(inputs.currentRotations - targetRotations) < ROTATIONS_THRESHOLD;
    }

    /**
     * Reset the amount of rotations on the motor to 0
     */
    public Command resetRotations(){
        return runOnce(
                io::resetRotations
        );
    }

    /**
     * Stops the motor by running at 0 voltage
     */
    public Command stop(){
        return runOnce(
                io::stop
        );
    }

    /**
     * Tells the shooter to move a certain amount of rotations and does not end
     */
    public Command followRotations(DoubleSupplier rotations){
        return runEnd(
                () -> {
                    targetRotations = rotations.getAsDouble();
                    io.setTargetRotations(rotations.getAsDouble());
                },
                io::stop
        );
    }

    /** Toggles between running 50 rotations and 0 rotations based on if the number of times the
     * button is pressed is even or odd
     *
     */
    public Command toggleRotations(double timesPressed) {
        if (timesPressed % 2 == 0) {
            return runOnce(
                    () -> toRotations(Units.rotationsToRadians(50)));
        }
        else {
            return runOnce(
                    ()-> toRotations(Units.rotationsToRadians(0)));
        }
    }

    /** Tells the shooter to move a certain amount of rotations and then ends
     *
     */
    public Command toRotations(double rotations) {
        return followRotations(()-> rotations).until(this::atTargetRotations);
    }

    /**
     * Tells the shooter to move with velocity and does not end
     */
    public Command followVelocity(DoubleSupplier position) {
        return runEnd(
                () -> {
                    targetVelocity = position.getAsDouble();
                    io.setTargetVelocity(position.getAsDouble());
                },
                io::stop
        );
    }

    /** Tells the shooter to move with a certain velocity and then ends
     *
     */
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
