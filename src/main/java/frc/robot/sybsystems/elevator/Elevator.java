package frc.robot.sybsystems.elevator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    // create the elevator io, inputs and threshold
    private final ElevatorIO io;
    private final ElevatorInputs inputs = new ElevatorInputs();
    private static final double POSITION_THRESHOLD = Units.inchesToMeters(0.25);

    public Elevator(ElevatorIO io){this.io = io;}
    private double targetPosition = 0.0;

    @Override
    public void periodic(){
        // updates the inputs every 20 ms
        io.updateInputs(inputs);
    }

    public double getPosition() {
        return inputs.currentPosition;
    }

    public boolean atTargetPosition() {
        return Math.abs(inputs.currentPosition - targetPosition) < POSITION_THRESHOLD;
    }

    public Command toPosition(double position){
        return runEnd(
                () -> {
                    io.setTargetPosition(position);
                    targetPosition = position;
                },
                io::stop
        );
    }
}
