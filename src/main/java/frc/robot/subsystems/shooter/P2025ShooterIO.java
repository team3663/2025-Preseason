package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.util.Units;

import java.util.function.DoubleSupplier;

public class P2025ShooterIO implements ShooterIO {
    private final TalonFX motor;

    private final VoltageOut voltageRequest = new VoltageOut(0.0);
    private final VelocityVoltage velocityRequest = new VelocityVoltage(0.0).withSlot(0);
    private final NeutralOut stopRequest = new NeutralOut();
    private final MotionMagicVoltage positionRequest = new MotionMagicVoltage(0.0).withSlot(1);

    public P2025ShooterIO(TalonFX motor) {
        this.motor = motor;

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        // Slot 0 is for velocity
        config.Slot0.kV = 0.115;
        config.Slot0.kP = 0.05;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;

        // Slot 1 is for position
        config.Slot1.kV = 0.115;
        config.Slot1.kA = 0.01;
        config.Slot1.kP = 0.5;
        config.Slot1.kI = 0.0;
        config.Slot1.kD = 0.0;

        // MotionMagic for position
        config.MotionMagic.MotionMagicCruiseVelocity = 5500.0/60.0;
        config.MotionMagic.MotionMagicAcceleration = 2500.0/60.0;

        motor.getConfigurator().apply(config);
    }

    @Override
    public void updateInputs(ShooterInputs inputs) {
        inputs.currentVelocity = Units.rotationsToRadians(motor.getVelocity().getValueAsDouble());
        inputs.currentAppliedVoltage = motor.getMotorVoltage().getValueAsDouble();
        inputs.currentPosition = Units.rotationsToRadians(motor.getPosition().getValueAsDouble());
        inputs.motorTemperature = motor.getDeviceTemp().getValueAsDouble();
        inputs.currentDraw = motor.getSupplyCurrent().getValueAsDouble();
    }

    @Override
    public void resetPosition() {
        motor.setPosition(0);
    }

    @Override
    public void stop() {
        motor.setControl(stopRequest);
    }

    @Override
    public void setTargetVelocity(double velocity) {
        motor.setControl(velocityRequest.withVelocity(Units.radiansToRotations(velocity)));
    }

    @Override
    public void setTargetVoltage(double voltage) {
        motor.setControl(voltageRequest.withOutput(voltage));
    }

    @Override
    public void setTargetPosition(double position) {
        motor.setControl(positionRequest.withPosition(Units.radiansToRotations(position)));
    }
}