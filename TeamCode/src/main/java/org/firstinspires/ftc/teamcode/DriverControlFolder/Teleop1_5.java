package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutonomousFolder.BlueLeft;
import org.firstinspires.ftc.teamcode.Controller;

import java.util.Objects;

@TeleOp(name = "Tele-op(1.5)", group = "Tele-Op")
public class Teleop1_5 extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private FieldCenterAuto1_5 fieldCenterAuto;
    private ConeTransporter1_5 coneTransporter;
    // Check if B is pressed
    private boolean b_Press = false;
    private boolean stackState = true;

    public enum TIP {
        TIPPING,
        NOT_TIPPING
    }

    public void runOpMode() {
        telemetry.clear();


        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            fieldCenterAuto = new FieldCenterAuto1_5(telemetry, hardwareMap);
            coneTransporter = new ConeTransporter1_5(telemetry, hardwareMap);


        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
             if(Objects.equals(exception.getMessage(), "The IMU was not initialized")){
                telemetry.addData("-", "Detected");
                telemetry.update();
            }

        }

        telemetry.update();
        int level = 16;
        int inConeLevel = 16;
        coneTransporter.init();
        TIP tip = TIP.NOT_TIPPING;
        waitForStart();
        while (opModeIsActive()) {
            try {
                controller.update();
                //FIELDCENTER_______________________________________________________________________________
                double gamepadX;
                double gamepadY;
                double gamepadRot;
                boolean rotationToggle = false;
                boolean strafeToggle = false;
                if (tip == TIP.NOT_TIPPING) {
                    if (Math.abs(controller.gamepad1X) > 0.01) {
                        gamepadX = controller.gamepad1X;
                    } else if (Math.abs(controller.gamepad2X) > 0.01) {
                        gamepadX = controller.gamepad2X;
                    } else {
                        gamepadX = 0;
                    }
                    if (Math.abs(controller.gamepad1Y) > 0.01) {
                        gamepadY = controller.gamepad1Y;
                    } else if (Math.abs(controller.gamepad2Y) > 0.01) {
                        gamepadY = controller.gamepad2Y;
                    } else {
                        gamepadY = 0;
                    }
                    if (Math.abs(controller.gamepad1Rot) > 0.01) {
                        gamepadRot = -controller.gamepad1Rot;
                    } else if (Math.abs(controller.gamepad2Rot) > 0.01) {
                        gamepadRot = -controller.gamepad2Rot;
                    } else {
                        gamepadRot = 0;
                    }
//                    rotationToggle = controller.rightTrigger >= 0.2f;
//                    strafeToggle = controller.rightTrigger >= 0.2f;
                    fieldCenterAuto.drive(gamepadX, gamepadY, gamepadRot, rotationToggle, strafeToggle);
                    telemetry.addData("gamepadX: ", gamepadX);
                    telemetry.addData("gamepadY: ", gamepadY);
                    telemetry.addData("gamepadRot: ", gamepadRot);
                    telemetry.addData("imuMeasure: ", fieldCenterAuto.imuMeasure);
                    telemetry.addData("leftBackPower: ", fieldCenterAuto.leftBackPower);
                    telemetry.addData("leftFrontPower: ", fieldCenterAuto.leftFrontPower);
                    telemetry.addData("rightBackPower: ", fieldCenterAuto.rightBackPower);
                    telemetry.addData("rightFrontPower: ", fieldCenterAuto.rightFrontPower);


                }

                //CONETRANSPORTER___________________________________________________________________________
                if (controller.y) {
                    coneTransporter.reset();
                    stackState = false;
                    coneTransporter.setRiseLevel(3);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.a){
                    coneTransporter.reset();
                    stackState = false;
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.x) {
                    coneTransporter.reset();
                    stackState = false;
                    coneTransporter.setRiseLevel(2);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                }

                //This will check if b is pressed if yes then it will check the position of the slides and decide where it should go
                if (controller.b & !b_Press) {
                    b_Press = true;
                    coneTransporter.reset();
                    if (coneTransporter.linearSlides.getTargetPosition() == coneTransporter.equate(100)) {
                        coneTransporter.setRiseLevel(-1);
                    } else {
                        coneTransporter.setRiseLevel(0);
                    }
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else {
                    b_Press = false;
                }
//                }
//                if (controller.dpadDown && level > 12) {
//                    level--;
//                    coneTransporter.setRiseLevel(level);
//                    coneTransporter.lift();
//                } else if (controller.dpadUp && level < 15) {
//                    level++;
//                    coneTransporter.setRiseLevel(level);
//                    coneTransporter.lift();
//                }
//                if (controller.dpadRight && inConeLevel > 12) {
//                    inConeLevel--;
//                    coneTransporter.setPosLevel(inConeLevel);
//                    coneTransporter.down();
//                } else if (controller.dpadLeft && inConeLevel < 15) {
//                    inConeLevel++;
//                    coneTransporter.setPosLevel(inConeLevel);
//                    coneTransporter.down();
//                }
                    if (controller.leftTrigger) {
                        if(!stackState && coneTransporter.arrayListIndex > 0){
                            coneTransporter.setHeight(coneTransporter.arrayListIndex);
                        }else{
                            coneTransporter.moveDown();
                        }
                        stackState = true;
                    } else if (controller.rightTrigger) {
                        if(!stackState && coneTransporter.arrayListIndex > 0){
                            coneTransporter.setHeight(coneTransporter.arrayListIndex);
                        }else{
                            coneTransporter.moveUp();
                        }
                        stackState = true;
                    }



                    //GRIPPER__________________________________________________________________________________

                    if (controller.leftBumper && !(controller.rightBumper)) {
                        coneTransporter.setGripperPosition(.75);
                        coneTransporter.grip();
                    }

                    if (controller.rightBumper && !(controller.leftBumper)) {
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.grip();
                    }
                    //Program for touch sensor
//                if(coneTransporter.touchSensor.isPressed() && coneTransporter.riseLevel == -1){
//                    coneTransporter.setGripperPosition(.75);
//                    coneTransporter.grip();
//               }
                    telemetry.addData("-", "tip is activated");

                float roll = fieldCenterAuto.getRoll();
                if (roll <= 10) {
                    tip = TIP.TIPPING;
                    fieldCenterAuto.checkifrobotnottipping();
                } else if (roll >= 45) {
                    tip = TIP.TIPPING;
                    fieldCenterAuto.checkifrobotnottipping();
                } else {
                    tip = TIP.NOT_TIPPING;
                    fieldCenterAuto.rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
                    fieldCenterAuto.rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
                    fieldCenterAuto.leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
                    fieldCenterAuto.leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
                }


                telemetry.addData("Linear slides speed", coneTransporter.linearSlidesSpeed);
                telemetry.addData("strafeFactor", fieldCenterAuto.STRAFE_TOGGLE_FACTOR);
                telemetry.addData("rotFactor", fieldCenterAuto.ROTATION_TOGGLE_FACTOR);
                telemetry.addData(" ", " ");
//                telemetry.addData("limit Switch", coneTransporter.limitSwitch.getState());
                telemetry.addData("Linear Slides Pos.", coneTransporter.linearSlides.getCurrentPosition());
                telemetry.addData("Linear Slides Pos. Current var ", coneTransporter.LINEAR_SLIDES_CURRENT);
                telemetry.addData("ROBOT TIP STATE: ", tip);
                telemetry.addData("stackLevel", coneTransporter.arrayListIndex);
                telemetry.addLine()
                    .addData("tip angle", roll);



            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
