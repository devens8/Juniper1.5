package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Mathematics;

public class FieldCenterAuto1_5 extends Drivetrain1_5 {

    public double STRAFE_TOGGLE_FACTOR = 0.5;
    public double ROTATION_TOGGLE_FACTOR = 0.5;
    public double imuMeasure;
    public double leftBackPower;
    public double rightBackPower;
    public double rightFrontPower;
    public double leftFrontPower;
    public double rotationEffectivness = 0.7;
    public double xyEffectivness = 0.9;
    public float globalRollAngle;
    Orientation lastAngle = new Orientation();

    public FieldCenterAuto1_5(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    public void checkifrobotnottipping() {
        if (globalRollAngle <= 15) {
            //Here it checks if the tip angle exceeds 8 degrees
            rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
            leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
            leftFrontMotor.setPower(1.0);
            rightFrontMotor.setPower(1.0);
        } else if (globalRollAngle >= 40) {
            rightBackMotor.setDirection(DcMotor.Direction.REVERSE);
            leftBackMotor.setDirection(DcMotor.Direction.FORWARD);
            rightBackMotor.setPower(1.0);
            leftBackMotor.setPower(1.0);
        } else {
            rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
            rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
            leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
            leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        }
    }



    public float getRoll() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        float deltaRollAngle = angles.thirdAngle - lastAngle.thirdAngle;//This is subtracting roll angle
        // It's going to record angles between -180 and 180
        globalRollAngle += deltaRollAngle;
        lastAngle = angles;
        return globalRollAngle;
    }

    @Override
    public void drive(double gamepadX, double gamepadY, double gamepadRot, boolean rotationToggle, boolean strafeToggle) {

        if (rotationToggle) {
            gamepadRot *= ROTATION_TOGGLE_FACTOR;
        }
        if (strafeToggle) {
            gamepadX *= STRAFE_TOGGLE_FACTOR;
            gamepadY *= STRAFE_TOGGLE_FACTOR;
        }


        // gamepadRot is negated because in math, a counterclockwise rotation is positive
        // (think unit circle), but on the controller, we expect the robot to rotate clockwise when
        // we push the stick to the right. Pushing the stick to the right outputs a positive value.
        double turn = -gamepadRot * rotationEffectivness;
        double controllerX = gamepadX * xyEffectivness;
        double controllerY = gamepadY * xyEffectivness;
        double[] controllerVector = {controllerX, controllerY};
//        telemetry.addData("controllerVector[0]: ", controllerVector[0]);
//        telemetry.addData("controllerVector[1]: ", controllerVector[1]);

        imuMeasure = readFromIMU();

        double[] rotatedVector = Mathematics.rotate(controllerVector, imuMeasure);
        double rotatedX = rotatedVector[0];
        double rotatedY = rotatedVector[1];
//        telemetry.addData("rotatedX: ", rotatedX);
//        telemetry.addData("rotatedY: ", rotatedY);


        double theta = Math.atan2(rotatedY, rotatedX);
//        telemetry.addData("theta: ", theta);
        double power = Math.hypot(rotatedX, rotatedY);
//        telemetry.addData("power: ", power);
        double sin = Math.sin(theta - Math.PI / 4);
//        telemetry.addData("sin: ", sin);
        double cos = Math.cos(theta - Math.PI / 4);
//        telemetry.addData("cos: ", cos);
        double max = Math.max(Math.abs(sin), Math.abs(cos));
//        telemetry.addData("max: ", max);

        leftBackPower = power * sin / max + turn;
        leftFrontPower = power * cos / max + turn;
        rightBackPower = power * cos / max - turn;
        rightFrontPower = power * sin / max - turn;


        if ((power + Math.abs(turn)) > 1) {
            leftFrontPower /= power + Math.abs(turn);
            rightFrontPower /= power + Math.abs(turn);
            leftBackPower /= power + Math.abs(turn);
            rightBackPower /= power + Math.abs(turn);
        }


        leftBackMotor.setPower(leftBackPower);
        leftFrontMotor.setPower(leftFrontPower);
        rightBackMotor.setPower(rightBackPower);
        rightFrontMotor.setPower(rightFrontPower);
    }
}