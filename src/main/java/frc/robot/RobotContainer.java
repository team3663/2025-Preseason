// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
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
    private final Shooter shooter = new Shooter(new P2025ShooterIO(new TalonFX(1)));
    private final Elevator elevator = new Elevator(new P2025ElevatorIO(new TalonFX(2)));
    private final CommandXboxController driver_controller = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Button A will make the shooter run with a velocity until it reaches target velocity
        driver_controller.a().onTrue(Commands.deadline(
                Commands.waitUntil(shooter::atTargetVelocity),
                shooter.withVelocity(Units.rotationsPerMinuteToRadiansPerSecond(1000.0))));

        Commands.deadline(Commands.waitSeconds(1.0));

        // Button B will make the elevator go to a position until it reaches the target position
        driver_controller.b().onTrue(Commands.deadline(
                Commands.waitUntil(elevator::atTargetPosition),
                elevator.toPosition(Units.inchesToMeters(0.25))));

        Commands.deadline(Commands.waitSeconds(1.0));



    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
