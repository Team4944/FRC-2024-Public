package frc.robot.subystems.Intake;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Util.TalonFXFactory;

public class IntakeSubsystem extends SubsystemBase {
 
  private TalonFX intakeTalon = configureIntakeTalon(TalonFXFactory.createTalon(IntakeConstants.intakeTalonID,
      IntakeConstants.intakeTalonCANBus, IntakeConstants.kIntakeConfiguration));

  private TimeOfFlight intakeSensor = new TimeOfFlight(IntakeConstants.intakeSensorID);

  public IntakeSubsystem() {

    intakeSensor.setRangingMode(IntakeConstants.intakeSensorRange, IntakeConstants.intakeSampleTime);
    intakeSensor.setRangeOfInterest(8, 8, 12, 12);
  }

  public void stop() {
    intakeTalon.setControl(IntakeConstants.intakeDutyCycle.withOutput(0));
  }

  public void setIntakeDutyCycle(double speed) {
    intakeTalon.setControl(IntakeConstants.intakeDutyCycle.withOutput(speed));
  }

  public void setIntakeTorqueControl(double amps) {
    intakeTalon.setControl(IntakeConstants.intakeTorqueControl.withOutput(amps));
  }

  public void setIntakeVoltage(double volts) {
    intakeTalon.setControl(new VoltageOut(volts));
  }

  public boolean isNotePresentTOF() {
    return intakeSensor.getRange() < IntakeConstants.isNotePresentTOF;
  }

  public boolean isNoteCenteredTOF() {
    return intakeSensor.getRange() != 0.0 && intakeSensor.getRange() < 100;
  }

  public double getRangeTOF() {
    return intakeSensor.getRange();
  }

  public boolean isIntakeRunning() {
    return Math.abs(intakeTalon.get()) > 0.01;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("range intake", getRangeTOF());
  }

  private TalonFX configureIntakeTalon(TalonFX motor) {
    return motor;
  }
}
