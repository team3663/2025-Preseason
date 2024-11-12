// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.shooter.P2025ShooterIO;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.sybsystems.elevator.Elevator;
import frc.robot.sybsystems.elevator.P2025ElevatorIO;

public class RobotContainer {
    // Create the shooter, elevator and xbox controller
    private final Shooter shooter = new Shooter(new P2025ShooterIO(new TalonFX(0)));
    private final Elevator elevator = new Elevator(new P2025ElevatorIO(new TalonFX(1)));
    private final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
    private final SlewRateLimiter limit = new SlewRateLimiter(Units.rotationsPerMinuteToRadiansPerSecond(100.0));
    public int timesYPressed = 0;
    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        
        // Button A -> Coast to Stop
        driverController.a().onTrue(shooter.stop());

        // Button B -> 1000 RPM
        driverController.b().onTrue(shooter.withVelocity(Units.rotationsPerMinuteToRadiansPerSecond(1000)));

        // Button X -> 2500 RPM
        driverController.x().onTrue(shooter.withVelocity(Units.rotationsPerMinuteToRadiansPerSecond(2500)));

        // Button Y -> Toggle between 0 and 50 rotations
        driverController.y().onTrue(
                shooter.toggleRotations(timesYPressed).andThen(()-> timesYPressed += 1));

        // Right Trigger -> 0-4000 RPM
        // When the trigger is pressed more than the threshold, it first resets the
        // slew to the current velocity of the motor and then increases or decreases on a
        //slew depending on how far the trigger is pressed
        // TODO Make this even more smooth
        driverController.rightTrigger(0.05).whileTrue(
                shooter.followVelocity(() -> limit.calculate(Units.rotationsPerMinuteToRadiansPerSecond(4000) * driverController.getRightTriggerAxis()))
                        .beforeStarting(() -> limit.reset(shooter.getVelocity())));

        // Start -> Reset Position to 0 Rotations
        driverController.start().onTrue(shooter.resetRotations());

        // Left Trigger -> 0-5 Rotations
        driverController.leftTrigger(0.5).whileTrue(shooter.followRotations(
                () -> limit.calculate(Units.rotationsToRadians(5) * driverController.getLeftTriggerAxis()))
                .beforeStarting(()-> limit.reset(shooter.getVelocity())));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
