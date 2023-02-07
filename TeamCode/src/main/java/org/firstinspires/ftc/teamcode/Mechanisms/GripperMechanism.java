package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.Mechanism;

public class GripperMechanism extends Mechanism {
    private  Servo gripper;

    public double position;
    public double _degrees;
    private boolean canGrip = false;
    //

    public GripperMechanism(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        gripper = this.hardwareMap.get(Servo.class, "gripper");
    }
    public double degreesToServo(double degrees){
        degrees = _degrees/300;

        return degrees;
    }
    public void run(Controller controller){
        if(controller.leftBumper){
            canGrip = !canGrip;
        }
        if(canGrip){
            position = .75;
            gripper.setPosition(position);
        } else if(canGrip = false){
            position = 1.0;
            gripper.setPosition(position);

        }

        telemetry.addData(">", "gripperPosition: " +  gripper.getPosition());
        telemetry.addData(">", "Position: " +  position);
    }




}