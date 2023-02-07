package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;

import java.util.ArrayList;

//TODO: Add fieldcenterauto for 1.5
public class ConeTransporter1_5 extends Mechanism {
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
    public double LINEAR_SLIDES_LOW = 367.5 - V15;// 13.5 inches converted to mm(low junction)
    public double LINEAR_SLIDES_MEDIUM = 627.5 - V15;// 23.5 inches converted to mm(medium junction)
    public double LINEAR_SLIDES_HIGH = 857.5 - V15;// 33.5 inches converted to mm(high junction) 2349
    public double LINEAR_SLIDES_NORM = 100 - V15;
    public double LINEAR_SLIDES_IN_CONE = 0 - V15;
    public double LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public double ticks;
    //Autonomous
    private final double inFactor_MM = 125;
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
    public float linearSlidesSpeed = 1f;
    public int level;
    public ArrayList<Double> stackLevel = new ArrayList<Double>();
    public ArrayList<String> telemetryLevel = new ArrayList<String>();
    public int arrayListIndex = -1;
    public int telemetryListIndex = -1;

    //LIMIT SWITCH_________________________________________________________________________________
//    public DigitalChannel limitSwitch;
//    public TouchSensor touchSensor;

    public ConeTransporter1_5(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        linearSlides = this.hardwareMap.get(DcMotor.class, "linearSlides");
        linearSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gripper = this.hardwareMap.get(Servo.class, "gripper");
//        limitSwitch = this.hardwareMap.get(DigitalChannel.class, "limit switch");
//        touchSensor = this.hardwareMap.get(TouchSensor.class, "touchSensor");
    }
    public void setArrayList() {
        stackLevel.add(AUTO_LINEAR_SLIDES_15);
        stackLevel.add(AUTO_LINEAR_SLIDES_15_IN_CONE);
        stackLevel.add(AUTO_LINEAR_SLIDES_14);
        stackLevel.add(AUTO_LINEAR_SLIDES_14_IN_CONE);
        stackLevel.add(AUTO_LINEAR_SLIDES_13);
        stackLevel.add(AUTO_LINEAR_SLIDES_13_IN_CONE);
        stackLevel.add(AUTO_LINEAR_SLIDES_12);
        stackLevel.add(AUTO_LINEAR_SLIDES_12_IN_CONE);

        telemetryLevel.add("AUTO_LINEAR_SLIDES_15");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_15_IN_CONE");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_14");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_14_IN_CONE");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_13");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_13_IN_CONE");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_12");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_12_IN_CONE");
//        stackLevel.add("AUTO_LINEAR_SLIDES_11");
//        stackLevel.add("AUTO_LINEAR_SLIDES_11_IN_CONE");


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
        if (arrayListIndex <= stackLevel.size()) {
            arrayListIndex++;
            telemetryListIndex++;
            setHeight(arrayListIndex);
        }
    }


    public void moveUp() {
        if (arrayListIndex >= 0) {
            if (arrayListIndex % 2 == 0) {
                arrayListIndex = Math.max(arrayListIndex - 2, 0);
                telemetryListIndex = Math.max(telemetryListIndex - 2, 0);
            } else {
                telemetryListIndex -= 1;
                arrayListIndex-=1;
            }
            setHeight(arrayListIndex);
        }
    }
    public void setHeight(int index) {
        double linearSlides_MM = stackLevel.get(index);
        linearSlides.setTargetPosition(equate(linearSlides_MM));
        linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlides.setPower(linearSlidesSpeed);
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
        setArrayList();
        setGripperPosition(1.0);
        grip();
        setRiseLevel(0);
        lift();
    }


}