// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.shooter.P2025ShooterIO;
import frc.robot.subsystems.shooter.Shooter;

public class RobotContainer {
    private final Shooter shooter = new Shooter(new P2025ShooterIO(new TalonFX(0)));

    private final CommandXboxController controller = new CommandXboxController(0);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        controller.a().onTrue(shooter.stop());
        controller.b().onTrue(shooter.withVelocity(Units.rotationsPerMinuteToRadiansPerSecond(1000)));
        controller.x().onTrue(shooter.withVelocity(Units.rotationsPerMinuteToRadiansPerSecond(2500)));
        controller.y().onTrue(shooter.togglePosition(0, Units.rotationsToRadians(50)).until(shooter::atTargetPosition));
        controller.start().onTrue(shooter.resetPosition());
        controller.rightTrigger(.05).whileTrue(shooter.followVelocity(() -> (controller.getRightTriggerAxis() * Units.rotationsPerMinuteToRadiansPerSecond(4000))));
        controller.leftTrigger(.05).whileTrue(shooter.followPosition(() -> (controller.getLeftTriggerAxis() * Units.rotationsToRadians(5))));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
