package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.Mechanism;

public class LinearSlidesMechanism extends Mechanism{
/*
Rotations needed for each level:
     * Low:3.52370014617
     * Medium:6.13384840259
     * High:8.74399665901
     * InCone:-0.52202965128
     *
*/



    private DcMotor linearSlides;

    public float diameterOfSpool = 34f;
    public float linearSlidesSpeed = 0.75f;
    public double LINEAR_SLIDES_LOW = 347.5;// 13.5 inches converted to mm(low junction)
    public double LINEAR_SLIDES_MEDIUM = 620.6;// 23.5 inches converted to mm(medium junction)
    public double LINEAR_SLIDES_HIGH = 875.6;// 33.5 inches converted to mm(high junction)
    public double LINEAR_SLIDES_NORM = 0;
    public double LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public double ticks;
    public double ticksPerRotation = 384.5;// 435 RPM motor 5202 GoBilda TPR
    public int ticksAsInt;
    public int currentTick;

    public LinearSlidesMechanism(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        linearSlides = this.hardwareMap.get(DcMotor.class, "linearSlides");
        linearSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlides.setDirection(DcMotor.Direction.REVERSE);
    }

    public int equate(double height) {
        ticks = ticksPerRotation * (height / (diameterOfSpool * Math.PI) );
        ticksAsInt = (int)ticks;
        return ticksAsInt;
    }

    public void run(Controller controller) {
        if (controller.y) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_HIGH;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (controller.x) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_MEDIUM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (controller.a) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_LOW;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (controller.b) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        }
        currentTick = linearSlides.getCurrentPosition();
        telemetry.addData(">", "LinearSlidesCurrent: " + LINEAR_SLIDES_CURRENT);
        telemetry.addData(">", "LinearSlidesCurrentTick: " + currentTick);
        telemetry.addData(">", "Tick: " + ticksAsInt);
        telemetry.addData(">", "Power: " + linearSlidesSpeed);
    }



}
