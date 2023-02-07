package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;

@TeleOp(name = "Tele-op(1.5)", group = "Tele-Op")
public class Teleop1_5 extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private FieldCenterAuto1_5 fieldCenterAuto;
    private ConeTransporter1_5 coneTransporter;
    // Check if B is pressed
    private boolean b_Press = false;
    private boolean stackState = false;

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
                boolean rotationToggle;
                boolean strafeToggle;
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
                    rotationToggle = controller.rightTrigger >= 0.2f;
                    strafeToggle = controller.rightTrigger >= 0.2f;
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
                    coneTransporter.setRiseLevel(3);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.a) {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.x) {
                    coneTransporter.setRiseLevel(2);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                }

                //This will check if b is pressed if yes then it will check the position of the slides and decide where it should go
                if (controller.b & !b_Press) {
                    b_Press = true;
                    if (coneTransporter.linearSlides.getTargetPosition() == coneTransporter.equate(100)) {
                        coneTransporter.setRiseLevel(-1);
                    } else {
                        coneTransporter.setRiseLevel(0);
                    }
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else {
                    b_Press = false;
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
                    if (controller.dpadDown) {
                        stackState = true;
                        coneTransporter.moveDown();
                    } else if (controller.dpadUp) {
                        stackState = true;
                        coneTransporter.moveUp();
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
                }
                float roll = fieldCenterAuto.getRoll();
                if (roll >= -20) {
                    tip = TIP.TIPPING;
                    fieldCenterAuto.checkifrobotnottipping();
                } else if (roll <= -60) {
                    tip = TIP.TIPPING;
                    fieldCenterAuto.checkifrobotnottipping();
                } else{
                    tip = TIP.NOT_TIPPING;
                }



                telemetry.addData("Linear slides speed", coneTransporter.linearSlidesSpeed);
                telemetry.addData("strafeFactor", fieldCenterAuto.STRAFE_TOGGLE_FACTOR);
                telemetry.addData("rotFactor", fieldCenterAuto.ROTATION_TOGGLE_FACTOR);
                telemetry.addData(" ", " ");
//                telemetry.addData("limit Switch", coneTransporter.limitSwitch.getState());
                telemetry.addData("Linear Slides Pos.", coneTransporter.linearSlides.getCurrentPosition());
                telemetry.addData("Linear Slides Pos. Current var ", coneTransporter.LINEAR_SLIDES_CURRENT);
                telemetry.addData("ROBOT TIP STATE: ", tip);
                //telemetry.addData("stackLevel", coneTransporter.telemetryLevel.get(coneTransporter.telemetryListIndex));


            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
