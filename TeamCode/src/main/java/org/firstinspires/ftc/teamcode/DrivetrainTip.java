package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class DrivetrainTip extends Mechanism {

    protected DcMotor leftBackMotor;
    protected DcMotor leftFrontMotor;
    protected DcMotor rightBackMotor;
    protected DcMotor rightFrontMotor;

    public BNO055IMU imu;




    public DrivetrainTip(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        leftBackMotor = this.hardwareMap.get(DcMotor.class, "leftBackMotor");
        leftFrontMotor = this.hardwareMap.get(DcMotor.class,"leftFrontMotor");
        rightBackMotor = this.hardwareMap.get(DcMotor.class,"rightBackMotor");
        rightFrontMotor = this.hardwareMap.get(DcMotor.class,"rightFrontMotor");

        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
    }


    public DcMotor getLeftBackMotor() { return leftBackMotor; }
    public DcMotor getLeftFrontMotor() { return leftFrontMotor; }
    public DcMotor getRightBackMotor() { return rightBackMotor; }
    public DcMotor getRightFrontMotor() { return rightFrontMotor; }


    public abstract void drive(double gamepadX, double gamepadY, double gamepadRot, boolean rotationToggle, boolean strafeToggle);

}