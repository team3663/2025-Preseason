package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

public class P2025ElevatorIO {
    private final TalonFX motor;

    private final VoltageOut voltageRequest = new VoltageOut(0.0);
    private final VelocityVoltage velocityRequest = new VelocityVoltage(0.0);
    private final NeutralOut stopRequest = new NeutralOut();
    private final AngleOut angleRequest = new AngleOut(0.0);
    private final PositionOut positionRequest = new PositionOut(0.0);

    public P2025ShooterIO(TalonFX motor) {
        this.motor = motor;

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.Slot0.kP = 1.0;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;

        motor.getConfigurator().apply(config);
    }

    @Override
    public void updateInputs(ShooterInputs inputs) {
        inputs.currentVelocity = motor.getVelocity().getValueAsDouble();
        inputs.currentAppliedVoltage = motor.getMotorVoltage().getValueAsDouble();
        inputs.motorTemperature = motor.getDeviceTemp().getValueAsDouble();
        inputs.currentDraw = motor.getSupplyCurrent().getValueAsDouble();
        inputs.currentAngle = motor.getAngle().getValueAsDouble();
        // inputs.currentPosition = motor.getAngle().getValueAsDouble()
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
        motor.setControl(angleRequest.withAngle(angle));
    }
    
    @Override
    public void setTargetPosition(double position) {
        motor.setControl(positionRequest.withPosition(position));
    }
}