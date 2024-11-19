package frc.robot.sybsystems.elevator;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;

public class P2025ElevatorIO implements ElevatorIO {
    // creates the TalonFX motor controller and sets the max + min positions of the elevator
    public static final double MAXPOSITION = Units.feetToMeters(2);
    public static final double MINPOSITION = Units.feetToMeters(0);
    private final TalonFX motor;

    // creates the requests to the motor
    private final NeutralOut stopRequest = new NeutralOut();
    private final PositionVoltage positionRequest = new PositionVoltage(0);
    private final VoltageOut voltageRequest = new VoltageOut(0);


    public P2025ElevatorIO(TalonFX motor){
        this.motor = motor;

        // Configuring the motor rotational direction and PID
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        config.Slot0.kP = 1.0;
        config.Slot0.kD = 0.0;
        config.Slot0.kI = 0.0;
        config.Slot0.GravityType = GravityTypeValue.Elevator_Static;
        motor.getConfigurator().apply(config);

    }

    @Override
    public void updateInputs(ElevatorInputs inputs){
        // updates the inputs from the Elevator Inputs class
        inputs.currentDraw = motor.getSupplyCurrent().getValueAsDouble();
        inputs.motorTemperature = motor.getDeviceTemp().getValueAsDouble();
        inputs.currentPosition = motor.getPosition().getValueAsDouble();
    }

    @Override
    public void stop(){
        motor.setControl(stopRequest);
    }

    @Override
    public void setTargetVoltage(double voltage){
        motor.setControl(voltageRequest.withOutput(voltage));
    }


    @Override
    public void setTargetPosition(double position) {
        // Sends a request to the motor to set the position
            motor.setControl(positionRequest.withPosition(MathUtil.clamp(position, MINPOSITION, MAXPOSITION)));
        }
    }

