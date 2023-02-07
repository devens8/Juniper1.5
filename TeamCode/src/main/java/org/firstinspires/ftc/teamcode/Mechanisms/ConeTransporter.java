package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;


public class ConeTransporter extends Mechanism {
    /*
    This is the general explanation for this class:
    liftPos0 = position to pick up the cone
    liftPos1 = lowest junction
    liftPos2 = medium junction
    liftPos3 = high junction

    gripperClose = gripper closed i.e. not grasping the cone - NOT ACTUALLY USED
    gripperOpen = gripper expanded i.e. grasping the cone
    */


    //    public Map<LinearSlidesLevels, Double> linearSlidesLevels;
    public double V15 = 0;

    // Tele-Op
    public double LINEAR_SLIDES_LOW = 347.5 - V15;// 13.5 inches converted to mm(low junction)
    public double LINEAR_SLIDES_MEDIUM = 607.5 - V15;// 23.5 inches converted to mm(medium junction)
    public double LINEAR_SLIDES_HIGH = 837.5 - V15;// 33.5 inches converted to mm(high junction) 2349
    public double LINEAR_SLIDES_NORM = 100 - V15;
    public double LINEAR_SLIDES_IN_CONE = 0 - V15;
    public double LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public double ticks;
    //Autonomous
    private final double inFactor_MM = 50;
    public double AUTO_LINEAR_SLIDES_12 = 165.1;// 6.5 inches converted to mm(2 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_13 = 196.85;// 7.75 inches converted to mm(3 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_14 = 228.6;// 9 inches converted to mm(4 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_15 = 260.35;// 10.25 inches converted to mm(5 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_12_IN_CONE = AUTO_LINEAR_SLIDES_12 - inFactor_MM;
    public double AUTO_LINEAR_SLIDES_13_IN_CONE = AUTO_LINEAR_SLIDES_13 - inFactor_MM;
    public double AUTO_LINEAR_SLIDES_14_IN_CONE = AUTO_LINEAR_SLIDES_14 - inFactor_MM;
    public double AUTO_LINEAR_SLIDES_15_IN_CONE = AUTO_LINEAR_SLIDES_15 - inFactor_MM;
    public double ticksPerRotation = 384.5;// 435 RPM motor 5202 GoBilda TPR
    public int ticksAsInt;

    //GRIPPER______________________________________________________________________________________
    public Servo gripper;
    public double gripperPosition;

    //LINEAR SLIDES________________________________________________________________________________
    public DcMotor linearSlides;
    public int riseLevel = 0;
    public int posLevel = 0;
    public float diameterOfSpool = 34f;
    public float linearSlidesSpeed = 0.75f;
    public int level;

    //LIMIT SWITCH_________________________________________________________________________________
//    public DigitalChannel limitSwitch;
//    public TouchSensor touchSensor;

    public ConeTransporter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        linearSlides = this.hardwareMap.get(DcMotor.class, "linearSlides");
        linearSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gripper = this.hardwareMap.get(Servo.class, "gripper");
//        limitSwitch = this.hardwareMap.get(DigitalChannel.class, "limit switch");
//        touchSensor = this.hardwareMap.get(TouchSensor.class, "touchSensor");
        linearSlides.setDirection(DcMotor.Direction.REVERSE);

    }

    public int equate(double height) {
        ticks = ticksPerRotation * (height / (diameterOfSpool * Math.PI));
        ticksAsInt = (int) ticks;
        return ticksAsInt;
    }


    public void grip() {
        gripper.setPosition(gripperPosition);
    }

    public void lift() {
        rise(riseLevel);
    }

    private void rise(int riseLevel) {
        if (riseLevel == 0) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 1) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_LOW;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 2) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_MEDIUM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 3) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_HIGH;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == -1) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_IN_CONE;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 12) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_12;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 13) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_13;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 14) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_14;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 15) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_15;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        }

    }

    public void moveDown() {
        LINEAR_SLIDES_CURRENT = Math.max(LINEAR_SLIDES_CURRENT - 5, 0);
        linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
        linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlides.setPower(0.25f);
    }

    public void moveUp() {
        LINEAR_SLIDES_CURRENT = Math.min(LINEAR_SLIDES_CURRENT + 5, LINEAR_SLIDES_HIGH);
        linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
        linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlides.setPower(0.25f);
    }

    public void setRiseLevel(int level) {
        riseLevel = level;
    }

    public void setGripperPosition(double position) {
        gripperPosition = position;
    }

    public void setPosLevel(int level){ posLevel = level; }
    public void down(){ inCone(posLevel); }
    public void inCone(int pos) {
        if (pos == 12) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_12_IN_CONE;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (pos == 13) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_13_IN_CONE;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (pos == 14) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_14_IN_CONE;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (pos == 15) {
            LINEAR_SLIDES_CURRENT = AUTO_LINEAR_SLIDES_15_IN_CONE;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        }
    }

    //    public void limitSwitch(){
//        //true means pressed(Slides are at home), false means it isn't pressed(Slides are up)
//        if (limitSwitch.getState()) {// This means the state = true
//            linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            linearSlides.setTargetPosition(equate(2));
//        }
//    }
    public void init() {
        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setGripperPosition(1.0);
        grip();
        setRiseLevel(0);
        lift();
    }


}