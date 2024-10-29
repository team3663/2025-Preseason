package frc.robot.sybsystems.elevator;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;

public class P2025ElevatorIO implements ElevatorIO{
    private final TalonFX motor;

    private final NeutralOut stopRequest = new NeutralOut();
    private final PositionVoltage positionRequest = new PositionVoltage(0);

    private final double maxPosition = Units.feetToMeters(2);

    public P2025ElevatorIO(TalonFX motor){
        this.motor = motor;
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        config.Slot0.kP = 1.0;
        config.Slot0.kD = 0.0;
        config.Slot0.kI = 0.0;

        motor.getConfigurator().apply(config);

    }

    @Override
    public void updateInputs(ElevatorInputs inputs){
        inputs.currentDraw = motor.getSupplyCurrent().getValueAsDouble();
        inputs.motorTemperature = motor.getDeviceTemp().getValueAsDouble();
        inputs.currentPosition = motor.getPosition().getValueAsDouble();
    }

    @Override
    public void stop(){motor.setControl(stopRequest);}

    @Override
    public void setTargetPosition(double position) {
        if (position <= maxPosition) {
            motor.setPosition((Angle) positionRequest.withPosition(position));
        }
    }
}
