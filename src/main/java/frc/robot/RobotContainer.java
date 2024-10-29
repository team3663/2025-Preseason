// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.shooter.P2025ShooterIO;
import frc.robot.subsystems.shooter.Shooter;

public class RobotContainer {
    private final Shooter shooter = new Shooter(new P2025ShooterIO(new TalonFX(1)));
    private final Elevator elevator = new Elevator(new P2025ElevatorIO(new TalonFX(1)));

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        Commands.deadline(
                Commands.waitUntil(shooter::atTargetVelocity),
                shooter.withVelocity(Units.rotationsPerMinuteToRadiansPerSecond(1000.0)));

    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}