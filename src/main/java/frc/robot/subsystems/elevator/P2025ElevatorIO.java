package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import frc.robot.subsystems.shooter.ShooterInputs;

public class P2025ElevatorIO implements ElevatorIO{

    private final TalonFX motor;

    private final VoltageOut voltageRequest = new VoltageOut(0.0);
    private final VelocityVoltage velocityRequest = new VelocityVoltage(0.0);
    private final NeutralOut stopRequest = new NeutralOut();

//this is the P.I.D.
    public P2025ElevatorIO(TalonFX motor) {
        this.motor = motor;

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.Slot0.kP = 1.0;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;

        motor.getConfigurator().apply(config);
    }

//    takes the ElevatorInputs as doubles
   @Override
   public void updateInputs(ElevatorInputs inputs) {
       inputs.currentVelocity = motor.getVelocity().getValueAsDouble();
       inputs.currentAppliedVoltage = motor.getMotorVoltage().getValueAsDouble();
       inputs.motorTemperature = motor.getDeviceTemp().getValueAsDouble();
       inputs.currentDraw = motor.getSupplyCurrent().getValueAsDouble();
   }

    @Override
    public void stop(){motor.setControl(stopRequest);}

    @Override
    public void setTargetVelocity(double velocity){motor.setControl(velocityRequest.withVelocity(velocity));}

    @Override
    public void setTargetVoltage(double voltage){motor.setControl(voltageRequest.withOutput(voltage));}

}
