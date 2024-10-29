package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.util.Units;

public class P2025ElevatorIO implements ElevatorIO {
    private final TalonFX motor;

    private final VoltageOut voltageRequest = new VoltageOut(0.0);
    private final VelocityVoltage velocityRequest = new VelocityVoltage(0.0);
    private final NeutralOut stopRequest = new NeutralOut();

    private final double elevatorMaxHeight = Units.feetToMeters(2);

    public P2025ElevatorIO(TalonFX motor) {
        this.motor = motor;

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.Slot0.kP = 1.0;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;

        motor.getConfigurator().apply(config);
    }

    @Override
    public void updateInputs(ElevatorInputs inputs) {
        inputs.currentVelocity = motor.getVelocity().getValueAsDouble();
        inputs.currentAppliedVoltage = motor.getMotorVoltage().getValueAsDouble();
        inputs.motorTemperature = motor.getDeviceTemp().getValueAsDouble();
        inputs.currentDraw = motor.getSupplyCurrent().getValueAsDouble();
        inputs.currentAngle = 0; // TODO make the UpdateInputs update the currentAngle
        inputs.currentPosition = 0; // TODO make the UpdateInputs update the currentPosition
    }

    @Override
    public void stop() {
        motor.setControl(stopRequest);
    }

    @Override
    public void setTargetVelocity(double velocity) {
        motor.setControl(velocityRequest.withVelocity(velocity));
    }
    
    @Override
    public void setTargetVoltage(double voltage) {
        motor.setControl(voltageRequest.withOutput(voltage));
    }

    @Override
    public void setTargetAngle(double angle) {
//        motor.setControl() TODO set the target angle
    }

    @Override
    public void setTargetPosition(double position) {
        // Make sure you can't enter anything greater than it's max position
        position = Math.min(position, elevatorMaxHeight);
//        motor.setControl(); TODO set the target position
    }
}