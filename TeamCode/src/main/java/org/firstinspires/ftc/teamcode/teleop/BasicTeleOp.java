package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "BasicTeleOp", group = "TeleOp")
public class BasicTeleOp extends OpMode {

    public static String CLASS_NAME = "AutoDemonstration";

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DistanceSensor distanceSensor;

    @Override
    public void init() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        distanceSensor = hardwareMap.get(DistanceSensor.class, "sensorRange");

        addAndUpdate(CLASS_NAME + " : finished init");

        moveForwardTime( 5000 );

    }

    /**
     *
     * @return the distance the robot has travelled in inches
     */
    public int howFarHaveWeMoved( ) {

        return frontLeft.getCurrentPosition() / 4 * 12;
    }

    /**
     *
     * @param timeToMove the amount of time to move the robot forward in milliseconds
     */
    public void moveForwardTime( long timeToMove ) {

        // set motor power to something
        frontLeft.setPower( 1 );
        frontRight.setPower( 1 );
        backRight.setPower( 1 );
        backLeft.setPower( 1 );

        // move specified time
        long startTime = System.currentTimeMillis( );
        while( startTime + timeToMove > System.currentTimeMillis( ) );

        // stop
        frontLeft.setPower( 0 );
        frontRight.setPower( 0 );
        backRight.setPower( 0 );
        backLeft.setPower( 0 );

    }


    @Override
    public void loop() {

        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        double frontLeftPower = drive + strafe - rotate;
        double frontRightPower = drive - strafe - rotate;
        double backLeftPower = drive - strafe + rotate;
        double backRightPower = drive + strafe + rotate;

        setPowers( frontLeftPower, frontRightPower, backLeftPower, backRightPower );

        telemetry.addLine( "Current distance :: " + distanceSensor.getDistance(DistanceUnit.INCH) + " inches");

        telemetry.update();
    }

    public void addAndUpdate( String text ) {
        telemetry.addLine( text );
        telemetry.update();

    }

    public void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        telemetry.addLine("FL: " + frontLeftPower + ", FR: " + frontRightPower);
        telemetry.addLine("BL: " + backLeftPower + ", BR: " + backRightPower);

    }
}
